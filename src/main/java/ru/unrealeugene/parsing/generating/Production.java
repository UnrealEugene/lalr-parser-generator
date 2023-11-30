package ru.unrealeugene.parsing.generating;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Production {
    private final String lhs;
    private final List<Symbol> rhs;
    private final String attrData;
    private final String actionData;

    public Production(String lhs, List<Symbol> rhs, String attrData, String actionData) {
        this.lhs = lhs;
        this.rhs = rhs.stream()
                .filter(s -> s.getType() != Symbol.Type.EPSILON)
                .collect(Collectors.toList());
        this.attrData = attrData;
        this.actionData = actionData;
    }

    public String getLhs() {
        return lhs;
    }

    public List<Symbol> getRhs() {
        return Collections.unmodifiableList(rhs);
    }

    public String getAttrData() {
        return attrData;
    }

    public String getActionData() {
        return actionData;
    }

    public String getSubstitutedActionData() {
        String result = actionData;
        for (int i = 0; i < rhs.size(); i++) {
            Symbol symbol = rhs.get(i);
            switch (symbol.getType()) {
                case TERMINAL ->
                        result = result.replaceAll("\\$" + i, "children.get(" + i + ").getToken().getValue()");
                case NON_TERMINAL ->
                        result = result.replaceAll("\\$" + i, "((" + symbol.getValue() + "Tree) children.get(" + i + "))");
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "%s -> %s",
                lhs,
                rhs.isEmpty() ? Symbol.EPSILON : rhs.stream()
                        .map(Objects::toString)
                        .collect(Collectors.joining(" "))
        );
    }

    public static class Symbol {
        private final String value;
        private final Type type;

        public static final Symbol EOF = new Symbol(null, Type.EOF);
        public static final Symbol EPSILON = new Symbol(null, Type.EPSILON);

        private Symbol(String value, Type type) {
            this.value = value;
            this.type = type;
        }

        public static String makeRegExp(String input) {
            return input.replaceAll("([-\\[\\]{}()*+?.,^$|#])", "\\\\$1");
        }

        public static Symbol newSymbol(String str) {
            if (str.startsWith("'")) {
                return new Symbol(makeRegExp(str.substring(1, str.length() - 1)), Type.TERMINAL);
            } else if (str.startsWith("r'")) {
                return new Symbol(str.substring(2, str.length() - 1), Type.TERMINAL);
            } else if ("ε".equals(str)){
                return new Symbol(null, Type.EPSILON);
            } else {
                return new Symbol(str, Type.NON_TERMINAL);
            }
        }

        public String getValue() {
            return value;
        }

        public Type getType() {
            return type;
        }

        public enum Type {
            NON_TERMINAL, TERMINAL, EOF, EPSILON;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Symbol)) return false;
            Symbol symbol = (Symbol) o;
            return Objects.equals(value, symbol.value) && type == symbol.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, type);
        }

        @Override
        public String toString() {
            return switch (type) {
                case TERMINAL -> "'" + value + "'";
                case NON_TERMINAL -> value;
                case EOF -> "$";
                case EPSILON -> "ε";
            };
        }
    }
}
