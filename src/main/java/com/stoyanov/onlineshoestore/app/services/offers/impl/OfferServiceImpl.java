package com.stoyanov.onlineshoestore.app.services.offers.impl;

import com.google.gson.Gson;
import com.stoyanov.onlineshoestore.app.errors.offer.*;
import com.stoyanov.onlineshoestore.app.errors.user.UserNotFoundException;
import com.stoyanov.onlineshoestore.app.models.entities.offer.Category;
import com.stoyanov.onlineshoestore.app.models.entities.offer.Offer;
import com.stoyanov.onlineshoestore.app.models.entities.offer.OfferAttribute;
import com.stoyanov.onlineshoestore.app.models.entities.offer.Photo;
import com.stoyanov.onlineshoestore.app.models.entities.user.User;
import com.stoyanov.onlineshoestore.app.models.services.offer.OfferServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.*;
import com.stoyanov.onlineshoestore.app.services.base.BaseService;
import com.stoyanov.onlineshoestore.app.services.offers.OfferManageService;
import com.stoyanov.onlineshoestore.app.services.offers.OfferService;
import com.stoyanov.onlineshoestore.app.services.services.DateService;
import com.stoyanov.onlineshoestore.app.services.validations.OfferValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl extends BaseService implements OfferService {

    private final OfferRepository offerRepository;
    private final OfferAttributeRepository offerAttributeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PhotoRepository photoRepository;

    private final DateService dateService;
    private final OfferValidationService offerValidationService;
    private final OfferManageService offerManageService;

    private final Gson gson;
    private final ModelMapper mapper;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository,
                            OfferAttributeRepository offerAttributeRepository,
                            UserRepository userRepository,
                            CategoryRepository categoryRepository,
                            PhotoRepository photoRepository,
                            DateService dateService,
                            OfferValidationService offerValidationService,
                            OfferManageService offerManageService,
                            Gson gson, ModelMapper mapper) {
        this.offerRepository = offerRepository;
        this.offerAttributeRepository = offerAttributeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.photoRepository = photoRepository;
        this.dateService = dateService;
        this.offerValidationService = offerValidationService;
        this.offerManageService = offerManageService;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public String create(OfferServiceModel serviceModel) {
        if (!this.offerValidationService.isValid(serviceModel)) {
            throw new OfferCreateException();
        }

        User user = this.userRepository.findByUsername(getCurrentAuthUsername())
                .orElseThrow(UserNotFoundException::new);

        Category category = this.categoryRepository.findById(serviceModel.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Offer offer = this.mapper.map(serviceModel, Offer.class);

        offer.setCreatedBy(user);
        offer.setCategory(category);
        offer.setCreatedOn(this.dateService.getCurrentTime());

        this.offerRepository.saveAndFlush(offer);

        List<Photo> photoList = serviceModel.getPhotos().stream()
                .map(photoModel -> this.mapper.map(photoModel, Photo.class))
                .peek(photo -> photo.setOffer(offer))
                .collect(Collectors.toList());

        offer.setPhotos(photoList);

        serviceModel.getAttributes().forEach((k, v) -> {
            try {
                OfferAttribute offerAttribute = this.offerManageService.createAttr(k, v);
                offerAttribute.setOffer(offer);
                offer.getAttributes().add(offerAttribute);
            } catch (AttributeNotFoundException e) {
                e.printStackTrace();
            }
        });

        this.offerRepository.saveAndFlush(offer);

        return offer.getId();
    }

    @Override
    public String edit(OfferServiceModel serviceModel, String id) {
        if (!this.offerValidationService.isValid(serviceModel)) {
            throw new OfferCreateException();
        }

        Offer offer = this.offerRepository.findById(id)
                .orElseThrow(OfferNotFoundException::new);

        Category category = this.categoryRepository.findById(serviceModel.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        this.mapper.map(serviceModel, offer);
        offer.setId(id);
        offer.setCategory(category);

        serviceModel.getAttributes().forEach((k, v) -> {
            try {
                OfferAttribute offerAttribute = this.offerManageService.editAttr(k, v, id);
                offerAttribute.setOffer(offer);
                offer.getAttributes().add(offerAttribute);
            } catch (OfferAttributeNotFoundException
                    | AttributeNotFoundException e) {
                e.printStackTrace();
            }
        });

        List<String> offerAttributes = offer.getAttributes().stream()
                .map(offerAttr -> offerAttr.getAttribute().getName())
                .collect(Collectors.toList());

        offerAttributes.forEach(attrStr -> {
            if (!serviceModel.getAttributes().containsKey(attrStr)) {
                try {
                    this.offerManageService.deleteAttr(attrStr, offer);
                } catch (OfferAttributeNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        this.offerRepository.saveAndFlush(offer);

        return offer.getId();
    }

    @Override
    public void delete(String id) {
        Offer offer = this.offerRepository.findById(id)
                .orElseThrow(OfferNotFoundException::new);

        offer.getAttributes().forEach(offerAttr -> {
            offerAttr.setOffer(null);
            this.offerAttributeRepository.delete(offerAttr);
        });
        offer.setAttributes(null);

        this.offerRepository.delete(offer);
    }

    @Override
    public OfferServiceModel getById(String id) {
        Offer offer = this.offerRepository.findById(id)
                .orElseThrow(OfferNotFoundException::new);

        OfferServiceModel model = this.mapper.map(offer, OfferServiceModel.class);
        model.setAttributes(this.getAttributes(offer));

        return model;
    }

    @Override
    public List<OfferServiceModel> getAll() {
        return this.offerRepository.findAll().stream()
                .map(offer -> {
                    OfferServiceModel model = this.mapper.map(offer, OfferServiceModel.class);
                    model.setAttributes(this.getAttributes(offer));
                    return model;
                })
                .collect(Collectors.toList());
    }

    private Map<String, String> getAttributes(Offer offer) {
        Map<String, String> map = new HashMap<>();
        offer.getAttributes().forEach(offerAttr -> {
            String k = offerAttr.getAttribute().getName();
            String v = offerAttr.getValue();
            map.put(k, v);
        });

        return map;
    }
}
