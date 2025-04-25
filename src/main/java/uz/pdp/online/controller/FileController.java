package uz.pdp.online.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/file")
public class FileController {
    private static final Path rootPath = Path.of(System.getProperty("user.dir"), "files");

    @GetMapping("/view/{filename}")
    public ResponseEntity<Resource> viewImage(@PathVariable("filename") String filename) {
        System.out.println(filename);

        Path filePath = rootPath.resolve(filename);
        FileSystemResource resource = new FileSystemResource(filePath);

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String mimeType;
        try {
            mimeType = Files.probeContentType(filePath);
        } catch (IOException e) {
            mimeType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + filename + "\"")
                .body(resource);
    }
}
