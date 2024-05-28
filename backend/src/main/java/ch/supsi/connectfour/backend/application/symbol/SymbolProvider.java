package ch.supsi.connectfour.backend.application.symbol;
import ch.supsi.connectfour.backend.business.symbols.SymbolInterface;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class SymbolProvider<T> implements SymbolProviderApplication<T>{

    public List<T> translateAll(Function<SymbolInterface, T> func, @NotNull List<SymbolInterface> s){
        return s.stream().map(func).toList();
    }

    public T translate(@NotNull Function<SymbolInterface, T> func, SymbolInterface s){
        return func.apply(s);
    }
}
