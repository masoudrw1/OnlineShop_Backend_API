package com.masoud.app.conroller.panel.user;

import com.masoud.app.anotation.ChekPermission;
import com.masoud.app.conroller.base.CRUDController;
import com.masoud.app.filter.JWTFilter;
import com.masoud.app.model.APIPanelResponse;
import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.dto.user.ChangePassDto;
import com.masoud.dto.user.UpdateProfileDto;
import com.masoud.dto.user.UserDto;
import com.masoud.service.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/panel/user")
public class UserPanelController implements CRUDController<UserDto> {

    private final UserService service;

    @Autowired
    public UserPanelController(UserService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    @ChekPermission("info_user")
    public APIResponse<UserDto> getById(@PathVariable("id") Long id, HttpServletRequest request) {
        return APIResponse.<UserDto>builder()
                .status(APIStatus.OK)
                .data(service.read(id))
                .build();
    }


    @Override
    @ChekPermission("add_user")
    public APIResponse<UserDto> add(UserDto dto) throws Exception {
        return APIResponse.<UserDto>builder()
                .status(APIStatus.OK).data(service.create(dto)).
                message("").
                build();
    }

    @Override
    @ChekPermission("delete_user")
    public APIResponse<Boolean> delete(Long id) {
        return APIResponse.<Boolean>builder()
                .status(APIStatus.OK).data(service.delete(id)).
                message("").
                build();
    }

    @Override
    @ChekPermission("list_user")
    public APIPanelResponse<List<UserDto>> getAll(Integer page, Integer Size) {
        Page<UserDto> data = service.readAll(page, Size);
        return APIPanelResponse.<List<UserDto>>builder().
                data(data.getContent()).status(APIStatus.OK).
                message("").
                totalCount(data.getTotalElements()).
                totalPages(data.getTotalPages()).
                build();
    }

    @Override
    @ChekPermission("edit_user")
    public APIResponse<UserDto> edit(UserDto dto) throws Exception {
        return APIResponse.<UserDto>builder()
                .status(APIStatus.OK).data(service.update(dto)).
                message("").
                build();
    }
    @PutMapping("change-pass/admin")
    @ChekPermission("change_password_by_admin")
    public APIResponse<UserDto> changePasswordByAdmin(UserDto dto) throws Exception {
        return APIResponse.<UserDto>builder()
                .status(APIStatus.OK).data(service.changePasswordByAdmin(dto)).
                message("").
                build();
    }
    @PutMapping("change-pass")
    @ChekPermission("change_password_by_user")
    public APIResponse<UserDto> changePassword(ChangePassDto dto,HttpServletRequest request) throws Exception {
        UserDto user =(UserDto) request.getAttribute(JWTFilter.CURRENT_USER);
        return APIResponse.<UserDto>builder()
                .status(APIStatus.OK).data(service.ChangePassByUser(dto,user)).
                message("").
                build();
    }
    @PutMapping("update-profile")
    @ChekPermission("edit_my_user")
    public APIResponse<UserDto> editProfile(@RequestBody UpdateProfileDto dto, HttpServletRequest request) throws Exception {
        UserDto user =(UserDto) request.getAttribute(JWTFilter.CURRENT_USER);
        dto.setId(user.getId());
        return APIResponse.<UserDto>builder()
                .status(APIStatus.OK).data(service.updateProfile(dto)).
                message("").
                build();
    }
}
