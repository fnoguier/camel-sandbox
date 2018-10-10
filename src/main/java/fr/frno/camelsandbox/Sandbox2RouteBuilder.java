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
            .to("direct:long-process")
            .log("test-long-process-with-a-seda-call-within - END");

        from("direct:long-process")
            .log("long-process - BEGIN")
            .log("processing 1 ...")
            .delay(500)
            .log("processing 2 ...")
            .delay(500)
            .log("processing 3 ...")
            .delay(500)
            .log("processing 4 ...")
            .delay(500)
            .log("processing 5 ...")
            .delay(500)
            .log("processing 6 ...")
            .delay(500)
            .log("processing 7 ...")
            .delay(500)
            .log("processing 8 ...")
            .delay(500)
            .log("processing 9 ...")
            .delay(500)
            .log("long-process - END");



        from("seda:non-disruptive-task")
            .onException(RuntimeException.class)
            .log("non-disruptive-task - EXCEPTION HANDLING")
            .end()
            .log("non-disruptive-task - BEGIN")
            .process(ex -> {
                throw new RuntimeException("runtime exception in non-disruptive-task");
            })
            .log("non-disruptive-task - END");
    }
}



