package com.gbabler.apibeerreactive.client;

import com.gbabler.apibeerreactive.model.BeerDTO;
import com.gbabler.apibeerreactive.model.BeerPagedList;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BeerClient {

    Mono<BeerDTO> getBeerById(UUID id, Boolean showInventoryOnHand);

    Mono<BeerPagedList> listBeers(Integer pageNumber, Integer pageSize, String beerName,
                                  String beerStyle, Boolean showInventoryOnHand);

    Mono<ResponseEntity<Void>> createBeer(BeerDTO beerDTO);

    Mono<ResponseEntity<Void>> updateBeer(UUID beerId, BeerDTO beerDto);

    Mono<ResponseEntity<Void>> deleteBeerById(UUID beerId);

    Mono<BeerDTO> getBeerByUPC(String upc);
}
