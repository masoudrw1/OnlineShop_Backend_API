package com.masoud.app.conroller.base;

import com.masoud.app.model.APIPanelResponse;
import com.masoud.app.model.APIResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CreateController<Dto> {
    @PostMapping("add")
    APIResponse<Dto> add(@RequestBody Dto dto) throws Exception;
}
