package com.tenniscourts.guests;


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
public class GuestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createGuestAndExpectStatus201() throws Exception {
        URI uri = new URI("/guest/create");
        String json = "{\"name\": \"Test Post\"}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(201));

    }

    @Test
    public void updateGuestAndExpectStatus200AndTestResponse() throws Exception {
        URI uri = new URI("/guest/update/2");
        String json = "{\"name\":\"Rafael Nadal\"}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .patch(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200))
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"id\":2,\"name\":\"Rafael Nadal\"}"));
    }


    @Test
    public void findByGuestIdOrGuestNameWithoutUrlParameters() throws Exception {
        URI uri = new URI("/guest/find");

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(404));

    }

    @Test
    public void findByGuestIdUrlParameter() throws Exception {
        URI uri = new URI("/guest/find?guestid=1");

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));

    }

    @Test
    public void findByGuestNameUrlParameter() throws Exception {
        URI uri = new URI("/guest/find?guestName=Rafael%20Nadal");

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));

    }

    @Test
    public void findByGuestNameUrlParameterTestResponse() throws Exception {
        URI uri = new URI("/guest/find?guestName=Rafael%20Nadal");

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"id\":2,\"name\":\"Rafael Nadal\"}"));

    }

    @Test
    public void findAllTestStatus() throws Exception {
        URI uri = new URI("/guest/findall");

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));

    }

    @Test
    public void testDeleteGuestAndStatus() throws Exception {
        URI uri = new URI("/guest/delete/3");

        createGuestAndExpectStatus201();

        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }


}
