package com.gino;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Inserire la query che si vuole eseguire
        String query = "SELECT u.email, cl.name, c.title, COUNT(p.id) AS presences FROM ca_users AS u\n" +
                "INNER JOIN ca_frequented_classes AS fc\n" +
                "ON u.id = fc.user_id AND fc.school_class_id = 3\n" +
                "INNER JOIN ca_school_classes AS cl ON fc.school_class_id = cl.id\n" +
                "LEFT JOIN ca_presences AS p\n" +
                "ON p.user_id = u.id\n" +
                "INNER JOIN ca_activities as a\n" +
                "ON a.id = p.activity_id AND a.course_id = 2\n" +
                "INNER JOIN ca_courses as c\n" +
                "ON a.course_id = c.id\n" +
                "GROUP BY u.id;";
        String databaseUrl = "jdbc:mysql://localhost:3306/calvino_academy";
        String username = "root";
        String password = "";

        DatabaseManager databaseManager = new DatabaseManager(databaseUrl, username, password);
        List<String> result = databaseManager.performQuery(query);
        System.out.println(databaseManager.getCountedRows());
        WordDocumentGenerator.generateWordDocument(result, databaseManager.getCountedColumns(), databaseManager.getCountedRows());
    }
}
