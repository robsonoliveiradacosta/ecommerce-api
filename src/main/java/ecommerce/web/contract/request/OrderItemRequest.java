package ecommerce.web.contract.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OrderItemRequest {

	@Valid
	@NotNull
	private ProductIdRequest product;

	@Positive
	@NotNull
	private Integer quantity;

	public ProductIdRequest getProduct() {
		return product;
	}

	public void setProduct(ProductIdRequest product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
