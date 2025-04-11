package com.masoud.app.conroller.panel.product;


import com.masoud.app.anotation.ChekPermission;
import com.masoud.app.conroller.base.CRUDController;
import com.masoud.app.model.APIPanelResponse;
import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.dto.product.ProductDto;
import com.masoud.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/product")
public class ProductPanelController implements CRUDController<ProductDto> {
    private final ProductService service;
@Autowired
    public ProductPanelController(ProductService service) {
        this.service = service;
    }


    @Override
    @ChekPermission("add_product")
    public APIResponse<ProductDto> add(ProductDto dto) throws Exception {
        return APIResponse.<ProductDto>builder()
                .status(APIStatus.OK).data(service.create(dto)).
                message("").
                build();
    }

    @Override
    @ChekPermission("delete_product")
    public APIResponse<Boolean> delete(Long id) {
        return APIResponse.<Boolean>builder()
                .status(APIStatus.OK).data(service.delete(id)).
                message("").
                build();
    }
    @Override
    @ChekPermission("list_product")
    public APIPanelResponse<List<ProductDto>> getAll(Integer page, Integer Size) {
        Page<ProductDto> data = service.readAll(page, Size);
        return APIPanelResponse.<List<ProductDto>>builder().
                data(data.getContent()).status(APIStatus.OK).
                message("").
                totalCount(data.getTotalElements()).
                totalPages(data.getTotalPages()).
                build();
    }

    @Override
    @ChekPermission("edit_product")
    public APIResponse<ProductDto> edit(ProductDto dto) throws Exception {
        return APIResponse.<ProductDto>builder()
                .status(APIStatus.OK).data(service.update(dto)).
                message("").
                build();
    }
    @GetMapping("cat/{id}")
    @ChekPermission("list_product")
    public APIPanelResponse<List<ProductDto>> getAllByCategory  (@PathVariable Long id,
                                                                 Integer page, Integer Size) {
        Page<ProductDto> data = service.readAllByCategory(id,page, Size);
        return APIPanelResponse.<List<ProductDto>>builder().
                data(data.getContent()).status(APIStatus.OK).
                message("").
                totalCount(data.getTotalElements()).
                totalPages(data.getTotalPages()).
                build();
    }
}
