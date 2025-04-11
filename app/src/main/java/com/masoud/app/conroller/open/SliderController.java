package com.masoud.app.conroller.open;

import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.dto.site.SliderDto;
import com.masoud.service.site.SliderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/slider")
public class SliderController {
    private final SliderService sliderService;

    public SliderController(SliderService sliderService) {
        this.sliderService = sliderService;
    }
    @GetMapping()

    public APIResponse<List<SliderDto>> getall()
    {
        return APIResponse.<List<SliderDto>>builder().status(APIStatus.OK).data(sliderService.readAll()).build();

    }
}
