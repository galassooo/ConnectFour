package ch.supsi.connectfour.frontend.model.exit;

import ch.supsi.connectfour.frontend.model.translations.TranslationModel;

public class ApplicationExitModel implements IExitModel {

    /* self reference */
    private static ApplicationExitModel instance;

    /* Data used by the view */
    private final String message;
    private final String title;
    private final String confirmText;
    private final String cancelText;

    private ApplicationExitModel() {
        TranslationModel translationModel = TranslationModel.getInstance();
        this.message = translationModel.translate("label.close_confirmation");
        this.title = translationModel.translate("label.confirm");
        this.confirmText = translationModel.translate("label.confirm");
        this.cancelText = translationModel.translate("label.cancel");
    }

    public static ApplicationExitModel getInstance() {
        if (instance == null) {
            instance = new ApplicationExitModel();
        }
        return instance;
    }

    /* getters */
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getConfirmText() {
        return confirmText;
    }

    @Override
    public String getCancelText() {
        return cancelText;
    }
}
