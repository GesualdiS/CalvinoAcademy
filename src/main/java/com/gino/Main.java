import org.apache.poi.xwpf.usermodel.*;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Perform your database query and get the result
        List<String> result = performDatabaseQuery();

        // Generate Word document
        generateWordDocument(result);
    }

    private static List<String> performDatabaseQuery() {
        List<String> resultList = new ArrayList<>();

        try {
            // Connect to your database (replace with your actual database connection details)
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");

            // Execute your SQL query (replace with your actual query)
            String sqlQuery = "SELECT column_name FROM your_table";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Process the query result
                while (resultSet.next()) {
                    // Replace "column_name" with the actual column name from your query result
                    String value = resultSet.getString("column_name");
                    resultList.add(value);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    private static void generateWordDocument(List<String> result) {
        try (XWPFDocument document = new XWPFDocument()) {
            // Create Word document content based on your query result
            for (String value : result) {
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText(value); // Add the actual content from your query result
            }

            // Save the Word document
            try (FileOutputStream out = new FileOutputStream("output.docx")) {
                document.write(out);
                System.out.println("Word document created successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}