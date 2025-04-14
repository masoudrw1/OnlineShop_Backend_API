package com.masoud.app.conroller.open;

import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.dto.site.BlogDto;
import com.masoud.dto.site.LimetedBlogDto;
import com.masoud.dto.site.SinglblogDto;
import com.masoud.service.site.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/blog")
public class BlogController {
    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("")
    @Cacheable(cacheNames = "apiCache30m",key = "'blog_all'+ #page +' - '+#size")
    public APIResponse<List<LimetedBlogDto>> getAll(@RequestParam(required = false) Integer page,
                                                    @RequestParam(required = false) Integer size) {
        return APIResponse.<List<LimetedBlogDto>>builder().
                data(blogService.readAllPublished(page, size)).status(APIStatus.OK).build();

    }

    @GetMapping("{id}")
    @Cacheable(cacheNames = "apiCache30m",key = "'blog_'+ #id")
    public APIResponse<SinglblogDto> getById(@PathVariable Long id) {
        try {
            return APIResponse.<SinglblogDto>builder().data(blogService.readById(id))
                    .status(APIStatus.OK).build();
        } catch (NotFoundExceptions e) {
            return APIResponse.<SinglblogDto>builder().status(APIStatus.NOT_FOUND).
                    message(e.getMessage()).build();
        }

    }
}
