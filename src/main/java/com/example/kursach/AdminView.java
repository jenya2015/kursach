package com.example.kursach;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminView implements Initializable {

    @FXML
    private Button administrators;
    @FXML
    private Button clients;
    @FXML
    private Button operators;
    @FXML
    private Button orders;
    @FXML
    private Button service;
    @FXML
    private Button windows;

    public static String fio = "";

    //метод инициализации кнопок для перехода к таблицам
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        administrators.setOnAction(actionEvent -> {
            try {
                openTable(administrators);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        clients.setOnAction(actionEvent -> {
            try {
                openTable(clients);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        operators.setOnAction(actionEvent -> {
            try {
                openTable(operators);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        orders.setOnAction(actionEvent -> {
            try {
                openTable(orders);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        service.setOnAction(actionEvent -> {
            try {
                openTable(service);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        windows.setOnAction(actionEvent -> {
            try {
                openTable(windows);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    //метод перехода к таблицам
    public void openTable(Button button) throws IOException {
        TablesView.table_id = button.getId();
        Stage st3 = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("tables-view.fxml")));
        st3.getIcons().add(new Image("logo.png"));
        st3.setTitle(button.getText());
        st3.setScene(new Scene(root));
        st3.setResizable(false);
        st3.show();
    }
    //метод выхода из профиля и перехода на форму авторизации
    public void exit(ActionEvent event) throws IOException {
        Stage st3 = new Stage();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        st3.getIcons().add(new Image("logo.png"));
        st3.setTitle("Электронная очередь");
        st3.setScene(new Scene(root, 600, 400));
        st3.setResizable(false);
        st3.show();

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
