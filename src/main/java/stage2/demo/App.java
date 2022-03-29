package stage2.demo;

import stage2.partTwo.Script.Locality;
import stage2.partTwo.Script.Search;
import stage2.partTwo.Script.Street;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class App {
    static Locality locality;
    static Street street;
    static Search search;

    public App() throws SQLException {
        locality = new Locality();
        street = new Street();
        search = new Search();
    }

    public static void main(String[] args) throws SQLException, IOException {

        App app = new App();
        app.createTables();
        int result = 1;
        while (result != 0) {
            result = menu();
        }
        search.close();
        app.driversDelete();

    }

    public static int menu() throws SQLException, IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("""                                
                Выберите действие:
                1) Добавить населенный пункт
                2) Добавить улицу в населенный пункт
                3) Вывести все города
                4) Вывести все улицы населенного пункта
                5) Модифицировать информацию
                6) Удалить запись(Населённый пункт/улицу)
                7) Получать полную справочную информацию о конкретном
                   населенном пункте
                8) Получать справочную информацию о наличии улиц(-ы)
                   в населенном пункте
                9) Выполнить поиск по расширенным критериям
                0) Выйти""");
        switch (sc.nextLine()) {
            case "1": {
                locality.executeAdding();
                break;
            }
            case "2": {
                street.executeAdding();
                break;
            }
            case "3": {
                locality.showAll();
                break;
            }
            case "4": {
                showStreet();
                break;
            }
            case "5": {
                modifyInfo();
                break;
            }
            case "6": {
                removeEntry();
                break;
            }
            case "7": {
                search.showAllInformation();
                break;
            }
            case "8": {
                search.getInformationStreet();
                break;
            }
            case "9": {
                search.menuSearches();
                break;
            }
            case "0": {
                return 0;
            }
            default: {
                System.out.println("\nТакого варианта нет\n");
                break;
            }
        }
        return 1;
    }

    public void createTables() throws SQLException {
        locality.createTable();
        street.createTable();
    }

    public static void showStreet() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Населенные пункты:");
        locality.showAll();

        System.out.println("Введите название населенного пункта:");
        String selectedLocality = sc.nextLine();
        street.printStreetsOfLocality(selectedLocality);
    }

    public static void modifyInfo() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int flag = 0;
        while (flag == 0) {
            System.out.println("""
                    Какую информацию хотите модифицировать:
                    1) Информацию о населенном пункте
                    2) Информацию об улицах населенного пункта
                    0) Назад
                    """);
            switch (sc.nextLine()) {
                case "1": {
                    locality.executeUpdating();
                    break;
                }
                case "2": {
                    street.executeUpdating();
                    break;
                }
                case "0": {
                    flag = 1;
                    break;
                }
                default: {
                    System.out.println("\nТакого варианта нет, Введите еще раз\n");
                    break;
                }
            }
        }
    }

    public static void removeEntry() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Какую информацию хотите удалить:
                1) Населенный пункт
                2) Улицу в насленном пункте
                """);
        switch (sc.nextLine()) {
            case "1": {
                locality.executeDelete();
                break;
            }
            case "2": {
                street.executeDelete();
                break;
            }
            default: {
                System.out.println("Неверный вариант! Повторите ввод");
                removeEntry();
                break;
            }
        }
    }

    public void driversDelete() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}