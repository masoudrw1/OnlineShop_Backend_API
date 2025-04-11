package com.masoud.dataaccess.repository.files;

import com.masoud.dataaccess.entity.files.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilesRepository extends JpaRepository<Files, Long> {

    Optional<Files> findFirstByNameEqualsIgnoreCase(String name);

}
