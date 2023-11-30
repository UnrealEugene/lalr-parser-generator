package ru.unrealeugene.parsing.generating;

public interface Lexer<T extends Token> {
    void nextToken() throws TranslationException;
    T getToken();
    int getTokenPosition();
}
