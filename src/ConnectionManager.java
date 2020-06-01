import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {

    private Connection connection;
    private Statement statement;
    public int prodId = 1;

    public void connectToDatabase(String newName, String newPassword, String newURL) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        try {
            this.connection = DriverManager.getConnection(newURL, newName, newPassword);
            this.statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void createTable() throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS items (id MEDIUMINT NOT NULL AUTO_INCREMENT, prodid int(11), " +
                "name CHAR(30) NOT NULL, value INT NOT NULL, PRIMARY KEY (id))");
    }

    public void clearTable() throws SQLException {
        statement.executeUpdate("truncate TABLE items");
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public int increaseProdId(){
        return this.prodId++;
    }
}
