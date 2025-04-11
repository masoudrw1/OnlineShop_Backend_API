package com.masoud.app.conroller.panel.site;

import com.masoud.app.anotation.ChekPermission;
import com.masoud.app.conroller.base.CRUDController;
import com.masoud.app.model.APIPanelResponse;
import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.dto.site.NaviDto;
import com.masoud.service.site.NaviService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/nav")
public class NavPanelController implements CRUDController<NaviDto> {
    private final NaviService naviService;
@Autowired
    public NavPanelController(NaviService naviService) {
        this.naviService = naviService;
    }


    @Override
    @ChekPermission("add_nav")
    public APIResponse<NaviDto> add(NaviDto dto) throws Exception {
        return APIResponse.<NaviDto>builder()
                .status(APIStatus.OK).data(naviService.create(dto)).
                message("").
                build();
    }

    @Override
    @ChekPermission("delete_nav")
    public APIResponse<Boolean> delete(Long id) {
        return APIResponse.<Boolean>builder()
                .status(APIStatus.OK).data(naviService.delete(id)).
                message("").
                build();
    }
    @Override
    @ChekPermission("list_nav")
    public APIPanelResponse<List<NaviDto>> getAll(Integer page, Integer Size) {
        Page<NaviDto> data = naviService.readAll(page, Size);
        return APIPanelResponse.<List<NaviDto>>builder().
                data(data.getContent()).status(APIStatus.OK).
                message("").
                totalCount(data.getTotalElements()).
                totalPages(data.getTotalPages()).
                build();
    }

    @Override
    @ChekPermission("edit_nav")
    public APIResponse<NaviDto> edit(NaviDto dto) throws Exception {
        return APIResponse.<NaviDto>builder()
                .status(APIStatus.OK).data(naviService.update(dto)).
                message("").
                build();
    }
    @ChekPermission("edit_nav")
    @PutMapping("swap-up/{id}")
    public APIResponse<Boolean> swapUp(@PathVariable Long id) throws Exception {
        return APIResponse.<Boolean>builder()
                .status(APIStatus.OK).data(naviService.swapUp(id)).
                message("").
                build();
    }
    @ChekPermission("edit_nav")
    @PutMapping("swap-down/{id}")
    public APIResponse<Boolean> swapDown(@PathVariable Long id) throws Exception {
        return APIResponse.<Boolean>builder()
                .status(APIStatus.OK).data(naviService.swapDown(id)).
                message("").
                build();
    }
}
