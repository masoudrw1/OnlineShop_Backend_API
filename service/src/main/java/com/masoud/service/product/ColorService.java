package com.masoud.service.product;

import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.comman.exseptions.ValidationExceptions;
import com.masoud.dataaccess.entity.product.Color;
import com.masoud.dataaccess.repository.product.ColorRepository;
import com.masoud.dto.product.ColorDto;
import com.masoud.service.base.CRUDService;
import com.masoud.service.base.HasValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ColorService implements CRUDService<ColorDto>, HasValidation<ColorDto> {
    private final ColorRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public ColorService(ColorRepository blogRepository, ColorRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public Page<ColorDto> readAll(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page)).
                map(x -> mapper.map(x, ColorDto.class));

    }


    @Override
    public ColorDto create(ColorDto blogDto) throws Exception {
        checkValidation(blogDto);
        Color data = mapper.map(blogDto, Color.class);
        return mapper.map(repository.save(data), ColorDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }


    @Override
    public ColorDto update(ColorDto blogDto) throws Exception {
        checkValidation(blogDto);
        if (blogDto.getId() == null || blogDto.getId() <= 0) {
            throw new ValidationExceptions("id is required");
        }
        Color olddata = repository.findById(blogDto.getId()).orElseThrow(NotFoundExceptions::new);
        olddata.setName(Optional.ofNullable(blogDto.getName()).orElse(olddata.getName()));
        olddata.setHex(Optional.ofNullable(blogDto.getHex()).orElse(olddata.getHex()));
        repository.save(olddata);
        return mapper.map(olddata, ColorDto.class);


    }

    @Override
    public void checkValidation(ColorDto blogDto) throws ValidationExceptions {
        if (blogDto == null) {
            throw new ValidationExceptions("blogDto is required");
        }
        if (blogDto.getName() == null || blogDto.getName().isEmpty()) {
            throw new ValidationExceptions("name is required");
        }
        if (blogDto.getHex() == null || blogDto.getHex().isEmpty()) {
            throw new ValidationExceptions("Hex is required");
        }

    }
}
