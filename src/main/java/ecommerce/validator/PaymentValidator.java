package ecommerce.validator;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import ecommerce.domain.PaymentType;
import ecommerce.web.contract.request.PaymentRequest;

public class PaymentValidator implements ConstraintValidator<PaymentConstraint, PaymentRequest> {

	@Override
	public boolean isValid(PaymentRequest paymentRequest, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		if (Objects.isNull(paymentRequest.getType())) {
			context.buildConstraintViolationWithTemplate("informe o tipo de pagamento").addPropertyNode("type")
					.addConstraintViolation();
			return false;
		}
		if (PaymentType.CARD.equals(paymentRequest.getType())) {
			if (StringUtils.isEmpty(paymentRequest.getCardNumber())) {
				context.buildConstraintViolationWithTemplate("informe o número do cartão de crédito")
						.addPropertyNode("cardNumber").addConstraintViolation();
				return false;
			}
		}
		if (PaymentType.BILLET.equals(paymentRequest.getType())) {
			boolean error = false;
			if (StringUtils.isEmpty(paymentRequest.getBarcode())) {
				context.buildConstraintViolationWithTemplate("informe o código de barras").addPropertyNode("barcode")
						.addConstraintViolation();
				error = true;
			}
			if (Objects.isNull(paymentRequest.getExpiration())) {
				context.buildConstraintViolationWithTemplate("informe a data de vencimento")
						.addPropertyNode("expiration").addConstraintViolation();
				error = true;
			} else if (paymentRequest.getExpiration().isBefore(LocalDate.now())) {
				context.buildConstraintViolationWithTemplate("data de vencimento deve ser hoje ou no futuro")
						.addPropertyNode("expiration").addConstraintViolation();
				error = true;
			}
			if (error) {
				return false;
			}
		}
		return true;
	}

}
