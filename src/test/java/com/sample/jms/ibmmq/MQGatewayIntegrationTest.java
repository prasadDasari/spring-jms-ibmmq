package com.sample.jms.ibmmq;

import com.sample.jms.ibmmq.mq.MQConfiguration;
import com.sample.jms.ibmmq.mq.MQGateway;
import com.sample.jms.ibmmq.mq.MQProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MQGatewayIntegrationTest.TestConfiguration.class, MQGateway.class, MQProperties.class})
@EnableAutoConfiguration
@EnableJms
public class MQGatewayIntegrationTest {
	@Autowired
	JmsTemplate jmsTemplate;

	@Autowired
	TestConfiguration configuration;

	@Autowired
	MQProperties mqProperties;

	/*@Value("${ibm.mq.incoming-queue}")
	String queue;*/

	@Test
	public void shouldReceiveMessageInListener() throws Exception {
		String message = "omsairam ";
		//System.out.println("queue" +queue);

		System.out.println("queue ===> " +mqProperties.getIncomingQueue());
		jmsTemplate.convertAndSend(mqProperties.getIncomingQueue(), message);
		await().atMost(5, TimeUnit.SECONDS)
				.until(configuration::hasReceivedMessages);

		assertThat(configuration.receivedMessages, contains(message));
	}


	@Configuration
	static class TestConfiguration extends MQConfiguration {
		List<String> receivedMessages = new CopyOnWriteArrayList<>();

        @Override
		@Bean
		public Consumer<String> messageConsumer() {
			return receivedMessages::add;
		}

		public boolean hasReceivedMessages() {
			return !receivedMessages.isEmpty();
		}

	}

	@Before
	public void setUp() throws Exception {
		waitForQueuesToBeInitialized();
	}

	private void waitForQueuesToBeInitialized() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			fail();
		}
	}
}
