package ecommerce.web.contract.response;

import ecommerce.domain.PaymentStatus;

public class PaymentResponse {

	private Long id;
	private PaymentStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

}
