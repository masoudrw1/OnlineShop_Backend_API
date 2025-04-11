package com.masoud.app.conroller.base;

import com.masoud.app.model.APIPanelResponse;
import com.masoud.app.model.APIResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface DeleteController<Dto> {
    @DeleteMapping("{id}")
    APIResponse<Boolean> delete(@PathVariable Long id);
}
