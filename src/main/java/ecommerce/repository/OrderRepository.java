package ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ecommerce.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
