package com.masoud.app.conroller.panel.file;


import com.masoud.app.anotation.ChekPermission;
import com.masoud.app.conroller.base.CRUDController;
import com.masoud.app.conroller.base.DeleteController;
import com.masoud.app.conroller.base.ReadController;
import com.masoud.app.model.APIPanelResponse;
import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.dto.files.FileDto;
import com.masoud.service.files.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/panel/file")
public class FilePanelController implements ReadController<FileDto>,
        DeleteController<FileDto>
{
    private final FilesService service;
@Autowired
    public FilePanelController(FilesService service) {
        this.service = service;
    }


@PostMapping("upload")
    @ChekPermission("add_file")
    public APIResponse<FileDto> upload(@RequestParam("file") MultipartFile file) throws Exception {

        return APIResponse.<FileDto>builder()
                .status(APIStatus.OK).
                data(service.upload(file)).
                message("").
                build();
    }

    @Override
    @ChekPermission("delete_file")
    public APIResponse<Boolean> delete(Long id) {
        return APIResponse.<Boolean>builder()
                .status(APIStatus.OK).data(service.delete(id)).
                message("").
                build();
    }
    @Override
    @ChekPermission("list_file")
    public APIPanelResponse<List<FileDto>> getAll(Integer page, Integer Size) {
        Page<FileDto> data = service.readAll(page, Size);
        return APIPanelResponse.<List<FileDto>>builder().
                data(data.getContent()).status(APIStatus.OK).
                message("").
                totalCount(data.getTotalElements()).
                totalPages(data.getTotalPages()).
                build();
    }

}
