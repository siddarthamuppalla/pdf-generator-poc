package org.sm.pdfgeneratorpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class PdfGeneratorPocApplication {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(PdfGeneratorPocApplication.class, args);
    }
}
