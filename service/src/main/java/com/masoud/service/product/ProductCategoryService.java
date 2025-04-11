package com.masoud.service.product;

import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.comman.exseptions.ValidationExceptions;
import com.masoud.dataaccess.entity.files.Files;
import com.masoud.dataaccess.entity.product.ProductCategory;
import com.masoud.dataaccess.repository.product.ProductCategoryRepository;
import com.masoud.dto.product.ProductCategoryDto;
import com.masoud.service.base.CRUDService;
import com.masoud.service.base.HasValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService implements CRUDService<ProductCategoryDto>, HasValidation<ProductCategoryDto> {
    private final ProductCategoryRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository blogRepository, ProductCategoryRepository repository, ProductCategoryRepository productCategoryRepository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public Page<ProductCategoryDto> readAll(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page)).
                map(x -> mapper.map(x, ProductCategoryDto.class));

    }


    @Override
    public ProductCategoryDto create(ProductCategoryDto Dto) throws Exception {
        checkValidation(Dto);
        ProductCategory data = mapper.map(Dto, ProductCategory.class);
        return mapper.map(repository.save(data), ProductCategoryDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }


    @Override
    public ProductCategoryDto update(ProductCategoryDto Dto) throws Exception {
        checkValidation(Dto);
        if (Dto.getId() == null || Dto.getId() <= 0) {
            throw new ValidationExceptions("id is required");
        }
        ProductCategory olddata = repository.findById(Dto.getId()).orElseThrow(NotFoundExceptions::new);
        olddata.setTitle(Optional.ofNullable(Dto.getTitle()).orElse(olddata.getTitle()));
        olddata.setDescription(Optional.ofNullable(Dto.getDescription()).orElse(olddata.getDescription()));
        if (Dto.getImage() != null) {
            olddata.setImage(Optional.ofNullable(mapper.map(Dto.getImage(), Files.class)).orElse(olddata.getImage()));
        }
        repository.save(olddata);
        return mapper.map(olddata, ProductCategoryDto.class);


    }
    public List<ProductCategoryDto> readAllActive()
    {
        return repository.findAllByEnabledIsTrue().stream()
                .map(x->mapper.map(x,ProductCategoryDto.class)).toList();
    }


    @Override
    public void checkValidation(ProductCategoryDto Dto) throws ValidationExceptions {
        if (Dto == null) {
            throw new ValidationExceptions("Dto is required");
        }
        if (Dto.getTitle() == null || Dto.getTitle().isEmpty()) {
            throw new ValidationExceptions("title is required");
        }
        if (Dto.getDescription() == null || Dto.getDescription().isEmpty()) {
            throw new ValidationExceptions("Description is required");
        }

    }
}
