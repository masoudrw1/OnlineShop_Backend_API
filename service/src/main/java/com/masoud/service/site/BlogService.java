package com.masoud.service.site;

import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.comman.exseptions.ValidationExceptions;
import com.masoud.dataaccess.entity.site.Blog;
import com.masoud.dataaccess.enums.BlogStatus;
import com.masoud.dataaccess.repository.site.BlogRepository;
import com.masoud.dto.site.BlogDto;
import com.masoud.dto.site.LimetedBlogDto;
import com.masoud.dto.site.SinglblogDto;
import com.masoud.service.base.CRUDService;
import com.masoud.service.base.HasValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class BlogService implements CRUDService<BlogDto> , HasValidation<BlogDto> {
    private final BlogRepository blogRepository;
    private final ModelMapper mapper;
@Autowired
    public BlogService(BlogRepository blogRepository, ModelMapper mapper) {
        this.blogRepository = blogRepository;
        this.mapper = mapper;
}

    public SinglblogDto readById(Long id) throws NotFoundExceptions {
        Blog blog =blogRepository.findById(id).orElseThrow(NotFoundExceptions::new);
        return mapper.map(blog,SinglblogDto.class);

    }
    public List<LimetedBlogDto> readAllPublished(Integer page, Integer size)
    {
        if(page == null)
        {
            page = 0;
        }
        if(size==null)
        {
            size = 10;
        }
        return blogRepository.findAllPublished(Pageable.ofSize(size).withPage(page)).stream().
                map(blog -> mapper.map(blog,LimetedBlogDto.class)).toList();

    }
@Override
    public Page<BlogDto> readAll(Integer page, Integer size)
    {
        if(page == null)
        {
            page = 0;
        }
        if(size==null)
        {
            size = 10;
        }
        return blogRepository.findAll(Pageable.ofSize(size).withPage(page)).
                map(x->mapper.map(x,BlogDto.class));

    }


    @Override
    public BlogDto create(BlogDto blogDto) throws Exception {
        checkValidation(blogDto);
        Blog data=mapper.map(blogDto,Blog.class);
        if(data.getPublishDate()==null) {
            data.setPublishDate(LocalDateTime.now());
        }
        if(data.getStatus()==null) {
            data.setStatus(BlogStatus.Published);
        }

        data.setVisitcount(0L);
        return mapper.map(blogRepository.save(data),BlogDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        blogRepository.deleteById(id);
        return true;
    }


    @Override
    public BlogDto update(BlogDto blogDto) throws Exception {
       checkValidation(blogDto);
       if (blogDto.getId() == null||blogDto.getId()<=0)
       {
           throw new ValidationExceptions("id is required");
       }
       Blog olddata=blogRepository.findById(blogDto.getId()).orElseThrow(NotFoundExceptions::new);
       olddata.setTitle(Optional.ofNullable(blogDto.getTitle()).orElse(olddata.getTitle()));
       olddata.setSubtitle(Optional.ofNullable(blogDto.getSubtitle()).orElse(olddata.getSubtitle()));
       olddata.setPublishDate(Optional.ofNullable(blogDto.getPublishDate()).orElse(olddata.getPublishDate()));
       olddata.setStatus(Optional.ofNullable(blogDto.getStatus()).orElse(olddata.getStatus()));
       olddata.setDescription(Optional.ofNullable(blogDto.getDescription()).orElse(olddata.getDescription()));
       blogRepository.save(olddata);
       return mapper.map(olddata,BlogDto.class);




    }
    @Override
    public void checkValidation(BlogDto blogDto) throws ValidationExceptions {
    if (blogDto==null)
    {
        throw new ValidationExceptions("blogDto is required");
    }
    if(blogDto.getTitle()==null||blogDto.getTitle().isEmpty())
    {
        throw new ValidationExceptions("title is required");
    }
    if(blogDto.getSubtitle()==null||blogDto.getSubtitle().isEmpty())
    {
        throw new ValidationExceptions("subtitle is required");
    }

    }
}
