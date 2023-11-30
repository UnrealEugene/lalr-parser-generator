package ru.unrealeugene.parsing.generating;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ParserGenerator {
    void generate(String aPackage,
                  List<Production.Symbol> skippedSymbols,
                  Path output,
                  Path outputClassPath,
                  Path outputImagePath) throws IOException;
}
