package com.masoud.app.conroller.open;

import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.dto.site.NaviDto;
import com.masoud.service.site.NaviService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/navi")
public class NaviController {
    private final NaviService naviService;
@Autowired
    public NaviController(NaviService naviService) {
        this.naviService = naviService;
    }
    @GetMapping()
    public APIResponse<List<NaviDto>> getall()
    {
       return APIResponse.<List<NaviDto>>builder().
               status(APIStatus.OK).data(naviService.readAll()).build();

    }
}
