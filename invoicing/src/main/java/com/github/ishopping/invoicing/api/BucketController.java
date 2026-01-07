package com.github.ishopping.invoicing.api;

import com.github.ishopping.invoicing.config.bucket.BucketFile;
import com.github.ishopping.invoicing.config.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService service;

    @PostMapping
    public ResponseEntity<String> uploadFile( @RequestParam("file") MultipartFile file ) {
        try (InputStream is = file.getInputStream()){
            MediaType type = MediaType.parseMediaType(file.getContentType());
            var bucketFile = new BucketFile(file.getOriginalFilename(), is, type, file.getSize());

            service.upload(bucketFile);
            return ResponseEntity.ok("File sended!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sendig file: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> getUrl(@RequestParam String filename) {
        try {
            var url = service.getUrl(filename);
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).body(url);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error getting URL from file: " + e.getMessage());
        }
    }
}
