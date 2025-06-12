package com.conversiondedevises.conversion_de_devises.service;

import com.conversiondedevises.conversion_de_devises.dto.ConversionRequest;
import com.conversiondedevises.conversion_de_devises.dto.ConversionResponse;
import com.conversiondedevises.conversion_de_devises.dto.ExchangeRateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import jakarta.annotation.PostConstruct;

import java.math.BigDecimal;

@Service
public class ConversionService {

    private final WebClient.Builder webClientBuilder; // Stocker le builder injecté
    private WebClient webClient; // Sera initialisé dans @PostConstruct
    private final Logger log = LoggerFactory.getLogger(ConversionService.class);
    private final ObjectMapper objectMapper; // Conservé

    @Value("${exchangerate.api.key}")
    private String apiKey;

    @Value("${exchangerate.api.url}")
    private String exchangeRateApiUrl;

    // Le constructeur injecte les dépendances
    public ConversionService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClientBuilder = webClientBuilder;
        this.objectMapper = objectMapper;
        log.info("ConversionService constructeur: webClientBuilder et objectMapper injectés.");
    }

    @PostConstruct
    public void initialize() {
        log.info("ConversionService @PostConstruct: Initialisation du WebClient.");
        log.info("Valeur injectée pour exchangeRateApiUrl dans @PostConstruct: '{}'", exchangeRateApiUrl);
        log.info("Valeur injectée pour apiKey dans @PostConstruct: '{}'", apiKey);

        if (exchangeRateApiUrl == null || exchangeRateApiUrl.trim().isEmpty()) {
            log.error("CRITICAL: exchangeRateApiUrl est null ou vide ! WebClient ne sera pas configuré. Vérifiez votre fichier application.properties (doit être dans src/main/resources).");
            throw new IllegalStateException("exchangeRateApiUrl est requis et n'a pas été injecté. Vérifiez la configuration des propriétés.");
        }
        if (apiKey == null || apiKey.trim().isEmpty()) {
            log.error("CRITICAL: apiKey est null ou vide ! Les appels API échoueront. Vérifiez votre fichier application.properties.");
            throw new IllegalStateException("apiKey est requis et n'a pas été injecté. Vérifiez la configuration des propriétés.");
        }

        this.webClient = this.webClientBuilder.baseUrl(exchangeRateApiUrl).build();
        log.info("WebClient initialisé avec baseUrl: {}", exchangeRateApiUrl);
    }

    public Mono<ConversionResponse> convert(ConversionRequest request) {
        if (this.webClient == null) {

            log.error("WebClient non initialisé. Problème de configuration interne critique.");
            return Mono.error(new IllegalStateException("WebClient non initialisé. Vérifiez les logs de démarrage pour des erreurs de configuration."));
        }

        String relativePathPattern = "{apiKey}/pair/{sourceCurrency}/{targetCurrency}";

        log.info("Appel de l'API de taux de change. Base URL: '{}', Pattern de chemin: '{}', Source: '{}', Cible: '{}'",
                this.exchangeRateApiUrl,
                relativePathPattern,
                request.getDeviseSource(),
                request.getDeviseCible());


        return this.webClient.get()
                .uri(relativePathPattern, this.apiKey, request.getDeviseSource(), request.getDeviseCible())
                .retrieve()
                .bodyToMono(ExchangeRateResponse.class)
                .flatMap(exchangeApiResponse -> {
                    if ("success".equals(exchangeApiResponse.getResult())) {
                        BigDecimal tauxDeChange = exchangeApiResponse.getConversion_rate();
                        if (tauxDeChange == null) {
                            log.error("Le taux de conversion est null dans la réponse API pour {} vers {}", request.getDeviseSource(), request.getDeviseCible());
                            return Mono.error(new RuntimeException("Taux de change non disponible dans la réponse de l'API."));
                        }
                        BigDecimal montantConverti = request.getMontant().multiply(tauxDeChange);
                        log.info("Conversion réussie : {} {} = {} {} (taux: {})",
                                request.getMontant(), request.getDeviseSource(),
                                montantConverti, request.getDeviseCible(), tauxDeChange);
                        return Mono.just(new ConversionResponse(montantConverti, tauxDeChange));
                    } else {
                        String errorType = exchangeApiResponse.getError_type() != null ? exchangeApiResponse.getError_type() : "Type d'erreur inconnu";
                        String errorMessage = "Erreur de l'API Exchangerate : " + errorType;
                        log.error(errorMessage);
                        return Mono.error(new RuntimeException(errorMessage));
                    }
                })
                .onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("Erreur HTTP {} lors de l'appel à l'API Exchangerate : {}", ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
                    return Mono.error(new RuntimeException("Erreur de communication avec le service de taux de change (HTTP " + ex.getStatusCode() + ")", ex));
                })
                .onErrorResume(e -> {
                    boolean isKnownError = e instanceof RuntimeException &&
                            (e.getMessage().startsWith("Erreur de l'API Exchangerate") ||
                                    e.getMessage().startsWith("Taux de change non disponible") ||
                                    e.getMessage().startsWith("Erreur de communication avec le service de taux de change"));

                    if (!isKnownError) {
                        if (e instanceof WebClientRequestException) {
                            log.error("Erreur de requête WebClient (ex: résolution DNS, problème de connexion) lors de la communication avec l'API de taux de change.", e);
                            return Mono.error(new RuntimeException("Erreur de requête WebClient (DNS/Connexion) lors de la communication avec l'API.", e));
                        }
                        log.error("Erreur inattendue lors de la conversion.", e);
                        return Mono.error(new RuntimeException("Erreur inattendue lors du processus de conversion.", e));
                    }
                    return Mono.error(e);
                });
    }
}