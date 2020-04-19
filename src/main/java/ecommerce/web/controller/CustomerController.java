package ecommerce.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.domain.Customer;
import ecommerce.repository.CustomerRepository;
import ecommerce.web.contract.CustomerRequest;
import ecommerce.web.contract.CustomerResponse;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private CustomerRepository repository;
	private ModelMapper modelMapper;

	public CustomerController(CustomerRepository repository, ModelMapper modelMapper) {
		this.repository = repository;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	public List<CustomerResponse> findAll() {
		return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public CustomerResponse findById(@PathVariable Long id) {
		Customer customer = repository.findById(id).get();
		return toResponse(customer);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public CustomerResponse create(@RequestBody @Valid CustomerRequest customerRequest) {
		Customer customer = modelMapper.map(customerRequest, Customer.class);
		return toResponse(repository.save(customer));
	}

	@PutMapping("/{id}")
	@Transactional
	public CustomerResponse update(@PathVariable Long id, @RequestBody @Valid CustomerRequest customerRequest) {
		Customer customer = repository.findById(id).get();
		modelMapper.map(customerRequest, customer);
		return toResponse(repository.save(customer));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void remove(@PathVariable Long id) {
		Customer customer = repository.findById(id).get();
		repository.delete(customer);
	}

	private CustomerResponse toResponse(Customer customer) {
		return modelMapper.map(customer, CustomerResponse.class);
	}

}
