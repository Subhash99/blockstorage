package com.example.object_storage_testing;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.io.*;

@RestController
@CrossOrigin(value = "http://localhost:8080")
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping
    public void test() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("objectstore.e2enetworks.net")
                        .credentials("5MWX2PWZZRDOZIL6P30Q", "C3BI0T7E77A3YQ6U7OYGFA4VA4Y0C06YC1X5AN4X")
                        .build();
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("traklabstest").build());
        System.out.println("bucket existance: "+ found);

        File file = new File("/home/dinesh/Desktop/a.txt");

        try (InputStream in = new FileInputStream(file)) {
//            int content;
//            while ((content = in.read()) != -1) {
//                System.out.print((char)content);
//            }
            minioClient.putObject(
                    PutObjectArgs.builder().bucket("traklabstest").object("testingobj1").stream(
                            in, -1, 10485760)
                            .contentType("text/plain")
                            .build());

        } catch (Exception e) {
            e.printStackTrace();
        }

//        BufferedReader br = new BufferedReader(new FileReader(file));;
//        String st;
//        while ((st = br.readLine()) != null) {
//            System.out.println(st);
//    }


    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void postTest(@RequestParam("file") MultipartFile file) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        System.out.println(file.getSize());
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("objectstore.e2enetworks.net")
                        .credentials("5MWX2PWZZRDOZIL6P30Q", "C3BI0T7E77A3YQ6U7OYGFA4VA4Y0C06YC1X5AN4X")
                        .build();
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("traklabstest").build());
        System.out.println("bucket existance: "+ found);

        InputStream inputStream = new ByteArrayInputStream(file.getBytes());
        System.out.println("system");
        minioClient.putObject(
                PutObjectArgs.builder().bucket("traklabstest").object("a.mp4").stream(
                        inputStream, -1, 10485760)
                        .contentType("video/mp4")
                        .build());
        System.out.println(file);

    }

//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void postTest(HttpServletRequest request) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
//        try {
//            InputStream is = request.getInputStream();
//            // Process and Store image in database
//        } catch (IOException e) {
//            // handle exception
//        }
//    }

}
