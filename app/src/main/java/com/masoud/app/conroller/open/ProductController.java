package com.masoud.app.conroller.open;

import com.masoud.app.model.APIResponse;
import com.masoud.app.model.enums.APIStatus;
import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.dto.product.LimitedProductDto;
import com.masoud.dto.product.ProductCategoryDto;
import com.masoud.dto.product.ProductDto;
import com.masoud.enums.ProductQueryType;
import com.masoud.service.product.ProductCategoryService;
import com.masoud.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
@Autowired
    public ProductController(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
    this.productCategoryService = productCategoryService;
}
    @GetMapping("category")
    public APIResponse<List<ProductCategoryDto>> getallCategory()
    {
        return APIResponse.<List<ProductCategoryDto>>builder().
                status(APIStatus.OK).data(productCategoryService.readAllActive()).build();
    }

    @GetMapping("top/{type}")
    public APIResponse<List<LimitedProductDto>> getTopProduct(@PathVariable ProductQueryType type)
    {
        return APIResponse.<List<LimitedProductDto>>builder().status(APIStatus.OK)
                .data(productService.read6topProduct(type)).build();
    }

    @GetMapping("{id}")
    public APIResponse<ProductDto> getById(@PathVariable Long id)
    {
        try {
            return APIResponse.<ProductDto>builder().data(productService.readById(id)).
                    status(APIStatus.OK).build();
        } catch (NotFoundExceptions e) {
            return APIResponse.<ProductDto>builder().
            status(APIStatus.NOT_FOUND).message(e.getMessage()).build();

        }

    }




}
