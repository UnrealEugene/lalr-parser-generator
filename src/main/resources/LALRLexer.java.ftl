<#-- @ftlvariable name="SKIPPED_TERMINALS" type="ru.unrealeugene.parsing.generating.Production.Symbol[]" -->
<#if PACKAGE_NAME?has_content>
package ${PACKAGE_NAME};

</#if>
import ru.unrealeugene.parsing.generating.Lexer;
import ru.unrealeugene.parsing.generating.TranslationException;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ${LEXER_CLASS_NAME} implements Lexer<${TOKEN_CLASS_NAME}> {
    private static final Pattern SPLIT_PATTERN = Pattern.compile("${GRAMMAR_TERMINALS?map(x -> x.value?j_string)?join("|", ".")}|${SKIPPED_TERMINALS?map(x -> x.value)?join("|", ".", "|.")}");
    <#if (SKIPPED_TERMINALS?size > 0)>
    private static final Pattern SKIP_PATTERN = Pattern.compile("${SKIPPED_TERMINALS?map(x -> x.value)?join("|")}");
    </#if>
    private final Matcher matcher;
    private ${TOKEN_CLASS_NAME} curToken;

    public ${LEXER_CLASS_NAME}(String input) {
        matcher = SPLIT_PATTERN.matcher(input);
    }

    public void nextToken() throws TranslationException {
        while (matcher.find()) {
            String token = matcher.group();
            <#if (SKIPPED_TERMINALS?size > 0)>
            if (SKIP_PATTERN.matcher(token).matches()) {
                continue;
            }
            </#if>
            for (${TOKEN_CLASS_NAME}.Type type : ${TOKEN_CLASS_NAME}.Type.values()) {
                if (type.matches(token)) {
                    curToken = new ${TOKEN_CLASS_NAME}(token, type);
                    return;
                }
            }
            throw new TranslationException("Unexpected token '" + token + "' at position " + getTokenPosition());
        }
        curToken = new ${TOKEN_CLASS_NAME}(${TOKEN_CLASS_NAME}.Type.END);
    }

    public ${TOKEN_CLASS_NAME} getToken() {
        return curToken;
    }

    public int getTokenPosition() {
        return matcher.hitEnd() ? matcher.regionEnd() : matcher.start();
    }
}