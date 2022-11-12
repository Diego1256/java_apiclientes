package br.com.cotiinformatica.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Cliente {
	
	private Integer idCliente;
	private String nome;
	private String cpf;
	private String email;

}
