import java.sql.*;

public class DataBaseManager {

    private final ConnectionManager connectionManager;

    public DataBaseManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void showAll() throws SQLException {
        ResultSet resultSet = connectionManager.getStatement().executeQuery("SELECT * FROM items");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(2)
                    + " " + resultSet.getString(3)
                    + " " + resultSet.getInt(4));
        }
    }

    public void addItem(String name, int price) throws SQLException {
        if (checkContains(name)) {
            System.out.println("Item already exists");
        } else {
            String query = "INSERT INTO items (prodid, name, value) VALUES (?, ?, ?) ";
            PreparedStatement preparedStatement = connectionManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, connectionManager.increaseProdId());
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, price);
            preparedStatement.execute();
        }
    }

    public void deleteItem(String name) throws SQLException {
        if (checkContains(name)) {
            connectionManager.getStatement().executeUpdate("DELETE FROM items WHERE NAME = '" + name + "'");
        } else System.out.println("Item doesnt exist");
    }

    public void showPrice(String name) throws SQLException {
        if (checkContains(name)) {
            ResultSet resultSet = connectionManager.getStatement().executeQuery("SELECT VALUE FROM items WHERE NAME = '" + name + "'");
            resultSet.next();
            System.out.println(resultSet.getInt(1));
        } else System.out.println("Item doesnt exits");
    }

    public void changePrice(String name, int newPrice) throws SQLException {
        if (checkContains(name)) {
            connectionManager.getStatement().executeUpdate("UPDATE items SET VALUE = " + newPrice + " WHERE NAME = '" + name + "'");
        } else System.out.println("Item doesnt exits");
    }

    public void getPriceInterval(int lowest, int highest) throws SQLException {
        ResultSet resultSet = connectionManager.getStatement().executeQuery("SELECT * FROM items WHERE VALUES BETWEEN" +
                " " + lowest + " AND " + highest);
        int c = 0;
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(2)
                    + " " + resultSet.getString(3)
                    + " " + resultSet.getInt(4));
            c++;
        }
        if (c == 0) System.out.println("No such items");
    }

    private Boolean checkContains(String name) throws SQLException {
        ResultSet resultSet = connectionManager.getStatement().executeQuery("SELECT count(*) FROM items WHERE NAME = '" + name + "'");
        resultSet.next();
        return resultSet.getInt("count(*)") == 1;
    }
}