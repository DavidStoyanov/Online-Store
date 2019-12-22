package com.stoyanov.onlineshoestore.app.controllers.rest;

import com.stoyanov.onlineshoestore.app.services.offer.ShoeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OfferRestController {

    private final ShoeService shoeService;

    @Autowired
    public OfferRestController(ShoeService shoeService) {
        this.shoeService = shoeService;
    }

    @GetMapping("/shoe-sizes")
    public List sizes() {
        return this.shoeService.getShoeSizes();
    }


    @GetMapping("/shoe-types")
    public List types() {
        return this.shoeService.getShoeTypes();
    }
}
