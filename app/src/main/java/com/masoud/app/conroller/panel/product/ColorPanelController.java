package com.masoud.app.conroller.panel.product;


import com.masoud.app.anotation.ChekPermission;
import com.masoud.app.conroller.base.CreateController;
import com.masoud.app.conroller.base.ReadController;
import com.masoud.app.conroller.base.UpdateController;
import com.masoud.app.model.APIPanelResponse;
import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.dto.product.ColorDto;
import com.masoud.service.product.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/color")
public class ColorPanelController implements
        CreateController<ColorDto>,
        UpdateController<ColorDto>,
        ReadController<ColorDto> {
    private final ColorService service;
@Autowired
    public ColorPanelController(ColorService service) {
        this.service = service;
    }


    @Override
    @ChekPermission("add_color")
    public APIResponse<ColorDto> add(ColorDto dto) throws Exception {
        return APIResponse.<ColorDto>builder()
                .status(APIStatus.OK).data(service.create(dto)).
                message("").
                build();
    }

    @Override
    @ChekPermission("list_color")
    public APIPanelResponse<List<ColorDto>> getAll(Integer page, Integer Color) {
        Page<ColorDto> data = service.readAll(page, Color);
        return APIPanelResponse.<List<ColorDto>>builder().
                data(data.getContent()).status(APIStatus.OK).
                message("").
                totalCount(data.getTotalElements()).
                totalPages(data.getTotalPages()).
                build();
    }

    @Override
    @ChekPermission("edit_color")
    public APIResponse<ColorDto> edit(ColorDto dto) throws Exception {
        return APIResponse.<ColorDto>builder()
                .status(APIStatus.OK).data(service.update(dto)).
                message("").
                build();
    }
}
