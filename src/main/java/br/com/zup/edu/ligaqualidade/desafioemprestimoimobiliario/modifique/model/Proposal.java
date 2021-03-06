package br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Proposal {

    private final String id;
    private final BigDecimal value;
    private final int installments;
    private final Set<Warranty> warranties = new HashSet<>();
    private final Set<Proponent> proponents = new HashSet<>();

    public Proposal(String id, BigDecimal proposalValue, int installments) {
        this.id = id;
        this.value = proposalValue;
        this.installments = installments;
    }

    public void addWarranty(BigDecimal warrantyValue, String warrantyProvince) {
        warranties.add(new Warranty(warrantyValue, warrantyProvince));
    }

    public void addProponent(Integer proponentAge, BigDecimal proponentIncome, boolean isMainProponent) {
        proponents.add(new Proponent(proponentAge, proponentIncome, isMainProponent));
    }

    public String getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public int getInstallments() {
        return installments;
    }

    public Set<Warranty> getWarranties() {
        return warranties;
    }

    public Set<Proponent> getProponents() {
        return proponents;
    }

}
