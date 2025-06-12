package com.conversiondedevises.conversion_de_devises.controller;

import com.conversiondedevises.conversion_de_devises.dto.ConversionRequest;
import com.conversiondedevises.conversion_de_devises.dto.ConversionResponse;
import com.conversiondedevises.conversion_de_devises.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/convert")
public class ConversionController {

    @Autowired
    private ConversionService conversionService;

    @PostMapping
    public Mono<ResponseEntity<ConversionResponse>> convert(@RequestBody ConversionRequest request) {
        return conversionService.convert(request)
                .map(ResponseEntity::ok);
    }
}
