package com.masoud.service.site;

import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.comman.exseptions.ValidationExceptions;
import com.masoud.dataaccess.entity.site.Navi;
import com.masoud.dataaccess.repository.site.NaviRepository;
import com.masoud.dto.site.NaviDto;
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
public class NaviService implements CRUDService<NaviDto>, HasValidation<NaviDto> {
    private final NaviRepository naviRepository;
    private final ModelMapper Mapper;
@Autowired
    public NaviService(NaviRepository naviRepository, ModelMapper modelMapper) {
        this.naviRepository = naviRepository;
    this.Mapper = modelMapper;
}


    public List<NaviDto> readAll()
    {
        return naviRepository.findAllByEnabledIsTrueOrderByOrderNumberAsc().stream()
                .map(x->Mapper.map(x,NaviDto.class)).toList();
    }
    @Override
    public Page<NaviDto> readAll(Integer page, Integer size)
    {
        if(page == null)
        {
            page = 0;
        }
        if(size == null)
        {

            size = 10;
        }
        return naviRepository.findAll(Pageable.ofSize(size).withPage(page)).
               map(x->Mapper.map(x,NaviDto.class));
    }

    @Override
    public NaviDto create(NaviDto naviDto) throws Exception {
        checkValidation(naviDto);
        Navi data = Mapper.map(naviDto, Navi.class);
        data.setEnabled(true);
        Integer lastOrderNumber = naviRepository.findLastOrderNumber();
        if(lastOrderNumber == null)
        {
            lastOrderNumber=0;
        }
        data.setOrderNumber(++lastOrderNumber);
        return Mapper.map(naviRepository.save(data),NaviDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        naviRepository.deleteById(id);
        return true;
    }

    @Override
    public NaviDto update(NaviDto naviDto) throws Exception {
            checkValidation(naviDto);
            if(naviDto.getId() == null||naviDto.getId()<=0)
            {
                throw new ValidationExceptions("Navi id can not be null");
            }
        Navi olddata = naviRepository.findById(naviDto.getId()).orElseThrow(NotFoundExceptions::new);
            olddata.setOrderNumber(Optional.ofNullable(naviDto.getOrderNumber()).orElse(olddata.getOrderNumber()));
            olddata.setLink(Optional.ofNullable(naviDto.getLink()).orElse(olddata.getLink()));
            olddata.setTitle(Optional.ofNullable(naviDto.getTitle()).orElse(olddata.getTitle()));
            naviRepository.save(olddata);
            return Mapper.map(olddata,NaviDto.class);


    }
    @Transactional
    public boolean swapUp(Long id ) throws Exception {

        Navi currentNav = naviRepository.findById(id).orElseThrow(NotFoundExceptions::new);
        Optional<Navi> previous = naviRepository.findFirstByOrderNumberLessThanOrderByOrderNumberDesc(currentNav.getOrderNumber());
        if (previous.isPresent())
        {
            Integer temp=currentNav.getOrderNumber();
            currentNav.setOrderNumber(previous.get().getOrderNumber());
            previous.get().setOrderNumber(temp);
            naviRepository.save(currentNav);
            naviRepository.save(previous.get());
            return true;

        }
        return false;
    }
    @Transactional
    public boolean swapDown(Long id ) throws Exception {

        Navi currentNav = naviRepository.findById(id).orElseThrow(NotFoundExceptions::new);
        Optional<Navi> next = naviRepository.findFirstByOrderNumberGreaterThanOrderByOrderNumberAsc(currentNav.getOrderNumber());
        if (next.isPresent())
        {
            Integer temp=currentNav.getOrderNumber();
            currentNav.setOrderNumber(next.get().getOrderNumber());
            next.get().setOrderNumber(temp);
            naviRepository.save(currentNav);
            naviRepository.save(next.get());
            return true;

        }
        return false;
    }


    @Override
    public void checkValidation(NaviDto naviDto) throws ValidationExceptions {
        if(naviDto==null)
        {
            throw new ValidationExceptions("pleas fill nave data");
        }
        if (naviDto.getTitle()==null||naviDto.getTitle().isEmpty())
        {
            throw new ValidationExceptions("pleas enter titel");
        }
        if (naviDto.getLink()==null||naviDto.getLink().isEmpty())
        {
            throw new ValidationExceptions("pleas enter link");
        }
    }
}
