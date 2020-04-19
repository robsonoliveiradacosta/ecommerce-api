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

import ecommerce.domain.Category;
import ecommerce.repository.CategoryRepository;
import ecommerce.web.contract.CategoryRequest;
import ecommerce.web.contract.CategoryResponse;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private CategoryRepository repository;
	private ModelMapper modelMapper;

	public CategoryController(CategoryRepository repository, ModelMapper modelMapper) {
		this.repository = repository;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	public List<CategoryResponse> findAll() {
		return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public CategoryResponse findById(@PathVariable Long id) {
		Category category = repository.findById(id).get();
		return toResponse(category);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public CategoryResponse create(@RequestBody @Valid CategoryRequest categoryRequest) {
		Category category = modelMapper.map(categoryRequest, Category.class);
		return toResponse(repository.save(category));
	}

	@PutMapping("/{id}")
	@Transactional
	public CategoryResponse update(@PathVariable Long id, @RequestBody @Valid CategoryRequest categoryRequest) {
		Category category = repository.findById(id).get();
		modelMapper.map(categoryRequest, category);
		return toResponse(repository.save(category));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Transactional
	public void remove(@PathVariable Long id) {
		Category category = repository.findById(id).get();
		repository.delete(category);
	}

	private CategoryResponse toResponse(Category category) {
		return modelMapper.map(category, CategoryResponse.class);
	}

}
