package com.masoud.app.conroller.panel.order;


import com.masoud.app.anotation.ChekPermission;
import com.masoud.app.conroller.base.CRUDController;
import com.masoud.app.filter.JWTFilter;
import com.masoud.app.model.APIPanelResponse;
import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.comman.exseptions.ValidationExceptions;
import com.masoud.dto.order.InvoiceDto;
import com.masoud.dto.user.UserDto;
import com.masoud.service.order.InvoiceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/invoice")
public class InvoicePanelController {
    private final InvoiceService service;

    @Autowired
    public InvoicePanelController(InvoiceService service) {
        this.service = service;
    }


@GetMapping("user/{uid}")
    @ChekPermission("list_invoice")
    public APIResponse<List<InvoiceDto>> getAllByUser(@PathVariable Long uid) {
        List<InvoiceDto> data = service.readAllByUserid(uid);
        return APIResponse.<List<InvoiceDto>>builder()
                .data(data)
                .status(APIStatus.OK)
                .message("success")
                .build();
    }

    @GetMapping("{id}")
    @ChekPermission("info_invoice")
    public APIResponse<InvoiceDto> get (@PathVariable Long id) {

        try {
            InvoiceDto data = service.read(id);
            return APIResponse.<InvoiceDto>builder()
                    .data(data)
                    .status(APIStatus.OK)
                    .message("success")
                    .build();

        } catch (NotFoundExceptions e)
        {
            return APIResponse.<InvoiceDto>builder()
                    .status(APIStatus.ERROR)
                    .message(e.getMessage())
                    .build();

        }


    }
    @GetMapping("mine")
    @ChekPermission("list_my_invoice")
    public APIResponse<List<InvoiceDto>> getMineList(HttpServletRequest request) {
        UserDto user=(UserDto) request.getAttribute(JWTFilter.CURRENT_USER);

        List<InvoiceDto> data = service.readAllByUserid(user.getId());
        return APIResponse.<List<InvoiceDto>>builder()
                .data(data)
                .status(APIStatus.OK)
                .message("success")
                .build();
    }

    @GetMapping("mine/{id}")
    @ChekPermission("info_invoice")
    public APIResponse<InvoiceDto> getmineinfo (@PathVariable Long id,HttpServletRequest request) {
            UserDto user=(UserDto) request.getAttribute(JWTFilter.CURRENT_USER);


        try {
            InvoiceDto data = service.read(id);
            if(!data.getUser().getId().equals(user.getId()))
            {
                throw new ValidationExceptions("Access to view this invoice");
            }
            return APIResponse.<InvoiceDto>builder()
                    .data(data)
                    .status(APIStatus.OK)
                    .message("success")
                    .build();

        } catch (Exception e)
        {
            return APIResponse.<InvoiceDto>builder()
                    .status(APIStatus.ERROR)
                    .message(e.getMessage())
                    .build();

        }

    }
}
