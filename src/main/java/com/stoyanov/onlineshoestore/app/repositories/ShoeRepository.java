package com.stoyanov.onlineshoestore.app.repositories;

import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoeRepository extends JpaRepository<Shoe, String> {

    Optional<Shoe> findById(String id);

    boolean existsByIdAndCreatedBy_Username(String id, String username);
}
