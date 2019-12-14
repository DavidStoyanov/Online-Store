package com.stoyanov.onlineshoestore.app.services.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stoyanov.onlineshoestore.app.errors.OfferCreateException;
import com.stoyanov.onlineshoestore.app.errors.OfferEditException;
import com.stoyanov.onlineshoestore.app.errors.OfferNotFoundException;
import com.stoyanov.onlineshoestore.app.errors.UserNotFoundException;
import com.stoyanov.onlineshoestore.app.models.entity.offer.Photo;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.Shoe;
import com.stoyanov.onlineshoestore.app.models.entity.user.User;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeCreateServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeEditServiceModel;
import com.stoyanov.onlineshoestore.app.models.view.photo.PhotoModel;
import com.stoyanov.onlineshoestore.app.repositories.ShoeRepository;
import com.stoyanov.onlineshoestore.app.repositories.UserRepository;
import com.stoyanov.onlineshoestore.app.services.CloudService;
import com.stoyanov.onlineshoestore.app.services.DataService;
import com.stoyanov.onlineshoestore.app.services.ShoeService;
import com.stoyanov.onlineshoestore.app.services.validations.ShoeValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoeServiceImpl implements ShoeService {

    private final ShoeRepository shoeRepository;
    private final UserRepository userRepository;

    private final DataService dataService;
    private final CloudService cloudService;
    private final ShoeValidationService shoeValidationService;

    private final ModelMapper mapper;
    private final Gson gson;

    @Autowired
    public ShoeServiceImpl(ShoeRepository shoeRepository,
                           UserRepository userRepository,
                           ModelMapper mapper,
                           DataService dataService,
                           CloudService cloudService,
                           ShoeValidationService shoeValidationService,
                           Gson gson) {
        this.shoeRepository = shoeRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.dataService = dataService;
        this.cloudService = cloudService;
        this.shoeValidationService = shoeValidationService;
        this.gson = gson;
    }

    @Override
    public void createByUser(ShoeCreateServiceModel serviceModel, String username) {
        if (!this.shoeValidationService.isValid(serviceModel)) {
            throw new OfferCreateException();
        }

        Optional<User> optionalUser = this.userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();

        Shoe shoe = this.mapper.map(serviceModel, Shoe.class);
        shoe.setCreatedBy(user);
        shoe.setCreatedOn(this.dataService.getCurrentTime());

        List<Photo> photos = this.parsePhotoFromJson(serviceModel.getPhotos()).stream()
                .map(photoModel -> this.mapper.map(photoModel, Photo.class))
                .peek(photo -> photo.setOffer(shoe))
                .collect(Collectors.toList());
        shoe.setPhotos(photos);

        this.shoeRepository.save(shoe);
    }

    @Override
    public void editByUser(ShoeEditServiceModel serviceModel, String username) {
        if (!this.shoeValidationService.isValid(serviceModel, username)) {
            throw new OfferEditException();
        }

        Shoe shoe = this.shoeRepository.findById(serviceModel.getId()).get();
        this.mapper.map(serviceModel, shoe);

        this.shoeRepository.save(shoe);
    }

    @Override
    public void deleteByUsername(String offerId, String username) {

    }

    @Override
    public ShoeEditServiceModel getOneById(String id) {
        Optional<Shoe> optionalShoe = this.shoeRepository.findById(id);
        if (optionalShoe.isEmpty()) {
            throw new OfferNotFoundException();
        }

        Shoe shoe = optionalShoe.get();
        return this.mapper.map(shoe, ShoeEditServiceModel.class);
    }

    private List<PhotoModel> parsePhotoFromJson(String json) {
        Type listType = new TypeToken<ArrayList<PhotoModel>>(){}.getType();
        return this.gson.fromJson(json, listType);
    }
}
