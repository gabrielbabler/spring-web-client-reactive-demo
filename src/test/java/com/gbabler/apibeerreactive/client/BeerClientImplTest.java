package com.gbabler.apibeerreactive.client;

import com.gbabler.apibeerreactive.config.WebClientConfig;
import com.gbabler.apibeerreactive.model.BeerDTO;
import com.gbabler.apibeerreactive.model.BeerPagedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BeerClientImplTest {

    BeerClientImpl beerClient;

    @BeforeEach
    void setUp() {
        beerClient = new BeerClientImpl(new WebClientConfig().webClient());
    }

    @Test
    void listBeers() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null,null,null,null,
                null);

        BeerPagedList pagedList = beerPagedListMono.block();

        assertThat(pagedList).isNotNull();
        assertThat(pagedList.getContent().size()).isGreaterThan(0);
    }

    @Test
    void listBeersPageSize10() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(1,10,null,null,
                null);

        BeerPagedList pagedList = beerPagedListMono.block();

        assertThat(pagedList).isNotNull();
        assertThat(pagedList.getContent().size()).isEqualTo(10);
    }

    @Test
    void listBeersNoRecords() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(10,20,null,null,
                null);

        BeerPagedList pagedList = beerPagedListMono.block();

        assertThat(pagedList).isNotNull();
        assertThat(pagedList.getContent().size()).isEqualTo(0);
    }

    @Disabled("API returning inventory when should not be")
    @Test
    void getBeerById() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null,null,null,null
        ,null);

        BeerPagedList beerPagedList = beerPagedListMono.block();
        UUID id = beerPagedList.getContent().get(0).getId();

        Mono<BeerDTO> beerDTOMono = beerClient.getBeerById(id, false);
        BeerDTO beer = beerDTOMono.block();

        assertThat(beer.getId()).isEqualTo(id);
        assertThat(beer.getQuantityOnHand()).isNull();
    }

    @Test
    void getBeerByIdShowInventoryTrue() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null, null, null,
                null);

        BeerPagedList pagedList = beerPagedListMono.block();

        UUID beerId = pagedList.getContent().get(0).getId();

        Mono<BeerDTO> beerDtoMono = beerClient.getBeerById(beerId, true);

        BeerDTO beerDto = beerDtoMono.block();

        assertThat(beerDto.getId()).isEqualTo(beerId);
        assertThat(beerDto.getQuantityOnHand()).isNotNull();
    }

    @Test
    void getBeerByUPC() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null, null, null,
                null);

        BeerPagedList pagedList = beerPagedListMono.block();

        String upc = pagedList.getContent().get(0).getUpc();

        Mono<BeerDTO> beerDtoMono = beerClient.getBeerByUPC(upc);

        BeerDTO beerDto = beerDtoMono.block();

        assertThat(beerDto.getUpc()).isEqualTo(upc);
    }

    @Test
    void createBeer() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Dogfish 90 Min IPA")
                .beerStyle("IPA")
                .upc("24234546")
                .price(new BigDecimal("10.99"))
                .build();

        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.createBeer(beerDTO);

        ResponseEntity response = responseEntityMono.block();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void updateBeer() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null,null,null,null
                ,null);

        BeerPagedList beerPagedList = beerPagedListMono.block();
        BeerDTO beerDTO = beerPagedList.getContent().get(0);

        BeerDTO updatedBeer = BeerDTO.builder()
                .beerName("Really Good Beer")
                .beerStyle(beerDTO.getBeerStyle())
                .price(beerDTO.getPrice())
                .upc(beerDTO.getUpc())
                .build();

        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.updateBeer(beerDTO.getId(), updatedBeer);
        ResponseEntity<Void> responseEntity = responseEntityMono.block();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deleteBeerById() {
    }
}