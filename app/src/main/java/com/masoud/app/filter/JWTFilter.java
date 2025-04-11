package com.masoud.app.filter;

import com.masoud.Utils.JWTUtil;
import com.masoud.service.User.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JWTFilter implements Filter {
    private final JWTUtil jwtUtil;
    private final UserService userService;
    public static final String CURRENT_USER="CURRENT_USER";
    @Autowired
    public JWTFilter(JWTUtil jwtUtil, UserService userService)
    {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httprequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpresponse = (HttpServletResponse) servletResponse;
         String token =extractTokenFromeHeader(httprequest);
         if (!token.isEmpty()&&jwtUtil.validateToken(token))
         {
             String usename=jwtUtil.getusernamefromjwt(token);
             httprequest.setAttribute(CURRENT_USER,userService.readuserbyusername(usename));
             filterChain.doFilter(servletRequest, servletResponse);
         }
         else
         {
             httpresponse.getWriter().write("Access denied");return;
         }



    }

    private static String extractTokenFromeHeader(HttpServletRequest servletRequest)
    {
        HttpServletRequest httprequest= servletRequest;
        String bearerToken=httprequest.getHeader("Authorization");
        String prefix="Bearer ";
        String token="";
        if(bearerToken!=null && bearerToken.startsWith(prefix))
        {
            token = bearerToken.substring(prefix.length());
        }
        return token;
    }
}
