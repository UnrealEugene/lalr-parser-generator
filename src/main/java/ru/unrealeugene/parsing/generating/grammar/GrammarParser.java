// Generated from D:/Projects/Java/parsing-2021/src/main/java/ru/unrealeugene/parsing/generating/grammar\GrammarParser.g4 by ANTLR 4.9.2
package ru.unrealeugene.parsing.generating.grammar;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.TerminalNode;
import ru.unrealeugene.parsing.generating.Production;
import ru.unrealeugene.parsing.generating.Production.Symbol;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SEP=1, OR=2, OPTION=3, SKIP_=4, END=5, TERM=6, NONTERM=7, WS=8, OPEN_ACTION=9, 
		OPEN_ATTR=10, CLOSE_ACTION=11, ACTION_CONTENT=12, CLOSE_ATTR=13, ATTR_CONTENT=14;
	public static final int
		RULE_start = 0, RULE_skipTerminal = 1, RULE_production = 2, RULE_productionAttr = 3, 
		RULE_productionAction = 4, RULE_lhs = 5, RULE_rhs = 6;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "skipTerminal", "production", "productionAttr", "productionAction", 
			"lhs", "rhs"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'->'", "'|'", "'@'", "'skip'", "';'", null, null, null, "'{'", 
			"'['", "'}'", null, "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "SEP", "OR", "OPTION", "SKIP_", "END", "TERM", "NONTERM", "WS", 
			"OPEN_ACTION", "OPEN_ATTR", "CLOSE_ACTION", "ACTION_CONTENT", "CLOSE_ATTR", 
			"ATTR_CONTENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "GrammarParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class StartContext extends ParserRuleContext {
		public List<Production> productions;
		public List<Symbol> skippedTerminals;
		public ProductionContext p;
		public SkipTerminalContext s;
		public List<ProductionContext> production() {
			return getRuleContexts(ProductionContext.class);
		}
		public ProductionContext production(int i) {
			return getRuleContext(ProductionContext.class,i);
		}
		public List<SkipTerminalContext> skipTerminal() {
			return getRuleContexts(SkipTerminalContext.class);
		}
		public SkipTerminalContext skipTerminal(int i) {
			return getRuleContext(SkipTerminalContext.class,i);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		((StartContext)_localctx).productions =  new ArrayList<>(); ((StartContext)_localctx).skippedTerminals =  new ArrayList<>();
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OPTION || _la==NONTERM) {
				{
				setState(20);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NONTERM:
					{
					setState(14);
					((StartContext)_localctx).p = production();
					_localctx.productions.addAll(((StartContext)_localctx).p.prods);
					}
					break;
				case OPTION:
					{
					setState(17);
					((StartContext)_localctx).s = skipTerminal();
					_localctx.skippedTerminals.add(((StartContext)_localctx).s.sym);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(24);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SkipTerminalContext extends ParserRuleContext {
		public Symbol sym;
		public Token t;
		public TerminalNode OPTION() { return getToken(GrammarParser.OPTION, 0); }
		public TerminalNode SKIP_() { return getToken(GrammarParser.SKIP_, 0); }
		public TerminalNode END() { return getToken(GrammarParser.END, 0); }
		public TerminalNode TERM() { return getToken(GrammarParser.TERM, 0); }
		public SkipTerminalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_skipTerminal; }
	}

	public final SkipTerminalContext skipTerminal() throws RecognitionException {
		SkipTerminalContext _localctx = new SkipTerminalContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_skipTerminal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			match(OPTION);
			setState(26);
			match(SKIP_);
			setState(27);
			((SkipTerminalContext)_localctx).t = match(TERM);
			setState(28);
			match(END);
			((SkipTerminalContext)_localctx).sym =  Symbol.newSymbol((((SkipTerminalContext)_localctx).t!=null?((SkipTerminalContext)_localctx).t.getText():null));
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProductionContext extends ParserRuleContext {
		public List<Production> prods;
		public String attrData;
		public String actionData;
		public LhsContext lhs;
		public ProductionAttrContext at;
		public RhsContext rhs;
		public ProductionActionContext ac;
		public LhsContext lhs() {
			return getRuleContext(LhsContext.class,0);
		}
		public TerminalNode SEP() { return getToken(GrammarParser.SEP, 0); }
		public List<RhsContext> rhs() {
			return getRuleContexts(RhsContext.class);
		}
		public RhsContext rhs(int i) {
			return getRuleContext(RhsContext.class,i);
		}
		public TerminalNode END() { return getToken(GrammarParser.END, 0); }
		public List<TerminalNode> OR() { return getTokens(GrammarParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(GrammarParser.OR, i);
		}
		public ProductionAttrContext productionAttr() {
			return getRuleContext(ProductionAttrContext.class,0);
		}
		public List<ProductionActionContext> productionAction() {
			return getRuleContexts(ProductionActionContext.class);
		}
		public ProductionActionContext productionAction(int i) {
			return getRuleContext(ProductionActionContext.class,i);
		}
		public ProductionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_production; }
	}

	public final ProductionContext production() throws RecognitionException {
		ProductionContext _localctx = new ProductionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_production);
		((ProductionContext)_localctx).prods =  new ArrayList<>();
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(31);
			((ProductionContext)_localctx).lhs = lhs();
			setState(35);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OPEN_ATTR) {
				{
				setState(32);
				((ProductionContext)_localctx).at = productionAttr();
				((ProductionContext)_localctx).attrData =  ((ProductionContext)_localctx).at.sourceAttr;
				}
			}

			setState(37);
			match(SEP);
			setState(38);
			((ProductionContext)_localctx).rhs = rhs();
			setState(42);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OPEN_ACTION) {
				{
				setState(39);
				((ProductionContext)_localctx).ac = productionAction();
				((ProductionContext)_localctx).actionData =  ((ProductionContext)_localctx).ac.sourceAction;
				}
			}

			_localctx.prods.add(new Production(((ProductionContext)_localctx).lhs.str, ((ProductionContext)_localctx).rhs.symbols, _localctx.attrData, _localctx.actionData));
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(45);
				match(OR);
				setState(46);
				((ProductionContext)_localctx).rhs = rhs();
				((ProductionContext)_localctx).actionData =  null;
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OPEN_ACTION) {
					{
					setState(48);
					((ProductionContext)_localctx).ac = productionAction();
					((ProductionContext)_localctx).actionData =  ((ProductionContext)_localctx).ac.sourceAction;
					}
				}

				_localctx.prods.add(new Production(((ProductionContext)_localctx).lhs.str, ((ProductionContext)_localctx).rhs.symbols, _localctx.attrData, _localctx.actionData));
				}
				}
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(60);
			match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProductionAttrContext extends ParserRuleContext {
		public String sourceAttr;
		public StringBuilder buf = new StringBuilder();;
		public Token c;
		public TerminalNode OPEN_ATTR() { return getToken(GrammarParser.OPEN_ATTR, 0); }
		public TerminalNode CLOSE_ATTR() { return getToken(GrammarParser.CLOSE_ATTR, 0); }
		public List<TerminalNode> ATTR_CONTENT() { return getTokens(GrammarParser.ATTR_CONTENT); }
		public TerminalNode ATTR_CONTENT(int i) {
			return getToken(GrammarParser.ATTR_CONTENT, i);
		}
		public ProductionAttrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_productionAttr; }
	}

	public final ProductionAttrContext productionAttr() throws RecognitionException {
		ProductionAttrContext _localctx = new ProductionAttrContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_productionAttr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(OPEN_ATTR);
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ATTR_CONTENT) {
				{
				{
				setState(63);
				((ProductionAttrContext)_localctx).c = match(ATTR_CONTENT);
				_localctx.buf.append((((ProductionAttrContext)_localctx).c!=null?((ProductionAttrContext)_localctx).c.getText():null).charAt((((ProductionAttrContext)_localctx).c!=null?((ProductionAttrContext)_localctx).c.getText():null).length() - 1));
				}
				}
				setState(69);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(70);
			match(CLOSE_ATTR);
			((ProductionAttrContext)_localctx).sourceAttr =  _localctx.buf.toString();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProductionActionContext extends ParserRuleContext {
		public String sourceAction;
		public StringBuilder buf = new StringBuilder();;
		public Token c;
		public TerminalNode OPEN_ACTION() { return getToken(GrammarParser.OPEN_ACTION, 0); }
		public TerminalNode CLOSE_ACTION() { return getToken(GrammarParser.CLOSE_ACTION, 0); }
		public List<TerminalNode> ACTION_CONTENT() { return getTokens(GrammarParser.ACTION_CONTENT); }
		public TerminalNode ACTION_CONTENT(int i) {
			return getToken(GrammarParser.ACTION_CONTENT, i);
		}
		public ProductionActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_productionAction; }
	}

	public final ProductionActionContext productionAction() throws RecognitionException {
		ProductionActionContext _localctx = new ProductionActionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_productionAction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(OPEN_ACTION);
			setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ACTION_CONTENT) {
				{
				{
				setState(74);
				((ProductionActionContext)_localctx).c = match(ACTION_CONTENT);
				_localctx.buf.append((((ProductionActionContext)_localctx).c!=null?((ProductionActionContext)_localctx).c.getText():null).charAt((((ProductionActionContext)_localctx).c!=null?((ProductionActionContext)_localctx).c.getText():null).length() - 1));
				}
				}
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(81);
			match(CLOSE_ACTION);
			((ProductionActionContext)_localctx).sourceAction =  _localctx.buf.toString();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LhsContext extends ParserRuleContext {
		public String str;
		public Token t;
		public TerminalNode NONTERM() { return getToken(GrammarParser.NONTERM, 0); }
		public LhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lhs; }
	}

	public final LhsContext lhs() throws RecognitionException {
		LhsContext _localctx = new LhsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_lhs);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			((LhsContext)_localctx).t = match(NONTERM);
			((LhsContext)_localctx).str =  (((LhsContext)_localctx).t!=null?((LhsContext)_localctx).t.getText():null);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RhsContext extends ParserRuleContext {
		public List<Symbol> symbols;
		public Token t;
		public List<TerminalNode> TERM() { return getTokens(GrammarParser.TERM); }
		public TerminalNode TERM(int i) {
			return getToken(GrammarParser.TERM, i);
		}
		public List<TerminalNode> NONTERM() { return getTokens(GrammarParser.NONTERM); }
		public TerminalNode NONTERM(int i) {
			return getToken(GrammarParser.NONTERM, i);
		}
		public RhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rhs; }
	}

	public final RhsContext rhs() throws RecognitionException {
		RhsContext _localctx = new RhsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_rhs);
		((RhsContext)_localctx).symbols =  new ArrayList<>();
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TERM || _la==NONTERM) {
				{
				{
				setState(87);
				((RhsContext)_localctx).t = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==TERM || _la==NONTERM) ) {
					((RhsContext)_localctx).t = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				_localctx.symbols.add(Symbol.newSymbol((((RhsContext)_localctx).t!=null?((RhsContext)_localctx).t.getText():null)));
				}
				}
				setState(93);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\20a\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3\2\3\2\3\2\3\2\3\2\7\2"+
		"\27\n\2\f\2\16\2\32\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\5\4&"+
		"\n\4\3\4\3\4\3\4\3\4\3\4\5\4-\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\66\n"+
		"\4\3\4\3\4\7\4:\n\4\f\4\16\4=\13\4\3\4\3\4\3\5\3\5\3\5\7\5D\n\5\f\5\16"+
		"\5G\13\5\3\5\3\5\3\5\3\6\3\6\3\6\7\6O\n\6\f\6\16\6R\13\6\3\6\3\6\3\6\3"+
		"\7\3\7\3\7\3\b\3\b\7\b\\\n\b\f\b\16\b_\13\b\3\b\2\2\t\2\4\6\b\n\f\16\2"+
		"\3\3\2\b\t\2b\2\30\3\2\2\2\4\33\3\2\2\2\6!\3\2\2\2\b@\3\2\2\2\nK\3\2\2"+
		"\2\fV\3\2\2\2\16]\3\2\2\2\20\21\5\6\4\2\21\22\b\2\1\2\22\27\3\2\2\2\23"+
		"\24\5\4\3\2\24\25\b\2\1\2\25\27\3\2\2\2\26\20\3\2\2\2\26\23\3\2\2\2\27"+
		"\32\3\2\2\2\30\26\3\2\2\2\30\31\3\2\2\2\31\3\3\2\2\2\32\30\3\2\2\2\33"+
		"\34\7\5\2\2\34\35\7\6\2\2\35\36\7\b\2\2\36\37\7\7\2\2\37 \b\3\1\2 \5\3"+
		"\2\2\2!%\5\f\7\2\"#\5\b\5\2#$\b\4\1\2$&\3\2\2\2%\"\3\2\2\2%&\3\2\2\2&"+
		"\'\3\2\2\2\'(\7\3\2\2(,\5\16\b\2)*\5\n\6\2*+\b\4\1\2+-\3\2\2\2,)\3\2\2"+
		"\2,-\3\2\2\2-.\3\2\2\2.;\b\4\1\2/\60\7\4\2\2\60\61\5\16\b\2\61\65\b\4"+
		"\1\2\62\63\5\n\6\2\63\64\b\4\1\2\64\66\3\2\2\2\65\62\3\2\2\2\65\66\3\2"+
		"\2\2\66\67\3\2\2\2\678\b\4\1\28:\3\2\2\29/\3\2\2\2:=\3\2\2\2;9\3\2\2\2"+
		";<\3\2\2\2<>\3\2\2\2=;\3\2\2\2>?\7\7\2\2?\7\3\2\2\2@E\7\f\2\2AB\7\20\2"+
		"\2BD\b\5\1\2CA\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2FH\3\2\2\2GE\3\2\2"+
		"\2HI\7\17\2\2IJ\b\5\1\2J\t\3\2\2\2KP\7\13\2\2LM\7\16\2\2MO\b\6\1\2NL\3"+
		"\2\2\2OR\3\2\2\2PN\3\2\2\2PQ\3\2\2\2QS\3\2\2\2RP\3\2\2\2ST\7\r\2\2TU\b"+
		"\6\1\2U\13\3\2\2\2VW\7\t\2\2WX\b\7\1\2X\r\3\2\2\2YZ\t\2\2\2Z\\\b\b\1\2"+
		"[Y\3\2\2\2\\_\3\2\2\2][\3\2\2\2]^\3\2\2\2^\17\3\2\2\2_]\3\2\2\2\13\26"+
		"\30%,\65;EP]";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}