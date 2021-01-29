package br.com.italopatricio.desafioelo7.models;

import java.io.Serializable;

public class PriceModel implements Serializable {
    private String current;
    private String installment;
    private String nonPromotion;

    public String getCurrent() {
        return current;
    }

    public String getInstallment() {
        return installment;
    }

    public String getNonPromotion() {
        return nonPromotion;
    }
}
