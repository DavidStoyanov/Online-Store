package com.stoyanov.onlineshoestore.app.services.impl;

import com.stoyanov.onlineshoestore.app.controllers.rest.OfferRestController;
import com.stoyanov.onlineshoestore.app.errors.offer.OfferCreateException;
import com.stoyanov.onlineshoestore.app.errors.offer.OfferNotFoundException;
import com.stoyanov.onlineshoestore.app.errors.user.UserNotFoundException;
import com.stoyanov.onlineshoestore.app.models.entity.offer.Photo;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.Shoe;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeSize;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeType;
import com.stoyanov.onlineshoestore.app.models.entity.user.User;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeCreateServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeEditServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.PhotoRepository;
import com.stoyanov.onlineshoestore.app.repositories.ShoeRepository;
import com.stoyanov.onlineshoestore.app.repositories.UserRepository;
import com.stoyanov.onlineshoestore.app.services.CloudService;
import com.stoyanov.onlineshoestore.app.services.DateService;
import com.stoyanov.onlineshoestore.app.services.ShoeService;
import com.stoyanov.onlineshoestore.app.services.validations.ShoeValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoeServiceImpl implements ShoeService {

    private final ShoeRepository shoeRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;

    private final DateService dateService;
    private final CloudService cloudService;
    private final ShoeValidationService shoeValidationService;

    private final ModelMapper mapper;


    @Autowired
    public ShoeServiceImpl(ShoeRepository shoeRepository,
                           UserRepository userRepository,
                           PhotoRepository photoRepository,
                           ModelMapper mapper,
                           DateService dateService,
                           CloudService cloudService,
                           ShoeValidationService shoeValidationService) {
        this.shoeRepository = shoeRepository;
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.mapper = mapper;
        this.dateService = dateService;
        this.cloudService = cloudService;
        this.shoeValidationService = shoeValidationService;
    }

    @Override
    public void create(ShoeCreateServiceModel serviceModel) {
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
            shoe.setPhotos(this.uploadPhotos(photos, shoe));
        } else {
            shoe.setPhotos(Collections.emptyList());
        }

        this.shoeRepository.save(shoe);
    }

    @Override
    public void edit(ShoeEditServiceModel serviceModel) {
        Optional<Shoe> optionalShoe = this.shoeRepository.findById(serviceModel.getId());
        if (optionalShoe.isEmpty()) {
            throw new OfferNotFoundException();
        }

        Shoe shoe = optionalShoe.get();
        List<MultipartFile> photos = serviceModel.getPhotos().stream()
                .filter(file -> !file.isEmpty())
                .collect(Collectors.toList());
        if (!photos.isEmpty()) {
            this.destroyPhotos(shoe);

            List<Photo> photo = shoe.getPhotos();
            shoe.setPhotos(null);
            this.photoRepository.deleteAll(photo);
            this.photoRepository.flush();

            shoe.setPhotos(this.uploadPhotos(photos, shoe));
        }

        this.mapper.map(serviceModel, shoe);

        this.shoeRepository.saveAndFlush(shoe);
    }

    @Override
    public void delete(String offerId) {
        Optional<Shoe> optionalShoe = this.shoeRepository.findById(offerId);
        if (optionalShoe.isEmpty()) {
            throw new OfferNotFoundException();
        }

        Shoe shoe = optionalShoe.get();
        this.destroyPhotos(shoe);

        this.shoeRepository.delete(shoe);
    }

    @Override
    public List<ShoeSize> getShoeSizes() {
        return List.of(ShoeSize.values());
    }

    @Override
    public List<ShoeType> getShoeTypes() {
        return List.of(ShoeType.values());
    }

    @Override
    public ShoeDetailsServiceModel getOneById(String id) {
        Optional<Shoe> optionalShoe = this.shoeRepository.findById(id);
        if (optionalShoe.isEmpty()) {
            throw new OfferNotFoundException();
        }

        Shoe shoe = optionalShoe.get();
        return this.mapper.map(shoe, ShoeDetailsServiceModel.class);
    }

    @Override
    public List<ShoeDetailsServiceModel> getAll() {
        return this.shoeRepository.findAll().stream()
                .map(shoe -> this.mapper.map(shoe, ShoeDetailsServiceModel.class))
                .collect(Collectors.toList());
    }



    private void destroyPhotos(Shoe shoe) {
        shoe.getPhotos().forEach(photo -> this.cloudService.destroy(photo.getImageUrl()));
    }

    private List<Photo> uploadPhotos(List<MultipartFile> photos, Shoe shoe) {
        return photos.stream()
                .map(file -> {
                    String imageUrl = this.cloudService.upload(file);
                    Photo photo = new Photo();
                    photo.setImageUrl(imageUrl);
                    photo.setName(file.getOriginalFilename());
                    photo.setOffer(shoe);

                    return photo;
                })
                .collect(Collectors.toList());
    }


    private String getCurrentAuthUsername() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return authentication.getName();
    }
}
