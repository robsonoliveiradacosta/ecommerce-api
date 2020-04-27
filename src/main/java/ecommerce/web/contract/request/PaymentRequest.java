package ecommerce.web.contract.request;

import java.time.LocalDate;

import ecommerce.domain.PaymentType;

public class PaymentRequest {

	private PaymentType type;
	private String cardNumber;
	private String barcode;
	private LocalDate expiration;

	public PaymentType getType() {
		return type;
	}

	public void setType(PaymentType type) {
		this.type = type;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public LocalDate getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDate expiration) {
		this.expiration = expiration;
	}

}
