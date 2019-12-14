package com.stoyanov.onlineshoestore.app.services.impl;

import com.stoyanov.onlineshoestore.app.services.DataService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DataServiceImpl implements DataService {
    @Override
    public Date getCurrentTime() {
        return new Date();
    }
}
