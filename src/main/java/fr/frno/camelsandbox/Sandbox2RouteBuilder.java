package fr.frno.camelsandbox;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("sandbox2")
public class Sandbox2RouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:test-long-process-with-a-seda-call-within")
            .onException(Exception.class)
            .log("test-long-process-with-a-seda-call-within - EXCEPTION HANDLING")
            .end()
            .log("test-long-process-with-a-seda-call-within - BEGIN")
            .to(ExchangePattern.InOnly, "seda:non-disruptive-task")
            .log("test-long-process-with-a-seda-call-within - BEFORE DELAY")
            .delay(5000)
            .log("test-long-process-with-a-seda-call-within - END");


        from("seda:non-disruptive-task")
            .onException(RuntimeException.class)
            .log("non-disruptive-task - EXCEPTION HANDLING")
            .end()
            .log("non-disruptive-task - BEGIN")
            .process(ex -> {
                throw new RuntimeException("runtime exception in routeY");
            })
            .log("non-disruptive-task - END");
    }
}



