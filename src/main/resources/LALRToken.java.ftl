<#if PACKAGE_NAME?has_content>
package ${PACKAGE_NAME};

</#if>
import ru.unrealeugene.parsing.generating.Token;

import java.util.regex.Pattern;

public class ${TOKEN_CLASS_NAME} implements Token {
    private final String value;
    private final Type type;

    public ${TOKEN_CLASS_NAME}(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    public ${TOKEN_CLASS_NAME}(Type type) {
        this(type.toString(), type);
    }

    public String getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        <#list GRAMMAR_TERMINALS as TERMINAL>
        TOKEN_${TERMINAL_index?string.computer}("${TERMINAL.value?j_string}", "${TERMINAL.toString()?j_string}"),
        </#list>
        END("", "EOF");

        private final Pattern pattern;
        private final String name;

        Type(String regex, String name) {
            this.pattern = Pattern.compile(regex);
            this.name = name;
        }

        public boolean matches(String str) {
            return pattern.matcher(str).matches();
        }

        public Pattern getPattern() {
            return pattern;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
