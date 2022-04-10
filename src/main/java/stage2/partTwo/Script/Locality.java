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
                "POPULATION LONG CHECK(POPULATION > 0)," +
                "DATEOFFOUNDATION DATE NOT NULL)");
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
        table[0] = new String[]{"ID", "Наименование", "Тип", "Площадь", "Население", "Дата основания"};

        int i = 1;
        while (resultSet.next()) {
            table[i] = new String[]{resultSet.getString("ID"),
                    resultSet.getString("name"),
                    resultSet.getString("type"),
                    resultSet.getString("square"),
                    resultSet.getString("population"),
                    resultSet.getString("dateOfFoundation")};
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
        table[0] = new String[]{"ID", "Наименование", "Тип", "Площадь", "Население", "Дата основания"};

        int i = 1;
        while (resultSet.next()) {
            table[i] = new String[]{resultSet.getString("ID"),
                    resultSet.getString("NAME"),
                    resultSet.getString("TYPE"),
                    resultSet.getString("SQUARE"),
                    resultSet.getString("POPULATION"),
                    resultSet.getString("DATEOFFOUNDATION")};
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
        table[0] = new String[]{"ID", "Наименование", "Тип", "Площадь", "Население", "Дата основания"};

        int i = 1;
        while (resultSet.next()) {
            table[i] = new String[]{resultSet.getString("ID"),
                    resultSet.getString("NAME"),
                    resultSet.getString("TYPE"),
                    resultSet.getString("SQUARE"),
                    resultSet.getString("POPULATION"),
                    resultSet.getString("DATEOFFOUNDATION")};
            i++;
        }

        PrintTable printTable = new PrintTable();
        printTable.print(table);
    }

    @Override
    public void executeAdding() throws SQLException {
        Scanner scan = new Scanner(System.in);
        String name, type, date;
        int square;
        long population;
        int flag = 0;

        System.out.println("Введите название населённого пункта: ");
        name = scan.nextLine();

        System.out.println("Введите тип населённого пункта: ");
        type = scan.nextLine();

        System.out.println("Введите площадь населённого пункта: ");
        square = Integer.parseInt(scan.nextLine());

        System.out.println("Введите количество жителей: ");
        population = Long.parseLong(scan.nextLine());

        System.out.println("Введите дату основания (формат yyyy-mm-dd): ");
        date = scan.nextLine();

        try {
            stat.execute("INSERT INTO LOCALITY (NAME, TYPE, SQUARE, POPULATION, DATEOFFOUNDATION) values('"
                    + name + "', '" + type + "', " + square + ", " + population + ", '" + date + "');");
            flag = 1;
        } catch (Exception ex) {
            System.out.println("Введены недопустимые параматры города! Вызвалась ошибка:\n" + ex);
        }
        if (flag == 1) {
            System.out.println("\nНаселённый пункт добавлен!\n");
        }
    }

    @Override
    public void executeUpdating() throws SQLException {
        Scanner scan = new Scanner(System.in);
        showAll();
        System.out.println("Введите ID населённого пункта, в котором хотите изменить информацию:");
        long idLocCh = scan.nextLong();

        System.out.println("Старая информация:");
        printCurrent(idLocCh);

        int flag = 1;
        while (flag != 0) {
            System.out.println("""
                    Какую информацию вы хотите изменить?
                    1) Название населённого пункта
                    2) Тип населённого пункта
                    3) Площадь населённого пункта
                    4) Количесвто жителей
                    5) Дату основания
                    6) Изменить всю информацию
                    0) Закончить изменение""");
            String str = scan.nextLine();
            switch (str) {
                case "1": {
                    System.out.println("Введите новое название населённого пункта:");
                    str = scan.nextLine();
                    stat.execute("UPDATE LOCALITY " +
                            "SET NAME = '" + str + "' WHERE ID = " + idLocCh);
                    break;
                }
                case "2": {
                    System.out.println("Введите новый тип населённого пункта:");
                    str = scan.nextLine();
                    stat.execute("UPDATE LOCALITY " +
                            "SET TYPE = '" + str + "' WHERE ID = " + idLocCh);
                    break;
                }
                case "3": {
                    System.out.println("Введите новую площадь населённого пункта:");
                    str = scan.nextLine();
                    stat.execute("UPDATE LOCALITY " +
                            "SET SQUARE = " + str + "' WHERE ID = " + idLocCh);
                    break;
                }
                case "4": {
                    System.out.println("Введите новое количество жителей населённого пункта:");
                    str = scan.nextLine();
                    stat.execute("UPDATE LOCALITY " +
                            "SET POPULATION = " + str + "' WHERE ID = " + idLocCh);
                    break;
                }
                case "5": {
                    System.out.println("Введите новую дату основания населённого пункта:");
                    str = scan.nextLine();
                    stat.execute("UPDATE LOCALITY " +
                            "SET DATEOFFOUNDATION = '" + str + "' WHERE ID = " + idLocCh);
                    break;
                }
                case "6": {
                    System.out.println("Введите всю новую информацию о городе\n" +
                            "Введите название населённого пункта:");
                    String name = scan.nextLine();

                    System.out.println("Введите тип населённого пункта:");
                    String type = scan.nextLine();

                    System.out.println("Введите площадь населённого пункта:");
                    int square = Integer.parseInt(scan.nextLine());

                    System.out.println("Введите количество жителей в тысячах:");
                    long population = Long.parseLong(scan.nextLine());

                    System.out.println("Введите дату основания:");
                    String date = scan.nextLine();

                    stat.execute("UPDATE LOCALITY " +
                            "SET NAME = '" + name + "', TYPE = '" + type + "', SQUARE = " + square + ", POPULATION = "
                            + population + ", DATEOFFOUNDATION = '" + date + "' WHERE ID = " + idLocCh);
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
        System.out.println("Введите ID населённого пункта который необходимо удалить:");
        long id = scan.nextLong();

        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM STREETS WHERE IDLOCALITY = " + id);
        while (resultSet.next()) {
            stat.execute("DELETE FROM STREETS WHERE ID = " + resultSet.getString("ID"));
        }

        stat.execute("DELETE FROM LOCALITY WHERE ID = " + id);
        System.out.println("Новый список:");
        showAll();
    }
}