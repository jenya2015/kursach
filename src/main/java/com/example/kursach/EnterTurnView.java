package com.example.kursach;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class EnterTurnView implements Initializable {

    @FXML
    private MenuButton order_menu;

    public static String selected_order = "";
    public static String selected_orders_id = "";

    //метод подгрузки услуг из базы данных
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DB db = new DB();
        ArrayList<String> orders = null;
        try {
            orders = db.getOrders();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < orders.size(); i++) {
            MenuItem menuItem = new MenuItem();
            menuItem.setText(orders.get(i));
            menuItem.setId(""+(i+1));
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    selected_order = menuItem.getText();
                    selected_orders_id = menuItem.getId();
                }
            });
            order_menu.getItems().add(menuItem);

        }

    }

    //метод внесения клиента в очередь
    public void enterTurn() throws SQLException {
        DB db = new DB();
        db.addClient();
    }

    //метод возвращения к окну очереди
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
