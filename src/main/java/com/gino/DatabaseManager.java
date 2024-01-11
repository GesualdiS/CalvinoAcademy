package com.gino;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private final String databaseUrl;
    private final String username;
    private final String password;
    private int countedColumns;
    private int countedRows;
    public DatabaseManager(String databaseUrl, String username, String password) {
        this.databaseUrl = databaseUrl;
        this.username = username;
        this.password = password;
    }

    public List<String> performQuery(String query) {
        List<String> resultList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(databaseUrl, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            this.countedRows = 1;
            this.countedColumns = metaData.getColumnCount();
            // Aggiungo il nome delle colonne nel risultato, così da stamparle
            for (int i = 1; i <= countedColumns; i++) {
                resultList.add(metaData.getColumnName(i));
            }
            while (resultSet.next()) {
                for (int i = 1; i <= countedColumns; i++) {
                    String value = resultSet.getString(i);
                    resultList.add(value);
                }
                countedRows += 1;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public int getCountedColumns() {
        return countedColumns;
    }

    public int getCountedRows() {
        return countedRows;
    }
}
