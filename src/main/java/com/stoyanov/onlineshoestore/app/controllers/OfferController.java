package com.stoyanov.onlineshoestore.app.controllers;

import com.stoyanov.onlineshoestore.app.controllers.base.BaseController;
import com.stoyanov.onlineshoestore.app.errors.offer.OfferNotFoundException;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeSaveServiceModel;
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

    @PostMapping("/delete/{id}")
    public String deleteConfirm(@PathVariable String id) {
        this.shoeService.delete(id);

        return "redirect:/offers";
    }


    @PostMapping("/create/1")
    public String createConfirm(@ModelAttribute ShoeSaveViewModel viewModel) {
        ShoeSaveServiceModel serviceModel = this.mapper.map(viewModel, ShoeSaveServiceModel.class);
        this.shoeService.create(serviceModel);

        return"redirect:/offers";
    }

//    @GetMapping("/edit/1/{id}")
//    public ModelAndView edit(@ModelAttribute("editModel") OfferEditViewModel viewModel,
//                             @PathVariable String id,
//                             ModelAndView mav) {
//
//        OfferDetailsViewModel offer = this.mapper.map(this.shoeService.getOneById(id), OfferDetailsViewModel.class);
//        mav.addObject("offer", offer);
//
//        mav.setViewName("offers/edit-offer.html");
//        return mav;
//    }
//
//    @PostMapping("/edit/{id}")
//    public ModelAndView editConfirm(@Valid @ModelAttribute("editModel") OfferEditViewModel viewModel,
//                                    @PathVariable String id,
//                                    BindingResult bindingResult,
//                                    ModelAndView mav) {
//        if (bindingResult.hasErrors()) {
//            return new ModelAndView("offers/edit-offer.html");
//        }
//
//        ShoeSaveServiceModel serviceModel = this.mapper.map(viewModel, ShoeSaveServiceModel.class);
//
//        this.shoeService.edit(serviceModel);
//
//        mav.setViewName("redirect:/offers");
//        return mav;
//    }

    @GetMapping("/details/1/{id}")
    public ModelAndView details(@PathVariable String id, ModelAndView mav) {
        ShoeDetailsServiceModel serviceModel = this.shoeService.getOneById(id);
        ShoeDetailsViewModel offer = this.mapper.map(serviceModel, ShoeDetailsViewModel.class);
        mav.addObject("offer", offer);

        mav.setViewName("offers/details/offer-shoe.html");
        return mav;
    }

    @ExceptionHandler({OfferNotFoundException.class, Exception.class})
    //@ExceptionHandler(OfferNotFoundException.class)
    public ModelAndView offerNotFound(Exception exception) {
        ModelAndView mav = new ModelAndView("offers/offers.html");
        if (exception instanceof OfferNotFoundException) {
            mav.addObject("offerExpiredError", exception.getMessage());
        } else {
            mav.addObject("offerExpiredError", "Something wrong happened, we will fix it as soon as possible");
        }

        return mav;
    }
}
