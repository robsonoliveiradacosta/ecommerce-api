package ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(enableDefaultTransactions = false, basePackages = "ecommerce.repository")
public class JpaConfig {

}
