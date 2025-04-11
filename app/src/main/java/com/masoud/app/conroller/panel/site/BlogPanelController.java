package com.masoud.app.conroller.panel.site;


import com.masoud.app.anotation.ChekPermission;
import com.masoud.app.conroller.base.CRUDController;
import com.masoud.app.model.APIPanelResponse;
import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.dto.site.BlogDto;
import com.masoud.service.site.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/blog")
public class BlogPanelController implements CRUDController<BlogDto> {
    private final BlogService service;
@Autowired
    public BlogPanelController(BlogService service) {
        this.service = service;
    }


    @Override
    @ChekPermission("add_blog")
    public APIResponse<BlogDto> add(BlogDto dto) throws Exception {
        return APIResponse.<BlogDto>builder()
                .status(APIStatus.OK).data(service.create(dto)).
                message("").
                build();
    }

    @Override
    @ChekPermission("delete_blog")
    public APIResponse<Boolean> delete(Long id) {
        return APIResponse.<Boolean>builder()
                .status(APIStatus.OK).data(service.delete(id)).
                message("").
                build();
    }
    @Override
    @ChekPermission("list_blog")
    public APIPanelResponse<List<BlogDto>> getAll(Integer page, Integer Size) {
        Page<BlogDto> data = service.readAll(page, Size);
        return APIPanelResponse.<List<BlogDto>>builder().
                data(data.getContent()).status(APIStatus.OK).
                message("").
                totalCount(data.getTotalElements()).
                totalPages(data.getTotalPages()).
                build();
    }

    @Override
    @ChekPermission("edit_blog")
    public APIResponse<BlogDto> edit(BlogDto dto) throws Exception {
        return APIResponse.<BlogDto>builder()
                .status(APIStatus.OK).data(service.update(dto)).
                message("").
                build();
    }
}
