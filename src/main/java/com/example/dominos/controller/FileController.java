package com.example.dominos.controller;

import com.example.dominos.model.exceptions.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;


@RestController
public class FileController extends AbstractController {

    @GetMapping("/images/{fileName}")
    @SneakyThrows
    public void download(@PathVariable String fileName, HttpServletResponse response) {
        File f = new File("uploads" + File.separator + fileName);
        if (!f.exists()){
            throw new NotFoundException("File not found!");
        }
        response.setContentType(Files.probeContentType(f.toPath()));
        Files.copy(f.toPath(), response.getOutputStream());
    }
}
