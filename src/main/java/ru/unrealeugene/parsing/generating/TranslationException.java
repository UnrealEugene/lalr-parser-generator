package ru.unrealeugene.parsing.generating;

public class TranslationException extends Exception {
    public TranslationException() { }

    public TranslationException(String message) {
        super(message);
    }

    public TranslationException(String message, Throwable cause) {
        super(message, cause);
    }
}
