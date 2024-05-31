package ch.supsi.connectfour.frontend.view.preferences;

import ch.supsi.connectfour.backend.business.symbols.SymbolBusiness;
import ch.supsi.connectfour.frontend.model.preferences.IPreferencesModel;
import javafx.event.ActionEvent;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;


/**
 * Defines the behaviour that a generic preferences view should expose to the controller
 */
public interface IPreferencesView {
    void setSymbols(List<SymbolBusiness> supportedShapes);

    void setColorPickerLocale(Locale locale);

    void setLanguages(List<String> supportedLanguages);

    void setOnCancelButton(Consumer<ActionEvent> eventConsumer);

    void setOnSaveButton(Consumer<ActionEvent> eventConsumer);

    void setModel(IPreferencesModel model);

    String getSelectedLanguage();

    String getPlayerOneColor();

    String getPlayerTwoColor();

    SymbolBusiness getPlayerOneShape();

    SymbolBusiness getPlayerTwoShape();
}
