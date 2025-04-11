package com.masoud.service.site;

import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.comman.exseptions.ValidationExceptions;
import com.masoud.dataaccess.entity.files.Files;
import com.masoud.dataaccess.entity.site.Navi;
import com.masoud.dataaccess.entity.site.Slider;
import com.masoud.dataaccess.repository.site.SliderRepository;
import com.masoud.dto.site.SliderDto;
import com.masoud.service.base.CRUDService;
import com.masoud.service.base.HasValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SliderService implements CRUDService<SliderDto>, HasValidation<SliderDto> {
    private final SliderRepository sliderRepository;
    private final ModelMapper mapper;
@Autowired
    public SliderService(SliderRepository sliderRepository, ModelMapper mapper) {
        this.sliderRepository = sliderRepository;
    this.mapper = mapper;
}
    public List<SliderDto> readAll() {
    return sliderRepository.findByEnabledIsTrueOrderByOrderNumberAsc().stream().map(x->mapper.map(x,SliderDto.class)).toList();

    }

    @Override
    public SliderDto create(SliderDto sliderDto) throws Exception {
        checkValidation(sliderDto);
        Slider data =mapper.map(sliderDto,Slider.class);
        data.setEnabled(true);
        Integer lastordernumber=sliderRepository.findLastOrderNumber();
        if(lastordernumber==null)
        {
            lastordernumber=0;
        }
        data.setOrderNumber(++lastordernumber);
        return mapper.map(sliderRepository.save(data),SliderDto.class);
    }

    @Override
    public Boolean delete(Long id) {
       sliderRepository.deleteById(id);
       return true;
    }


    @Override
    public Page<SliderDto> readAll(Integer page, Integer size) {
        if(page==null)
        {
            page=0;

        }
        if (size==null)
        {
            size=10;
        }
        return sliderRepository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x->mapper.map(x,SliderDto.class));
    }

    @Override
    public SliderDto update(SliderDto sliderDto) throws Exception {
        checkValidation(sliderDto);
        if(sliderDto.getId()==null||sliderDto.getId()<=0)
        {
                throw new ValidationExceptions("id is required");
        }
        Slider olddata=sliderRepository.findById(sliderDto.getId()).orElseThrow(NotFoundExceptions::new);
        olddata.setOrderNumber(Optional.ofNullable(sliderDto.getOrderNumber()).orElse(olddata.getOrderNumber()));
        olddata.setTitle(Optional.ofNullable(sliderDto.getTitle()).orElse(olddata.getTitle()));
        olddata.setLink(Optional.ofNullable(sliderDto.getLink()).orElse(olddata.getLink()));
        if(sliderDto.getImag()!=null) {
            olddata.setImag(Optional.ofNullable(mapper.map(sliderDto.getImag(), Files.class)).orElse(olddata.getImag()));
        }
        sliderRepository.save(olddata);
        return mapper.map(olddata,SliderDto.class);
    }
    @Transactional
    public boolean swapUp(Long id ) throws Exception {

        Slider currentNav = sliderRepository.findById(id).orElseThrow(NotFoundExceptions::new);
        Optional<Slider> previous = sliderRepository.findFirstByOrderNumberLessThanOrderByOrderNumberDesc(currentNav.getOrderNumber());
        if (previous.isPresent())
        {
            Integer temp=currentNav.getOrderNumber();
            currentNav.setOrderNumber(previous.get().getOrderNumber());
            previous.get().setOrderNumber(temp);
            sliderRepository.save(currentNav);
            sliderRepository.save(previous.get());
            return true;

        }
        return false;
    }
    @Transactional
    public boolean swapDown(Long id ) throws Exception {

        Slider currentNav = sliderRepository.findById(id).orElseThrow(NotFoundExceptions::new);
        Optional<Slider> next = sliderRepository.findFirstByOrderNumberGreaterThanOrderByOrderNumberAsc(currentNav.getOrderNumber());
        if (next.isPresent())
        {
            Integer temp=currentNav.getOrderNumber();
            currentNav.setOrderNumber(next.get().getOrderNumber());
            next.get().setOrderNumber(temp);
            sliderRepository.save(currentNav);
            sliderRepository.save(next.get());
            return true;

        }
        return false;
    }




    @Override
    public void checkValidation(SliderDto sliderDto) throws ValidationExceptions {
        if(sliderDto==null)
        {
            throw new ValidationExceptions("pleas fill  data");
        }
        if (sliderDto.getTitle()==null||sliderDto.getTitle().isEmpty())
        {
            throw new ValidationExceptions("pleas enter titel");
        }
        if (sliderDto.getLink()==null||sliderDto.getLink().isEmpty())
        {
            throw new ValidationExceptions("pleas enter link");
        }

    }
}
