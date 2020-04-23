package ecommerce.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ecommerce.exception.ResourceNotFoundException;

public class ApiRestController<T, REQUEST, RESPONSE> {

	private JpaRepository<T, Long> repository;
	private Class<T> entityClass;
	private Class<RESPONSE> responseClass;

	@Autowired
	private ModelMapper modelMapper;

	public ApiRestController(JpaRepository<T, Long> repository, Class<T> entityClass, Class<RESPONSE> responseClass) {
		this.repository = repository;
		this.entityClass = entityClass;
		this.responseClass = responseClass;
	}

	@GetMapping
	public List<RESPONSE> findAll() {
		return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public RESPONSE findById(@PathVariable Long id) {
		T entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		return toResponse(entity);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public RESPONSE create(@Valid @RequestBody REQUEST request) {
		T entity = modelMapper.map(request, entityClass);
		return toResponse(repository.save(entity));
	}

	@PutMapping("/{id}")
	@Transactional
	public RESPONSE update(@PathVariable Long id, @Valid @RequestBody REQUEST request) {
		T entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		modelMapper.map(request, entity);
		return toResponse(repository.save(entity));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void delete(@PathVariable Long id) {
		T entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		repository.delete(entity);
	}

	private RESPONSE toResponse(T t) {
		return modelMapper.map(t, responseClass);
	}

}
