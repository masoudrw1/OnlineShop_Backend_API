package com.masoud.dataaccess.repository.site;

import com.masoud.dataaccess.entity.site.Content;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    Optional<Content> findFirstByKeynameEqualsIgnoreCase(String key);
}
