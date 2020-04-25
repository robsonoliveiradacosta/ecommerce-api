package ecommerce.web.controller.builder;

import java.time.LocalDate;
import java.time.Month;

import ecommerce.domain.Customer;
import ecommerce.domain.Sex;

public class CustomerBuilder {

	private Long id;
	private String name;
	private String cpf;
	private Sex sex;
	private LocalDate birth;

	public CustomerBuilder() {
		this.id = null;
		this.name = "Robson";
		this.cpf = "95984354071";
		this.sex = Sex.MALE;
		this.birth = LocalDate.of(2000, Month.DECEMBER, 31);
	}

	public Customer build() {
		Customer customer = new Customer();
		customer.setId(id);
		customer.setName(name);
		customer.setCpf(cpf);
		customer.setSex(sex);
		customer.setBirth(birth);
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
