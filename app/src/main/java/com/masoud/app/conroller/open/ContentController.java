package com.masoud.app.conroller.open;

import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.dto.site.ContentDto;
import com.masoud.service.site.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/content")
public class ContentController {
    private final ContentService contentService;
    @Autowired

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }
    @GetMapping("")
    public APIResponse<List<ContentDto>> getAll()
    {
        return APIResponse.<List<ContentDto>>builder().
                status(APIStatus.OK).data(contentService.readAll()).build();

    }
    @GetMapping("{key}")
    public APIResponse<ContentDto> getByKey(@PathVariable String key)
    {
        try {
            return APIResponse.<ContentDto>builder().
                    data(contentService.read(key)).status(APIStatus.OK).build();
        } catch (NotFoundExceptions e) {
            return APIResponse.<ContentDto>builder().status(APIStatus.NOT_FOUND).
                    message(e.getMessage()).build();
        }


    }
}
