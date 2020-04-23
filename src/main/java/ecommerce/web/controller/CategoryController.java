package ecommerce.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.domain.Category;
import ecommerce.repository.CategoryRepository;
import ecommerce.web.contract.CategoryRequest;
import ecommerce.web.contract.CategoryResponse;

@RestController
@RequestMapping("/api/categories")
public class CategoryController extends ApiRestController<Category, CategoryRequest, CategoryResponse> {

	public CategoryController(CategoryRepository repository) {
		super(repository, Category.class, CategoryResponse.class);
	}

}
