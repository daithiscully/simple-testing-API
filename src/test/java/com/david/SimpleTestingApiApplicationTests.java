package com.david;

import org.testng.annotations.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SimpleTestingApiApplication.class)
@WebAppConfiguration
public class SimpleTestingApiApplicationTests {

	@Test
	public void contextLoads() {
	}

}
