package ch.supsi.connectfour.backend.application.symbol;

import ch.supsi.connectfour.backend.business.symbols.SymbolInterface;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class SymbolProvider<T> implements SymbolProviderApplication<T> {

    /**
     * Given a list of symbols, this function translates each element into a type T using the function FUNC.
     * For example, if T is initialized as URL, FUNC should be a function capable of translating a symbol into a URL.
     * Applying the FUNC function will translate the symbols into URLs and return them as the return type.
     *
     * @param func function to apply on all list symbols
     * @param s    list of symbols
     * @return a list of translated symbols
     */
    @Override
    public List<T> translateAll(Function<SymbolInterface, T> func, @NotNull List<SymbolInterface> s) {
        return s.stream().map(func).toList();
    }

    /**
     * Given a symbol, this function translates it into a type T using the function FUNC.
     * For example, if T is initialized as URL, FUNC should be a function capable of translating a symbol into a URL.
     * Applying the FUNC function will translate the symbol into URL and return it as the return type.
     *
     * @param func function to apply on symbol
     * @param s    symbol
     * @return a translated type
     */
    @Override
    public T translate(@NotNull Function<SymbolInterface, T> func, SymbolInterface s) {
        return func.apply(s);
    }
}
