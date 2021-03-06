package br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Proposta {

    private List<GarantiaImovel> garantiaImovelList;

    private List<Proponente> proponenteList;

    private Emprestimo emprestimo;

}
