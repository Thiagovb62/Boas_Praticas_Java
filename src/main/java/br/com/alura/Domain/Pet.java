package br.com.alura.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    private Long id;
    private String tipo;
    private String nome;
    private String raca;
    private String cor;
    private Float peso;
    private int idade;


}
