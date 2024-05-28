package ch.supsi.connectfour.backend.application.symbol;

import ch.supsi.connectfour.backend.business.symbols.SymbolInterface;

import java.util.List;
import java.util.function.Function;

public interface SymbolProviderApplication <T>{
    T translate(Function<SymbolInterface, T> func, SymbolInterface s);
    List<T> translateAll(Function<SymbolInterface, T> func, List<SymbolInterface> s);
}
