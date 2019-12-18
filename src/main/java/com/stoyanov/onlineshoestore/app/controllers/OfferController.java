package com.stoyanov.onlineshoestore.app.controllers;

import com.stoyanov.onlineshoestore.app.controllers.base.BaseController;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeCreateServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeEditServiceModel;
import com.stoyanov.onlineshoestore.app.models.view.offer.OfferCreateViewModel;
import com.stoyanov.onlineshoestore.app.models.view.offer.OfferDetailsViewModel;
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

        ShoeCreateServiceModel serviceModel = this.mapper.map(viewModel, ShoeCreateServiceModel.class);

        //TODO: refactor
        // String username = this.getUsername(httpSession);
        this.shoeService.createByUser(serviceModel, "refactor");

        mav.setViewName("redirect:/");
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

        OfferDetailsViewModel offer = this.mapper.map(this.shoeService.getOneById(id), OfferDetailsViewModel.class);
        mav.addObject("offer", offer);

        mav.setViewName("offers/edit-offer.html");
        return mav;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editConfirm(@Valid @ModelAttribute("editModel") OfferEditViewModel viewModel,
                                    @PathVariable String id,
                                    BindingResult bindingResult,
                                    HttpSession httpSession,
                                    ModelAndView mav) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("offers/edit-offer.html");
        }

        ShoeEditServiceModel serviceModel = this.mapper.map(viewModel, ShoeEditServiceModel.class);

        //TODO: refactor
        // String username = this.getUsername(httpSession);
        this.shoeService.editByUser(serviceModel, "refactor");

        mav.setViewName("redirect:/offers");
        return mav;
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable String id, ModelAndView mav) {
        ShoeDetailsServiceModel model = this.shoeService.getOneById(id);
        OfferDetailsViewModel offer = this.mapper.map(model, OfferDetailsViewModel.class);
        mav.addObject("offer", offer);

        mav.setViewName("offers/details-offer.html");
        return mav;
    }

    @PostMapping("/delete/{id}")
    public String deleteConfirm(@PathVariable String id, HttpSession httpSession) {
        //TODO: refactor
        // String username = this.getUsername(httpSession);
        this.shoeService.deleteByUsername(id, "refactor");

        return "redirect:/offers";
    }
}
