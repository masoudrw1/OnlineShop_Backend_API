package com.masoud.app.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CacheEvictScheduler {

    @CacheEvict("apiCache30m")
    @Scheduled(fixedRate = 108000)
    public void evictApiCache30m()
    {
        System.out.println(LocalDateTime.now().toLocalDate()+" - "
                +LocalDateTime.now().toLocalTime()+">api 30 min cache evicted ");

    }
    @CacheEvict("apiCache15m")
    @Scheduled(fixedRate = 54000)
    public void evictApiCache15m()
    {
        System.out.println(LocalDateTime.now().toLocalDate()+" - "
                +LocalDateTime.now().toLocalTime()+">api 15 min cache evicted ");

    }

}
