package com.stoyanov.onlineshoestore.app.repositories;

import com.stoyanov.onlineshoestore.app.models.entities.offer.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, String> {
}
