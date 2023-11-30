package ru.unrealeugene.parsing.generating;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.io.FilenameUtils;
import ru.unrealeugene.parsing.generating.grammar.GrammarLexer;
import ru.unrealeugene.parsing.generating.grammar.GrammarParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GenerateParser {
    private static void printHelp() {
        System.out.println("""
                           Look-Ahead LR parser generator.
                           Usage: ru.unrealeugene.parsing.generating.GenerateParser [OPTION...] [GRAMMAR_FILE]
                           
                           General options:
                           
                            -o, --class-dir DIR     specify directory where parser will be generated
                            -i, --image-dir DIR     generate LR automaton image in specified folder
                            -n, --name NAME         specify name of the grammar (will be used as prefix for all classes)
                            -p, --package NAME      specify package for all generated classes
                            -?, --help              give this help list
                            
                           Options arguments must be preceded by corresponding options.
                           """);
    }

    public static void main(String[] args) throws IOException {
        if (args == null || Arrays.stream(args).anyMatch(Objects::isNull)) {
            printHelp();
            return;
        }

        Path inputGrammarPath = null;
        Path outputClassPath = Path.of("./gen");
        Path outputImagePath = null;
        String grammarName = null;
        String packageName = "";

        int i = 0;
        while (i < args.length) {
            switch (args[i]) {
                case "-o", "--class-dir" -> {
                    outputClassPath = Path.of(args[i + 1]);
                    i += 2;
                }
                case "-i", "--image-dir" -> {
                    outputImagePath = Path.of(args[i + 1]);
                    i += 2;
                }
                case "-n", "--name" -> {
                    grammarName = args[i + 1];
                    i += 2;
                }
                case "-?", "--help" -> {
                    printHelp();
                    return;
                }
                case "-p", "--package" -> {
                    packageName = args[i + 1];
                    i += 2;
                }
                default -> {
                    if (args[i].startsWith("-")) {
                        System.out.println("Unknown option: " + args[i]);
                        printHelp();
                        return;
                    }
                    inputGrammarPath = Path.of(args[i]);
                    i++;
                }
            }
        }


        if (inputGrammarPath == null) {
            System.err.println("Input grammar file is not specified.");
            return;
        }

        if (!inputGrammarPath.toFile().isFile()) {
            System.err.println("Input grammar file does not exist.");
            return;
        }

        if (grammarName == null) {
            grammarName = FilenameUtils.removeExtension(inputGrammarPath.getFileName().toString());
        }

        CharStream charStream;
        try {
            charStream = CharStreams.fromString(Files.readString(inputGrammarPath, StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.printf("Error occurred while reading file '%s': %s.%n", inputGrammarPath, e.getMessage());
            return;
        }

        GrammarLexer lexer = new GrammarLexer(charStream);
        GrammarParser parser = new GrammarParser(new CommonTokenStream(lexer));

        GrammarParser.StartContext result = parser.start();
        List<Production> productions = result.productions;
        List<Production.Symbol> skippedSymbols = result.skippedTerminals;

        new LALRGenerator(grammarName, productions).generate(
                packageName,
                skippedSymbols,
                inputGrammarPath,
                outputClassPath,
                outputImagePath
        );
    }
}
