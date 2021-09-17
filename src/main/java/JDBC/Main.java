package JDBC;

import JDBC.controller.MainController;

import JDBC.model.Companies;
import lombok.SneakyThrows;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {

        MainController mainController = new MainController();
        mainController.userInterface();
    }
}