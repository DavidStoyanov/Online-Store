package com.stoyanov.onlineshoestore.app.services.impl;

import com.stoyanov.onlineshoestore.app.errors.OfferNotFoundException;
import com.stoyanov.onlineshoestore.app.errors.UserNotFoundException;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.Shoe;
import com.stoyanov.onlineshoestore.app.models.entity.user.User;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.OfferCreateServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.OfferEditServiceModel;
import com.stoyanov.onlineshoestore.app.repositories.ShoeRepository;
import com.stoyanov.onlineshoestore.app.repositories.UserRepository;
import com.stoyanov.onlineshoestore.app.services.CloudService;
import com.stoyanov.onlineshoestore.app.services.CloudinaryService;
import com.stoyanov.onlineshoestore.app.services.DataService;
import com.stoyanov.onlineshoestore.app.services.ShoeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoeServiceImpl implements ShoeService {

    private final ShoeRepository shoeRepository;
    private final UserRepository userRepository;

    private final ModelMapper mapper;
    private final DataService dataService;
    private final CloudService cloudService;

    @Autowired
    public ShoeServiceImpl(ShoeRepository shoeRepository,
                           UserRepository userRepository,
                           ModelMapper mapper,
                           DataService dataService,
                           CloudService cloudService) {
        this.shoeRepository = shoeRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.dataService = dataService;
        this.cloudService = cloudService;
    }

    @Override
    public void createByUser(OfferCreateServiceModel serviceModel, String username) {
        Shoe shoe = this.mapper.map(serviceModel, Shoe.class);
        List<MultipartFile> photos = serviceModel.getPhotos().stream()
                .filter(p -> !p.isEmpty())
                .collect(Collectors.toList());

        Optional<User> optionalUser = this.userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();

        shoe.setCreatedBy(user);
        shoe.setCreatedOn(this.dataService.getCurrentTime());
        shoe.setPhotos(this.cloudService.upload(photos));

        this.shoeRepository.save(shoe);
    }

    @Override
    public OfferEditServiceModel getOneById(String id) {
        Optional<Shoe> optionalShoe = this.shoeRepository.findById(id);
        if (optionalShoe.isEmpty()) {
            throw new OfferNotFoundException();
        }

        Shoe shoe = optionalShoe.get();
        shoe.setPhotos(List.of());
        this.cloudService.destroy("e5eyl1z8rhiijoslqiqh");
        return this.mapper.map(shoe, OfferEditServiceModel.class);
    }
}
