package com.masoud.service.base;

import com.masoud.comman.exseptions.ValidationExceptions;

public interface HasValidation <Dto>{
    void checkValidation(Dto dto) throws ValidationExceptions;
}
