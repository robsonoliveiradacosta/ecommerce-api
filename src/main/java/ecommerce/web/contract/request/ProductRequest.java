package ecommerce.web.contract.request;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.web.multipart.MultipartFile;

import ecommerce.domain.Category;

public class ProductRequest {

	@NotBlank
	private String name;

	private String description;

	@NotNull
	@Positive
	private BigDecimal price;

	private MultipartFile file;

	@NotNull
	private Boolean active;

	private Set<Category> categories;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public boolean hasFile() {
		return Objects.nonNull(file) && file.getSize() > 0;
	}

}
