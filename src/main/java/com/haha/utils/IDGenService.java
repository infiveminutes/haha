package com.haha.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class IDGenService {

    @Autowired
    @Qualifier("snowFlakeWorker")
    private SnowFlakeWorker snowFlakeWorker;

    public long getSnowFlakeId() {
        return snowFlakeWorker.nextId();
    }
}
