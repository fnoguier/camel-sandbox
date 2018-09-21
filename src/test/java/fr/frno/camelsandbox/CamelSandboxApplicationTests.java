package fr.frno.camelsandbox;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CamelSandboxApplicationTests {

	@Autowired
	private CamelContext camelContext;
	private ProducerTemplate producerTemplate;

	@PostConstruct
	void postConstruct(){
		producerTemplate = camelContext.createProducerTemplate();
	}

	@Test
	public void send_to_direct_to_direct() {
	    // message exchange pattern is 'in only' because of sendBody
		assertThatThrownBy(() -> producerTemplate.sendBody("direct:routeAA", ""))
            .isInstanceOf(CamelExecutionException.class)
            .hasCauseInstanceOf(RuntimeException.class);
	}

    @Test
    public void request_direct_to_direct() {
        // message exchange pattern is 'in-out' because of requestBody
        assertThatThrownBy(() -> producerTemplate.requestBody("direct:routeAA", ""))
            .isInstanceOf(CamelExecutionException.class)
            .hasCauseInstanceOf(RuntimeException.class);
    }

    @Test
    public void request_to_direct_to_direct_force_in_only() {
        // message exchange pattern is 'in-out' because of requestBody
        assertThatThrownBy(() -> producerTemplate.requestBody("direct:routeAB", ""))
            .isInstanceOf(CamelExecutionException.class)
            .hasCauseInstanceOf(RuntimeException.class);
    }

    @Test
    public void send_to_direct_to_direct_force_in_out() {
        // message exchange pattern is 'in only' because of sendBody
        assertThatThrownBy(() -> producerTemplate.sendBody("direct:routeAC", ""))
            .isInstanceOf(CamelExecutionException.class)
            .hasCauseInstanceOf(RuntimeException.class);
    }

    @Test
    public void send_to_direct_to_seda() {
        // message exchange pattern is 'in only' because of sendBody
        assertThatCode(() -> producerTemplate.sendBody("direct:routeBA", ""))
            .doesNotThrowAnyException();
    }

    @Test
    public void request_to_direct_to_seda() {
        // message exchange pattern is 'in-out' because of requestBody
        assertThatThrownBy(() -> producerTemplate.requestBody("direct:routeBA", ""))
            .isInstanceOf(CamelExecutionException.class)
            .hasCauseInstanceOf(RuntimeException.class);
    }

    @Test
    public void request_to_direct_to_seda_force_in_only() {
        assertThatCode(() -> producerTemplate.sendBody("direct:routeBB", ""))
            .doesNotThrowAnyException();
    }

    @Test
    public void send_to_direct_to_seda_force_in_out() {
        assertThatCode(() -> producerTemplate.sendBody("direct:routeBC", ""))
            .isInstanceOf(CamelExecutionException.class)
            .hasCauseInstanceOf(RuntimeException.class);
    }
}
