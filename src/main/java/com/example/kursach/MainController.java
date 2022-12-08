package com.example.kursach;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainController {
    @FXML
    private ScrollPane scroll_turn;
    @FXML
    private VBox inturn_box;
    @FXML
    private VBox serving_box;
    @FXML
    private Button enter_turn;
    @FXML
    private Button enter_operators;

    //метод инициализации окна очереди на форме
    public void initialize() throws SQLException {
        DB db = new DB();
        ArrayList<String> number = db.getTurn(1);
        ArrayList<String> window = db.getTurn(2);
        ArrayList<String> status = db.getTurn(3);

        scroll_turn.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: #333");
        inturn_box.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: #333");
        serving_box.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: #333");
        for (int j = 0; j < number.size(); j++) {
            HBox hboxtest = new HBox(); //собственно создание hbox
            hboxtest.setSpacing(5); //отступы между элементами
            hboxtest.setAlignment(Pos.TOP_CENTER);
            hboxtest.setPadding(new Insets(5, 5, 5, 5)); //отступы от краев общего vbox
            // Перебираем их через цикл
            VBox vbox = new VBox(); //создаем новый vbox
            vbox.setAlignment(Pos.CENTER); //выравниваем элементы по центру
            //создаем выделение границ для vbox
            vbox.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: #333");
            // устанавливаем минимальные высоту с шириной и максимальную ширину для vbox
            vbox.setMinHeight(50);
            vbox.setMinWidth(100);
            vbox.setMaxWidth(100);
            VBox vbox2 = new VBox(); //создаем новый vbox
            vbox2.setAlignment(Pos.CENTER); //выравниваем элементы по центру
            //создаем выделение границ для vbox
            vbox2.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: #333");
            // устанавливаем минимальные высоту с шириной и максимальную ширину для vbox
            vbox2.setMinHeight(50);
            vbox2.setMinWidth(80);
            vbox2.setMaxWidth(80);

            Label lab1 = new Label("Клиент: "+number.get(j));
            lab1.setTextAlignment(TextAlignment.CENTER);
            vbox.getChildren().add(lab1);
            vbox.setPadding(new Insets(5, 5, 5, 5));
            Label lab2 = new Label("Окно: "+window.get(j));
            lab2.setTextAlignment(TextAlignment.CENTER);
            vbox2.getChildren().add(lab2);
            vbox2.setPadding(new Insets(5, 5, 5, 5));

            hboxtest.getChildren().addAll(vbox,vbox2);

           if (status.get(j).equals("в очереди")) {
               inturn_box.getChildren().add(hboxtest);
           } else {
               serving_box.getChildren().add(hboxtest);
           }
        }

    }

    //метод обновления окна очереди
    public void updateTurn() throws SQLException {
        inturn_box.getChildren().clear();
        serving_box.getChildren().clear();
        initialize();
    }

    //метод перехода к окну записи на прием
    public void openEnterTurnView(ActionEvent event) throws IOException {
        Stage stage = (Stage) enter_turn.getScene().getWindow();
        stage.close();
        Stage estage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("enter-turn-view.fxml"));
        estage.getIcons().add(new Image("logo.png"));
        estage.setTitle("Записаться");
        estage.setScene(new Scene(root));
        estage.show();
    }

    //Метод перехода к окну авторизации
    public void openOperatorAuthView() throws IOException {
        Stage stage = (Stage) enter_operators.getScene().getWindow();
        stage.close();
        Stage estage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("auth.fxml"));
        estage.getIcons().add(new Image("logo.png"));
        estage.setTitle("Авторизация");
        estage.setScene(new Scene(root));
        estage.show();
    }
}