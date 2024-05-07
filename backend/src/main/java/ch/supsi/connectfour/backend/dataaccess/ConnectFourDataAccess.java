package ch.supsi.connectfour.backend.dataaccess;

import ch.supsi.connectfour.backend.business.connectfour.ConnectFourDataAccessInterface;

public class ConnectFourDataAccess implements ConnectFourDataAccessInterface {
    protected static ConnectFourDataAccess dataAccess;


    // TODO: implement singleton pattern (private constructor)
    public ConnectFourDataAccess getInstance() {
        if (dataAccess == null) {
            dataAccess = new ConnectFourDataAccess();
        }
        return dataAccess;
    }
}
