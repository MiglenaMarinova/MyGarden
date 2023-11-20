package com.example.mygarden.repository;

import com.example.mygarden.model.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    Optional<List<Picture>> findAllByTitle(String title);


}
