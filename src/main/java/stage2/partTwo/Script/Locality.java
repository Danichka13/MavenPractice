package stage2.partTwo.Script;

import stage2.demo.PrintTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Locality extends Operation implements OperationTable {
    public Locality() throws SQLException {
        super("locality");
    }

    @Override
    public void createTable() throws SQLException {
        stat.execute("CREATE TABLE IF NOT EXISTS locality(" +
                "ID BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR(255) NOT NULL," +
                "TYPE VARCHAR(255) NOT NULL," +
                "SQUARE INT CHECK(SQUARE > 0)," +
                "POPULATION DOUBLE CHECK(POPULATION > 0)," +
                "DATEOFFOUNDATION DATE NOT NULL," +
                "created_by VARCHAR(50) NOT NULL," +
                "created_when DATETIME NOT NULL," +
                "updated_by VARCHAR(50)," +
                "updated_when DATETIME)");
    }

    public void showAll() throws SQLException {
        int count = 0;
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM locality");
        while (resultSet.next()) {
            count++;
        }
        resultSet.beforeFirst();

        String[][] table = new String[count + 1][];
        table[0] = new String[]{"ID", "Наименование", "Тип", "Площадь", "Население",
                "Дата основания", "Кем создано", "Когда создано", "Кем обновлено", "Когда обновлено"};

        int i = 1;
        while (resultSet.next()) {
            table[i] = new String[]{resultSet.getString("ID"),
                    resultSet.getString("name"),
                    resultSet.getString("type"),
                    resultSet.getString("square"),
                    resultSet.getString("population"),
                    resultSet.getString("dateOfFoundation"),
                    resultSet.getString("created_by"),
                    resultSet.getString("created_when"),
                    resultSet.getString("updated_by"),
                    resultSet.getString("updated_when")};
            i++;
        }
        PrintTable printTable = new PrintTable();
        printTable.print(table);
    }

    @Override
    public void printCurrent(long id) throws SQLException {
        int count = 0;
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM LOCALITY WHERE ID = " + id);
        while (resultSet.next()) {
            count++;
        }
        resultSet.beforeFirst();

        String[][] table = new String[count + 1][];
        table[0] = new String[]{"ID", "Наименование", "Тип", "Площадь", "Население",
                "Дата основания", "Кем создано", "Когда создано", "Кем обновлено", "Когда обновлено"};

        int i = 1;
        while (resultSet.next()) {
            table[i] = new String[]{resultSet.getString("ID"),
                    resultSet.getString("NAME"),
                    resultSet.getString("TYPE"),
                    resultSet.getString("SQUARE"),
                    resultSet.getString("POPULATION"),
                    resultSet.getString("DATEOFFOUNDATION"),
                    resultSet.getString("created_by"),
                    resultSet.getString("created_when"),
                    resultSet.getString("updated_by"),
                    resultSet.getString("updated_when")};
            i++;
        }
        PrintTable printTable = new PrintTable();
        printTable.print(table);
    }

    public void printCurrent(String nameLoc) throws SQLException {
        int count = 0;
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM LOCALITY WHERE NAME = '" + nameLoc + "'");
        while (resultSet.next()) {
            count++;
        }
        resultSet.beforeFirst();

        String[][] table = new String[count + 1][];
        table[0] = new String[]{"ID", "Наименование", "Тип", "Площадь", "Население",
                "Дата основания", "Кем создано", "Когда создано", "Кем обновлено", "Когда обновлено"};

        int i = 1;
        while (resultSet.next()) {
            table[i] = new String[]{resultSet.getString("ID"),
                    resultSet.getString("NAME"),
                    resultSet.getString("TYPE"),
                    resultSet.getString("SQUARE"),
                    resultSet.getString("POPULATION"),
                    resultSet.getString("DATEOFFOUNDATION"),
                    resultSet.getString("created_by"),
                    resultSet.getString("created_when"),
                    resultSet.getString("updated_by"),
                    resultSet.getString("updated_when")};
            i++;
        }

        PrintTable printTable = new PrintTable();
        printTable.print(table);
    }

    @Override
    public void executeAdding() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String name, type, date;
        int square;
        double population;


        System.out.println("Введите название населенного пункта: ");
        name = sc.nextLine();

        System.out.println("Введите тип населенного пункта: ");
        type = sc.nextLine();

        System.out.println("Введите площадь населенного пункта: ");
        square = Integer.parseInt(sc.nextLine());

        System.out.println("Введите количество жителей в тысячах: ");
        population = Double.parseDouble(sc.nextLine());

        System.out.println("Введите дату основания (формат yyyy-mm-dd): ");
        date = sc.nextLine();

        try {
            stat.execute("INSERT INTO LOCALITY (NAME, TYPE, SQUARE, POPULATION, DATEOFFOUNDATION, created_by," +
                    "created_when, updated_by, updated_when) values('" + name + "', '" + type + "', " + square + ", "
                    + population + ", '" + date + "', SESSION_USER, CURRENT_TIMESTAMP, 'DID NOT UPDATED', CURRENT_TIMESTAMP);");
        } catch (Exception ex) {
            System.out.println("Введены недопустимые параматры города! Вызвалась ошибка:\n" + ex);
        }
        System.out.println("\nНаселенный пункт добавлен!\n");
    }

    @Override
    public void executeUpdating() throws SQLException {
        Scanner scan = new Scanner(System.in);
        showAll();
        System.out.println("Введите ID населенного пункта, в котором хотите изменить информацию:");
        long idLocCh = scan.nextLong();

        System.out.println("Старая информация:");
        printCurrent(idLocCh);

        int flag = 1;
        while (flag != 0) {
            System.out.println("""
                    Какую информацию вы хотите модифицировать?
                    1) Название населенного пункта
                    2) Тип населенного пункта
                    3) Площадь населенного пункта
                    4) Количесвто жителей
                    5) Дату основания
                    6) Модифицировать всю информацию
                    0) Закончить модификацию""");
            String str = scan.nextLine();
            switch (str) {
                case "1": {
                    System.out.println("Введите новое название населенного пункта:");
                    str = scan.nextLine();
                    stat.execute("UPDATE LOCALITY " +
                            "SET NAME = '" + str + "', updated_by = SESSION_USER, updated_when = CURRENT_TIMESTAMP" +
                            " WHERE ID = " + idLocCh);
                    break;
                }
                case "2": {
                    System.out.println("Введите новый тип населенного пункта:");
                    str = scan.nextLine();
                    stat.execute("UPDATE LOCALITY " +
                            "SET TYPE = '" + str + "', updated_by = SESSION_USER, updated_when = CURRENT_TIMESTAMP" +
                            " WHERE ID = " + idLocCh);
                    break;
                }
                case "3": {
                    System.out.println("Введите новую площадь населенного пункта:");
                    str = scan.nextLine();
                    stat.execute("UPDATE LOCALITY " +
                            "SET SQUARE = " + str + ", updated_by = SESSION_USER, updated_when = CURRENT_TIMESTAMP" +
                            " WHERE ID = " + idLocCh);
                    break;
                }
                case "4": {
                    System.out.println("Введите новое количество жителей населенного пункта:");
                    str = scan.nextLine();
                    stat.execute("UPDATE LOCALITY " +
                            "SET POPULATION = " + str + ", updated_by = SESSION_USER, updated_when = CURRENT_TIMESTAMP" +
                            " WHERE ID = " + idLocCh);
                    break;
                }
                case "5": {
                    System.out.println("Введите новую дату основания населенного пункта:");
                    str = scan.nextLine();
                    stat.execute("UPDATE LOCALITY " +
                            "SET DATEOFFOUNDATION = '" + str + "', updated_by = SESSION_USER, updated_when = CURRENT_TIMESTAMP" +
                            " WHERE ID = " + idLocCh);
                    break;
                }
                case "6": {
                    System.out.println("Введите всю новую информацию о городе");
                    System.out.println("Введите название населенного пункта:");
                    String name = scan.nextLine();

                    System.out.println("Введите тип населенного пункта:");
                    String type = scan.nextLine();

                    System.out.println("Введите площадь населенного пункта:");
                    int square = Integer.parseInt(scan.nextLine());

                    System.out.println("Введите количество жителей в тысячах:");
                    double population = Double.parseDouble(scan.nextLine());

                    System.out.println("Введите дату основания:");
                    String date = scan.nextLine();

                    stat.execute("UPDATE LOCALITY " +
                            "SET NAME = '" + name + "', TYPE = '" + type + "', SQUARE = " + square + ", POPULATION = "
                            + population + ", updated_by = SESSION_USER, updated_when = CURRENT_TIMESTAMP" +
                            ", DATEOFFOUNDATION = '" + date + "' WHERE ID = " + idLocCh);
                    break;
                }
                case "0": {
                    flag = 0;
                    break;
                }
                default: {
                    System.out.println("Такого вариант нет!");
                    break;
                }
            }
        }
        System.out.println("Изменённая информация:");
        printCurrent(idLocCh);
    }

    @Override
    public void executeDelete() throws SQLException {
        Scanner scan = new Scanner(System.in);
        showAll();
        System.out.println("Введите ID населенного пункта который необходимо удалить:");
        long id = scan.nextLong();

        stat.execute("DELETE FROM LOCALITY WHERE ID = " + id);
        System.out.println("Новый список:");
        showAll();
    }
}