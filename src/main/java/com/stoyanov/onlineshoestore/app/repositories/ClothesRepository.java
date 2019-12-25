package com.stoyanov.onlineshoestore.app.repositories;

import com.stoyanov.onlineshoestore.app.models.entity.offer.clothes.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, String> {
}
