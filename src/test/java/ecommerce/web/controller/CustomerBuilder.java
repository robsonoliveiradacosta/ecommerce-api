package ecommerce.web.controller;

import ecommerce.domain.Customer;

public class CustomerBuilder {

	private Long id;
	private String name;
	private String cpf;

	public CustomerBuilder() {
		this.id = null;
		this.name = "Robson";
		this.cpf = "95984354071";
	}

	public Customer build() {
		Customer customer = new Customer();
		customer.setId(id);
		customer.setName(name);
		customer.setCpf(cpf);
		return customer;
	}

	public CustomerBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public CustomerBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public CustomerBuilder withCpf(String cpf) {
		this.cpf = cpf;
		return this;
	}

}
