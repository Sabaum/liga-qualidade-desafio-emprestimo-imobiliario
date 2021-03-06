package br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.usecase;

import br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.model.Proponente;
import br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.model.Proposta;

public class ValidaEmprestimo {

    private Double valorMaximo = 3000000.00;
    private Double valorMinimo = 30000.00;
    private Integer prazoMaximo = 15*12;
    private Integer prazoMinimo = 2*12;
    private Proposta proposta;

    public boolean validaInervaloValorEmprestimo() {
        Double valorEmprestimo = this.proposta.getEmprestimo().getValorEmprestimo();

        if (valorEmprestimo.compareTo(valorMaximo) != 1
                && valorEmprestimo.compareTo(valorMinimo) != -1)
            return true;
        return false;
    }

    public boolean validaPrazoEmprestimo(){
        Integer prazoEmprestimo = this.proposta.getEmprestimo().getPrazoEmprestimoMeses();

        if (prazoEmprestimo.compareTo(prazoMaximo) != 1
                && prazoEmprestimo.compareTo(prazoMinimo) != -1)
            return true;
        return false;
    }

    public boolean validaQuantidadeProponentes() {
        return this.proposta.getProponenteList().size() < 2 ? false : true;
    }

    public long validaProponentePrincipal() {
        return this.proposta.getProponenteList().stream().filter(proponente -> proponente.getTipoProponente() == "principal").count();
    }

    public boolean validaPrazoEmprestimoIntervalo(){

        for (Proponente proponente : this.proposta.getProponenteList())
            if (proponente.getIdadeProponente() == -1) return false;
        return true;
    }

    public boolean validaGarantiaProposta() {
        return this.proposta.getGarantiaImovelList().isEmpty() ? false : true;
    }

    public boolean validaSomaGarantias() {
        Double valorTotalGarantia;
        valorTotalGarantia = this.proposta.getGarantiaImovelList().stream().mapToDouble(garantia -> garantia.getValorGarantiaImovel()).sum();
        return valorTotalGarantia >= (2 * this.proposta.getEmprestimo().getValorEmprestimo());
    }

    public void removeGarantiasEstadosNaoAceitos() {
        this.proposta.getGarantiaImovelList().removeIf(garantiaImovel -> garantiaImovel.getEstadoImovel() != "PR"
                || garantiaImovel.getEstadoImovel() != "SC"
                || garantiaImovel.getEstadoImovel() != "RS");
    }

    public boolean validaRendaProponentePrinciapal() {
        Proponente proponentePrincipal = (Proponente) this.proposta.getProponenteList().stream()
                .filter(proponente -> proponente.getTipoProponente().equals("principal"));

        if (proponentePrincipal.getIdadeProponente() > 17 && proponentePrincipal.getIdadeProponente() < 24)
            if(proponentePrincipal.getRendaProponente() >= (4*this.proposta.getEmprestimo().getParcelaEmprestimo()))
                return true;

        if (proponentePrincipal.getIdadeProponente() > 24 && proponentePrincipal.getIdadeProponente() < 50)
            if(proponentePrincipal.getRendaProponente() >= (3*this.proposta.getEmprestimo().getParcelaEmprestimo()))
                return true;

        if (proponentePrincipal.getIdadeProponente() > 50)
            if(proponentePrincipal.getRendaProponente() >= (2*this.proposta.getEmprestimo().getParcelaEmprestimo()))
                return true;

        return false;
    }
}
