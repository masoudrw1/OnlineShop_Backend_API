package com.masoud.app.conroller.panel.product;


import com.masoud.app.anotation.ChekPermission;
import com.masoud.app.conroller.base.CreateController;
import com.masoud.app.conroller.base.ReadController;
import com.masoud.app.conroller.base.UpdateController;
import com.masoud.app.model.APIPanelResponse;
import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.dto.product.SizeDto;
import com.masoud.service.product.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/size")
public class SizePanelController implements
        CreateController<SizeDto>,
        UpdateController<SizeDto>,
        ReadController<SizeDto> {
    private final SizeService service;
@Autowired
    public SizePanelController(SizeService service) {
        this.service = service;
    }


    @Override
    @ChekPermission("add_size")
    public APIResponse<SizeDto> add(SizeDto dto) throws Exception {
        return APIResponse.<SizeDto>builder()
                .status(APIStatus.OK).data(service.create(dto)).
                message("").
                build();
    }

    @Override
    @ChekPermission("list_size")
    public APIPanelResponse<List<SizeDto>> getAll(Integer page, Integer Size) {
        Page<SizeDto> data = service.readAll(page, Size);
        return APIPanelResponse.<List<SizeDto>>builder().
                data(data.getContent()).status(APIStatus.OK).
                message("").
                totalCount(data.getTotalElements()).
                totalPages(data.getTotalPages()).
                build();
    }

    @Override
    @ChekPermission("edit_size")
    public APIResponse<SizeDto> edit(SizeDto dto) throws Exception {
        return APIResponse.<SizeDto>builder()
                .status(APIStatus.OK).data(service.update(dto)).
                message("").
                build();
    }
}
