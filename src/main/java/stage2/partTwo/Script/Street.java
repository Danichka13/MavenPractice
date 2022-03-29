package stage2.partTwo.Script;

import stage2.demo.PrintTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Street extends Operation implements OperationTable {
    public Street() throws SQLException {
        super("streets");
    }

    @Override
    public void createTable() throws SQLException {
        stat.execute("CREATE TABLE IF NOT EXISTS streets(" +
                "ID BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR(255) NOT NULL," +
                "IDLOCALITY BIGINT NOT NULL," +
                "TYPE VARCHAR(255) NOT NULL," +
                "created_by  VARCHAR(50) NOT NULL," +
                "created_when DATETIME NOT NULL," +
                "updated_by VARCHAR(50) NOT NULL," +
                "updated_when DATETIME NOT NULL," +
                "FOREIGN KEY (idLocality) REFERENCES Locality(id))");
    }

    public void printStreetsOfLocality(String localityName) throws SQLException {
        long id = getIDIfManyLocalities(getCountLocality(localityName), localityName);
        int count = 0;

        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSetStreet = statement.executeQuery("SELECT * FROM STREETS WHERE IDLOCALITY = " + id);
        while (resultSetStreet.next()) {
            count++;
        }
        resultSetStreet.beforeFirst();

        String[][] table = new String[count + 1][];
        table[0] = new String[]{"ID", "Наименование", "Тип", "Кем создано", "Когда создано", "Кем обновлено", "Когда обновлено"};

        int i = 1;
        while (resultSetStreet.next()) {
            table[i] = new String[]{resultSetStreet.getString("ID"),
                    resultSetStreet.getString("NAME"),
                    resultSetStreet.getString("TYPE"),
                    resultSetStreet.getString("created_by"),
                    resultSetStreet.getString("created_when"),
                    resultSetStreet.getString("updated_by"),
                    resultSetStreet.getString("updated_when")};
            i++;
        }
        if (checkCityStreet(i)) {
            PrintTable printTable = new PrintTable();
            printTable.print(table);
        }
    }

    @Override
    public void printCurrent(long id) throws SQLException {
        int count = 0;
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSetStreet = statement.executeQuery("SELECT * FROM STREETS WHERE IDLOCALITY = " + id);
        while (resultSetStreet.next()) {
            count++;
        }
        resultSetStreet.beforeFirst();
        String[][] table = new String[count + 1][];
        table[0] = new String[]{"ID", "Наименование", "Тип", "Кем создано", "Когда создано", "Кем обновлено", "Когда обновлено"};

        int i = 1;
        while (resultSetStreet.next()) {
            table[i] = new String[]{resultSetStreet.getString("ID"),
                    resultSetStreet.getString("NAME"),
                    resultSetStreet.getString("TYPE"),
                    resultSetStreet.getString("created_by"),
                    resultSetStreet.getString("created_when"),
                    resultSetStreet.getString("updated_by"),
                    resultSetStreet.getString("updated_when")};
            i++;
        }
        if (checkCityStreet(i)) {
            PrintTable printTable = new PrintTable();
            printTable.print(table);
        }
    }

    @Override
    public void executeAdding() throws SQLException {
        Scanner scan = new Scanner(System.in);
        String name, type;
        long id;

        Locality locality = new Locality();
        locality.showAll();

        System.out.println("Введите название населенного пункта, куда хотите добавить улицу:");
        name = scan.nextLine();
        boolean check = false;
        id = getIDIfManyLocalities(getCountLocality(name), name);
        ResultSet resultSet = stat.executeQuery("SELECT * FROM LOCALITY WHERE NAME = '" + name + "'");
        while (resultSet.next()) {
            if (id == resultSet.getLong("ID")) {
                check = true;
                break;
            }
        }
        if (!check) {
            System.out.println("\nТакого города нет!\n");
            return;
        }
        System.out.println("Введите название улицы:");
        name = scan.nextLine();

        System.out.println("Введите тип улицы:");
        type = scan.nextLine();

        stat.execute("insert into streets (NAME, IDLOCALITY, TYPE, created_by, created_when, updated_by, updated_when)  " +
                "values('" + name + "', " + resultSet.getLong("ID") + ", '" + type + "', SESSION_USER, CURRENT_TIMESTAMP, 'DID NOT UPDATED', CURRENT_TIMESTAMP);");

        System.out.println("\nУлица добавлена!\n");
    }

    @Override
    public void executeUpdating() throws SQLException {
        Scanner scan = new Scanner(System.in);

        System.out.println("Введите имя населенного пункта:");
        String nameLocality = scan.nextLine();
        printStreetsOfLocality(nameLocality);

        System.out.println("Введите ID улицы, в которой хотите изменить информацию:");
        long idLocCh = Integer.parseInt(scan.nextLine());

        System.out.println("Старая информация:");
        printCurrent(idLocCh);

        int flag = 1;
        while (flag != 0) {
            System.out.println("""
                    Какую информацию вы хотите изменить?
                    1) Название улицы
                    2) Тип улицы
                    3) Изменить всю информацию
                    0) Закончить изменение""");
            String str = scan.nextLine();
            switch (str) {
                case "1": {
                    System.out.println("Введите новое название улицы:");
                    str = scan.nextLine();
                    stat.execute("UPDATE STREETS " +
                            "SET NAME = '" + str + "', updated_by = SESSION_USER, updated_when = CURRENT_TIMESTAMP" +
                            " WHERE ID = " + idLocCh);
                }
                case "2": {
                    System.out.println("Введите новый тип улицы:");
                    str = scan.nextLine();
                    stat.execute("UPDATE STREETS " +
                            "SET TYPE = '" + str + "', updated_by = SESSION_USER, updated_when = CURRENT_TIMESTAMP " +
                            "WHERE ID = " + idLocCh);
                }
                case "3": {
                    System.out.println("Введите всю новую информацию об улице:");
                    System.out.println("Введите новое название улицы:");
                    String nameStreet = scan.nextLine();

                    System.out.println("Введите новый тип улицы:");
                    String nameType = scan.nextLine();

                    stat.execute("UPDATE STREETS " +
                            "SET NAME = '" + nameStreet + "', TYPE = '" + nameType +
                            "', updated_by = SESSION_USER, updated_when = CURRENT_TIMESTAMP" +
                            " WHERE ID = " + idLocCh);
                }
                case "0": {
                    flag = 0;
                }
            }
            System.out.println("Изменённая информация:");
            printCurrent(idLocCh);
        }
    }

    @Override
    public void executeDelete() throws SQLException {
        Scanner scan = new Scanner(System.in);
        Locality locality = new Locality();
        locality.showAll();
        System.out.println("Введите название населенного пункта в котором необходимо удалить улицу:");
        String str = scan.nextLine();
        printStreetsOfLocality(str);
        System.out.println("Введите ID улицы, которую хотите удалить:");
        long id = scan.nextLong();

        stat.execute("DELETE FROM STREETS WHERE ID = " + id);
        System.out.println("Новый список:");
        printStreetsOfLocality(str);
    }

    public int getCountLocality(String nameLoc) throws SQLException {
        int count = 0;
        ResultSet resultSet = stat.executeQuery("SELECT * FROM LOCALITY WHERE NAME = '" + nameLoc + "'");

        while (resultSet.next()) {
            count++;
        }
        return count;
    }

    public int getIdLocality(String nameLoc) throws SQLException {
        int count = 0;
        ResultSet resultSet = stat.executeQuery("SELECT * FROM LOCALITY WHERE NAME = '" + nameLoc + "'");

        while (resultSet.next()) {
            count++;
        }
        return count;
    }

    public long getIDIfManyLocalities(int count, String nameLoc) throws SQLException {
        long id;
        ResultSet resultSet = stat.executeQuery("SELECT ID FROM LOCALITY WHERE NAME = '" + nameLoc + "'");

        if (count == 1) {
            resultSet.next();
            id = resultSet.getLong("ID");
        } else if (count > 1) {
            System.out.println("Городов с таким названием больше чем 1:");
            Locality locality = new Locality();
            locality.printCurrent(nameLoc);
            System.out.println("Введите ID нужного города:");
            Scanner sc = new Scanner(System.in);
            id = sc.nextLong();
        } else {
            return 0;
        }
        return id;
    }

    public boolean checkCityStreet(int check) {
        if (check == 1) {
            System.out.println("\nНет такого населённого пункта или отсутствует улица в заданном населённом пункте! Попробуйте еще раз!");
            return false;
        } else {
            return true;
        }
    }
}