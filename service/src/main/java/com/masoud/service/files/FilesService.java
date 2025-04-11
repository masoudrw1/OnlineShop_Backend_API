package com.masoud.service.files;

import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.comman.exseptions.ValidationExceptions;
import com.masoud.dataaccess.entity.files.Files;
import com.masoud.dataaccess.repository.files.FilesRepository;
import com.masoud.dto.files.FileDto;
import com.masoud.dto.site.NaviDto;
import com.masoud.service.base.DeleteService;
import com.masoud.service.base.ReadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FilesService implements ReadService<FileDto>, DeleteService<FileDto>{
    private final FilesRepository filesRepository;
    private final ModelMapper mapper;
    @Value("${app.file.upload.path}")
    private String uploadPath;

@Autowired
    public FilesService(FilesRepository filesRepository, ModelMapper mapper) {
        this.filesRepository = filesRepository;
    this.mapper = mapper;
}

    @Override
    public Page<FileDto> readAll(Integer page, Integer size) {
        if(page == null)
        {
            page = 0;
        }
        if(size == null)
        {

            size = 10;
        }
        return filesRepository.findAll(Pageable.ofSize(size).withPage(page)).
                map(x->mapper.map(x, FileDto.class));
    }

    @Override
    public Boolean delete(Long id) {
        filesRepository.deleteById(id);
        return true;
    }

    public FileDto upload(MultipartFile file) throws Exception {
    if (file==null)
    {
        throw new ValidationExceptions("plese select file to upload");
    }
    String head=file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
    String extension=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
    String fileName=head+"."+extension;
    Files entity= Files.builder()
            .createDate(LocalDateTime.now())
            .extension(extension)
            .name(head)
            .path(fileName)
            .uuid(UUID.randomUUID().toString())
            .size(file.getSize())
            .contentType(file.getContentType())
            .build();
    String filePath=uploadPath+ File.separator+fileName;
    Path savePath=Paths.get(filePath);
    java.nio.file.Files.write(savePath,file.getBytes());  //دخیره فایل
        Files savedFile = filesRepository.save(entity);
        return mapper.map(savedFile, FileDto.class);
    }
    public FileDto readByName(String name) throws NotFoundExceptions {

        Files data = filesRepository.findFirstByNameEqualsIgnoreCase(name).orElseThrow(NotFoundExceptions::new);
        return mapper.map(data, FileDto.class);
    }
}
