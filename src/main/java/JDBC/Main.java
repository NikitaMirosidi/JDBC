package JDBC;

import JDBC.controller.MainController;
import lombok.SneakyThrows;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {

        MainController mainController = new MainController();
        mainController.userInterface();
    }
}