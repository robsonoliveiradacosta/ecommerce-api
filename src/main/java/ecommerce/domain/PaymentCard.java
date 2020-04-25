package ecommerce.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@DiscriminatorValue("cartao")
@Entity
public class PaymentCard extends Payment {

	@NotEmpty
	@Column(name = "card_number", length = 50)
	private String cardNumber;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

}
