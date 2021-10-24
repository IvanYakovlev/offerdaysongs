package com.example.offerdaysongs.service;

import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.model.Singer;
import com.example.offerdaysongs.repository.CompanyRepository;
import com.example.offerdaysongs.repository.RecordingRepository;
import com.example.offerdaysongs.repository.SingerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Transactional
@Sql(value = {"/add-data-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-data-after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CopyrightServiceTest {

    @Autowired
    private CopyrightService copyrightService;

    @Test
    void save() {

        CreateCopyrightRequest createCopyrightRequest = new CreateCopyrightRequest();

        Copyright copyright = copyrightService.save(createCopyrightRequest);
        Assert.assertNotNull(copyright.getId());
        Assert.assertTrue(copyrightService.deleteById(copyright.getId()));
    }

    @Test
    void getAll() {
        List<Copyright> copyrightList = copyrightService.getAll();
        Assert.assertTrue(copyrightList.size()>0);
    }

    @Test
    void getById() {
        Copyright copyright = copyrightService.getById(1);
        Assert.assertTrue(copyright.getRoyalty()==1000000);
        Assert.assertTrue(copyright.getCompany().getId()==1);
        Assert.assertTrue(copyright.getRecording().getId()==3);
    }


    @Test
    void getByCompanyId() {
        Assert.assertTrue(copyrightService.getByCompanyId(1).get(0).getId()==1);
    }

    @Test
    void getCopyrightByNameTitlePeriod() {
        Assert.assertTrue(copyrightService.getCopyrightByNameTitlePeriod("2018-01-01","2020-01-01","Bad Guy","Billie Eilish").get(0).getId()==1);
    }

    @Test
    void getRoyaltyBySong() {
        Assert.assertTrue(copyrightService.getRoyaltyBySong("2020-01-01","Bad Guy","Billie Eilish")==1000000);
    }
}