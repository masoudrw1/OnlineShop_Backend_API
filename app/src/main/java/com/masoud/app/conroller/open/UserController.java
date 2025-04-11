package com.masoud.app.conroller.open;

import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.dto.user.LimitUserDto;
import com.masoud.dto.user.LoginDto;
import com.masoud.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public APIResponse<LimitUserDto> login(@RequestBody LoginDto dto) {
        try {
            return APIResponse.<LimitUserDto>builder()
                    .data(userService.login(dto))
                    .status(APIStatus.OK)
                    .build();
        } catch (Exception e) {
            return APIResponse.<LimitUserDto>builder()
                    .data(null)
                    .message(e.getMessage())
                    .status(APIStatus.ERROR)
                    .build();
        }
    }
}
