package JDBC.util;

import JDBC.model.Companies;
import JDBC.model.Customers;
import JDBC.repository.RepositoryCreator;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Scanner;

public class ModelCreatorUtil {

    public static int getInt(String fieldName, Scanner scanner) {

        int value;

        while (true) {

            System.out.print("Укажите значение для '" + fieldName + "': ");

            try {

                value = Integer.parseInt(scanner.nextLine());
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Значение для '" + fieldName + "' указано неверно.\n");
            }
        }

        return value;
    }

    public static String getString(String fieldName, Scanner scanner) {

        String value;

        if(fieldName.toLowerCase().contains("date")) {
            return getCorrectDate(scanner);
        }

        System.out.print("Укажите значение для '" + fieldName + "': ");
        value = scanner.nextLine();

        return value;
    }

    private static String getCorrectDate(Scanner scanner) {

        boolean a = true;
        String[] date;
        int errorCounter;
        String correctDate = "";

        while(a) {

            System.out.print("Укажите дату в формате год-месяц-день цифрами: ");
            date = scanner.nextLine().split("-");
            errorCounter = 0;

            if(date.length != 3) {
                System.out.println("Дата указана неверно!\nНе соблюден формат.");
                continue;
            }

            try {
                int year = Integer.parseInt(date[0]);

                if(year < 1 || year > 9999) {
                    System.out.println("Год указан неверно!\nЗначение выходит за допустимые пределы (1 <= год <= 9999).");
                    errorCounter++;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Год указан неверно!\nИспользуйте цифры для указания года.");
                errorCounter++;
            }

            try {
                int month = Integer.parseInt(date[1]);

                if(month < 1 || month > 12) {
                    System.out.println("Месяц указан неверно!\nЗначение выходит за допустимые пределы (1 <= месяц <= 12).");
                    errorCounter++;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Месяц указан неверно!\nИспользуйте цифры для указания месяца.");
                errorCounter++;
            }

            try {
                int day = Integer.parseInt(date[2]);

                if(day < 1 || day > 31) {
                    System.out.println("День указан неверно!\nЗначение выходит за допустимые пределы (1 <= день <= 31).");
                    errorCounter++;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("День указан неверно!\nИспользуйте цифры для указания дня.");
                errorCounter++;
            }

            if (errorCounter == 0) {
                correctDate = String.join("-", date);
                a = false;
            }
        }

        return correctDate;
    }

    @SneakyThrows
    private static boolean isIdInBase(String fieldName, int id) {

        boolean isInBase = false;

        if(fieldName.toLowerCase().contains("company")) {

            List<Companies> all = RepositoryCreator.of(Companies.class).getAll();

            for (Companies company : all) {

                if (company.getId() == id) {
                    isInBase = true;
                    break;
                }
            }
        }

        if(fieldName.toLowerCase().contains("customer")) {
            List<Customers> all = RepositoryCreator.of(Customers.class).getAll();

            for (Customers customer : all) {

                if (customer.getId() == id) {
                    isInBase = true;
                    break;
                }
            }
        }

        return isInBase;
    }
}