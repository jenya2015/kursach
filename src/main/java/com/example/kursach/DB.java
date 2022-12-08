package com.example.kursach;

import java.sql.*;
import java.util.ArrayList;

public class DB {

    public static final String DATABASE_URL = "jdbc:mysql://galka0e0.beget.tech/galka0e0_1";
    public static final String DATABASE_USERNAME = "galka0e0_1";
    public static final String DATABASE_PASSWORD = "Scarlxx128";
    private static final String SELECT_QUERY = "SELECT * FROM `operators` WHERE login = ? and password = ?";
    private static final String SELECT_QUERY2 = "SELECT c.number, w.window, s.status, ord.order_name FROM service s, operators o, clients c, windows w, orders ord WHERE s.operators_id=o.id AND s.orders_id=o.id AND s.status='в очереди' AND o.fio=?  AND s.clients_id=c.id and w.id=s.operators_windows_id GROUP BY c.number";
    private static final String SELECT_QUERY3 = "SELECT order_name FROM orders";
    private static final String SELECT_QUERY4 = "SELECT * FROM clients";
    private static final String SELECT_QUERY5 = "SELECT c.number, c.id, w.window, s.status, ord.order_name FROM service s, operators o, clients c, windows w, orders ord WHERE s.operators_id=o.id AND s.orders_id=ord.id AND s.clients_id=c.id and w.id=s.operators_windows_id AND s.status='завершено' GROUP BY c.number";
    private static final String SELECT_QUERY6 = "SELECT id FROM operators";
    private static final String SELECT_QUERY7 = "SELECT * FROM administrators WHERE login = ? AND password = ?";
    public static final String UPDATE_QUERY = "UPDATE service SET status = 'обслуживается' WHERE status='в очереди' AND operators_id=(SELECT id FROM operators WHERE fio=?) AND clients_id=(SELECT id FROM clients WHERE number=?)";
    public static String query_turn = "SELECT c.number, w.window, s.status FROM service s, clients c, windows w WHERE s.clients_id=c.id and w.id=s.operators_windows_id AND s.status NOT IN ('завершено')";
    public static String UPDATE_QUERY2 = "UPDATE service SET status='завершено' WHERE clients_id=(SELECT id FROM clients WHERE number=?)";
    public static String INSERT_QUERY = "INSERT INTO `clients` (`id`, `number`) VALUES (?, ?)";
    public static String INSERT_QUERY2 = "INSERT INTO `service` (`operators_id`, `operators_windows_id`, `clients_id`, `orders_id`, `application_date`, `status`) VALUES (?,?,?,?,CURRENT_TIMESTAMP(),'в очереди')";

    //метод сравнения введенного логина и пароля с фактическим в таблице Операторы
    public boolean validate(String login, String password) throws SQLException {
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                connection.close();
                return true;
            }

        } catch (SQLException e) {
            printSQLException(e);
        }

        return false;
    }

    //метод сравнения введенного логина и пароля с фактическим в таблице Администраторы
    public boolean validateAdmins(String login, String password) throws SQLException {
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY7)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                connection.close();
                return true;
            }

        } catch (SQLException e) {
            printSQLException(e);
        }

        return false;
    }

    //Метод для получения информации об операторе
    public String getEmpInfo(String login, String password) throws SQLException {

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                OperatorView.fio = resultSet.getString(2);
                return OperatorView.fio;
            }


        } catch (SQLException e) {
            printSQLException(e);
        }
        return "Failed";


    }

    //метод получения информации об администраторе
    public String getAdminInfo(String login, String password) throws SQLException {

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY7)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                AdminView.fio = resultSet.getString(2);
                return AdminView.fio;
            }


        } catch (SQLException e) {
            printSQLException(e);
        }
        return "Failed";


    }

    //Вывод sql ошибок
    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    //метод получения общей очереди клиентов
    public ArrayList<String> getTurn(int i) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(query_turn);
        ResultSet res = preparedStatement.executeQuery();
        ArrayList<String> info = new ArrayList<>();
        while(res.next()){
            info.add(res.getString(i));
        }
        connection.close();
        return info;
    }

    //метод получения очереди клиентов к конкретному оператору
    public ArrayList<String> getOperatorTurn(int i) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY2);
        preparedStatement.setString(1, OperatorView.fio);
        ResultSet res = preparedStatement.executeQuery();
        ArrayList<String> info = new ArrayList<>();
        while(res.next()){
            info.add(res.getString(i));
        }
        connection.close();
        return info;
    }

    //метод обновления очереди
    public void updateTableService(String f) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
        preparedStatement.setString(1, OperatorView.fio);
        preparedStatement.setString(2, OperatorView.client);
        preparedStatement.executeUpdate();
        connection.close();
    }

    //метод отсеивания клиентов, которым оказали услугу
    public void deleteClient(String f) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY2);
        preparedStatement.setString(1, f);
        preparedStatement.executeUpdate();
        connection.close();
    }

    //метод получения информации об услугах
    public ArrayList<String> getOrders() throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY3);
        ResultSet res = preparedStatement.executeQuery();
        ArrayList<String> info = new ArrayList<>();
        while(res.next()){
            info.add(res.getString(1));
        }
        connection.close();
        return info;
    }

    //метод добавления клиента в очередь
    public void addClient() throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY4);
        PreparedStatement preparedStatement2 = connection.prepareStatement(SELECT_QUERY5);
        PreparedStatement preparedStatement5 = connection.prepareStatement(SELECT_QUERY6);
        ResultSet res = preparedStatement.executeQuery();
        ResultSet res2 = preparedStatement2.executeQuery();
        ResultSet res3 = preparedStatement5.executeQuery();
        ArrayList<Integer> clients_id = new ArrayList<>();
        ArrayList<String> clients_numbers = new ArrayList<>();
        ArrayList<String> end_clients = new ArrayList<>();
        ArrayList<String> end_clients_id = new ArrayList<>();
        ArrayList<Integer> operators_id = new ArrayList<>();
        while(res.next()){
            clients_id.add(res.getInt(1));
            clients_numbers.add(res.getString(2));
        }
        while(res2.next()){
            end_clients.add(res2.getString(1));
            end_clients_id.add(res2.getString(2));
        }
        while(res3.next()){
            operators_id.add(res3.getInt(1));
        }
        String new_client = "null";
        String new_client_id = "null";
        if (end_clients.size()==0) {
            if (clients_id.get(clients_id.size()-1)<10) {
                new_client = "B00" + (clients_id.size()+1);
            } else if (clients_id.get(clients_id.size()-1)>9) {
                new_client = "B0" + (clients_id.size()+1);

            }
            PreparedStatement preparedStatement3 = connection.prepareStatement(INSERT_QUERY);
            preparedStatement3.setInt(1, clients_id.size()+1 );
            preparedStatement3.setString(2, new_client);
            preparedStatement3.executeUpdate();
            PreparedStatement preparedStatement4 = connection.prepareStatement(INSERT_QUERY2);
            int operator = (int) (( Math.random() * ( ( operators_id.size() - 1 ) + 1 ) ) + 1);
            preparedStatement4.setInt(1, operator);
            String sql = "SELECT windows_id FROM operators WHERE id = ?";
            PreparedStatement preparedStatement6 = connection.prepareStatement(sql);
            preparedStatement6.setInt(1, operator);
            ResultSet res4 = preparedStatement6.executeQuery();
            int window = 0;
            while(res4.next()){
                window = res4.getInt(1);
            }
            preparedStatement4.setInt(2, window);
            preparedStatement4.setInt(3, clients_id.size()+1);
            preparedStatement4.setInt(4, Integer.parseInt(EnterTurnView.selected_orders_id));
            preparedStatement4.executeUpdate();
            connection.close();
        }
        else if (end_clients.size() > 0) {
            new_client = end_clients.get(0);
            new_client_id = end_clients_id.get(0);
            String delete_query = "DELETE FROM service WHERE clients_id="+new_client_id;
            PreparedStatement preparedStatementDelete = connection.prepareStatement(delete_query);
            preparedStatementDelete.executeUpdate();
            PreparedStatement preparedStatement4 = connection.prepareStatement(INSERT_QUERY2);
            int operator = (int) (( Math.random() * ( ( operators_id.size() - 1 ) + 1 ) ) + 1);
            preparedStatement4.setInt(1, operator);
            String sql = "SELECT windows_id FROM operators WHERE id = ?";
            PreparedStatement preparedStatement6 = connection.prepareStatement(sql);
            preparedStatement6.setInt(1, operator);
            ResultSet res4 = preparedStatement6.executeQuery();
            int window = 0;
            while(res4.next()){
                window = res4.getInt(1);
            }
            preparedStatement4.setInt(2, window);
            preparedStatement4.setInt(3, Integer.parseInt(new_client_id));
            preparedStatement4.setInt(4, Integer.parseInt(EnterTurnView.selected_orders_id));
            preparedStatement4.executeUpdate();
            connection.close();
        }
    }

    //метод вывода одной из таблиц базы данных (в параметрах метода указывается имя таблицы)
    public ArrayList<String> getTable(String table_name) throws SQLException {
        String query = "SELECT * FROM "+table_name;
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet res = preparedStatement.executeQuery();
        ResultSetMetaData rsmd = res.getMetaData();
        int col_count = rsmd.getColumnCount();
        ArrayList<String> info = new ArrayList<>();
        while(res.next()){
            for (int i = 1; i <= col_count; i++) {
                info.add(res.getString(i));
            }
        }
        connection.close();
        return info;
    }

    //метод добавления информации в таблицы Услуги и Окна
    public void insertTables(String table, String id, String name, String data1, String data2) throws SQLException {
        String insert_query = "INSERT INTO "+table+" ("+id+", "+name+") VALUES ("+data1+", '"+data2+"')";
        System.out.println(insert_query);
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(insert_query);
        preparedStatement.executeUpdate();
        connection.close();
    }

}
