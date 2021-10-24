package com.example.offerdaysongs;

import com.example.offerdaysongs.controller.CopyrightController;
import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/add-data-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-data-after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class OfferDaySongsApplicationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CopyrightController copyrightController;

    @Test
    public void getCopyrightById() throws Exception {

        String jsonString = new JSONObject()
                .put("id", 1)
                .put("startDate", "2019-01-15T00:00:00+07:00")
                .put("endDate", "2030-01-15T00:00:00+07:00")
                .put("royalty", 1000000)
                .put("company", new JSONObject().put("id", 1)
                        .put("name", "Black Company"))
                .put("recording", new JSONObject()
                        .put("id", 3)
                        .put("title", "Bad Guy")
                        .put("version", "1")
                        .put("releaseTime", "2019-01-15T00:00:00+07:00")
                        .put("singer", new JSONObject()
                                .put("id", 3)
                                .put("name", "Billie Eilish"))
                ).toString();


        this.mockMvc.perform(get("/api/copyrights/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));

    }

    @Test
    public void royaltyTest() throws Exception {
        this.mockMvc.perform(get("/api/copyrights/getRoyalty/?currentDate=2021-01-01&name=Billie Eilish&title=Bad Guy"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Стоимость прав на песню составит - 1000000")));
    }

    @Test
    public void saveCopyRight() throws Exception {
        String jsonString = new JSONObject()
                .put("startDate", "2001-01-15T00:00:00+07:00")
                .put("endDate", "2040-01-15T00:00:00+07:00")
                .put("royalty", 123456)
                .put("company", new JSONObject().put("id", 1)
                        .put("name", "Black Company"))
                .put("recording", new JSONObject()
                        .put("id", 5)
                        .put("title", "Mother")
                        .put("version", "1")
                        .put("releaseTime", "2001-01-15T00:00:00+07:00")
                        .put("singer", new JSONObject()
                                .put("id", 1)
                                .put("name", "Madonna"))
                ).toString();

        System.out.println(jsonString);
        this.mockMvc.perform(post("/api/copyrights/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }

}
