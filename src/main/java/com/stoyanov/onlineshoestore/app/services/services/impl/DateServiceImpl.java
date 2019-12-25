package com.stoyanov.onlineshoestore.app.services.services.impl;

import com.stoyanov.onlineshoestore.app.services.services.DateService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DateServiceImpl implements DateService {

    @Override
    public Date getCurrentTime() {
        return new Date();
    }
}
