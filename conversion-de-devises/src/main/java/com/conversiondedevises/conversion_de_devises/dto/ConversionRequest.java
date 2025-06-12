package com.conversiondedevises.conversion_de_devises.dto;

import java.math.BigDecimal;

public class ConversionRequest {

    private String deviseSource;
    private String deviseCible;
    private BigDecimal montant;

    // Constructeurs, getters et setters
    public ConversionRequest() {}

    public ConversionRequest(String deviseSource, String deviseCible, BigDecimal montant) {
        this.deviseSource = deviseSource;
        this.deviseCible = deviseCible;
        this.montant = montant;
    }

    public String getDeviseSource() {
        return deviseSource;
    }

    public void setDeviseSource(String deviseSource) {
        this.deviseSource = deviseSource;
    }

    public String getDeviseCible() {
        return deviseCible;
    }

    public void setDeviseCible(String deviseCible) {
        this.deviseCible = deviseCible;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }
}

