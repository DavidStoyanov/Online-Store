package com.stoyanov.onlineshoestore.app.services.impl;

import com.stoyanov.onlineshoestore.app.services.DateService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DateServiceImpl implements DateService {
    @Override
    public Date getCurrentTime() {
        return new Date();
    }
}
