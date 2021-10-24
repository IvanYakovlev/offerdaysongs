package com.example.offerdaysongs;

import com.example.offerdaysongs.service.CopyrightService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class OfferDaySongsApplicationTests {
    @Autowired
    private CopyrightService copyrightService;

    @Test
    void contextLoads() {
    }

}
