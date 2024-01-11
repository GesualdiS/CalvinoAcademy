import org.apache.poi.xwpf.usermodel.*;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String query = "Inserisci la query che vuoi eseguire";
        // Perform your database query and get the result
        List<String> result = performDatabaseQuery(query);

        // Generate Word document
        generateWordDocument(result);
    }

    private static List<String> performDatabaseQuery(String query) {
        List<String> resultList = new ArrayList<>();

        try {
            // Connect to your database (replace with your actual database connection details)
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:3306/calvino_academy", "root", "");

            // Execute your SQL query (replace with your actual query)
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    for (int i = 1; i <= columnCount; i++) {
                        String value = resultSet.getString(i);
                        resultList.add(value);
                    }
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