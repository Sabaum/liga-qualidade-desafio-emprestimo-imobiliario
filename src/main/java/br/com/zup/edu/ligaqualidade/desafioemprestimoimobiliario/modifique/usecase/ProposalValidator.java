package br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.usecase;

import br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.exception.UnexpectedException;
import br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.model.Proponent;
import br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.model.Proposal;
import br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.model.Warranty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public class ProposalValidator {

    private ProposalValidator() {}

    private static final String MAIN_PROPONENT_ERROR = "No main proponent";
    private static final Set<String> NOT_ACCEPTABLE_PROVINCES = Set.of("PR", "SC", "RS");
    private static final BigDecimal MIN_VALUE = BigDecimal.valueOf(30000.00);
    private static final BigDecimal MAX_VALUE = BigDecimal.valueOf(3000000.00);
    private static final Integer MAX_INSTALLMENTS = 15*12;
    private static final Integer MIN_INSTALLMENTS = 2*12;

    public static boolean isValid(Proposal proposal) {
        return validateLoanValue(proposal)
                && validateInstallments(proposal)
                && validateProponents(proposal)
                && validateWarranties(proposal);
    }

    private static boolean validateLoanValue(Proposal proposal) {
        return proposal.getValue().compareTo(MIN_VALUE) >= 0 && proposal.getValue().compareTo(MAX_VALUE) <= 0;
    }

    private static boolean validateInstallments(Proposal proposal) {
        return proposal.getInstallments() >= MIN_INSTALLMENTS
                && proposal.getInstallments() <= MAX_INSTALLMENTS;
    }

    private static boolean validateProponents(Proposal proposal) {
        return validateNumberOfProponents(proposal)
                && validateNumberOfMainProponents(proposal)
                && validateProponentsAge(proposal)
                && validateMainProponentIncome(proposal);
    }

    private static boolean validateNumberOfProponents(Proposal proposal) {
        return proposal.getProponents().size() >= 2;
    }

    private static boolean validateNumberOfMainProponents(Proposal proposal) {
        return proposal.getProponents().stream().filter(Proponent::isMainProponent).count() == 1;
    }

    private static boolean validateProponentsAge(Proposal proposal) {
        return proposal.getProponents().stream().noneMatch(proponent -> proponent.getAge() < 18);
    }

    private static boolean validateWarranties(Proposal proposal) {
        return validateNumberOfWarranties(proposal)
                && validateWarrantiesTotalValue(proposal)
                && validateWarrantiesProvinces(proposal);
    }

    private static boolean validateNumberOfWarranties(Proposal proposal) {
        return !proposal.getWarranties().isEmpty();
    }

    private static boolean validateWarrantiesTotalValue(Proposal proposal) {
        BigDecimal totalWarrantiesValue = proposal.getWarranties().stream().map(Warranty::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal minimumWarrantyValue = proposal.getValue().multiply(BigDecimal.valueOf(2));
        return totalWarrantiesValue.compareTo(minimumWarrantyValue) >= 0;
    }

    private static boolean validateWarrantiesProvinces(Proposal proposal) {
        return proposal.getWarranties().stream().noneMatch(warranty -> NOT_ACCEPTABLE_PROVINCES.contains(warranty.getProvince()));
    }

    private static boolean validateMainProponentIncome(Proposal proposal) {
        Proponent mainProponent = proposal.getProponents().stream()
                .filter(Proponent::isMainProponent).findAny().orElseThrow(() -> new UnexpectedException(MAIN_PROPONENT_ERROR));

        BigDecimal installmentValue = proposal.getValue().divide(BigDecimal.valueOf(proposal.getInstallments()), 2, RoundingMode.HALF_UP);
        BigDecimal desiredIncome;

        if (mainProponent.getAge() > 17 && mainProponent.getAge() < 24) {
            desiredIncome = installmentValue.multiply(BigDecimal.valueOf(4));
        } else if (mainProponent.getAge() > 24 && mainProponent.getAge() < 50) {
            desiredIncome = installmentValue.multiply(BigDecimal.valueOf(3));
        } else {
            desiredIncome = installmentValue.multiply(BigDecimal.valueOf(2));
        }

        return mainProponent.getIncome().compareTo(desiredIncome) >= 0;
    }
}
