package org.sm.pdfgeneratorpoc;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final ConsumerRepo consumerRepo;
    private final S3Service s3Service;

//    public void createPdf(Long id) throws FileNotFoundException {
//        try (
//                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                PdfWriter writer = new PdfWriter(outputStream);
//                PdfDocument pdf = new PdfDocument(writer);
//                Document document = new Document(pdf);
//            ){
//                Consumer consumer = consumerRepo.findById(id).orElseThrow();
//
//                document.add(new Paragraph("Hello " + consumer.getFirstName()));
//
//                Table table = new Table(UnitValue.createPercentArray(new float[]{10, 10, 80}));
//                table.setMarginTop(5);
//                table.addCell("Col A");
//                table.addCell("Col B");
//                document.add(table);
//                document.add(new Paragraph("With 2 columns: "));
//                table = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
//                table.setMarginTop(5);
//                table.addCell(new Cell(1, 2).add(new Paragraph("Value b")));
//                table.addCell(new Cell(1, 2).add(new Paragraph("This is a long description for column c. " +
//                        "It needs much more space hence we made sure that the third column is wider.")));
//                table.addCell(new Cell(1, 2).add(new Paragraph("This is a long description for column c. " +
//                        "It needs much more space hence we made sure that the third column is wider.")));
//                document.add(table);
//
//                document.close();
//
//                // Create the steam for the S3 upload
//                InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//                String bucketName = "your-bucket-name";
//                String key = "generated-pdf.pdf";
//
//                s3Service.uploadFile(outputStream.write())
//
//                System.out.println("Document generated");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//    }

    public void generateUploadPdf(Long id) {
        File pdfFile = new File("generated_" + System.currentTimeMillis() + ".pdf");
        try {
            Consumer consumer = consumerRepo.findById(id).orElseThrow();
            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);
            document.add(new Paragraph(consumer.getFirstName()));
            document.add(new Paragraph(consumer.getLastName()));
            document.add(new Paragraph(consumer.getEmail()));
            document.add(new Paragraph(consumer.getIpAddress()));
            document.close();
        } catch (IOException | RuntimeException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
        }
        s3Service.uploadFile(pdfFile);
    }

//    public ResponseEntity<Byte[]> downloadFile(String documentId) {
//        s3Service.
//    }
}





