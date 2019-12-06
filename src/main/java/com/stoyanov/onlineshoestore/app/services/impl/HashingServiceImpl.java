package com.stoyanov.onlineshoestore.app.services.impl;

import com.stoyanov.onlineshoestore.app.services.HashingService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class HashingServiceImpl implements HashingService {

    @Override
    public String hash(String str) {
        return DigestUtils.sha256Hex(str);
    }
}