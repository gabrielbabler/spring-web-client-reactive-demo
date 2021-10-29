package com.gbabler.apibeerreactive.client;

import com.gbabler.apibeerreactive.config.WebClientProperties;
import com.gbabler.apibeerreactive.model.BeerDTO;
import com.gbabler.apibeerreactive.model.BeerPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {

    private final WebClient webClient;

    @Override
    public Mono<BeerDTO> getBeerById(UUID id, Boolean showInventoryOnHand) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_GET_BY_ID)
                        .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand))
                        .build(id)
                ).retrieve()
                .bodyToMono(BeerDTO.class);
    }

    @Override
    public Mono<BeerPagedList> listBeers(Integer pageNumber, Integer pageSize, String beerName, String beerStyle, Boolean showInventoryOnHand) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH)
                        .queryParamIfPresent("pageNumber", Optional.ofNullable(pageNumber))
                        .queryParamIfPresent("pageSize", Optional.ofNullable(pageSize))
                        .queryParamIfPresent("beerName", Optional.ofNullable(beerName))
                        .queryParamIfPresent("beerStyle", Optional.ofNullable(beerStyle))
                        .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand))
                        .build())
                .retrieve()
                .bodyToMono(BeerPagedList.class);
    }

    @Override
    public Mono<ResponseEntity<Void>> createBeer(BeerDTO beerDTO) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH)
                        .build())
                .body(BodyInserters.fromValue(beerDTO))
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public Mono<ResponseEntity<Void>> updateBeer(UUID beerId, BeerDTO beerDto) {
        return webClient.put()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_GET_BY_ID)
                        .build(beerId))
                .body(BodyInserters.fromValue(beerDto))
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteBeerById(UUID beerId) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH_GET_BY_ID)
                        .build(beerId))
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public Mono<BeerDTO> getBeerByUPC(String upc) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_UPC_V1_PATH)
                        .build(upc)
                ).retrieve()
                .bodyToMono(BeerDTO.class);
    }
}
