package ch.supsi.connectfour.backend.application.serialization;

import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.backend.business.serialization.SerializationModel;

import java.io.File;

/**
 * TODO: delete before merging
 * Notes:
 *
 *  * Controller
 *  *
 *  * Let it be responsible for handling requests and providing response. Also responsible for controlling flow of application.
 *  * Controllers should only call Business Logic methods and they should not contain any business logic
 *  * Don't interact with Data Access Layer directly. Let this Business Logic Layet take this responsibility.
 *  *
 *
 * Model
 *
 * Let it be responsible for transferring data between layers.
 * Use plain classes as View Models or Input Models.
 * Don't put business logic or data-access into model classes.
 *
 * Business Logic
 *
 * Let it be responsible for business operations like changing data, processing data, search and loading data.
 * Use data access layer methods for CRUD operations. Don't rely on database directly. Let Data Access Layer handle CRUD operations.
 * Create each business method as a single unit of work which
 * Data Access
 *
 * Let it be responsible for CRUD operations with data.
 * While you can use a ORM instead of this layer, but in a large application, you can also create this layer as layer which uses ORM.
 */

public class SerializationController {
    private static SerializationController instance;
    private final SerializationBusinessInterface model;

    /**
     * Private constructor, initializes both the static reference to this instance of controller
     * and the model
     */
    private SerializationController() {
        instance = this;
        this.model = SerializationModel.getInstance();
    }

    // Singleton
    public static SerializationController getInstance() {
        if (instance == null)
            return instance;
        return new SerializationController();
    }


    /*
    What it must do:
     - Given a Game model, it should allow to serialize it
     - Given the name of a save, it should allow to retrieve the game
     */

    public ConnectFourModel getSave(final File file) {
        ConnectFourModel model = this.model.getSave(file);
        if (model == null) {
            // TODO: do something
        }
        return model;
    }
    public boolean persist(final ConnectFourModel model, final File file) {
        if (this.model.persist(model, file)) {
            return true;
        } else {
            return false;
        }
    }
}
