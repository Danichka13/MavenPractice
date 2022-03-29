package stage2.partTwo.Script;

import java.sql.SQLException;

public interface OperationTable {
    void createTable() throws SQLException;
    void executeAdding() throws SQLException;
    void executeUpdating() throws SQLException;
    void executeDelete() throws SQLException;
    void printCurrent(long id) throws SQLException;
}
