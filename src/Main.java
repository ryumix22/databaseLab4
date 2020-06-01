import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private final static String NAME = "root";
    private final static String PASSWORD = "root";
    private final static String URL = "jdbc:mysql://localhost:3306/test?useSSL=false";
    private final static int ITEMS = 30;
    private static final ConnectionManager connectionManager = new ConnectionManager();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        DataBaseManager dataBaseManager = new DataBaseManager(connectionManager);
        connectionManager.connectToDatabase(NAME, PASSWORD, URL);
        connectionManager.createTable();
        connectionManager.clearTable();

        Random r = new Random();
        for (int i = 1; i < ITEMS; i++) {
            String name = "items" + i;
            dataBaseManager.addItem(name, r.nextInt(5000 - 100) + 100);
        }

        Scanner s = new Scanner(System.in);
        String command = "";
        while (!command.equals("/quit")) {
            command = s.nextLine();
            String[] input = command.split(" ");
            try {
                switch (input[0]) {
                    case "/showall" -> dataBaseManager.showAll();
                    case "/add" -> dataBaseManager.addItem(input[1], Integer.parseInt(input[2]));
                    case "/delete" -> dataBaseManager.deleteItem(input[1]);
                    case "/showprice" -> dataBaseManager.showPrice(input[1]);
                    case "/changeprice" -> dataBaseManager.changePrice(input[1], Integer.parseInt(input[2]));
                    case "/filterbyprice" -> dataBaseManager.getPriceInterval(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                    default -> System.out.println("Incorrect input");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        s.close();
    }
}