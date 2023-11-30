// Generated from D:/Projects/Java/parsing-2021/src/main/java/ru/unrealeugene/parsing/generating/grammar\GrammarLexer.g4 by ANTLR 4.9.2
package ru.unrealeugene.parsing.generating.grammar;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SEP=1, OR=2, OPTION=3, SKIP_=4, END=5, TERM=6, NONTERM=7, WS=8, OPEN_ACTION=9, 
		OPEN_ATTR=10, CLOSE_ACTION=11, ACTION_CONTENT=12, CLOSE_ATTR=13, ATTR_CONTENT=14;
	public static final int
		ACTION=1, ATTR=2;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "ACTION", "ATTR"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"SEP", "OR", "OPTION", "SKIP_", "END", "TERM", "NONTERM", "WS", "OPEN_ACTION", 
			"OPEN_ATTR", "CLOSE_ACTION", "ACTION_CONTENT", "CLOSE_ATTR", "ATTR_CONTENT"
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


	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "GrammarLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\20e\b\1\b\1\b\1\4"+
		"\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n"+
		"\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\3\3\3\3\3\4"+
		"\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\5\7\61\n\7\3\7\3\7\3\7\3\7\7\7\67"+
		"\n\7\f\7\16\7:\13\7\3\7\3\7\5\7>\n\7\3\b\6\bA\n\b\r\b\16\bB\3\t\6\tF\n"+
		"\t\r\t\16\tG\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3"+
		"\f\3\r\3\r\3\r\5\r[\n\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\5\17d\n\17"+
		"\2\2\20\5\3\7\4\t\5\13\6\r\7\17\b\21\t\23\n\25\13\27\f\31\r\33\16\35\17"+
		"\37\20\5\2\3\4\5\6\2\f\f\17\17))^^\7\2//\62;C\\aac|\5\2\13\f\17\17\"\""+
		"\2j\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\3\31\3\2"+
		"\2\2\3\33\3\2\2\2\4\35\3\2\2\2\4\37\3\2\2\2\5!\3\2\2\2\7$\3\2\2\2\t&\3"+
		"\2\2\2\13(\3\2\2\2\r-\3\2\2\2\17=\3\2\2\2\21@\3\2\2\2\23E\3\2\2\2\25K"+
		"\3\2\2\2\27O\3\2\2\2\31S\3\2\2\2\33Z\3\2\2\2\35\\\3\2\2\2\37c\3\2\2\2"+
		"!\"\7/\2\2\"#\7@\2\2#\6\3\2\2\2$%\7~\2\2%\b\3\2\2\2&\'\7B\2\2\'\n\3\2"+
		"\2\2()\7u\2\2)*\7m\2\2*+\7k\2\2+,\7r\2\2,\f\3\2\2\2-.\7=\2\2.\16\3\2\2"+
		"\2/\61\7t\2\2\60/\3\2\2\2\60\61\3\2\2\2\61\62\3\2\2\2\628\7)\2\2\63\67"+
		"\n\2\2\2\64\65\7^\2\2\65\67\13\2\2\2\66\63\3\2\2\2\66\64\3\2\2\2\67:\3"+
		"\2\2\28\66\3\2\2\289\3\2\2\29;\3\2\2\2:8\3\2\2\2;>\7)\2\2<>\7\u03b7\2"+
		"\2=\60\3\2\2\2=<\3\2\2\2>\20\3\2\2\2?A\t\3\2\2@?\3\2\2\2AB\3\2\2\2B@\3"+
		"\2\2\2BC\3\2\2\2C\22\3\2\2\2DF\t\4\2\2ED\3\2\2\2FG\3\2\2\2GE\3\2\2\2G"+
		"H\3\2\2\2HI\3\2\2\2IJ\b\t\2\2J\24\3\2\2\2KL\7}\2\2LM\3\2\2\2MN\b\n\3\2"+
		"N\26\3\2\2\2OP\7]\2\2PQ\3\2\2\2QR\b\13\4\2R\30\3\2\2\2ST\7\177\2\2TU\3"+
		"\2\2\2UV\b\f\5\2V\32\3\2\2\2WX\7^\2\2X[\13\2\2\2Y[\13\2\2\2ZW\3\2\2\2"+
		"ZY\3\2\2\2[\34\3\2\2\2\\]\7_\2\2]^\3\2\2\2^_\b\16\5\2_\36\3\2\2\2`a\7"+
		"^\2\2ad\13\2\2\2bd\13\2\2\2c`\3\2\2\2cb\3\2\2\2d \3\2\2\2\r\2\3\4\60\66"+
		"8=BGZc\6\b\2\2\7\3\2\7\4\2\6\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}