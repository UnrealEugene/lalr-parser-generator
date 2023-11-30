package ru.unrealeugene.parsing.generating;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Tree<T extends Token> {
    private final List<Tree<T>> children;
    private final T token;
    private final int productionIndex;

    public Tree(List<Tree<T>> children, int productionIndex) {
        this.children = Collections.unmodifiableList(children);
        this.token = null;
        this.productionIndex = productionIndex;
    }

    public Tree(T token) {
        this.children = Collections.emptyList();
        this.token = token;
        this.productionIndex = -1;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public Tree<T> get(int i) {
        return children.get(i);
    }

    public T getToken() {
        return token;
    }

    @Override
    public String toString() {
        return token == null ? children.stream().map(Tree::toString).collect(Collectors.joining()) : token.getValue();
    }

    public interface Constructor<T extends Token> {
        Tree<T> construct(List<Tree<T>> children, int productionIndex);
    }
}
