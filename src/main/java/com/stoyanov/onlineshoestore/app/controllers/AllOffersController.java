package com.stoyanov.onlineshoestore.app.controllers;

import com.stoyanov.onlineshoestore.app.models.view.offer.OfferDetailsViewModel;
import com.stoyanov.onlineshoestore.app.services.ShoeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AllOffersController {

    private final ShoeService shoeService;
    private final ModelMapper mapper;

    @Autowired
    public AllOffersController(ShoeService shoeService, ModelMapper mapper) {
        this.shoeService = shoeService;
        this.mapper = mapper;
    }

    @GetMapping("/offers")
    public ModelAndView offers(ModelAndView mav) {
        List<OfferDetailsViewModel> offers = this.shoeService.getAll().stream()
                .map(serviceModel -> this.mapper.map(serviceModel, OfferDetailsViewModel.class))
                .collect(Collectors.toList());
        mav.addObject("offers", offers);
        mav.setViewName("offers/offers.html");
        return mav;
    }
}
