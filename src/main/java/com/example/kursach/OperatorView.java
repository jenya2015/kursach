package com.example.kursach;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class OperatorView implements Initializable {
    @FXML
    private Button but_accept;
    @FXML
    private Button but_end;
    @FXML
    private ScrollPane op_scroll_turn;
    @FXML
    private VBox operator_turn;
    @FXML
    private Text fio_lab;
    @FXML
    private Text order_lab;
    @FXML
    private Text your_client;

    public static String fio = "";
    public static String order = "";
    public static String client = "";

    //Метод инициализации формы профиля оператора
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fio_lab.setText(fio);
        order_lab.setText(order);
        your_client.setText(client);
        DB db = new DB();
        ArrayList<String> number = null;
        ArrayList<String> window = null;
        try {
            number = db.getOperatorTurn(1);
            window = db.getOperatorTurn(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        op_scroll_turn.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: #333");
        operator_turn.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: #333");
        for (int j = 0; j < number.size(); j++) {
            HBox hboxtest = new HBox();
            hboxtest.setSpacing(5);
            hboxtest.setPadding(new Insets(5, 5, 5, 5));
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: #333");
            vbox.setMinHeight(50);
            vbox.setMinWidth(100);
            vbox.setMaxWidth(100);
            VBox vbox2 = new VBox();
            vbox2.setAlignment(Pos.CENTER);
            vbox2.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: #333");
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
            operator_turn.getChildren().add(hboxtest);
        }
    }
    //метод приема клиента
    public void acceptClient() throws SQLException {
        DB db = new DB();
        ArrayList<String> number = null;
        ArrayList<String> order_name = null;
        try {
            number = db.getOperatorTurn(1);
            order_name = db.getOperatorTurn(4);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        order = String.valueOf(order_name.get(order_name.size()-1));
        order_lab.setText(order);
        client = String.valueOf(number.get(number.size()-1));
        your_client.setText(client);
        but_accept.setDisable(true);
        but_end.setDisable(false);
        db.updateTableService(fio);
        operator_turn.getChildren().clear();
        initialize(null,null);
    }
    //метод завершения приема
    public void endService() throws SQLException {
        DB db = new DB();
        db.deleteClient(client);
        order = "";
        client = "";
        order_lab.setText(order);
        your_client.setText(client);
        but_accept.setDisable(false);
        but_end.setDisable(true);
    }
    //метод обновления очереди
    public void updateTurnTable() {
        operator_turn.getChildren().clear();
        initialize(null, null);
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
