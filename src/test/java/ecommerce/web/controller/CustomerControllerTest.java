package ecommerce.web.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ecommerce.config.ModelMapperConfig;
import ecommerce.domain.Customer;
import ecommerce.exception.ResourceNotFoundException;
import ecommerce.repository.CustomerRepository;
import ecommerce.web.controller.builder.CustomerBuilder;

@WebMvcTest(CustomerController.class)
@Import(ModelMapperConfig.class)
class CustomerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	CustomerRepository customerRepository;

	String url = "/api/customers/";
	ObjectMapper objectMapper;

	@BeforeEach
	void beforeEach() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Test
	void testFindAll() throws Exception {
		List<Customer> customers = List.of(new CustomerBuilder().build(),
				new CustomerBuilder().withName("Atreus").withCpf("89950434076").build());

		when(customerRepository.findAll()).thenReturn(customers);

		mockMvc.perform(get(url)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	void testFindById() throws Exception {
		Customer customer = new CustomerBuilder().withId(1L).build();

		when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

		mockMvc.perform(get(url.concat(customer.getId().toString()))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(customer.getId().intValue())))
				.andExpect(jsonPath("$.name", is(customer.getName())))
				.andExpect(jsonPath("$.cpf", is(customer.getCpf())));
	}

	@Test
	void testCreate() throws Exception {
		Customer customer = new CustomerBuilder().withId(1L).build();

		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		String json = objectMapper.writeValueAsString(customer);
		RequestBuilder requestBuilder = post(url).content(json).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(customer.getId().intValue())))
				.andExpect(jsonPath("$.name", is(customer.getName())))
				.andExpect(jsonPath("$.cpf", is(customer.getCpf()))).andExpect(jsonPath("$.cpf", is(customer.getCpf())))
				.andExpect(jsonPath("$.sex", is(customer.getSex().toString())))
				.andExpect(jsonPath("$.birth", is(customer.getBirth().toString())));
	}

	@Test
	void testUpdate() throws Exception {
		Customer customer = new CustomerBuilder().withId(1L).build();
		Customer changedCustomer = new CustomerBuilder().withId(1L).withName("Robson XYZ").build();

		when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
		when(customerRepository.save(any(Customer.class))).thenReturn(changedCustomer);

		String json = objectMapper.writeValueAsString(changedCustomer);
		RequestBuilder requestBuilder = put(url.concat(changedCustomer.getId().toString())).content(json)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(changedCustomer.getId().intValue())))
				.andExpect(jsonPath("$.name", is(changedCustomer.getName())))
				.andExpect(jsonPath("$.cpf", is(changedCustomer.getCpf())))
				.andExpect(jsonPath("$.sex", is(changedCustomer.getSex().toString())))
				.andExpect(jsonPath("$.birth", is(changedCustomer.getBirth().toString())));
	}

	@Test
	void testRemove() throws Exception {
		Customer customer = new CustomerBuilder().withId(1L).build();

		when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

		mockMvc.perform(delete(url.concat(customer.getId().toString()))).andExpect(status().isNoContent());

		verify(customerRepository, times(1)).delete(customer);
	}

	@Test
	void testFindByIdNotFound() throws Exception {
		when(customerRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get(url.concat("1"))).andExpect(status().isNotFound());
	}

	@Test
	void testCreateWithBlankParameters() throws Exception {
		Customer customer = new CustomerBuilder().withName("").withCpf("").build();
		String json = objectMapper.writeValueAsString(customer);

		RequestBuilder requestBuilder = post(url).content(json).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andExpect(jsonPath("$.fields", hasSize(3)));
	}

	@Test
	void testCreateWithInvalidCpf() throws Exception {
		Customer customer = new CustomerBuilder().withCpf("12345678901").build();
		String json = objectMapper.writeValueAsString(customer);

		RequestBuilder requestBuilder = post(url).content(json).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.fields[*].name", hasItem("cpf")));
	}

}
