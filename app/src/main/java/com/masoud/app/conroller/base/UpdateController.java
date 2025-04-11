package com.masoud.app.conroller.base;

import com.masoud.app.model.APIPanelResponse;
import com.masoud.app.model.APIResponse;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UpdateController<Dto> {
    @PutMapping("edit")
    APIResponse<Dto> edit(@RequestBody Dto dto) throws Exception;
}
