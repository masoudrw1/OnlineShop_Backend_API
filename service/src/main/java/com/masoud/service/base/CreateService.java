package com.masoud.service.base;

import com.masoud.dto.site.NaviDto;

import java.util.List;

public interface CreateService<Dto>{
    Dto create(Dto dto) throws Exception;
}
