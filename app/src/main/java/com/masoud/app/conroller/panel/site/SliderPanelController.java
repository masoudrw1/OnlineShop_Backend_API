package com.masoud.app.conroller.panel.site;

import com.masoud.app.anotation.ChekPermission;
import com.masoud.app.conroller.base.CRUDController;
import com.masoud.app.model.APIPanelResponse;
import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.dto.site.SliderDto;
import com.masoud.service.site.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/slider")
public class SliderPanelController implements CRUDController<SliderDto> {
    private final SliderService sliderService;
@Autowired
    public SliderPanelController( SliderService sliderService) {

    this.sliderService = sliderService;
}


    @Override
    @ChekPermission("add_slider")
    public APIResponse<SliderDto> add(SliderDto dto) throws Exception {
        return APIResponse.<SliderDto>builder()
                .status(APIStatus.OK).data(sliderService.create(dto)).
                message("").
                build();
    }

    @Override
    @ChekPermission("delete_slider")
    public APIResponse<Boolean> delete(Long id) {
        return APIResponse.<Boolean>builder()
                .status(APIStatus.OK).data(sliderService.delete(id)).
                message("").
                build();
    }
    @Override
    @ChekPermission("list_slider")
    public APIPanelResponse<List<SliderDto>> getAll(Integer page, Integer Size) {
        Page<SliderDto> data = sliderService.readAll(page, Size);
        return APIPanelResponse.<List<SliderDto>>builder().
                data(data.getContent()).status(APIStatus.OK).
                message("").
                totalCount(data.getTotalElements()).
                totalPages(data.getTotalPages()).
                build();
    }

    @Override
    @ChekPermission("edit_slider")
    public APIResponse<SliderDto> edit(SliderDto dto) throws Exception {
        return APIResponse.<SliderDto>builder()
                .status(APIStatus.OK).data(sliderService.update(dto)).
                message("").
                build();
    }
    @ChekPermission("edit_slider")
    @PutMapping("swap-up/{id}")
    public APIResponse<Boolean> swapUp(@PathVariable Long id) throws Exception {
        return APIResponse.<Boolean>builder()
                .status(APIStatus.OK).data(sliderService.swapUp(id)).
                message("").
                build();
    }
    @ChekPermission("edit_slider")
    @PutMapping("swap-down/{id}")
    public APIResponse<Boolean> swapDown(@PathVariable Long id) throws Exception {
        return APIResponse.<Boolean>builder()
                .status(APIStatus.OK).data(sliderService.swapDown(id)).
                message("").
                build();
    }
}
