package ecommerce.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.domain.Order;
import ecommerce.domain.OrderItem;
import ecommerce.domain.OrderStatus;
import ecommerce.domain.PaymentBillet;
import ecommerce.domain.PaymentCard;
import ecommerce.domain.PaymentStatus;
import ecommerce.domain.PaymentType;
import ecommerce.domain.Product;
import ecommerce.exception.ResourceNotFoundException;
import ecommerce.repository.OrderRepository;
import ecommerce.repository.ProductRepository;
import ecommerce.web.contract.request.OrderRequest;
import ecommerce.web.contract.response.OrderResponse;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public OrderResponse create(OrderRequest orderRequest) {
		Order order = modelMapper.map(orderRequest, Order.class);

		BigDecimal total = BigDecimal.ZERO;
		for (OrderItem orderItem : order.getItens()) {
			Product product = productRepository.findById(orderItem.getProduct().getId())
					.orElseThrow(() -> new ResourceNotFoundException());
			orderItem.setProductPrice(product.getPrice());
			BigDecimal partial = orderItem.getProductPrice().multiply(new BigDecimal(orderItem.getQuantity()));
			total = total.add(partial);

			orderItem.setOrder(order);
			orderItem.setProduct(product);
		}
		order.setTotal(total);
		order.setStatus(OrderStatus.WAITING);

		if (PaymentType.CARD.equals(orderRequest.getPayment().getType())) {
			PaymentCard payment = new PaymentCard();
			payment.setCardNumber(orderRequest.getPayment().getCardNumber());
			payment.setStatus(PaymentStatus.PROCESSING);
			order.setPayment(payment);
		} else if (PaymentType.BILLET.equals(orderRequest.getPayment().getType())) {
			PaymentBillet payment = new PaymentBillet();
			payment.setBarcode(orderRequest.getPayment().getBarcode());
			payment.setExpiration(orderRequest.getPayment().getExpiration());
			payment.setStatus(PaymentStatus.PROCESSING);
			order.setPayment(payment);
		}

		return toResponse(repository.save(order));
	}

	public List<OrderResponse> findAll() {
		return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
	}

	public OrderResponse findById(Long id) {
		Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		return toResponse(order);
	}

	private OrderResponse toResponse(Order order) {
		return modelMapper.map(order, OrderResponse.class);
	}

}
