package com.stoyanov.onlineshoestore.app.controllers;

import com.stoyanov.onlineshoestore.app.controllers.base.BaseController;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.OfferCreateServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.OfferEditServiceModel;
import com.stoyanov.onlineshoestore.app.models.view.offer.OfferCreateViewModel;
import com.stoyanov.onlineshoestore.app.models.view.offer.OfferEditViewModel;
import com.stoyanov.onlineshoestore.app.services.ShoeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/offer")
public class OfferController extends BaseController {

    private final ShoeService shoeService;

    private final ModelMapper mapper;


    @Autowired
    public OfferController(ShoeService shoeService, ModelMapper modelMapper) {
        this.shoeService = shoeService;
        this.mapper = modelMapper;
    }


    @ModelAttribute("createModel")
    public OfferCreateViewModel createModel() {
        return new OfferCreateViewModel();
    }

    @GetMapping("/create")
    public String create(@ModelAttribute("createModel") OfferCreateViewModel viewModel) {
        return "offers/create-offer.html";
    }

    @PostMapping("/create")
    public ModelAndView createConfirm(@Valid @ModelAttribute("createModel") OfferCreateViewModel viewModel,
                                      BindingResult bindingResult,
                                      HttpSession httpSession,
                                      ModelAndView mav) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("offers/create-offer.html");
        }

        OfferCreateServiceModel serviceModel = this.mapper.map(viewModel, OfferCreateServiceModel.class);

        String username = this.getUsername(httpSession);
        this.shoeService.createByUser(serviceModel, username);

        mav.setViewName("redirect:/home");
        return mav;
    }

    @ModelAttribute("editModel")
    public OfferEditViewModel editModel() {
        return new OfferEditViewModel();
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@ModelAttribute("editModel") OfferEditViewModel viewModel,
                             @PathVariable String id,
                             ModelAndView mav) {

        OfferEditViewModel offer = this.mapper.map(this.shoeService.getOneById(id), OfferEditViewModel.class);
        mav.addObject("editModel", offer);
        mav.setViewName("offers/create-offer.html)");
        return mav;
    }
}
