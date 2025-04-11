package com.masoud.service.product;

import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.comman.exseptions.ValidationExceptions;
import com.masoud.dataaccess.entity.product.Size;
import com.masoud.dataaccess.repository.product.SizeRepository;
import com.masoud.dto.product.SizeDto;
import com.masoud.service.base.CRUDService;
import com.masoud.service.base.HasValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SizeService implements CRUDService<SizeDto>, HasValidation<SizeDto> {
    private final SizeRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public SizeService(SizeRepository blogRepository, SizeRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public Page<SizeDto> readAll(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page)).
                map(x -> mapper.map(x, SizeDto.class));

    }


    @Override
    public SizeDto create(SizeDto blogDto) throws Exception {
        checkValidation(blogDto);
        Size data = mapper.map(blogDto, Size.class);
        return mapper.map(repository.save(data), SizeDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }


    @Override
    public SizeDto update(SizeDto blogDto) throws Exception {
        checkValidation(blogDto);
        if (blogDto.getId() == null || blogDto.getId() <= 0) {
            throw new ValidationExceptions("id is required");
        }
        Size olddata = repository.findById(blogDto.getId()).orElseThrow(NotFoundExceptions::new);
        olddata.setTitle(Optional.ofNullable(blogDto.getTitle()).orElse(olddata.getTitle()));
        olddata.setDescription(Optional.ofNullable(blogDto.getDescription()).orElse(olddata.getDescription()));
        repository.save(olddata);
        return mapper.map(olddata, SizeDto.class);


    }

    @Override
    public void checkValidation(SizeDto blogDto) throws ValidationExceptions {
        if (blogDto == null) {
            throw new ValidationExceptions("blogDto is required");
        }
        if (blogDto.getTitle() == null || blogDto.getTitle().isEmpty()) {
            throw new ValidationExceptions("title is required");
        }
        if (blogDto.getDescription() == null || blogDto.getDescription().isEmpty()) {
            throw new ValidationExceptions("description is required");
        }

    }
}
