package com.gino;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.util.List;

public class WordDocumentGenerator {
    public static void generateWordDocument(List<String> result, int columnCount, int rowCount) {
        try (XWPFDocument document = new XWPFDocument()) {
            XWPFParagraph headerParagraph = document.createParagraph();
            headerParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun headerRun = headerParagraph.createRun();
            headerRun.setBold(true);
            headerRun.setText("GRAZIE PER AVERE USATO LA NOSTRA APPLICAZIONE");
            headerRun.addBreak();

            // Creo la tabella
            XWPFTable table = document.createTable(rowCount, columnCount);

            // Ci inserisco i dati
            for (int j = 0; j < rowCount; j += 1) {
                for (int i = 0; i < columnCount; i++) {

                    XWPFTableCell cell = table.getRow(j).getCell(i);
                    cell.setText(result.get(j * columnCount + i));
                }
            }

            // Salvo il documento
            try (FileOutputStream out = new FileOutputStream("result.docx")) {
                document.write(out);
                System.out.println("Word document created successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
