package org.sm.pdfgeneratorpoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final ConsumerRepo consumerRepo;

    public void createPdf(Long id) throws FileNotFoundException {
        try (Document document = new Document(new PdfDocument(new PdfWriter("hello.pdf")))) {

            Consumer consumer =  consumerRepo.findById(id).orElseThrow();
            System.out.println(consumer);

            document.add(new Paragraph("Hello "));

            Table table = new Table(UnitValue.createPercentArray(new float[]{10, 10, 80}));

            table.setMarginTop(5);
            table.addCell("Col A");
            table.addCell("Col B");
            table.addCell("Col C");
            table.addCell("Value A");
            table.addCell("Value B");
            table.addCell("Long description for column C" + "IT NEEDS MUCH MORE SPACE");

            document.add(table);

            document.add(new Paragraph("With 2 columns: "));

            table = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
            table.setMarginTop(5);
            table.addCell("Col a");
            table.addCell("Col b");
            table.addCell("Value a");
            table.addCell("Value b");
            table.addCell(new Cell(1, 2).add(new Paragraph("Value b")));
            table.addCell(new Cell(1, 2).add(new Paragraph("This is a long description for column c. " +
                    "It needs much more space hence we made sure that the third column is wider.")));
            table.addCell("Col a");
            table.addCell("Col b");
            table.addCell("Value a");
            table.addCell("Value b");
            table.addCell(new Cell(1, 2).add(new Paragraph("Value b")));
            table.addCell(new Cell(1, 2).add(new Paragraph("This is a long description for column c. " +
                    "It needs much more space hence we made sure that the third column is wider.")));

            document.add(table);

            document.close();

            System.out.println("Document generated");
        }
    }

    public boolean isDuplicate(File pdfFile, String existingChecksum) {
        String checksum = calculateChecksum(pdfFile);
        return checksum.equals(existingChecksum);
    }

    private String calculateChecksum(File file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = Files.readAllBytes(file.toPath());
            byte[] hash = digest.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}

