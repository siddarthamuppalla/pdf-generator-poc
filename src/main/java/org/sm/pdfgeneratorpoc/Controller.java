package org.sm.pdfgeneratorpoc;

import lombok.RequiredArgsConstructor;
import org.bouncycastle.pqc.legacy.crypto.rainbow.util.RainbowUtil;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("pdf")
@RequiredArgsConstructor
public class Controller {

    private final DocumentService documentService;
    private final S3Service s3Service;

//    @GetMapping("/{id}")
//    public String generatePdf(@PathVariable int id) throws FileNotFoundException {
//        System.out.println("endpoint requested");
//        documentService.createPdf((long) id);
//        return "hello";
//    }

    @GetMapping("{id}")
    public ResponseEntity<String> generatePdf(@PathVariable Long id) {
        try {
            documentService.generateUploadPdf(id);
            return ResponseEntity.ok("PDF generated and uploaded successfully");
        } catch (ConsumerNotFoundException e ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumer not found: " + e.getMessage());
        }
    }

    @GetMapping("download/{documentId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String documentId) throws IOException {
        byte[] data = s3Service.downloadFile(documentId);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-Type", "application/octet-stream")
                .header("Content-Disposition", "attachment, filename=\"" + documentId + "\"" )
                .body(resource);
    }
}
