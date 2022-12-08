package com.example.kursach;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;
import java.sql.SQLException;

public class Auth {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;
    @FXML
    private CheckBox pass_toggle;
    @FXML
    private TextField passwordHidden;

    int enter_counter = 0;

    @FXML
    //Метод для авторизации
    public void login(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        Window owner = submitButton.getScene().getWindow();
        if (loginField.getText().isEmpty()) {
            showAlert(AlertType.ERROR, owner, "Form Error!",
                    "Please enter your login");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert(AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }
        String login = loginField.getText();
        String password = passwordField.getText();
        DB db = new DB();
        boolean flag = db.validate(login, password);
        boolean flag_admin = db.validateAdmins(login,password);
        if (!flag && !flag_admin) {
            if(enter_counter>1){
                new Thread(()->{
                    submitButton.setDisable(true);
                    try {
                        Thread.sleep(3000);
                        submitButton.setDisable(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            infoBox("Please enter correct Login and Password", null, "Failed");
            System.out.println("counter: "+enter_counter);
            enter_counter++;
        }
        else if (flag && !flag_admin) {
            db.getEmpInfo(login, password);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("operator-view.fxml"));
            stage.getIcons().add(new Image("logo.png"));
            stage.setTitle("Личный кабинет оператора");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        else if (!flag && flag_admin) {
            db.getAdminInfo(login, password);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("admin-view.fxml"));
            stage.getIcons().add(new Image("logo.png"));
            stage.setTitle("Личный кабинет администратора");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    //Метод показа предупреждений
    private static void showAlert(AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    @FXML
    //Метод для возможности просмотра зашифрованного пароля
    public void togglevisiblePassword(ActionEvent event) {
        if (pass_toggle.isSelected()) {
            passwordHidden.setText(passwordField.getText());
            passwordHidden.setVisible(true);
            passwordField.setVisible(false);
            return;
        }
        passwordField.setText(passwordHidden.getText());
        passwordField.setVisible(true);
        passwordHidden.setVisible(false);
    }
}