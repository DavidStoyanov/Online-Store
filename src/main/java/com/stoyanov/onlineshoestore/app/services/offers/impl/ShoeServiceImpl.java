package com.stoyanov.onlineshoestore.app.services.offers.impl;

import com.stoyanov.onlineshoestore.app.errors.offer.OfferCreateException;
import com.stoyanov.onlineshoestore.app.errors.offer.OfferNotFoundException;
import com.stoyanov.onlineshoestore.app.errors.user.UserNotFoundException;
import com.stoyanov.onlineshoestore.app.models.entity.offer.Photo;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.Shoe;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeSize;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeType;
import com.stoyanov.onlineshoestore.app.models.entity.user.User;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeSaveServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.PhotoRepository;
import com.stoyanov.onlineshoestore.app.repositories.ShoeRepository;
import com.stoyanov.onlineshoestore.app.repositories.UserRepository;
import com.stoyanov.onlineshoestore.app.services.offers.ShoeService;
import com.stoyanov.onlineshoestore.app.services.offers.base.BaseOfferService;
import com.stoyanov.onlineshoestore.app.services.services.CloudService;
import com.stoyanov.onlineshoestore.app.services.services.DateService;
import com.stoyanov.onlineshoestore.app.services.validations.ShoeValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoeServiceImpl extends BaseOfferService implements ShoeService {

    private final ShoeRepository shoeRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;

    private final DateService dateService;
    private final ShoeValidationService shoeValidationService;

    private final ModelMapper mapper;


    @Autowired
    public ShoeServiceImpl(ShoeRepository shoeRepository,
                           UserRepository userRepository,
                           PhotoRepository photoRepository,
                           DateService dateService,
                           CloudService cloudService,
                           ShoeValidationService shoeValidationService,
                           ModelMapper mapper) {
        super(cloudService);
        this.shoeRepository = shoeRepository;
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.dateService = dateService;
        this.shoeValidationService = shoeValidationService;
        this.mapper = mapper;
    }

    @Override
    public void create(ShoeSaveServiceModel serviceModel) {
        if (!this.shoeValidationService.isValid(serviceModel)) {
            throw new OfferCreateException();
        }

        User user = this.userRepository.findByUsername(getCurrentAuthUsername())
                .orElseThrow(UserNotFoundException::new);

        Shoe shoe = this.mapper.map(serviceModel, Shoe.class);
        shoe.setCreatedBy(user);
        shoe.setCreatedOn(this.dateService.getCurrentTime());

        List<MultipartFile> photos = serviceModel.getPhotos().stream()
                .filter(file -> !file.isEmpty())
                .collect(Collectors.toList());
        if (!photos.isEmpty()) {
            this.uploadPhotos(shoe, photos);
        } else {
            shoe.setPhotos(Collections.emptyList());
        }

        this.shoeRepository.save(shoe);
    }

    @Override
    public void edit(ShoeSaveServiceModel serviceModel) {
        if (!this.shoeValidationService.isValid(serviceModel)) {
            throw new OfferCreateException();
        }

        Shoe shoe = this.shoeRepository.findById(serviceModel.getId())
                .orElseThrow(OfferNotFoundException::new);

        List<MultipartFile> photos = serviceModel.getPhotos().stream()
                .filter(file -> !file.isEmpty())
                .collect(Collectors.toList());
        if (!photos.isEmpty()) {
            this.destroyPhotos(shoe);

            List<Photo> photo = shoe.getPhotos();
            shoe.setPhotos(null);
            this.photoRepository.deleteAll(photo);
            this.photoRepository.flush();

            this.uploadPhotos(shoe, photos);
        }

        this.mapper.map(serviceModel, shoe);

        this.shoeRepository.saveAndFlush(shoe);
    }

    @Override
    public void delete(String id) {
        Shoe shoe = this.shoeRepository.findById(id)
                .orElseThrow(OfferNotFoundException::new);

        this.destroyPhotos(shoe);

        this.shoeRepository.delete(shoe);
    }

    @Override
    public ShoeDetailsServiceModel getOneById(String id) {
        Shoe shoe = this.shoeRepository.findById(id)
                .orElseThrow(OfferNotFoundException::new);

        return this.mapper.map(shoe, ShoeDetailsServiceModel.class);
    }

    @Override
    public List<ShoeDetailsServiceModel> getAll() {
        return this.shoeRepository.findAll().stream()
                .map(shoe -> this.mapper.map(shoe, ShoeDetailsServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShoeSize> getShoeSizes() {
        return List.of(ShoeSize.values());
    }

    @Override
    public List<ShoeType> getShoeTypes() {
        return List.of(ShoeType.values());
    }
}
