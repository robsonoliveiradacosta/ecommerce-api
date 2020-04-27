package ecommerce.web.contract.request;

import javax.validation.constraints.NotNull;

public class ProductIdRequest {

	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
