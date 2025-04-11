package com.masoud.app.conroller.base;

public interface CRUDController<Dto> extends CreateController<Dto>
        ,ReadController<Dto>
        ,UpdateController<Dto> ,DeleteController<Dto> {
}
