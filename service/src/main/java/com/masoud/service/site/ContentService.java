package com.masoud.service.site;

import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.comman.exseptions.ValidationExceptions;
import com.masoud.config.ModelMapperConfig;
import com.masoud.dataaccess.entity.site.Content;
import com.masoud.dataaccess.entity.site.Navi;
import com.masoud.dataaccess.repository.site.ContentRepository;
import com.masoud.dto.site.ContentDto;
import com.masoud.dto.site.NaviDto;
import com.masoud.service.base.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentService implements CreateService<ContentDto>,
        UpdateService<ContentDto>, ReadService<ContentDto>, HasValidation<ContentDto> {
    final private ContentRepository contentRepository;
    private final ModelMapper mapper;
@Autowired
    public ContentService(ContentRepository contentRepository, ModelMapper mapper) {
        this.contentRepository = contentRepository;
    this.mapper = mapper;
}
    public List<ContentDto> readAll()
    {
        return contentRepository.findAll().stream()
                .map(x->mapper.map(x,ContentDto.class)).toList();
    }


    public ContentDto read(String key) throws NotFoundExceptions {
        Content content=contentRepository.findFirstByKeynameEqualsIgnoreCase(key).orElseThrow(NotFoundExceptions::new);
        return mapper.map(content,ContentDto.class);

    }

    @Override
    public ContentDto create(ContentDto contentDto) throws Exception {
        checkValidation(contentDto);
        Content data = mapper.map(contentDto, Content.class);
        return mapper.map(contentRepository.save(data),ContentDto.class);
    }

    @Override
    public void checkValidation(ContentDto contentDto) throws ValidationExceptions
    {
        if(contentDto==null)
        {
            throw new ValidationExceptions("pleas fill nave data");
        }
        if (contentDto.getKeyname()==null||contentDto.getKeyname().isEmpty())
        {
            throw new ValidationExceptions("pleas enter keyname");
        }
        if (contentDto.getValueCuntent()==null||contentDto.getValueCuntent().isEmpty())
        {
            throw new ValidationExceptions("pleas enter Value");
        }


    }

    @Override
    public Page<ContentDto> readAll(Integer page, Integer size) {
        if(page == null)
        {
            page = 0;
        }
        if(size == null)
        {

            size = 10;
        }
        return contentRepository.findAll(Pageable.ofSize(size).withPage(page)).
                map(x->mapper.map(x, ContentDto.class));
    }

    @Override
    public ContentDto update(ContentDto contentDto) throws Exception {
        checkValidation(contentDto);
        if(contentDto.getId() == null||contentDto.getId()<=0)
        {
            throw new ValidationExceptions("Navi id can not be null");
        }
        Content olddata = contentRepository.findById(contentDto.getId()).orElseThrow(NotFoundExceptions::new);
        olddata.setKeyname(Optional.ofNullable(contentDto.getKeyname()).orElse(olddata.getKeyname()));
        olddata.setValueCuntent(Optional.ofNullable(contentDto.getValueCuntent()).orElse(olddata.getValueCuntent()));
        contentRepository.save(olddata);
        return mapper.map(olddata,ContentDto.class);


    }
}
