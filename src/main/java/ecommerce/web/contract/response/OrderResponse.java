package ecommerce.web.contract.response;

import java.util.Set;

public class OrderResponse {

	private Long id;
	private CustomerIdResponse customer;
	private Set<OrderItemResponse> itens;
	private AddressResponse address;
	private PaymentResponse payment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerIdResponse getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerIdResponse customer) {
		this.customer = customer;
	}

	public Set<OrderItemResponse> getItens() {
		return itens;
	}

	public void setItens(Set<OrderItemResponse> itens) {
		this.itens = itens;
	}

	public AddressResponse getAddress() {
		return address;
	}

	public void setAddress(AddressResponse address) {
		this.address = address;
	}

	public PaymentResponse getPayment() {
		return payment;
	}

	public void setPayment(PaymentResponse payment) {
		this.payment = payment;
	}

}
