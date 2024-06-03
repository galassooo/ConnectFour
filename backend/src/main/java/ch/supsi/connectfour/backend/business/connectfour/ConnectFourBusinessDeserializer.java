package ch.supsi.connectfour.backend.business.connectfour;

import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * This is a deserializer, a class used in combination with the Jackson library to obtain custom deserialization behaviour
 * Further information on why we decided to use a custom deserializer can be found in the body of the deserialize method
 */
public class ConnectFourBusinessDeserializer extends JsonDeserializer<ConnectFourBusiness> {

    private static final List<String> fieldNames = List
            .of("player1", "player2", "currentPlayer", "pathToSave",
                "finished", "lastPositionOccupied", "wasLastMoveValid", "gameMatrix");

    /**
     * A better approach for this would have been using reflections to extract all the names of the fields dynamically
     * so that the code would be more resilient to change. Due to time constraints, this was unfortunately not possible
     * so the name of the fields are hardcoded
     */
    @Override
    public ConnectFourBusiness deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        for (String fieldName : fieldNames) {
            if (!node.has(fieldName)) {
                System.err.println("Error! The selected file is not valid!");
                throw new IOException();
            }
        }

        ConnectFourPlayerInterface player1 = jp.getCodec().treeToValue(node.get("player1"), ConnectFourPlayerInterface.class);
        ConnectFourPlayerInterface player2 = jp.getCodec().treeToValue(node.get("player2"), ConnectFourPlayerInterface.class);
        ConnectFourPlayerInterface currentPlayer = jp.getCodec().treeToValue(node.get("currentPlayer"), ConnectFourPlayerInterface.class);

        ConnectFourBusiness connectFourBusiness = new ConnectFourBusiness(player1, player2);
        connectFourBusiness.setCurrentPlayer(currentPlayer);

        // If a field with the provided name exists, then read and load it onto the model
        if (node.has("pathToSave")) {
            Path pathToSave = jp.getCodec().treeToValue(node.get("pathToSave"), Path.class);
            connectFourBusiness.setPathToSave(pathToSave);
        }

        if (node.has("finished")) {
            boolean isFinished = node.get("finished").asBoolean();
            connectFourBusiness.setFinished(isFinished);
        }

        if (node.has("lastPositionOccupied")) {
            int[] lastPositionOccupied = jp.getCodec().treeToValue(node.get("lastPositionOccupied"), int[].class);
            connectFourBusiness.setLastPositionOccupied(lastPositionOccupied);
        }

        if (node.has("wasLastMoveValid")) {
            boolean wasLastMoveValid = node.get("wasLastMoveValid").asBoolean();
            connectFourBusiness.setWasLastMoveValid(wasLastMoveValid);
        }

        /*
         * The whole point of having this custom deserializer was to prevent Jacksons default deserializer to create a new
         * instance of player for each cell of the matrix. This custom behaviour allows us to only have 2 instances of
         * Player around the matrix, instead of 6*7 different instances representing the same players.
         */
        if (node.has("gameMatrix")) {
            JsonNode matrixNode = node.get("gameMatrix");
            ConnectFourPlayerInterface[][] gameMatrix = new ConnectFourPlayerInterface[6][7];
            for (int i = 0; i < matrixNode.size(); i++) {
                for (int j = 0; j < matrixNode.get(i).size(); j++) {
                    JsonNode cellNode = matrixNode.get(i).get(j);
                    if (cellNode.isNull()) {
                        gameMatrix[i][j] = null;
                    } else {
                        ConnectFourPlayerInterface player = jp.getCodec().treeToValue(cellNode, ConnectFourPlayerInterface.class);
                        if (player.equals(player1)) {
                            gameMatrix[i][j] = player1;
                        } else if (player.equals(player2)) {
                            gameMatrix[i][j] = player2;
                        }
                    }
                }
            }
            connectFourBusiness.setGameMatrix(gameMatrix);
        }

        return connectFourBusiness;
    }
}