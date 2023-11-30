<#-- @ftlvariable name="PRODUCTION_LHS" type="java.lang.String[]" -->
<#-- @ftlvariable name="PARSING_TABLE" type="int[][]" -->
<#-- @ftlvariable name="NON_TERMINAL_BY_PRODUCTION" type="int[]" -->
<#-- @ftlvariable name="REDUCE_AMOUNT" type="int[]" -->
<#-- @ftlvariable name="PRODUCTIONS" type="ru.unrealeugene.parsing.generating.Production[]" -->
<#if PACKAGE_NAME?has_content>
package ${PACKAGE_NAME};

</#if>
import ru.unrealeugene.parsing.generating.Lexer;
import ru.unrealeugene.parsing.generating.Parser;
import ru.unrealeugene.parsing.generating.TranslationException;
import ru.unrealeugene.parsing.generating.Tree;

import java.lang.*;
import java.util.*;
import java.util.stream.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ${PARSER_CLASS_NAME} implements Parser<${TOKEN_CLASS_NAME}> {
    private final Lexer<${TOKEN_CLASS_NAME}> lexer;

    private static final int NIL = Integer.MAX_VALUE;

    private static final int TERMINALS_COUNT = ${TOKEN_CLASS_NAME}.Type.values().length;
    private static final int[] REDUCE_AMOUNT = new int[] { ${REDUCE_AMOUNT?map(x -> x?string.computer)?join(", ")} };
    private static final int[] NON_TERMINAL_BY_PRODUCTION = new int[] { ${NON_TERMINAL_BY_PRODUCTION?map(x -> x?string.computer)?join(", ")} };
    private static final List<Tree.Constructor<${TOKEN_CLASS_NAME}>> TREE_CONSTRUCTORS = List.of(
            <#list PRODUCTIONS as production>${production.lhs}Tree::new<#if production_has_next>, </#if><#if production_index % 5 == 4>${"\n            "}</#if></#list>
    );
    private static final int[][] PARSING_TABLE = new int[][] {
            <#list PARSING_TABLE as row>
            { ${row?map(x -> (x == 2147483647)?then("NIL", x?string.computer)?left_pad(3))?join(", ")} }<#if row_has_next>,</#if>
            </#list>
    };

    public ${PARSER_CLASS_NAME}(Lexer<${TOKEN_CLASS_NAME}> lexer) {
        this.lexer = lexer;
    }

    @Override
    public Tree<${TOKEN_CLASS_NAME}> parse() throws TranslationException {
        Deque<Tree<${TOKEN_CLASS_NAME}>> treeStack = new ArrayDeque<>();
        Deque<Integer> nodeStack = new ArrayDeque<>();

        nodeStack.add(0);

        do {
            lexer.nextToken();
            while (true) {
                int state = nodeStack.getLast();
                int token = lexer.getToken().getType().ordinal();
                int newState = PARSING_TABLE[state][token];
                if (newState == NIL) {
                    throw new TranslationException("Unexpected token " + lexer.getToken().getType()
                            + " at position " + lexer.getTokenPosition());
                } else if (newState <= 0) {
                    List<Tree<${TOKEN_CLASS_NAME}>> treeAcc = new ArrayList<>();
                    for (int i = 0; i < REDUCE_AMOUNT[-newState]; i++) {
                        treeAcc.add(treeStack.removeLast());
                        nodeStack.removeLast();
                    }
                    Collections.reverse(treeAcc);
                    treeStack.addLast(TREE_CONSTRUCTORS.get(-newState).construct(treeAcc, -newState));
                    nodeStack.addLast(PARSING_TABLE[nodeStack.getLast()][TERMINALS_COUNT + NON_TERMINAL_BY_PRODUCTION[-newState] - 1]);
                    if (newState == 0) {
                        break;
                    }
                } else {
                    treeStack.addLast(new Tree<>(lexer.getToken()));
                    nodeStack.addLast(newState);
                    break;
                }
            }
        } while (lexer.getToken().getType() != ${TOKEN_CLASS_NAME}.Type.END);

        return treeStack.getLast();
    }
    <#list PRODUCTION_LHS as lhs>

    public static class ${lhs}Tree extends Tree<${TOKEN_CLASS_NAME}> {
        <#list PRODUCTIONS as production>
        <#if production.lhs == lhs && production.attrData??>
        public ${production.attrData?split(",\\s*", "r")?join(";\n        public ")};

        <#break>
        </#if>
        </#list>
        public ${lhs}Tree(List<Tree<${TOKEN_CLASS_NAME}>> children, int productionIndex) {
            super(children, productionIndex);
            <#list PRODUCTIONS as production>
            <#if production.lhs == lhs && production.actionData??>
            if (productionIndex == ${production_index}) {
                ${production.substitutedActionData}
            }
            </#if>
            </#list>
        }
    }
    </#list>
}
