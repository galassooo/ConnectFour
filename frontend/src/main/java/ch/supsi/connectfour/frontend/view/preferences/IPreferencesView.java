package ch.supsi.connectfour.frontend.view.preferences;

import ch.supsi.connectfour.backend.business.symbols.SymbolBusiness;
import ch.supsi.connectfour.frontend.model.preferences.IPreferencesModel;
import ch.supsi.connectfour.frontend.model.preferences.PreferencesModel;
import javafx.event.ActionEvent;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public interface IPreferencesView {
    void setShapes(List<SymbolBusiness> supportedShapes);

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
