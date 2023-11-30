package ru.unrealeugene.parsing.generating;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import ru.unrealeugene.parsing.graphics.GraphViz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LALRGenerator implements ParserGenerator {
    private final List<Production> productions;
    private final Map<Production, Integer> productionIndex;
    private final Set<Production.Symbol> symbols;
    private final Map<String, List<Production>> productionByLhs;
    private final Node root;
    private final List<Node> nodes;
    private final List<NodeCore> nodeCores;
    private final Map<Node, Integer> nodeIndex;
    private final Map<NodeCore, Integer> nodeCoreIndex;
    private final Map<Node, NodeCore> nodeToNodeCore;
    private final Map<String, Set<Production.Symbol>> firstSet;
    private final String grammarName;

    private static final Path RESOURCES_DIR_PATH = Path.of("src/main/resources");
    private static final Configuration config = new Configuration(Configuration.VERSION_2_3_31);
    private static final String TOKEN_TEMPLATE_FILE = "LALRToken.java.ftl";
    private static final String LEXER_TEMPLATE_FILE = "LALRLexer.java.ftl";
    private static final String PARSER_TEMPLATE_FILE = "LALRParser.java.ftl";
    private static final String TOKEN_SUFFIX = "Token";
    private static final String LEXER_SUFFIX = "Lexer";
    private static final String PARSER_SUFFIX = "Parser";
    private final Map<String, Object> defaultView;

    static {
        config.setDefaultEncoding(StandardCharsets.UTF_8.name());
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setLogTemplateExceptions(false);
        config.setWrapUncheckedExceptions(true);
        config.setFallbackOnNullLoopVariable(false);
        try {
            config.setDirectoryForTemplateLoading(RESOURCES_DIR_PATH.toFile());
        } catch (IOException e) {
            throw new UncheckedIOException("Can't set default template load directory: " + e.getMessage(), e);
        }
    }

    public LALRGenerator(String grammarName, List<Production> productions) {
        this.productions = productions;
        this.productionIndex = IntStream.iterate(0, i -> i + 1)
                .limit(productions.size())
                .mapToObj(i -> new ImmutablePair<>(productions.get(i), i))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        this.symbols = new LinkedHashSet<>();
        for (Production production : productions) {
            for (Production.Symbol symbol : production.getRhs()) {
                if (symbol.getType() == Production.Symbol.Type.TERMINAL
                        || symbol.getType() == Production.Symbol.Type.NON_TERMINAL) {
                    this.symbols.add(symbol);
                }
            }
        }
        this.productionByLhs = productions.stream().collect(Collectors.groupingBy(Production::getLhs));
        this.root = new Node(Set.of(new LRItem(productions.get(0), 0, Set.of(Production.Symbol.EOF))));
        this.nodes = new ArrayList<>(List.of(root));
        this.nodeCores = new ArrayList<>();
        this.nodeIndex = new HashMap<>(Map.of(root, 0));
        this.nodeCoreIndex = new HashMap<>();
        this.nodeToNodeCore = new LinkedHashMap<>();
        this.firstSet = new HashMap<>();
        this.grammarName = grammarName;
        this.defaultView = new HashMap<>();
    }

    @Override
    public void generate(String aPackage,
                         List<Production.Symbol> skippedSymbols,
                         Path output,
                         Path outputClassPath,
                         Path outputImagePath) throws IOException {
        buildFirst();
        buildClosureFor(this.root);
        generateAutomaton();
        if (outputImagePath != null) {
            drawGraph(Files.createDirectories(outputImagePath));
        }
        Path packageDirectory = createDirectories(outputClassPath, aPackage);
        initializeFreemarker(aPackage);
        generateTokenSource(packageDirectory);
        generateLexerSource(packageDirectory, skippedSymbols);
        generateParserSource(packageDirectory);
    }

    private void initializeFreemarker(String aPackage) {
        String capitalizedGrammarName = StringUtils.capitalize(grammarName);
        defaultView.put("PACKAGE_NAME", aPackage);
        defaultView.put("GRAMMAR_NAME", grammarName);
        defaultView.put("TOKEN_CLASS_NAME", capitalizedGrammarName + TOKEN_SUFFIX);
        defaultView.put("LEXER_CLASS_NAME", capitalizedGrammarName + LEXER_SUFFIX);
        defaultView.put("PARSER_CLASS_NAME", capitalizedGrammarName + PARSER_SUFFIX);
        defaultView.put("GRAMMAR_TERMINALS", symbols.stream()
                .filter(s -> s.getType() == Production.Symbol.Type.TERMINAL)
                .collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    private Path createDirectories(Path output, String aPackage) throws IOException {
        Path packageDirectory = output.resolve(aPackage.replace('.', File.separatorChar).trim());
        Files.createDirectories(packageDirectory);
        return packageDirectory;
    }

    private Path createFileIfDoesNotExist(Path lexerPath) throws IOException {
        if (lexerPath.toFile().exists() && !lexerPath.toFile().isFile()) {
            throw new IOException("Can't overwrite Lexer which is not file");
        }
        if (!lexerPath.toFile().exists()) {
            Files.createFile(lexerPath);
        }
        return lexerPath;
    }

    private void generateTokenSource(Path directory) throws IOException {
        String tokenClassName = (String) defaultView.get("TOKEN_CLASS_NAME");
        Path tokenPath = createFileIfDoesNotExist(directory.resolve(tokenClassName + ".java"));

        Template template = config.getTemplate(TOKEN_TEMPLATE_FILE);
        try (BufferedWriter writer = Files.newBufferedWriter(tokenPath, StandardCharsets.UTF_8)) {
            template.process(defaultView, writer);
        } catch (TemplateException e) {
            throw new RuntimeException("Can't process Token template: " + e.getMessage());
        }
    }

    private void generateLexerSource(Path directory, List<Production.Symbol> skippedSymbols) throws IOException {
        String lexerClassName = (String) defaultView.get("LEXER_CLASS_NAME");
        Path lexerPath = createFileIfDoesNotExist(directory.resolve(lexerClassName + ".java"));

        HashMap<String, Object> view = new HashMap<>(defaultView);
        view.put("SKIPPED_TERMINALS", skippedSymbols);

        Template template = config.getTemplate(LEXER_TEMPLATE_FILE);
        try (BufferedWriter writer = Files.newBufferedWriter(lexerPath, StandardCharsets.UTF_8)) {
            template.process(view, writer);
        } catch (TemplateException e) {
            throw new RuntimeException("Can't process Lexer template: " + e.getMessage());
        }
    }

    private void generateParserSource(Path directory) throws IOException {
        String parserClassName = (String) defaultView.get("PARSER_CLASS_NAME");
        Path parserPath = createFileIfDoesNotExist(directory.resolve(parserClassName + ".java"));

        Map<String, Object> view = new HashMap<>(defaultView);

        int[][] parsingTable = new int[nodeCores.size()][symbols.size() + 1];

        List<Production.Symbol> terminals = symbols.stream()
                .filter(s -> s.getType() == Production.Symbol.Type.TERMINAL)
                .collect(Collectors.toCollection(ArrayList::new));
        terminals.add(Production.Symbol.EOF);

        List<Production.Symbol> nonTerminals = symbols.stream()
                .filter(s -> s.getType() == Production.Symbol.Type.NON_TERMINAL
                        && !s.getValue().equals(productions.get(0).getLhs()))
                .collect(Collectors.toList());

        Map<NodeCore, List<Node>> nodeCoreToNodes = nodeToNodeCore.entrySet()
                .stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        LinkedHashMap::new,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList()))
                );

        int i = 0;
        for (List<Node> nodeClass : nodeCoreToNodes.values()) {
            for (Node node : nodeClass) {
                List<Production.Symbol> symbols = Stream.of(terminals, nonTerminals)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
                for (int j = 0; j < symbols.size(); j++) {
                    Production.Symbol symbol = symbols.get(j);
                    Integer otherNodeIndex = node.getTransitions().get(symbol);
                    parsingTable[i][j] = otherNodeIndex == null
                            ? Integer.MAX_VALUE
                            : nodeCoreIndex.get(nodeToNodeCore.get(nodes.get(otherNodeIndex)));
                }
            }
            for (Node node : nodeClass) {
                for (int j = 0; j < terminals.size(); j++) {
                    Production.Symbol terminal = terminals.get(j);
                    if (parsingTable[i][j] == Integer.MAX_VALUE) {
                        for (LRItem item : node.getItems().keySet()) {
                            if (item.isTerminal() && item.getLookAhead().contains(terminal)) {
                                parsingTable[i][j] = -productionIndex.get(item.getProduction());
                                break;
                            }
                        }
                    }
                }
            }
            i++;
        }
        view.put("PARSING_TABLE", parsingTable);

        view.put("REDUCE_AMOUNT", productions.stream()
                .map(Production::getRhs)
                .map(List::size)
                .collect(Collectors.toList()));

        List<Integer> nonTerminalByProduction = new ArrayList<>();
        Set<String> usedProductionLhs = new HashSet<>();
        for (Production production : productions) {
            String lhs = production.getLhs();
            if (usedProductionLhs.contains(lhs)) {
                continue;
            }
            for (int j = 0; j < productionByLhs.get(lhs).size(); j++) {
                nonTerminalByProduction.add(nonTerminals.indexOf(Production.Symbol.newSymbol(lhs)) + 1);
            }
            usedProductionLhs.add(lhs);
        }
        view.put("NON_TERMINAL_BY_PRODUCTION", nonTerminalByProduction);
        view.put("PRODUCTIONS", productions);
        view.put("PRODUCTION_LHS", productionByLhs.keySet());

        Template template = config.getTemplate(PARSER_TEMPLATE_FILE);
        try (BufferedWriter writer = Files.newBufferedWriter(parserPath, StandardCharsets.UTF_8)) {
            template.process(view, writer);
        } catch (TemplateException e) {
            throw new RuntimeException("Can't process Parser template: " + e.getMessage());
        }
    }

    private void generateAutomaton() {
        Queue<Node> queue = new ArrayDeque<>();
        HashSet<Node> used = new HashSet<>();

        queue.add(root);
        used.add(root);

        while (!queue.isEmpty()) {
            Node curNode = queue.poll();
            NodeCore curNodeCore = new NodeCore(curNode);

            if (!nodeCoreIndex.containsKey(curNodeCore)) {
                nodeCoreIndex.put(curNodeCore, nodeCores.size());
                nodeCores.add(curNodeCore);
            }
            nodeToNodeCore.put(curNode, curNodeCore);

            for (Production.Symbol symbol : symbols) {
                Node shiftedNode = getShifted(curNode, symbol);
                if (shiftedNode.getItems().size() > 0) {
                    buildClosureFor(shiftedNode);
                    if (!nodeIndex.containsKey(shiftedNode)) {
                        nodeIndex.put(shiftedNode, nodes.size());
                        nodes.add(shiftedNode);
                    }
                    curNode.getTransitions().put(symbol, nodeIndex.get(shiftedNode));
                    if (!used.contains(shiftedNode)) {
                        queue.add(shiftedNode);
                        used.add(shiftedNode);
                    }
                }
            }
        }
    }

    private Node getShifted(Node node, Production.Symbol symbol) {
        Node newNode = new Node(Collections.emptySet());
        for (LRItem item : node.getItems().keySet()) {
            List<Production.Symbol> rhs = item.getProduction().getRhs();
            if (!item.isTerminal() && rhs.get(item.getPosition()).equals(symbol)) {
                LRItem newItem = new LRItem(item.getProduction(), item.getPosition() + 1, item.getLookAhead());
                newNode.getItems().put(newItem, newItem);
            }
        }
        return newNode;
    }

    private Set<Production.Symbol> getFirst(List<Production.Symbol> rhs) {
        Set<Production.Symbol> first = new HashSet<>();
        for (Production.Symbol symbol : rhs) {
            if (symbol.getType() == Production.Symbol.Type.TERMINAL) {
                first.add(symbol);
                break;
            } else if (symbol.getType() == Production.Symbol.Type.NON_TERMINAL) {
                Set<Production.Symbol> otherFirst = firstSet.get(symbol.getValue());
                first.addAll(otherFirst);
                if (!otherFirst.contains(Production.Symbol.EPSILON)) {
                    break;
                }
            } else { // EPSILON
                return new HashSet<>(Set.of(Production.Symbol.EPSILON));
            }
        }
        return first;
    }

    private void buildFirst() {
        for (String nonTerm : productionByLhs.keySet()) {
            firstSet.put(nonTerm, new HashSet<>());
        }

        boolean changed = true;
        while (changed) {
            changed = false;
            for (Production production : productions) {
                Set<Production.Symbol> firstLhs = firstSet.get(production.getLhs());
                Set<Production.Symbol> firstRhs = getFirst(production.getRhs());
                if (firstLhs.addAll(firstRhs)) {
                    changed = true;
                }
            }
        }
    }

    private String escapeBackSlashes(String input) {
        return input.replaceAll("\\\\", "\\\\\\\\");
    }

    private void drawGraph(Path output) {
        GraphViz gv = new GraphViz();

        gv.addLine(gv.startGraph());
        String grammarDesc = productions.stream().map(Objects::toString).collect(Collectors.joining("\n"));
        gv.addLine("label = \"LALR(1) Parsing Automaton\n" + escapeBackSlashes(grammarDesc) + "\"");
        gv.addLine("rankdir = LR");
        gv.addLine("node [shape = box, style = rounded]");
        traverseAutomaton(root, new HashSet<>(), gv);
        gv.addLine(gv.endGraph());

        String type = "png";
        File outFile = output.resolve(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + "." + type).toFile();
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), outFile);
    }

    private void traverseAutomaton(Node node, Set<Node> used, GraphViz gv) {
        boolean isTerminal = node.getItems().keySet().stream().anyMatch(LRItem::isTerminal);
        gv.addLine(String.format("%d [label = \"%s\", xlabel = \"%s\", color = %s]",
                nodeIndex.get(node),
                escapeBackSlashes(node.toString()),
                nodeIndex.get(node),
                isTerminal ? "red" : "black"));
        used.add(node);
        for (Map.Entry<Production.Symbol, Integer> entry : node.getTransitions().entrySet()) {
            Production.Symbol symbol = entry.getKey();
            Node otherNode = nodes.get(entry.getValue());
            gv.addLine(String.format("%d -> %d [label = \"%s\"]",
                    nodeIndex.get(node),
                    nodeIndex.get(otherNode),
                    escapeBackSlashes(symbol.toString())));
            if (!used.contains(otherNode)) {
                traverseAutomaton(otherNode, used, gv);
            }
        }
    }

    private void buildClosureFor(Node node) {
        Queue<LRItem> queue = new ArrayDeque<>(node.getItems().keySet());
        while (!queue.isEmpty()) {
            LRItem item = queue.poll();
            List<Production.Symbol> rhs = item.getProduction().getRhs();
            if (item.isTerminal()) {
                continue;
            }
            Production.Symbol symbol = rhs.get(item.getPosition());
            if (symbol.getType() == Production.Symbol.Type.NON_TERMINAL) {
                for (Production production : productionByLhs.get(symbol.getValue())) {
                    Set<Production.Symbol> newLookAhead = getFirst(rhs.subList(item.getPosition() + 1, rhs.size()));
                    if (newLookAhead.isEmpty() || newLookAhead.contains(Production.Symbol.EPSILON)) {
                        newLookAhead.addAll(item.getLookAhead());
                    }
                    LRItem newItem = new LRItem(production, 0, newLookAhead);
                    if (!node.getItems().containsKey(newItem)) {
                        node.getItems().put(newItem, newItem);
                        queue.add(newItem);
                    } else {
                        node.getItems()
                                .get(newItem)
                                .getLookAhead()
                                .addAll(newLookAhead);
                    }
                }
            }
        }

        Map<LRItemCore, LRItemCore> itemCores = new LinkedHashMap<>();
        for (LRItem item : node.getItems().keySet()) {
            LRItemCore lrItemCore = new LRItemCore(item);
            if (itemCores.containsKey(lrItemCore)) {
                itemCores.get(lrItemCore)
                        .getInner()
                        .getLookAhead()
                        .addAll(lrItemCore.getInner().getLookAhead());
            } else {
                itemCores.put(lrItemCore, lrItemCore);
            }
        }
        node.getItems().clear();

        Map<LRItem, LRItem> newItems = new LinkedHashMap<>();
        for (Map.Entry<LRItemCore, LRItemCore> x : itemCores.entrySet()) {
            newItems.put(x.getKey().getInner(), x.getValue().getInner());
        }
        node.getItems().putAll(newItems);
    }

    private static class Node {
        private final Map<LRItem, LRItem> items;
        private final Map<Production.Symbol, Integer> transitions;

        public Node(Set<LRItem> items, Map<Production.Symbol, Integer> transitions) {
            this.items = new LinkedHashMap<>(items.stream()
                    .collect(Collectors.toMap(Function.identity(), Function.identity())));
            this.transitions = new HashMap<>(transitions);
        }

        public Node(Set<LRItem> items) {
            this(items, Collections.emptyMap());
        }

        public Map<LRItem, LRItem> getItems() {
            return items;
        }

        public Map<Production.Symbol, Integer> getTransitions() {
            return transitions;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node node = (Node) o;
            return Objects.equals(items, node.items);
        }

        @Override
        public int hashCode() {
            return Objects.hash(items);
        }

        @Override
        public String toString() {
            return items.keySet()
                    .stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining("\n"));
        }
    }

    private static class LRItem {
        private static final String POSITION_POINTER = "▪";

        private final Production production;
        private final int position;
        private final Set<Production.Symbol> lookAhead; // terminals


        private LRItem(Production production, int position, Set<Production.Symbol> lookAhead) {
            this.production = production;
            this.position = position;
            this.lookAhead = new HashSet<>(lookAhead);
        }

        public Production getProduction() {
            return production;
        }

        public int getPosition() {
            return position;
        }

        public Set<Production.Symbol> getLookAhead() {
            return lookAhead;
        }

        public boolean isTerminal() {
            return position == production.getRhs().size();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof LRItem)) return false;
            LRItem item = (LRItem) o;
            return position == item.position && production.equals(item.production) && lookAhead.equals(item.lookAhead);
        }

        @Override
        public int hashCode() {
            return Objects.hash(production, position, lookAhead);
        }

        @Override
        public String toString() {
            List<String> rhs = new ArrayList<>();
            if (production.getRhs().isEmpty()) {
                rhs.add(Production.Symbol.EPSILON.toString());
            } else {
                int i = 0;
                while (true) {
                    if (i == position) {
                        rhs.add(POSITION_POINTER);
                    }
                    if (i >= production.getRhs().size()) {
                        break;
                    }
                    Production.Symbol symbol = production.getRhs().get(i);
                    if (symbol.getType() != Production.Symbol.Type.EPSILON) {
                        rhs.add(symbol.toString());
                    }
                    i++;
                }
            }
            return String.format(
                    "%s -> %s ║ %s",
                    production.getLhs(),
                    String.join(" ", rhs),
                    lookAhead.stream()
                            .map(Objects::toString)
                            .collect(Collectors.joining(" "))
            );
        }
    }

    private static class NodeCore {
        private final Map<LRItemCore, LRItemCore> items;

        public NodeCore(Node node) {
            Map<LRItemCore, LRItemCore> map = new HashMap<>();
            for (LRItem item : node.getItems().keySet()) {
                LRItemCore lrItemCore = new LRItemCore(item);
                if (!map.containsKey(lrItemCore)) {
                    map.put(lrItemCore, lrItemCore);
                }
            }
            this.items = map;
        }

        public Map<LRItemCore, LRItemCore> getItems() {
            return items;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NodeCore)) return false;
            NodeCore nodeCore = (NodeCore) o;
            return items.equals(nodeCore.items);
        }

        @Override
        public int hashCode() {
            return Objects.hash(items);
        }

        @Override
        public String toString() {
            return items.keySet().stream().map(Objects::toString).collect(Collectors.joining("\n"));
        }
    }

    private static class LRItemCore {
        private final LRItem inner;

        private LRItemCore(LRItem inner) {
            this.inner = inner;
        }

        public LRItem getInner() {
            return inner;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof LRItemCore)) return false;
            LRItemCore that = (LRItemCore) o;
            return inner.position == that.inner.position && inner.production.equals(that.inner.production);
        }

        @Override
        public int hashCode() {
            return Objects.hash(inner.position, inner.production);
        }

        @Override
        public String toString() {
            String innerStr = inner.toString();
            return innerStr.substring(0, innerStr.indexOf('║') - 1);
        }
    }
}
