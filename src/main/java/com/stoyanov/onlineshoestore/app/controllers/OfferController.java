package com.stoyanov.onlineshoestore.app.controllers;

import com.stoyanov.onlineshoestore.app.controllers.base.BaseController;
import com.stoyanov.onlineshoestore.app.errors.offer.OfferNotFoundException;
import com.stoyanov.onlineshoestore.app.models.service.offer.clothes.ClothesDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.clothes.ClothesSaveServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeSaveServiceModel;
import com.stoyanov.onlineshoestore.app.models.view.offer.clothes.ClothesDetailsViewModel;
import com.stoyanov.onlineshoestore.app.models.view.offer.shoe.ShoeDetailsViewModel;
import com.stoyanov.onlineshoestore.app.models.view.offer.shoe.ShoeSaveViewModel;
import com.stoyanov.onlineshoestore.app.services.offers.ClothesService;
import com.stoyanov.onlineshoestore.app.services.offers.ShoeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/offer")
public class OfferController extends BaseController {

    private final ShoeService shoeService;
    private final ClothesService clothesService;

    private final ModelMapper mapper;

    @Autowired
    public OfferController(ShoeService shoeService,
                           ClothesService clothesService,
                           ModelMapper modelMapper) {
        this.shoeService = shoeService;
        this.clothesService = clothesService;
        this.mapper = modelMapper;
    }

    @GetMapping("/create")
    public String create() {
        return "offers/create-offer.html";
    }


    @PostMapping("/create/1")
    public String createShoe(@ModelAttribute ShoeSaveViewModel viewModel) {
        ShoeSaveServiceModel serviceModel = this.mapper.map(viewModel, ShoeSaveServiceModel.class);
        this.shoeService.create(serviceModel);

        return"redirect:/offers";
    }

    @PostMapping("/create/2")
    public String createClothes(@ModelAttribute ClothesSaveServiceModel viewModel) {
        ClothesSaveServiceModel serviceModel = this.mapper.map(viewModel, ClothesSaveServiceModel.class);
        this.clothesService.create(serviceModel);

        return"redirect:/offers";
    }

    @GetMapping("/edit/1/{id}")
    public ModelAndView editShoe(@PathVariable String id, ModelAndView mav) {
        ShoeDetailsViewModel viewModel = this.mapper.map(this.shoeService.getOneById(id), ShoeDetailsViewModel.class);
        mav.addObject("saveModel", viewModel);

        mav.setViewName("offers/edit/offer-shoe.html");
        return mav;
    }

    @GetMapping("/edit/2/{id}")
    public ModelAndView editClothes(@PathVariable String id, ModelAndView mav) {
        ClothesDetailsViewModel viewModel = this.mapper.map(this.clothesService.getOneById(id), ClothesDetailsViewModel.class);
        mav.addObject("saveModel", viewModel);

        mav.setViewName("offers/edit/offer-clothes.html");
        return mav;
    }

    @PostMapping("/edit/1/{id}")
    public String editShoeConfirm(@ModelAttribute ShoeSaveViewModel viewModel, @PathVariable String id) {
        ShoeSaveServiceModel serviceModel = this.mapper.map(viewModel, ShoeSaveServiceModel.class);
        this.shoeService.edit(serviceModel);

        return "redirect:/offer/details/1/" + id;
    }

    @PostMapping("/edit/2/{id}")
    public String editClothesConfirm(@ModelAttribute ClothesSaveServiceModel viewModel, @PathVariable String id) {
        ClothesSaveServiceModel serviceModel = this.mapper.map(viewModel, ClothesSaveServiceModel.class);
        this.clothesService.edit(serviceModel);

        return "redirect:/offer/details/2/" + id;
    }

    @PostMapping("/delete/1/{id}")
    public String deleteShoeConfirm(@PathVariable String id) {
        this.shoeService.delete(id);

        return "redirect:/offers";
    }

    @PostMapping("/delete/2/{id}")
    public String deleteClothesConfirm(@PathVariable String id) {
        this.clothesService.delete(id);

        return "redirect:/offers";
    }

    @GetMapping("/details/1/{id}")
    public ModelAndView detailsShoe(@PathVariable String id, ModelAndView mav) {
        ShoeDetailsServiceModel serviceModel = this.shoeService.getOneById(id);
        ShoeDetailsViewModel offer = this.mapper.map(serviceModel, ShoeDetailsViewModel.class);
        mav.addObject("offer", offer);

        mav.setViewName("offers/details/offer-shoe.html");
        return mav;
    }

    @GetMapping("/details/2/{id}")
    public ModelAndView detailsClothes(@PathVariable String id, ModelAndView mav) {
        ClothesDetailsServiceModel serviceModel = this.clothesService.getOneById(id);
        ClothesDetailsViewModel offer = this.mapper.map(serviceModel, ClothesDetailsViewModel.class);
        mav.addObject("offer", offer);

        mav.setViewName("offers/details/offer-clothes.html");
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
