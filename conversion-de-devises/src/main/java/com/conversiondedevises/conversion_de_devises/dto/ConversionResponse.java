package com.conversiondedevises.conversion_de_devises.dto;

import java.math.BigDecimal;

public class ConversionResponse {

    private BigDecimal montantConverti;
    private BigDecimal tauxDeChangeUtilise; // Optionnel

    // Constructeurs, getters et setters
    public ConversionResponse() {}

    public ConversionResponse(BigDecimal montantConverti) {
        this.montantConverti = montantConverti;
    }

    public ConversionResponse(BigDecimal montantConverti, BigDecimal tauxDeChangeUtilise) {
        this.montantConverti = montantConverti;
        this.tauxDeChangeUtilise = tauxDeChangeUtilise;
    }

    public BigDecimal getMontantConverti() {
        return montantConverti;
    }

    public void setMontantConverti(BigDecimal montantConverti) {
        this.montantConverti = montantConverti;
    }

    public BigDecimal getTauxDeChangeUtilise() {
        return tauxDeChangeUtilise;
    }

    public void setTauxDeChangeUtilise(BigDecimal tauxDeChangeUtilise) {
        this.tauxDeChangeUtilise = tauxDeChangeUtilise;
    }
}
