package com.stoyanov.onlineshoestore.app.services;

import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeSize;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeType;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeCreateServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeDetailsServiceModel;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeEditServiceModel;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ShoeService {

    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ROOT')")
    void create(ShoeCreateServiceModel serviceModel);

    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ROOT')")
    void edit(ShoeEditServiceModel serviceModel);

    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ROOT')")
    void delete(String offerId);

    List<ShoeSize> getShoeSizes();

    List<ShoeType> getShoeTypes();

    ShoeDetailsServiceModel getOneById(String id);

    List<ShoeDetailsServiceModel> getAll();
}
