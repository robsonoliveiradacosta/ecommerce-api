package ecommerce.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.domain.Product;
import ecommerce.exception.ResourceNotFoundException;
import ecommerce.repository.ProductRepository;
import ecommerce.web.contract.request.ProductRequest;
import ecommerce.web.contract.response.ProductResponse;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProductRepository repository;

	@GetMapping
	public List<ProductResponse> findAll() {
		return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ProductResponse findById(@PathVariable Long id) {
		Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		return toResponse(product);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public ProductResponse create(@Valid ProductRequest productRequest) {
		Product product = modelMapper.map(productRequest, Product.class);
		if (productRequest.hasFile()) {
			product.setPhoto("http://res.cloudinary.com/demo/image/upload/sample.jpg");
		}
		return toResponse(repository.save(product));
	}

	@PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Transactional
	public ProductResponse update(@PathVariable Long id, @Valid ProductRequest productRequest) {
		Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		modelMapper.map(productRequest, product);
		if (productRequest.hasFile()) {
			product.setPhoto("http://res.cloudinary.com/demo/image/upload/w_150,h_100,c_fill/sample.jpg");
		}
		return toResponse(repository.save(product));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void delete(@PathVariable Long id) {
		Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		repository.delete(product);
	}

	private ProductResponse toResponse(Product product) {
		return modelMapper.map(product, ProductResponse.class);
	}

}
