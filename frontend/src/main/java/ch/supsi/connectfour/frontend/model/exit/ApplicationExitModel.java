package ch.supsi.connectfour.frontend.model.exit;

import ch.supsi.connectfour.backend.application.translations.TranslationsApplication;
import ch.supsi.connectfour.frontend.model.Translatable;

import java.util.HashMap;
import java.util.List;

public class ApplicationExitModel implements Translatable {

    /* self reference */
    private static ApplicationExitModel instance;

    /* Data used by the view */
    private final HashMap<String, String> translatedLabels = new HashMap<>();


    private ApplicationExitModel() {

    }

    public static ApplicationExitModel getInstance() {
        if (instance == null) {
            instance = new ApplicationExitModel();
        }
        return instance;
    }

    @Override
    public String getTranslation(String key){
        return translatedLabels.get(key);
    }

    @Override
    public void translateAndSave() {
        List<String> list = List.of(
                "label.close_confirmation","label.confirmation", "label.cancel", "label.confirm" );
        TranslationsApplication translator = TranslationsApplication.getInstance();
        list.forEach( key -> translatedLabels.putIfAbsent(key, translator.translate(key)));
    }
}
