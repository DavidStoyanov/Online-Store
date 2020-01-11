package com.stoyanov.onlineshoestore.app.services.offers.impl;

import com.stoyanov.onlineshoestore.app.errors.offer.CategoryNotFoundException;
import com.stoyanov.onlineshoestore.app.errors.offer.OfferCreateException;
import com.stoyanov.onlineshoestore.app.errors.offer.OfferNotFoundException;
import com.stoyanov.onlineshoestore.app.errors.user.UserNotFoundException;
import com.stoyanov.onlineshoestore.app.models.entity.offer.Category;
import com.stoyanov.onlineshoestore.app.models.entity.offer.Photo;
import com.stoyanov.onlineshoestore.app.models.entity.offer.clothes.Clothes;
import com.stoyanov.onlineshoestore.app.models.entity.user.User;
import com.stoyanov.onlineshoestore.app.models.service.offer.clothes.ClothesDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.clothes.ClothesSaveServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.CategoryRepository;
import com.stoyanov.onlineshoestore.app.repositories.ClothesRepository;
import com.stoyanov.onlineshoestore.app.repositories.PhotoRepository;
import com.stoyanov.onlineshoestore.app.repositories.UserRepository;
import com.stoyanov.onlineshoestore.app.services.offers.ClothesService;
import com.stoyanov.onlineshoestore.app.services.offers.base.BaseOfferService;
import com.stoyanov.onlineshoestore.app.services.services.CloudService;
import com.stoyanov.onlineshoestore.app.services.services.CloudinaryService;
import com.stoyanov.onlineshoestore.app.services.services.DateService;
import com.stoyanov.onlineshoestore.app.services.services.PCloudService;
import com.stoyanov.onlineshoestore.app.services.validations.ClothesValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClothesServiceImpl extends BaseOfferService
        implements ClothesService {

    private final ClothesRepository clothesRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PhotoRepository photoRepository;

    private final DateService dateService;
    private final ClothesValidationService clothesValidationService;

    private final ModelMapper mapper;

    @Autowired
    public ClothesServiceImpl(ClothesRepository clothesRepository,
                              UserRepository userRepository,
                              CategoryRepository categoryRepository,
                              PhotoRepository photoRepository,
                              ModelMapper mapper,
                              DateService dateService,
                              CloudinaryService cloudinaryService,
                              ClothesValidationService clothesValidationService) {
        super(cloudinaryService);
        this.clothesRepository = clothesRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.photoRepository = photoRepository;
        this.mapper = mapper;
        this.dateService = dateService;
        this.clothesValidationService = clothesValidationService;
    }

    @Override
    public void create(ClothesSaveServiceModel serviceModel) {
        if (!this.clothesValidationService.isValid(serviceModel)) {
            throw new OfferCreateException();
        }

        User user = this.userRepository.findByUsername(super.getCurrentAuthUsername())
                .orElseThrow(UserNotFoundException::new);

        Category category = this.categoryRepository.findById(serviceModel.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Clothes clothes = this.mapper.map(serviceModel, Clothes.class);
        clothes.setCreatedBy(user);
        clothes.setCategory(category);
        clothes.setCreatedOn(this.dateService.getCurrentTime());

        List<MultipartFile> photos = serviceModel.getPhotos().stream()
                .filter(file -> !file.isEmpty())
                .collect(Collectors.toList());

        if (!photos.isEmpty()) {
            super.uploadPhotos(clothes, photos);
        } else {
            clothes.setPhotos(Collections.emptyList());
        }

        this.clothesRepository.save(clothes);
    }

    @Override
    public void edit(ClothesSaveServiceModel serviceModel) {
        if (!this.clothesValidationService.isValid(serviceModel)) {
            throw new OfferCreateException();
        }

        Clothes clothes = this.clothesRepository.findById(serviceModel.getId())
                .orElseThrow(OfferNotFoundException::new);

        Category category = this.categoryRepository.findById(serviceModel.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        List<MultipartFile> photos = serviceModel.getPhotos().stream()
                .filter(file -> !file.isEmpty())
                .collect(Collectors.toList());

        if (!photos.isEmpty()) {
            this.destroyPhotos(clothes);

            List<Photo> photo = clothes.getPhotos();
            clothes.setPhotos(null);
            this.photoRepository.deleteAll(photo);
            this.photoRepository.flush();

            this.uploadPhotos(clothes, photos);
        }

        this.mapper.map(serviceModel, clothes);
        clothes.setCategory(category);

        this.clothesRepository.saveAndFlush(clothes);
    }

    @Override
    public void delete(String id) {
        Clothes clothes = this.clothesRepository.findById(id)
                .orElseThrow(OfferNotFoundException::new);

        super.destroyPhotos(clothes);

        this.clothesRepository.delete(clothes);
    }

    @Override
    public ClothesDetailsServiceModel getOneById(String id) {
        Clothes clothes = this.clothesRepository.findById(id)
                .orElseThrow(OfferNotFoundException::new);

        return this.mapper.map(clothes, ClothesDetailsServiceModel.class);
    }

    @Override
    public List<ClothesDetailsServiceModel> getAll() {
        return this.clothesRepository.findAll().stream()
                .map(clothes -> this.mapper.map(clothes, ClothesDetailsServiceModel.class))
                .collect(Collectors.toList());
    }
}
