package com.masoud.service.base;

public interface CRUDService<Dto> extends CreateService<Dto>, UpdateService<Dto>
        , DeleteService<Dto>,ReadService<Dto> {



}
