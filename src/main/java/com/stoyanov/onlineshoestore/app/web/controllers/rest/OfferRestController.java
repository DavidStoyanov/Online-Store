package com.stoyanov.onlineshoestore.app.web.controllers.rest;

import com.stoyanov.onlineshoestore.app.errors.offer.OfferNotFoundException;
import com.stoyanov.onlineshoestore.app.models.services.offer.OfferResponse;
import com.stoyanov.onlineshoestore.app.models.services.offer.OfferServiceModel;
import com.stoyanov.onlineshoestore.app.models.services.offer.PhotoServiceModel;
import com.stoyanov.onlineshoestore.app.services.factories.HeadersFactory;
import com.stoyanov.onlineshoestore.app.services.offers.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/offer")
public class OfferRestController {

    private final static String DETAILS_URL_PATH = "/offer/details/";

    private final OfferService offerService;
    private final HeadersFactory headersFactory;

    @Autowired
    public OfferRestController(OfferService offerService, HeadersFactory headersFactory) {
        this.offerService = offerService;
        this.headersFactory = headersFactory;
    }

    @PostMapping("/create")
    public ResponseEntity createConfirm(@RequestBody OfferServiceModel serviceModel) {
        String offerId = this.offerService.create(serviceModel);
        HttpHeaders headers = this.headersFactory.withLocation(offerId);
        OfferResponse response = new OfferResponse(DETAILS_URL_PATH + offerId);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity createConfirm(@RequestBody OfferServiceModel offerResource, @PathVariable String id) throws OfferNotFoundException {
        this.offerService.edit(offerResource, id);
        HttpHeaders headers = this.headersFactory.withLocation(id);
        OfferResponse response = new OfferResponse(DETAILS_URL_PATH + id);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity deleteConfirm(@PathVariable String id) throws OfferNotFoundException {
        this.offerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/attributes/{id}")
    public ResponseEntity getAttributes(@PathVariable String id) throws OfferNotFoundException {
        OfferServiceModel model = this.offerService.getById(id);
        Map<String, String> attributes = model.getAttributes();
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @GetMapping("/photos/{id}")
    public ResponseEntity getPhotos(@PathVariable String id) throws OfferNotFoundException {
        OfferServiceModel model = this.offerService.getById(id);
        List<PhotoServiceModel> photos = model.getPhotos();
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity getOne(@PathVariable String id) throws OfferNotFoundException {
        OfferServiceModel model = this.offerService.getById(id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/offers")
    public ResponseEntity getAll() {
        List<OfferServiceModel> models = this.offerService.getAll();
        return new ResponseEntity<>(models, HttpStatus.OK);
    }
}
