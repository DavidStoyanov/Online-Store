package com.stoyanov.onlineshoestore.app.web.controllers;

import com.stoyanov.onlineshoestore.app.models.services.offer.OfferServiceModel;
import com.stoyanov.onlineshoestore.app.models.services.offer.OffersServiceModel;
import com.stoyanov.onlineshoestore.app.models.services.offer.PhotoServiceModel;
import com.stoyanov.onlineshoestore.app.services.offers.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OffersController {

    private final OfferService offerService;

    private final ModelMapper mapper;

    @Autowired
    public OffersController(OfferService offerService, ModelMapper mapper) {
        this.offerService = offerService;
        this.mapper = mapper;
    }

    @GetMapping("/offers")
    public ModelAndView offers(ModelAndView mav) {
        List<OfferServiceModel> offerModelList = this.offerService.getAll();
        List<OffersServiceModel> offers = offerModelList.stream()
                .map(offerService -> {
                    OffersServiceModel model = this.mapper.map(offerService, OffersServiceModel.class);
                    PhotoServiceModel firstPhoto = offerService.getPhotos().stream()
                            .filter(p -> p.getPosition() == 1)
                            .findFirst()
                            .orElse(new PhotoServiceModel());
                    model.setPhoto(firstPhoto);

                    return model;
                })
                .collect(Collectors.toList());

        mav.addObject("offers", offers);
        mav.setViewName("offers/offers.html");
        return mav;
    }
}
