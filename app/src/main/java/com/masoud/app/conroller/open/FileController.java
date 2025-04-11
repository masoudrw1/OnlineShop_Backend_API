package com.masoud.app.conroller.open;

import com.masoud.app.model.APIResponse;
import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.dto.files.FileDto;
import com.masoud.service.files.FilesService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.testng.internal.protocols.Input;

import java.io.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/file")
public class FileController {
    @Value("${app.file.upload.path}")
    private String uploadPath;
    private final FilesService service;
@Autowired
    public FileController(FilesService service) {
        this.service = service;
    }


    @GetMapping("{name}")
    public ResponseEntity<InputStreamResource> getFileByName(@PathVariable String name)
    {

        try {
            FileDto fileDto = service.readByName(name);

            File file = new File(uploadPath + java.io.File.separator + fileDto.getPath());
            if (!file.exists()) {
                throw new NotFoundExceptions();
            }
            InputStream input =new FileInputStream(file);
            InputStreamResource resource=new InputStreamResource(input);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(fileDto.getContentType()));
            headers.setContentLength(fileDto.getSize());
            headers.setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
            return new ResponseEntity<>(resource,headers,HttpStatus.OK);
        }

        catch (NotFoundExceptions | FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

    }
}
