package ecommerce.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.service.OrderService;
import ecommerce.web.contract.request.OrderRequest;
import ecommerce.web.contract.response.OrderResponse;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderResponse create(@Valid @RequestBody OrderRequest orderRequest) {
		return service.create(orderRequest);
	}

	@GetMapping
	public List<OrderResponse> findAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public OrderResponse findById(@PathVariable Long id) {
		return service.findById(id);
	}

}
