package ecommerce.web.contract.request;

import javax.validation.constraints.NotNull;

public class CustomerIdRequest {

	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
