package ru.unrealeugene.parsing.generating;

public interface Parser<T extends Token> {
    Tree<T> parse() throws TranslationException;
}
