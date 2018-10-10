package fr.frno.camelsandbox;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("sandbox2")
public class CamelSandbox2Tests {

	@Autowired
	private CamelContext camelContext;
	private ProducerTemplate producerTemplate;

	@PostConstruct
	void postConstruct(){
		producerTemplate = camelContext.createProducerTemplate();
	}

	@Test
    public void request_to_test_long_process_with_a_seda_call_within() {
        producerTemplate.requestBody("direct:test-long-process-with-a-seda-call-within", "");
    }

}
