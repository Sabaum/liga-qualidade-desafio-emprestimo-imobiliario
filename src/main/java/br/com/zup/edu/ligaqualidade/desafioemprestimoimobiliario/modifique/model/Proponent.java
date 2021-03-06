package br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.model;

import java.math.BigDecimal;

public class Proponent {

    private final Integer age;
    private final BigDecimal income;
    private final boolean mainProponent;

    public Proponent(Integer age, BigDecimal income, boolean mainProponent) {
        this.age = age;
        this.income = income;
        this.mainProponent = mainProponent;
    }

    public Integer getAge() {
        return age;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public boolean isMainProponent() {
        return mainProponent;
    }
}
