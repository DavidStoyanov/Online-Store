package com.stoyanov.onlineshoestore.app.services.services;

import com.stoyanov.onlineshoestore.app.errors.offer.OfferNotFoundException;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.Shoe;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.ShoeRepository;
import com.stoyanov.onlineshoestore.app.services.ShoeService;
import com.stoyanov.onlineshoestore.app.services.base.ServiceTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class ShoeServiceTest extends ServiceTestBase {

    @MockBean
    ShoeRepository shoeRepository;

    @Autowired
    ShoeService service;

    @Test
    void delete_whenShoeDoesNotExist_shouldThrowOfferNotFoundException() {
        String shoeId = "shoe-id";

        Mockito.when(shoeRepository.findById(shoeId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(OfferNotFoundException.class,
                () -> service.delete(shoeId));
    }

    @Test
    void getOneById_whenShoeDoesExist_shouldReturnShoe() {
        String shoeId = "shoe-id";

        Shoe shoe = new Shoe();
        shoe.setId(shoeId);

        Mockito.when(shoeRepository.findById(shoeId))
                .thenReturn(Optional.of(shoe));

        ShoeDetailsServiceModel shoeDetails = service.getOneById(shoeId);

        Assertions.assertEquals(shoe.getId(), shoeDetails.getId());
    }

    @Test
    void getOneById_whenShoeDoesNotExist_shouldThrowOfferNotFoundException() {
        String shoeId = "shoe-id";

        Mockito.when(shoeRepository.findById(shoeId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(OfferNotFoundException.class,
                () -> service.getOneById(shoeId));
    }

    @Test
    void getAll_whenTheRepositoryIsEmpty_shouldReturnEmptyList() {
        Mockito.when(shoeRepository.findAll())
                .thenReturn(Collections.emptyList());

        Assertions.assertEquals(0, service.getAll().size());
    }

    @Test
    void getAll_whenTheRepositoryIsNotEmpty_shouldReturnListOfShoes() {
        Shoe shoe1 = new Shoe();
        shoe1.setId("shoe-1");
        Shoe shoe2 = new Shoe();
        shoe2.setId("shoe-2");

        ArrayList<Shoe> list = new ArrayList<>(
                List.of(shoe1, shoe2)
        );

        Mockito.when(shoeRepository.findAll())
                .thenReturn(list);

        Assertions.assertEquals(2, service.getAll().size());
        Assertions.assertEquals("shoe-1", service.getAll().get(0).getId());
        Assertions.assertEquals("shoe-2", service.getAll().get(1).getId());
    }
}