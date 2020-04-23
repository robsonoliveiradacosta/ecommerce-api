package ecommerce.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.domain.Customer;
import ecommerce.repository.CustomerRepository;
import ecommerce.web.contract.CustomerRequest;
import ecommerce.web.contract.CustomerResponse;

@RestController
@RequestMapping("/api/customers")
public class CustomerController extends ApiRestController<Customer, CustomerRequest, CustomerResponse> {

	public CustomerController(CustomerRepository repository) {
		super(repository, Customer.class, CustomerResponse.class);
	}

}
