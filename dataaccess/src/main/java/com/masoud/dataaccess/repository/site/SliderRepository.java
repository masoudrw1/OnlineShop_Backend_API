package com.masoud.dataaccess.repository.site;

import com.masoud.dataaccess.entity.site.Navi;
import com.masoud.dataaccess.entity.site.Slider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SliderRepository extends JpaRepository<Slider, Long> {
    List<Slider> findByEnabledIsTrueOrderByOrderNumberAsc();

    @Query("""
        select orderNumber from Slider order by orderNumber desc limit 1
    """)
    Integer findLastOrderNumber();

    Optional<Slider> findFirstByOrderNumberLessThanOrderByOrderNumberDesc(Integer orderNumber);
    Optional<Slider> findFirstByOrderNumberGreaterThanOrderByOrderNumberAsc(Integer orderNumber);
}
