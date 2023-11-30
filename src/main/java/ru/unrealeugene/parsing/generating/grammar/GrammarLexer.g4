lexer grammar GrammarLexer;

SEP : '->';

OR : '|';

OPTION : '@';

SKIP_ : 'skip';

END : ';';

TERM : 'r'? '\'' (~('\'' | '\\' | '\r' | '\n') | '\\' .)* '\'' | 'ε';

NONTERM : ('a'..'z' | 'A'..'Z' | '-' | '_' | '0'..'9')+;

WS : [ \t\n\r]+ -> skip;

OPEN_ACTION : '{' -> pushMode(ACTION);

OPEN_ATTR : '[' -> pushMode(ATTR);

mode ACTION;

CLOSE_ACTION : '}' -> popMode;

ACTION_CONTENT : '\\' . | .;

mode ATTR;

CLOSE_ATTR : ']' -> popMode;

ATTR_CONTENT : '\\' . | .;

