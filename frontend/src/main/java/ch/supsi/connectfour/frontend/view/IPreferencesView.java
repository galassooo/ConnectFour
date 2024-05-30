package ch.supsi.connectfour.frontend.view;

import ch.supsi.connectfour.backend.business.symbols.Symbol;
import ch.supsi.connectfour.frontend.model.PreferencesModel;
import javafx.event.ActionEvent;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public interface IPreferencesView {
    void setShapes(List<Symbol> supportedShapes);

    void setColorPickerLocale(Locale locale);

    void setLanguages(List<String> supportedLanguages);

    void setOnCancelButton(Consumer<ActionEvent> eventConsumer);

    void setOnSaveButton(Consumer<ActionEvent> eventConsumer);

    void setModel(PreferencesModel model);

    String getSelectedLanguage();

    String getPlayerOneColor();

    String getPlayerTwoColor();

    Symbol getPlayerOneShape();

    Symbol getPlayerTwoShape();
}
