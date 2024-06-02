package ch.supsi.connectfour.frontend.model;


public interface Translatable {
    String getTranslation(String key);

    void translateAndSave();
}
