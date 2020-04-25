package ecommerce.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@DiscriminatorValue("boleto")
@Entity
public class PaymentBillet extends Payment {

	@NotBlank
	@Column(length = 100)
	private String barcode;

	@NotNull
	@FutureOrPresent
	private LocalDate expiration;

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
