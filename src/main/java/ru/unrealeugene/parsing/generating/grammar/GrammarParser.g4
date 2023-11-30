parser grammar GrammarParser;
options { tokenVocab = GrammarLexer; }

@header {
import java.util.*;
import ru.unrealeugene.parsing.generating.Production;
import ru.unrealeugene.parsing.generating.Production.Symbol;
}

start returns [List<Production> productions, List<Symbol> skippedTerminals]
@init {$productions = new ArrayList<>(); $skippedTerminals = new ArrayList<>();}
    : (p=production {$productions.addAll($p.prods);}
      | s=skipTerminal {$skippedTerminals.add($s.sym);})*;

skipTerminal returns [Symbol sym]
    : OPTION SKIP_ t=TERM END {$sym = Symbol.newSymbol($t.text);};

production returns [List<Production> prods] locals [String attrData, String actionData]
@init {$prods = new ArrayList<>();}
    : lhs (at=productionAttr {$attrData = $at.sourceAttr;})?
      SEP rhs (ac=productionAction {$actionData = $ac.sourceAction;})?
          {$prods.add(new Production($lhs.str, $rhs.symbols, $attrData, $actionData));}
      (OR rhs {$actionData = null;} (ac=productionAction {$actionData = $ac.sourceAction;})?
          {$prods.add(new Production($lhs.str, $rhs.symbols, $attrData, $actionData));})* END;

productionAttr returns [String sourceAttr] locals [StringBuilder buf = new StringBuilder();]
    : OPEN_ATTR (c=ATTR_CONTENT {$buf.append($c.text.charAt($c.text.length() - 1));})*
      CLOSE_ATTR {$sourceAttr = $buf.toString();};

productionAction returns [String sourceAction] locals [StringBuilder buf = new StringBuilder();]
    : OPEN_ACTION (c=ACTION_CONTENT {$buf.append($c.text.charAt($c.text.length() - 1));})*
      CLOSE_ACTION {$sourceAction = $buf.toString();};

lhs returns [String str]
    : t=NONTERM {$str = $t.text;};

rhs returns [List<Symbol> symbols] @init {$symbols = new ArrayList<>();}
    : (t=(TERM | NONTERM) {$symbols.add(Symbol.newSymbol($t.text));})*;
