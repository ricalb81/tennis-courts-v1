package com.tenniscourts.reservations;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void rescheduleReservationTestAndStatus() throws Exception {

        URI uri = new URI("/reservation/reschedule/2");
        String json = "{\"tennisCourtId\":\"3\",\"startDateTime\":\"2022-02-20T12:00\"}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .patch(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }

}
