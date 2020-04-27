package ecommerce.web.contract.request;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ecommerce.validator.PaymentConstraint;

public class OrderRequest {

	@Valid
	@NotNull
	private CustomerIdRequest customer;

	@Valid
	@NotEmpty
	private Set<OrderItemRequest> itens;

	@Valid
	@NotNull
	private AddressRequest address;

	@PaymentConstraint
	private PaymentRequest payment;

	public CustomerIdRequest getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerIdRequest customer) {
		this.customer = customer;
	}

	public Set<OrderItemRequest> getItens() {
		return itens;
	}

	public void setItens(Set<OrderItemRequest> itens) {
		this.itens = itens;
	}

	public AddressRequest getAddress() {
		return address;
	}

	public void setAddress(AddressRequest address) {
		this.address = address;
	}

	public PaymentRequest getPayment() {
		return payment;
	}

	public void setPayment(PaymentRequest payment) {
		this.payment = payment;
	}

}
