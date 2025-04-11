package com.masoud.dataaccess.repository.site;

import com.masoud.dataaccess.entity.site.Navi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NaviRepository extends JpaRepository<Navi, Long>
{
    List<Navi> findAllByEnabledIsTrueOrderByOrderNumberAsc();



    @Query("""
        select orderNumber from Navi order by orderNumber desc limit 1
    """)
    Integer findLastOrderNumber();


    Optional<Navi> findFirstByOrderNumberLessThanOrderByOrderNumberDesc(Integer orderNumber);
    Optional<Navi> findFirstByOrderNumberGreaterThanOrderByOrderNumberAsc(Integer orderNumber);


}
