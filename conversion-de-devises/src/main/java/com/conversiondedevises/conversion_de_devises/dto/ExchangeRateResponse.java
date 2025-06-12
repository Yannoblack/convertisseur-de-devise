package com.conversiondedevises.conversion_de_devises.dto;

import java.math.BigDecimal;

public class ExchangeRateResponse {

    private String result; // "success" ou "error"
    private String documentation;
    private String terms_of_use;
    private BigDecimal conversion_rate;
    private String error_type; // Présent en cas d'erreur

    // Getters et setters pour tous les champs

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getTerms_of_use() {
        return terms_of_use;
    }

    public void setTerms_of_use(String terms_of_use) {
        this.terms_of_use = terms_of_use;
    }

    public BigDecimal getConversion_rate() {
        return conversion_rate;
    }

    public void setConversion_rate(BigDecimal conversion_rate) {
        this.conversion_rate = conversion_rate;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }
}