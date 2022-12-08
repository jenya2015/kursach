package com.example.kursach;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TablesView implements Initializable {
    @FXML
    private AnchorPane anch;
    @FXML
    private Text text_id;
    @FXML
    private Text text_name;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private Button add;
    @FXML
    private Button update;

    private ObservableList<AdmData> adm = FXCollections.observableArrayList();
    private ObservableList<CliData> cli = FXCollections.observableArrayList();
    private ObservableList<OperaData> ope = FXCollections.observableArrayList();
    private ObservableList<OrderData> ord = FXCollections.observableArrayList();
    private ObservableList<ServiceData> ser = FXCollections.observableArrayList();
    private ObservableList<WindowData> win = FXCollections.observableArrayList();
    public static String table_id = "";
    //метод загрузки таблицы и ее определения
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DB db = new DB();
        ArrayList<String> data = null;
        try {
            data = db.getTable(table_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (table_id.equals("administrators")) {
            for(int i = 0; i < data.size(); ++i){
                adm.add(new AdmData(data.get(i), data.get(++i), data.get(++i), data.get(++i)));
            }

            TableView<AdmData> tableView = new TableView<AdmData>(adm);
            tableView.setPrefWidth(600);
            tableView.setPrefHeight(280);

            TableColumn<AdmData, String> tableColumn1 = new TableColumn<AdmData, String>("id");
            tableColumn1.setCellValueFactory(new PropertyValueFactory<AdmData, String>("id"));
            tableView.getColumns().add(tableColumn1);

            TableColumn<AdmData, String> tableColumn2 = new TableColumn<AdmData, String>("fio");
            tableColumn2.setCellValueFactory(new PropertyValueFactory<AdmData, String>("fio"));
            tableView.getColumns().add(tableColumn2);

            TableColumn<AdmData, String> tableColumn3 = new TableColumn<AdmData, String>("login");
            tableColumn3.setCellValueFactory(new PropertyValueFactory<AdmData, String>("login"));
            tableView.getColumns().add(tableColumn3);

            TableColumn<AdmData, String> tableColumn4 = new TableColumn<AdmData, String>("password");
            tableColumn4.setCellValueFactory(new PropertyValueFactory<AdmData, String>("password"));
            tableView.getColumns().add(tableColumn4);

            anch.getChildren().add(tableView);
        }
        else if (table_id.equals("clients")) {
            for(int i = 0; i < data.size(); ++i){
                cli.add(new CliData(data.get(i), data.get(++i)));
            }

            TableView<CliData> tableView = new TableView<CliData>(cli);
            tableView.setPrefWidth(600);
            tableView.setPrefHeight(280);

            TableColumn<CliData, String> tableColumn1 = new TableColumn<CliData, String>("id");
            tableColumn1.setCellValueFactory(new PropertyValueFactory<CliData, String>("id"));
            tableView.getColumns().add(tableColumn1);

            TableColumn<CliData, String> tableColumn2 = new TableColumn<CliData, String>("number");
            tableColumn2.setCellValueFactory(new PropertyValueFactory<CliData, String>("number"));
            tableView.getColumns().add(tableColumn2);

            anch.getChildren().add(tableView);
        }
        else if (table_id.equals("operators")) {
            for(int i = 0; i < data.size(); ++i){
                ope.add(new OperaData(data.get(i), data.get(++i), data.get(++i), data.get(++i), data.get(++i)));
            }

            TableView<OperaData> tableView = new TableView<OperaData>(ope);
            tableView.setPrefWidth(600);
            tableView.setPrefHeight(280);

            TableColumn<OperaData, String> tableColumn1 = new TableColumn<OperaData, String>("id");
            tableColumn1.setCellValueFactory(new PropertyValueFactory<OperaData, String>("id"));
            tableView.getColumns().add(tableColumn1);

            TableColumn<OperaData, String> tableColumn2 = new TableColumn<OperaData, String>("fio");
            tableColumn2.setCellValueFactory(new PropertyValueFactory<OperaData, String>("fio"));
            tableView.getColumns().add(tableColumn2);

            TableColumn<OperaData, String> tableColumn3 = new TableColumn<OperaData, String>("login");
            tableColumn3.setCellValueFactory(new PropertyValueFactory<OperaData, String>("login"));
            tableView.getColumns().add(tableColumn3);

            TableColumn<OperaData, String> tableColumn4 = new TableColumn<OperaData, String>("password");
            tableColumn4.setCellValueFactory(new PropertyValueFactory<OperaData, String>("password"));
            tableView.getColumns().add(tableColumn4);

            TableColumn<OperaData, String> tableColumn5 = new TableColumn<OperaData, String>("windows_id");
            tableColumn5.setCellValueFactory(new PropertyValueFactory<OperaData, String>("windows"));
            tableView.getColumns().add(tableColumn5);

            anch.getChildren().add(tableView);
        }
        else if (table_id.equals("orders")) {
            for(int i = 0; i < data.size(); ++i){
                ord.add(new OrderData(data.get(i), data.get(++i)));
            }

            TableView<OrderData> tableView = new TableView<OrderData>(ord);
            tableView.setPrefWidth(600);
            tableView.setPrefHeight(280);

            TableColumn<OrderData, String> tableColumn1 = new TableColumn<OrderData, String>("id");
            tableColumn1.setCellValueFactory(new PropertyValueFactory<OrderData, String>("id"));
            tableView.getColumns().add(tableColumn1);

            TableColumn<OrderData, String> tableColumn2 = new TableColumn<OrderData, String>("order_name");
            tableColumn2.setCellValueFactory(new PropertyValueFactory<OrderData, String>("order"));
            tableView.getColumns().add(tableColumn2);

            anch.getChildren().add(tableView);

            text_id.setVisible(true);
            text_id.setText(tableColumn1.getText());
            text_name.setVisible(true);
            text_name.setText(tableColumn2.getText());
            id.setDisable(false);
            id.setVisible(true);
            name.setDisable(false);
            name.setVisible(true);
            add.setDisable(false);
            add.setVisible(true);
            update.setDisable(false);
            update.setVisible(true);
        }
        else if (table_id.equals("service")) {
            for(int i = 0; i < data.size(); ++i){
                ser.add(new ServiceData(data.get(i), data.get(++i), data.get(++i), data.get(++i), data.get(++i), data.get(++i)));
            }

            TableView<ServiceData> tableView = new TableView<ServiceData>(ser);
            tableView.setPrefWidth(600);
            tableView.setPrefHeight(280);

            TableColumn<ServiceData, String> tableColumn1 = new TableColumn<ServiceData, String>("operators_id");
            tableColumn1.setCellValueFactory(new PropertyValueFactory<ServiceData, String>("operator"));
            tableView.getColumns().add(tableColumn1);

            TableColumn<ServiceData, String> tableColumn2 = new TableColumn<ServiceData, String>("operators_windows_id");
            tableColumn2.setCellValueFactory(new PropertyValueFactory<ServiceData, String>("window"));
            tableView.getColumns().add(tableColumn2);

            TableColumn<ServiceData, String> tableColumn3 = new TableColumn<ServiceData, String>("clients_id");
            tableColumn3.setCellValueFactory(new PropertyValueFactory<ServiceData, String>("client"));
            tableView.getColumns().add(tableColumn3);

            TableColumn<ServiceData, String> tableColumn4 = new TableColumn<ServiceData, String>("orders_id");
            tableColumn4.setCellValueFactory(new PropertyValueFactory<ServiceData, String>("order"));
            tableView.getColumns().add(tableColumn4);

            TableColumn<ServiceData, String> tableColumn5 = new TableColumn<ServiceData, String>("application_date");
            tableColumn5.setCellValueFactory(new PropertyValueFactory<ServiceData, String>("date"));
            tableView.getColumns().add(tableColumn5);

            TableColumn<ServiceData, String> tableColumn6 = new TableColumn<ServiceData, String>("status");
            tableColumn6.setCellValueFactory(new PropertyValueFactory<ServiceData, String>("status"));
            tableView.getColumns().add(tableColumn6);

            anch.getChildren().add(tableView);
        }
        else if (table_id.equals("windows")) {
            for(int i = 0; i < data.size(); ++i){
                win.add(new WindowData(data.get(i), data.get(++i)));
            }

            TableView<WindowData> tableView = new TableView<WindowData>(win);
            tableView.setPrefWidth(600);
            tableView.setPrefHeight(280);

            TableColumn<WindowData, String> tableColumn1 = new TableColumn<WindowData, String>("id");
            tableColumn1.setCellValueFactory(new PropertyValueFactory<WindowData, String>("id"));
            tableView.getColumns().add(tableColumn1);

            TableColumn<WindowData, String> tableColumn2 = new TableColumn<WindowData, String>("window");
            tableColumn2.setCellValueFactory(new PropertyValueFactory<WindowData, String>("window"));
            tableView.getColumns().add(tableColumn2);

            anch.getChildren().add(tableView);

            text_id.setVisible(true);
            text_id.setText(tableColumn1.getText());
            text_name.setVisible(true);
            text_name.setText(tableColumn2.getText());
            id.setDisable(false);
            id.setVisible(true);
            name.setDisable(false);
            name.setVisible(true);
            add.setDisable(false);
            add.setVisible(true);
            update.setDisable(false);
            update.setVisible(true);
        }

    }
    //метод добавления записей в таблицу
    public void addToTable() throws SQLException {
        DB db = new DB();
        db.insertTables(table_id,text_id.getText(),text_name.getText(),id.getText(),name.getText());
    }
    //метод обновления таблицы
    public void updateTable() {
        ord.clear();
        win.clear();
        initialize(null, null);
    }
}
