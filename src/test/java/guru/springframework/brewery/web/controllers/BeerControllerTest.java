package guru.springframework.brewery.web.controllers;

import guru.springframework.brewery.domain.Beer;
import guru.springframework.brewery.services.BeerService;
import guru.springframework.brewery.web.model.BeerDto;
import guru.springframework.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JsonContentAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
class BeerControllerTest {
    Beer beer;
    MockMvc mockMvc;
    BeerDto bluemoon;
    @Mock
    BeerService beerService;
    @InjectMocks
    BeerController beerController;

    @BeforeEach
    void setUp() {
        bluemoon = BeerDto.builder().beerName("Bluemoon").beerStyle(BeerStyleEnum.ALE)
                .id(UUID.randomUUID())
                .price(new BigDecimal(12.09))
                .quantityOnHand(3)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
        mockMvc = MockMvcBuilders.standaloneSetup(beerController).build();

    }

    @Test
    void getBeerById() throws Exception {
        when(beerService.findBeerById(bluemoon.getId())).thenReturn(bluemoon);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/{beerId}", bluemoon.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bluemoon.getId().toString())))
                .andExpect(jsonPath("$.beerName",is(bluemoon.getBeerName())))
                .andExpect(jsonPath("$.quantityOnHand",is(bluemoon.getQuantityOnHand())))
        ;
    }
}