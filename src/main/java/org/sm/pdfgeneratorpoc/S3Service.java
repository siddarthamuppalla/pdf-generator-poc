package org.sm.pdfgeneratorpoc;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class S3Service {
    private static final Logger log = LoggerFactory.getLogger(S3Service.class);
    @Value("${application.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    public String uploadFile(File file) {
        String filename = System.currentTimeMillis() + "_" + file.getName();
        s3Client.putObject(new PutObjectRequest(bucketName, filename, file));
        file.delete();
        return "File uploaded successfully: " + filename;
    }

    public byte[] downloadFile(String fileName) throws IOException {
        S3Object o = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream s3Is = o.getObjectContent();

        try{
            return IOUtils.toByteArray(s3Is);
        } catch (AmazonS3Exception e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } finally {
            s3Is.close();
        }

        System.out.println("Done!");
        return null;
    }


}
