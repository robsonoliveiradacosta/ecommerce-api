package ecommerce.web.contract.response;

public class OrderItemResponse {

	private OrderItemIdResponse id;
	private ProductIdResponse product;
	private Integer quantity;

	public OrderItemIdResponse getId() {
		return id;
	}

	public void setId(OrderItemIdResponse id) {
		this.id = id;
	}

	public ProductIdResponse getProduct() {
		return product;
	}

	public void setProduct(ProductIdResponse product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
