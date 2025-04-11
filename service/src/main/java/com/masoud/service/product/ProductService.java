package com.masoud.service.product;

import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.comman.exseptions.ValidationExceptions;
import com.masoud.dataaccess.entity.files.Files;
import com.masoud.dataaccess.entity.product.Product;
import com.masoud.dataaccess.repository.product.ProductCategoryRepository;
import com.masoud.dataaccess.repository.product.ProductRepository;
import com.masoud.dto.product.LimitedProductDto;
import com.masoud.dto.product.ProductDto;
import com.masoud.enums.ProductQueryType;
import com.masoud.service.base.CRUDService;
import com.masoud.service.base.HasValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements CRUDService<ProductDto>, HasValidation<ProductDto> {
    private final ProductRepository productRepository;

    private final ModelMapper mapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }


    public List<LimitedProductDto> read6topProduct(ProductQueryType type) {
        List<Product> result = new ArrayList<>();
        switch (type) {
            case popular -> {
                result = productRepository.find6popularProducts();
            }
            case newest -> {
                result = productRepository.find6NewestProducts();
            }
            case cheapest -> {
                result = productRepository.find6CheapestProducts();
            }
            case expensive -> {
                result = productRepository.find6ExpensiveProducts();
            }
        }
        return result.stream().map(x -> mapper.map(x, LimitedProductDto.class)).toList();

    }

    public ProductDto readById(Long id) throws NotFoundExceptions {
        Product product = productRepository.findById(id).orElseThrow(NotFoundExceptions::new);
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public Page<ProductDto> readAll(Integer page, Integer size)
    {
        if(page == null)
        {
            page = 0;
        }
        if(size==null)
        {
            size = 10;
        }
        return productRepository.findAll(Pageable.ofSize(size).withPage(page)).
                map(x->mapper.map(x,ProductDto.class));

    }


    @Override
    public ProductDto create(ProductDto dto) throws Exception {
        checkValidation(dto);
        Product data=mapper.map(dto,Product.class);
        data.setEnabled(true);
        data.setVisitcount(0L);
        data.setExist(true);
        data.setAddDate(LocalDateTime.now());
        return mapper.map(productRepository.save(data),ProductDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        productRepository.deleteById(id);
        return true;
    }


    @Override
    public ProductDto update(ProductDto dto) throws Exception {
        checkValidation(dto);
        if (dto.getId() == null||dto.getId()<=0)
        {
            throw new ValidationExceptions("id is required");
        }
        Product olddata=productRepository.findById(dto.getId()).orElseThrow(NotFoundExceptions::new);
        olddata.setTitle(Optional.ofNullable(dto.getTitle()).orElse(olddata.getTitle()));
        olddata.setDescription(Optional.ofNullable(dto.getDescription()).orElse(olddata.getDescription()));
        olddata.setPrice(Optional.ofNullable(dto.getPrice()).orElse(olddata.getPrice()));
        olddata.setEnabled(Optional.ofNullable(dto.getEnabled()).orElse(olddata.getEnabled()));
        olddata.setExist(Optional.ofNullable(dto.getExist()).orElse(olddata.getExist()));
        if(dto.getImage()!=null) {
            olddata.setImage(Optional.ofNullable(mapper.map(dto.getImage(), Files.class)).orElse(olddata.getImage()));
        }
        productRepository.save(olddata);
        return mapper.map(olddata,ProductDto.class);




    }
    public Page<ProductDto> readAllByCategory(Long id ,Integer page, Integer size)
    {
        if(page == null)
        {
            page = 0;
        }
        if(size==null)
        {
            size = 10;
        }
        return productRepository.findAllByCategory_Id(id,Pageable.ofSize(size).withPage(page)).
                map(x->mapper.map(x,ProductDto.class));

    }
    @Override
    public void checkValidation(ProductDto dto) throws ValidationExceptions {
        if (dto==null)
        {
            throw new ValidationExceptions("dto is required");
        }
        if(dto.getTitle()==null||dto.getTitle().isEmpty())
        {
            throw new ValidationExceptions("title is required");
        }
        if(dto.getPrice()==null||dto.getPrice()<0)
        {
            throw new ValidationExceptions("subtitle is price");
        }

    }



}
