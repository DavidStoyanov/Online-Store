package com.stoyanov.onlineshoestore.app.web.controllers;

import com.stoyanov.onlineshoestore.app.errors.offer.OfferNotFoundException;
import com.stoyanov.onlineshoestore.app.models.services.offer.OfferServiceModel;
import com.stoyanov.onlineshoestore.app.services.offers.OfferService;
import com.stoyanov.onlineshoestore.app.web.controllers.base.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/offer")
public class OfferController extends BaseController {

    private final OfferService offerService;

    private final ModelMapper mapper;

    @Autowired
    public OfferController(OfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.mapper = modelMapper;
    }

    @GetMapping("/create")
    public String create() {
        return "offers/offer-create.html";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(ModelAndView mav, @PathVariable String id) throws OfferNotFoundException {
        OfferServiceModel model = this.offerService.getById(id);
        mav.addObject("model", model);
        mav.addObject("id", id);
        mav.setViewName("offers/offer-edit.html");
        return mav;
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(ModelAndView mav, @PathVariable String id) throws OfferNotFoundException {
        OfferServiceModel offer = this.offerService.getById(id);
        mav.addObject("offer", offer);
        mav.addObject("id", id);
        mav.setViewName("offers/offer-details.html");
        return mav;
    }

    @ExceptionHandler({OfferNotFoundException.class, Exception.class})
    public ModelAndView offerNotFound(Exception exception) {
        ModelAndView mav = new ModelAndView("offers/offers.html");
        if (exception instanceof OfferNotFoundException) {
            mav.addObject("offerExpiredError", exception.getMessage());
            exception.printStackTrace();
        } else {
            mav.addObject("offerExpiredError",
                    "Something wrong happened, we will fix it as soon as possible");
            exception.printStackTrace();
        }

        return mav;
    }
}
