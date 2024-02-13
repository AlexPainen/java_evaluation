package com.aapg.java_evaluation.service;

import com.aapg.java_evaluation.model.dao.UserDAO;
import com.aapg.java_evaluation.model.entity.Phone;
import com.aapg.java_evaluation.model.entity.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceTest {

	@Autowired
	UserDAO userDAO;

	@Test
	@Order(1)
	public void testCreate() {
		List<Phone> phones = new ArrayList<>();
		User user = new User();
		Phone phone = new Phone();

		phone.setNumber("12345678");
		phone.setCityCode("1");
		phone.setCountryCode("57");

		phones.add(phone);

		user.setName("Juan Rodriguez");
		user.setEmail("juan@rodriguez.org");
		user.setPassword("hunter123");
		user.setPhones(phones);

		userDAO.save(user);

		assertNotNull(userDAO.findById(UUID.randomUUID()));
	}

	@Test
	@Order(2)
	public void testShowAll() {
		List<User> users = (List<User>) userDAO.findAll();
		assertThat(users).size().isGreaterThan(0);

	}

}
