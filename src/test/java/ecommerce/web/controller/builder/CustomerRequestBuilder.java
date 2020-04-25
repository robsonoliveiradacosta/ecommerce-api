package ecommerce.web.controller.builder;

import java.time.LocalDate;
import java.time.Month;

import ecommerce.domain.Sex;
import ecommerce.web.contract.CustomerRequest;

public class CustomerRequestBuilder {

	private String name;
	private String cpf;
	private Sex sex;
	private LocalDate birth;

	public CustomerRequestBuilder() {
		this.name = "Robson";
		this.cpf = "95984354071";
		this.sex = Sex.MALE;
		this.birth = LocalDate.of(2000, Month.DECEMBER, 31);
	}

	public CustomerRequest build() {
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setName(name);
		customerRequest.setCpf(cpf);
		customerRequest.setSex(sex);
		customerRequest.setBirth(birth);
		return customerRequest;
	}

	public CustomerRequestBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public CustomerRequestBuilder withCpf(String cpf) {
		this.cpf = cpf;
		return this;
	}

}
