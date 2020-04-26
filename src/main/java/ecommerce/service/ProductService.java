package ecommerce.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import ecommerce.domain.Product;
import ecommerce.exception.FileException;
import ecommerce.exception.ResourceNotFoundException;
import ecommerce.repository.ProductRepository;
import ecommerce.service.storage.PhotoStorageService;
import ecommerce.web.contract.request.ProductRequest;
import ecommerce.web.contract.response.ProductResponse;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PhotoStorageService photoStorageService;

	public List<ProductResponse> findAll() {
		return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
	}

	public ProductResponse findById(Long id) {
		Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		return toResponse(product);
	}

	@Transactional
	public ProductResponse create(ProductRequest productRequest) {
		Product product = modelMapper.map(productRequest, Product.class);
		return save(productRequest, product);
	}

	@Transactional
	public ProductResponse update(Long id, ProductRequest productRequest) {
		Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		modelMapper.map(productRequest, product);
		return save(productRequest, product);
	}

	@Transactional
	public void delete(@PathVariable Long id) {
		Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		repository.delete(product);
	}

	private ProductResponse toResponse(Product product) {
		return modelMapper.map(product, ProductResponse.class);
	}

	private byte[] getBytes(MultipartFile file) {
		try {
			return file.getBytes();
		} catch (IOException e) {
			throw new FileException();
		}
	}

	private ProductResponse save(ProductRequest productRequest, Product product) {
		if (productRequest.hasFile()) {
			photoStorageService.delete(product.getPhoto());
			byte[] file = getBytes(productRequest.getFile());
			product.setPhoto(photoStorageService.upload(file));
		}
		product.setCategories(productRequest.categories());
		return toResponse(repository.save(product));
	}

}
