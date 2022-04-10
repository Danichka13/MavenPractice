package stage2.partTwo.Script;

import stage2.demo.App;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Search extends Operation {
    Street street = new Street();

    public Search() throws SQLException {
        super("search");
    }

    public void getInformationStreet() throws SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите название населённого пункта:");
        String nameLocality = scan.nextLine();
        long id = street.getIDIfManyLocalities(street.getCountLocality(nameLocality), nameLocality);

        System.out.println("Введите название улицы (like запрос):");
        String nameStreet = scan.nextLine();

        ResultSet resultSet = stat.executeQuery("SELECT * FROM LOCALITY WHERE ID = " + id);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("TYPE") + " " +
                    resultSet.getString("NAME"));
        }
        printStreets(nameStreet, id);
    }

    public void printStreets(String nameStreet, long id) throws SQLException {
        ResultSet resultSet = stat.executeQuery("SELECT * FROM STREETS WHERE LOWER(NAME) " +
                "LIKE LOWER('%" + nameStreet + "%') AND IDLOCALITY = " + id + ";");
        while (resultSet.next()) {
            System.out.println("\t- " + resultSet.getString("TYPE") +
                    " \"" + resultSet.getString("NAME") + "\"");
        }
    }

    public void showAllInformation() throws SQLException {
        Scanner scan = new Scanner(System.in);
        Locality locality = new Locality();
        locality.showAll();
        System.out.println("Введите название населённого пункта:");
        String nameLocality = scan.nextLine();
        long idLocality = street.getIDIfManyLocalities(street.getCountLocality(nameLocality), nameLocality);

        if (idLocality == 0) {
            System.out.println("Такого населённого пункта нет!");
        }
        locality.printCurrent(idLocality);
        System.out.println("\nУлицы из этого населённого пункта:\n");
        street.printStreetsOfLocality(nameLocality);
    }

    public void menuSearches() throws SQLException, IOException {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> result = new ArrayList<>();
        boolean flag = true;
        String back = "";
        while (flag) {
            System.out.println("""
                    Получить справочную информацию о наличии населённых пунктов,
                    по следующим расширенным критериям поиска:
                    1) По названию населённого пункта (like запрос)
                    2) По количеству жителей (>,<,=,!=,<=,>=)
                    3) По площади (>,<,=,!=,<=,>=)
                    4) Дате основания (строгое соответствие и в диапазоне)
                    5) По названию улицы (like запрос)
                    6) По типу улицы (строгое соответствие)
                    0) Назад""");
            String str = scan.nextLine();
            back = str;
            switch (str) {
                case "1": {
                    result = searchByNameLocality();
                    break;
                }
                case "2": {
                    result = searchByPopulation();
                    break;
                }
                case "3": {
                    result = searchBySquare();
                    break;
                }
                case "4": {
                    result = searchByDate();
                    break;
                }
                case "5": {
                    result = searchByNameStreet();
                    break;
                }
                case "6": {
                    result = searchByTypeStreet();
                    break;
                }
                case "0": {
                    flag = false;
                    break;
                }
                default: {
                    System.out.println("\nТакого варианта ответа нет!\n");
                    return;
                }
            }
        }
        if (flag) {
            writingInFile(result);
        }
        if (back.equals("0")) {
            App.menu();
        }
    }

    public ArrayList<String> searchByNameLocality() throws SQLException {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> result = new ArrayList<>();

        System.out.println("\nВведите название населённого пункта(like запрос):");
        String nameLocality = scan.nextLine();

        System.out.println("Найденные населённые пункты:\n");
        ResultSet resultSet = stat.executeQuery("SELECT * FROM LOCALITY WHERE LOWER(NAME) " +
                "LIKE LOWER('%" + nameLocality + "%');");
        while (resultSet.next()) {
            System.out.println("\t- " + resultSet.getString("TYPE") +
                    " " + resultSet.getString("NAME") + " ");
            result.add(resultSet.getString("TYPE") + " " + resultSet.getString("NAME") + "\n");
        }
        return result;
    }

    public ArrayList<String> searchByPopulation() throws SQLException {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> result = new ArrayList<>();

        System.out.println("\nВведите выражение(Пример: > 10):");
        String str = scan.nextLine();
        str = str.replaceAll("\\s+", "");
        String symbol = getCompareSymbol(str);
        long population = getNumber(str);

        System.out.println("Найденные населённые пункты:\n");
        ResultSet resultSet = stat.executeQuery("SELECT * FROM LOCALITY WHERE POPULATION " + symbol + " " +
                population + ";");
        while (resultSet.next()) {
            System.out.println("\t- " + resultSet.getString("TYPE") +
                    "  " + resultSet.getString("NAME") + " с населением " + resultSet.getString("POPULATION") + " человек");
            result.add(resultSet.getString("TYPE")
                    + " " + resultSet.getString("NAME")
                    + " с населением " + resultSet.getString("POPULATION") + " человек\n");
        }
        return result;
    }

    public ArrayList<String> searchBySquare() throws SQLException {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> result = new ArrayList<>();

        System.out.println("\nВведите выражение(Пример: > 10):");
        String str = scan.nextLine();
        str = str.replaceAll("\\s+", "");
        String compareSymbol = getCompareSymbol(str);
        long population = getNumber(str);

        System.out.println("Найденные населённые пункты:\n");
        ResultSet resultSet = stat.executeQuery("SELECT * FROM LOCALITY WHERE SQUARE " + compareSymbol + " " +
                population + ";");
        while (resultSet.next()) {
            System.out.println("\t- " + resultSet.getString("TYPE") +
                    "  " + resultSet.getString("NAME") + " с площадью " + resultSet.getString("SQUARE"));
            result.add(resultSet.getString("TYPE")
                    + " " + resultSet.getString("NAME")
                    + " с площадью " + resultSet.getString("SQUARE") + "\n");
        }
        return result;
    }

    public ArrayList<String> searchByDate() throws SQLException {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> result = new ArrayList<>();
        int count = 0;
        System.out.println("""
                По какому критерию хотите найти?
                1)Строгое соответствие
                2)В диапазоне""");
        String str = scan.nextLine();
        switch (str) {
            case "1": {
                System.out.println("Введите дату строгое соответствие (формат yyyy-mm-dd):");
                str = scan.nextLine();

                System.out.println("Найденные населённые пункты:\n");
                ResultSet resultSet = stat.executeQuery("SELECT * FROM LOCALITY WHERE DATEOFFOUNDATION = '" + str + "';");
                while (resultSet.next()) {
                    count++;
                    System.out.println("\t- " + resultSet.getString("TYPE") +
                            "  " + resultSet.getString("NAME") + " c датой основания " + resultSet.getString("DATEOFFOUNDATION"));
                    result.add(resultSet.getString("TYPE")
                            + " " + resultSet.getString("NAME")
                            + " c датой основания " + resultSet.getString("DATEOFFOUNDATION") + "\n");
                }
                break;
            }
            case "2": {
                System.out.println("Введите первую дату из диапазона (формат yyyy-mm-dd):");
                String date1 = scan.nextLine();
                System.out.println("Введите вторую дату из диапазона (формат yyyy-mm-dd):");
                String date2 = scan.nextLine();

                System.out.println("Найденные населённые пункты:\n");
                ResultSet resultSet = stat.executeQuery("SELECT * FROM LOCALITY WHERE DATEOFFOUNDATION BETWEEN '" + date1 + "' AND '" + date2 + "';");
                while (resultSet.next()) {
                    count++;
                    System.out.println("\t- " + resultSet.getString("TYPE") +
                            "  " + resultSet.getString("NAME") + " c датой основания " + resultSet.getString("DATEOFFOUNDATION"));
                    result.add(resultSet.getString("TYPE")
                            + " " + resultSet.getString("NAME")
                            + " c датой основания " + resultSet.getString("DATEOFFOUNDATION") + "\n");
                }
                break;
            }
            default: {
                System.out.println("Такого варианта нет!");
                break;
            }
        }
        if (count == 0) {
            System.out.println("Населённого пункта с такой датой нет!");
            result.add("Населённого пункта с такой датой нет!");
        }
        return result;
    }

    public ArrayList<String> searchByNameStreet() throws SQLException {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> result = new ArrayList<>();
        System.out.println("Выбрано: По названию улицы (like запрос)\nВведите название улицы (like запрос):");
        String str = scan.nextLine();
        String id = "";
        int count = 0;

        ResultSet resultSet = stat.executeQuery("SELECT LOCALITY.ID, LOCALITY.TYPE, LOCALITY.NAME," +
                " STREETS.TYPE, STREETS.NAME" +
                " FROM LOCALITY, STREETS WHERE (LOWER(STREETS.NAME) " +
                "LIKE LOWER('%" + str + "%'))" +
                "AND (LOCALITY.ID = STREETS.IDLOCALITY);");

        while (resultSet.next()) {
            if (!id.equals(resultSet.getString("LOCALITY.ID"))) {
                System.out.println(resultSet.getString("LOCALITY.TYPE") + " \"" + resultSet.getString("LOCALITY.NAME") + "\"");
                result.add("\nВ " + resultSet.getString("LOCALITY.TYPE") + " \"" + resultSet.getString("LOCALITY.NAME") +
                        "\"\nНайденные улицы: ");
                id = resultSet.getString("LOCALITY.ID");
            }
            count++;
            System.out.println("\t- " + resultSet.getString("STREETS.TYPE") +
                    "\" " + resultSet.getString("STREETS.NAME") + "\"");
            result.add("\t- " + resultSet.getString("STREETS.TYPE") +
                    " \"" + resultSet.getString("STREETS.NAME") + "\"");
        }
        if (count == 0) {
            System.out.println("Населённых пунктов с такими именами улиц нет!");
            result.add("Населённых пунктов с такими именами улиц нет!");
        }
        return result;
    }

    public ArrayList<String> searchByTypeStreet() throws SQLException {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> result = new ArrayList<>();
        System.out.println("Введите тип улицы (строгое соответствие): ");
        String str = scan.nextLine();
        long id = -1;
        int count = 0;

        ResultSet resultSet = stat.executeQuery("SELECT LOCALITY.ID, LOCALITY.TYPE, LOCALITY.NAME, STREETS.TYPE, STREETS.NAME " +
                "FROM LOCALITY, STREETS WHERE (STREETS.TYPE = '" + str + "') " +
                "AND (LOCALITY.ID = STREETS.IDLOCALITY)  ;");

        while (resultSet.next()) {
            if (id != resultSet.getLong("LOCALITY.ID")) {
                System.out.println(resultSet.getString("LOCALITY.TYPE") + " \"" + resultSet.getString("LOCALITY.NAME") + "\"");
                result.add("В " + resultSet.getString("LOCALITY.TYPE") + " \"" + resultSet.getString("LOCALITY.NAME") +
                        "\"\nНайденные улицы:");

                id = resultSet.getLong("LOCALITY.ID");
            }
            count++;
            System.out.println("\t- " + resultSet.getString("STREETS.TYPE") +
                    "\" " + resultSet.getString("STREETS.NAME") + "\"");
            result.add("\t- " + resultSet.getString("STREETS.TYPE") +
                    " \"" + resultSet.getString("STREETS.NAME") + "\"");
        }
        if (count == 0) {
            System.out.println("Населённых пунктов с такими типами улиц нет!");
            result.add("Населённых пунктов с такими типами улиц нет!");
        }
        return result;
    }

    public String getCompareSymbol(String expression) {
        char[] arr = expression.toCharArray();
        StringBuilder compare = new StringBuilder();
        for (char c : arr) {
            if (!Character.isDigit(c)) {
                compare.append(c);
            }
        }
        return compare.toString();
    }

    public long getNumber(String expression) {
        char[] arr = expression.toCharArray();
        StringBuilder num = new StringBuilder();
        for (char c : arr) {
            if (Character.isDigit(c)) {
                num.append(c);
            }
        }
        return Long.parseLong(num.toString());
    }

    public void writingInFile(ArrayList<String> list) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("""
                Хотите ли сохранить результат в виде отчета в файл?
                1)Да
                2)Нет""");
        switch (scan.nextLine()) {
            case "1": {
                System.out.println("Введите путь, где хотите сохранить файл:");
                String filePath = scan.nextLine();
                File file = new File(filePath);
                FileWriter fileWriter = new FileWriter(file);
                for (String s : list) {
                    fileWriter.write(s);
                }
                fileWriter.close();
                break;
            }
            case "2": {
                break;
            }
            default: {
                System.out.println("\nТакого варианта ответа нет\n");
                break;
            }
        }
    }
}