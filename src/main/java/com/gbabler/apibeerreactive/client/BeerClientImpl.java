package com.gbabler.apibeerreactive.client;

import com.gbabler.apibeerreactive.model.BeerDTO;
import com.gbabler.apibeerreactive.model.BeerPagedList;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class BeerClientImpl implements BeerClient {

    @Override
    public Mono<BeerDTO> getBeerById(UUID id, Boolean showInventoryOnHand) {
        return null;
    }

    @Override
    public Mono<BeerPagedList> listBeers(Integer pageNumber, Integer pageSize, String beerName, String beerStyle, Boolean showInventoryOnHand) {
        return null;
    }

    @Override
    public Mono<ResponseEntity> createBeer(BeerDTO beerDTO) {
        return null;
    }

    @Override
    public Mono<ResponseEntity> updateBeer(BeerDTO beerDto) {
        return null;
    }

    @Override
    public Mono<ResponseEntity> deleteBeerById(UUID id) {
        return null;
    }

    @Override
    public Mono<BeerDTO> getBeerByUPC(String upc) {
        return null;
    }
}
