package org.sm.pdfgeneratorpoc;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController("pdf")
@RequiredArgsConstructor
public class Controller {

    private final DocumentService documentService;

    @GetMapping("/{id}")
    public String generatePdf(@PathVariable int id) throws FileNotFoundException {
        documentService.createPdf((long) id);
        return "hello";
    }
}
