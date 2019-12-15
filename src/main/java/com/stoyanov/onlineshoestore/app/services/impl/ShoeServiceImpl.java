package com.stoyanov.onlineshoestore.app.services.impl;

import com.stoyanov.onlineshoestore.app.errors.offer.OfferCreateException;
import com.stoyanov.onlineshoestore.app.errors.offer.OfferDeleteException;
import com.stoyanov.onlineshoestore.app.errors.offer.OfferEditException;
import com.stoyanov.onlineshoestore.app.errors.offer.OfferNotFoundException;
import com.stoyanov.onlineshoestore.app.errors.user.UserNotFoundException;
import com.stoyanov.onlineshoestore.app.models.entity.offer.Photo;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.Shoe;
import com.stoyanov.onlineshoestore.app.models.entity.user.User;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeCreateServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeEditServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.PhotoRepository;
import com.stoyanov.onlineshoestore.app.repositories.ShoeRepository;
import com.stoyanov.onlineshoestore.app.repositories.UserRepository;
import com.stoyanov.onlineshoestore.app.services.CloudService;
import com.stoyanov.onlineshoestore.app.services.DataService;
import com.stoyanov.onlineshoestore.app.services.ShoeService;
import com.stoyanov.onlineshoestore.app.services.validations.ShoeValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoeServiceImpl implements ShoeService {

    private final ShoeRepository shoeRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;

    private final DataService dataService;
    private final CloudService cloudService;
    private final ShoeValidationService shoeValidationService;

    private final ModelMapper mapper;

    @Autowired
    public ShoeServiceImpl(ShoeRepository shoeRepository,
                           UserRepository userRepository,
                           PhotoRepository photoRepository,
                           ModelMapper mapper,
                           DataService dataService,
                           CloudService cloudService,
                           ShoeValidationService shoeValidationService) {
        this.shoeRepository = shoeRepository;
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.mapper = mapper;
        this.dataService = dataService;
        this.cloudService = cloudService;
        this.shoeValidationService = shoeValidationService;
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

        List<MultipartFile> photos = serviceModel.getPhotos().stream()
                .filter(file -> !file.isEmpty())
                .collect(Collectors.toList());
        if (!photos.isEmpty()) {
            shoe.setPhotos(this.uploadPhotos(photos, shoe));
        }

        this.shoeRepository.save(shoe);
    }

    @Override
    public void editByUser(ShoeEditServiceModel serviceModel, String username) {
        Optional<Shoe> optionalShoe = this.shoeRepository.findById(serviceModel.getId());
        if (optionalShoe.isEmpty()) {
            throw new OfferNotFoundException();
        }

        if (!this.shoeValidationService.isValid(serviceModel, username)) {
            throw new OfferEditException();
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
    public void deleteByUsername(String offerId, String username) {
        Optional<Shoe> optionalShoe = this.shoeRepository.findById(offerId);
        if (optionalShoe.isEmpty()) {
            throw new OfferNotFoundException();
        }
        if (!this.shoeValidationService.isValid(offerId, username)) {
            throw new OfferDeleteException();
        }

        Shoe shoe = optionalShoe.get();
        this.destroyPhotos(shoe);

        this.shoeRepository.delete(shoe);
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
}
