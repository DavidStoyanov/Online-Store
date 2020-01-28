package com.stoyanov.onlineshoestore.app.repositories;

import com.stoyanov.onlineshoestore.app.models.entities.offer.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AttributeRepository extends JpaRepository<Attribute, String> {

    Optional<Attribute> findByName(String name);

    boolean existsByName(String name);
}
