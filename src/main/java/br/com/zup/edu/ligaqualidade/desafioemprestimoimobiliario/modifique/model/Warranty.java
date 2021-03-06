package br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.model;

import java.math.BigDecimal;

public class Warranty {

    private final BigDecimal value;
    private final String province;

    public Warranty(BigDecimal value, String province) {
        this.value = value;
        this.province = province;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getProvince() {
        return province;
    }
}
