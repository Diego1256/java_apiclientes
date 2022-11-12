package br.com.cotiinformatica.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailComponentModel {
	private String emailDest;
	private String assunto;
	private String texto;
	private boolean html;
}
