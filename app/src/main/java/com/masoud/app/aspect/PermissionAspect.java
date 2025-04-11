package com.masoud.app.aspect;

import com.masoud.app.anotation.ChekPermission;
import com.masoud.app.filter.JWTFilter;
import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.dto.user.PermissionDto;
import com.masoud.dto.user.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class PermissionAspect {
    private final HttpServletRequest request;
    @Autowired
    public PermissionAspect(HttpServletRequest request) {
        this.request = request;
    }
    @SneakyThrows
    @Around("@annotation(chekPermission)")
    public Object chekuserpermission(ProceedingJoinPoint joinPoint ,ChekPermission chekPermission)  {

        UserDto user =(UserDto)  request.getAttribute(JWTFilter.CURRENT_USER);
        if(user==null)
        {
            return  APIResponse.builder().status(APIStatus.FILED).message("access dinaid").build();
        }
        List<String> permissions = user.getRoles().stream().flatMap(x -> x.getPermissions().stream().map(PermissionDto::getName)).toList();
        if(!permissions.contains(chekPermission.value()))
        {
            return APIResponse.builder().status(APIStatus.FILED).message("AccessDinaid!").build();

        }
        System.out.println("hi");
        return joinPoint.proceed();



    }
}
