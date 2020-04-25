package ecommerce.web.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ecommerce.domain.Customer;
import ecommerce.repository.CustomerRepository;
import ecommerce.web.contract.CustomerRequest;
import ecommerce.web.controller.builder.CustomerBuilder;
import ecommerce.web.controller.builder.CustomerRequestBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class CustomerControllerIntegrationTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	CustomerRepository customerRepository;

	String url = "/api/customers/";
	ObjectMapper objectMapper;

	@BeforeEach
	void beforeEach() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Transactional
	@Test
	void testFindAll() throws Exception {
		customerRepository.save(new CustomerBuilder().build());
		customerRepository.save(new CustomerBuilder().withName("Atreus").withCpf("89950434076").build());

		mockMvc.perform(get(url)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Transactional
	@Test
	void testFindById() throws Exception {
		Customer customer = customerRepository.save(new CustomerBuilder().build());

		mockMvc.perform(get(url + customer.getId())).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(customer.getId().intValue())))
				.andExpect(jsonPath("$.name", is(customer.getName())))
				.andExpect(jsonPath("$.cpf", is(customer.getCpf())));
	}

	@Transactional
	@Test
	void testCreate() throws Exception {
		CustomerRequest customerRequest = new CustomerRequestBuilder().build();
		String json = objectMapper.writeValueAsString(customerRequest);

		RequestBuilder requestBuilder = post(url).content(json).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").isNotEmpty()).andExpect(jsonPath("$.name", is(customerRequest.getName())))
				.andExpect(jsonPath("$.cpf", is(customerRequest.getCpf())))
				.andExpect(jsonPath("$.sex", is(customerRequest.getSex().toString())))
				.andExpect(jsonPath("$.birth", is(customerRequest.getBirth().toString())));
	}

	@Transactional
	@Test
	void testUpdate() throws Exception {
		Customer customer = customerRepository.save(new CustomerBuilder().build());
		CustomerRequest customerRequest = new CustomerRequestBuilder().withName("Robson XYZ").build();
		String json = objectMapper.writeValueAsString(customerRequest);

		RequestBuilder requestBuilder = put(url + customer.getId()).content(json)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(customer.getId().intValue())))
				.andExpect(jsonPath("$.name", is(customerRequest.getName())))
				.andExpect(jsonPath("$.cpf", is(customerRequest.getCpf())))
				.andExpect(jsonPath("$.sex", is(customerRequest.getSex().toString())))
				.andExpect(jsonPath("$.birth", is(customerRequest.getBirth().toString())));
	}

	@Transactional
	@Test
	void testRemove() throws Exception {
		Customer customer = customerRepository.save(new CustomerBuilder().build());

		mockMvc.perform(delete(url + customer.getId())).andExpect(status().isNoContent());
	}

	@Test
	void testFindByIdNotFound() throws Exception {
		int idNotFound = 1;
		mockMvc.perform(get(url + idNotFound)).andExpect(status().isNotFound());
	}

	@Test
	void testCreateWithBlankParameters() throws Exception {
		CustomerRequest customerRequest = new CustomerRequestBuilder().withName("").withCpf("").build();
		String json = objectMapper.writeValueAsString(customerRequest);

		RequestBuilder requestBuilder = post(url).content(json).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andExpect(jsonPath("$.fields").isNotEmpty());
	}

	@Test
	void testCreateWithInvalidCpf() throws Exception {
		CustomerRequest customerRequest = new CustomerRequestBuilder().withCpf("12345678901").build();
		String json = objectMapper.writeValueAsString(customerRequest);

		RequestBuilder requestBuilder = post(url).content(json).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.fields[*].name", hasItem("cpf")));
	}

}
