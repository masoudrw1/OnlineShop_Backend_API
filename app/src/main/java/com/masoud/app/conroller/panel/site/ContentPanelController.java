package com.masoud.app.conroller.panel.site;


import com.masoud.app.anotation.ChekPermission;
import com.masoud.app.conroller.base.CreateController;
import com.masoud.app.conroller.base.ReadController;
import com.masoud.app.conroller.base.UpdateController;
import com.masoud.app.model.APIPanelResponse;
import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.dto.site.ContentDto;
import com.masoud.service.site.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/content")
public class ContentPanelController implements
        CreateController<ContentDto>,
        UpdateController<ContentDto>,
        ReadController<ContentDto> {
    private final ContentService service;
@Autowired
    public ContentPanelController(ContentService service) {
        this.service = service;
    }


    @Override
    @ChekPermission("add_content")
    public APIResponse<ContentDto> add(ContentDto dto) throws Exception {
        return APIResponse.<ContentDto>builder()
                .status(APIStatus.OK).data(service.create(dto)).
                message("").
                build();
    }

    @Override
    @ChekPermission("list_content")
    public APIPanelResponse<List<ContentDto>> getAll(Integer page, Integer Size) {
        Page<ContentDto> data = service.readAll(page, Size);
        return APIPanelResponse.<List<ContentDto>>builder().
                data(data.getContent()).status(APIStatus.OK).
                message("").
                totalCount(data.getTotalElements()).
                totalPages(data.getTotalPages()).
                build();
    }

    @Override
    @ChekPermission("edit_content")
    public APIResponse<ContentDto> edit(ContentDto dto) throws Exception {
        return APIResponse.<ContentDto>builder()
                .status(APIStatus.OK).data(service.update(dto)).
                message("").
                build();
    }
}
