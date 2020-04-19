package ecommerce.web.contract;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

public class CustomerRequest {

	@NotBlank
	private String name;

	@NotBlank
	@CPF
	private String cpf;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
