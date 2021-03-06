package br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Emprestimo {

    private Double valorEmprestimo;

    private Integer prazoEmprestimoMeses;

    private List<Proponente> proponenteList;

    private Double parcelaEmprestimo;

}
