package com.masoud.app.conroller.panel.product;


import com.masoud.app.anotation.ChekPermission;
import com.masoud.app.conroller.base.CreateController;
import com.masoud.app.conroller.base.ReadController;
import com.masoud.app.conroller.base.UpdateController;
import com.masoud.app.model.APIPanelResponse;
import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.dto.product.ProductCategoryDto;
import com.masoud.service.product.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/panel/productCategory")
public class ProductCategoryPanelController implements
        CreateController<ProductCategoryDto>,
        UpdateController<ProductCategoryDto>,
        ReadController<ProductCategoryDto> {
    private final ProductCategoryService service;
@Autowired
    public ProductCategoryPanelController(ProductCategoryService service) {
        this.service = service;
    }


    @Override
    @ChekPermission("add_product_category")
    public APIResponse<ProductCategoryDto> add(ProductCategoryDto dto) throws Exception {
        return APIResponse.<ProductCategoryDto>builder()
                .status(APIStatus.OK).data(service.create(dto)).
                message("").
                build();
    }

    @Override
    @ChekPermission("list_product_category")
    public APIPanelResponse<List<ProductCategoryDto>> getAll(Integer page, Integer ProductCategory) {
        Page<ProductCategoryDto> data = service.readAll(page, ProductCategory);
        return APIPanelResponse.<List<ProductCategoryDto>>builder().
                data(data.getContent()).status(APIStatus.OK).
                message("").
                totalCount(data.getTotalElements()).
                totalPages(data.getTotalPages()).
                build();
    }

    @Override
    @ChekPermission("edit_product_category")
    public APIResponse<ProductCategoryDto> edit(ProductCategoryDto dto) throws Exception {
        return APIResponse.<ProductCategoryDto>builder()
                .status(APIStatus.OK).data(service.update(dto)).
                message("").
                build();
    }
}
