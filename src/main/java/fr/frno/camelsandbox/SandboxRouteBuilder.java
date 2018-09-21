package fr.frno.camelsandbox;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SandboxRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:routeAA")
            .onException(Exception.class)
            .log("routeAA - EXCEPTION HANDLING")
            .end()
            .log("routeAA - BEGIN")
            .to("direct:routeZ")
            .log("routeAA - END");

        from("direct:routeAB")
            .onException(Exception.class)
            .log("routeAB - EXCEPTION HANDLING")
            .end()
            .log("routeAB - BEGIN")
            .to(ExchangePattern.InOnly, "direct:routeZ")
            .log("routeAB - END");

        from("direct:routeAC")
            .onException(Exception.class)
            .log("routeAC - EXCEPTION HANDLING")
            .end()
            .log("routeAC - BEGIN")
            .to(ExchangePattern.InOut, "direct:routeZ")
            .log("routeAC - END");

        from("direct:routeBA")
            .onException(Exception.class)
            .log("routeBA - EXCEPTION HANDLING")
            .end()
            .log("routeBA - BEGIN")
            .to("seda:routeY")
            .log("routeBA - END");

        from("direct:routeBB")
            .onException(Exception.class)
            .log("routeBB - EXCEPTION HANDLING")
            .end()
            .log("routeBB - BEGIN")
            .to(ExchangePattern.InOnly, "seda:routeY")
            .log("routeBB - END");

        from("direct:routeBC")
            .onException(Exception.class)
            .log("routeBC - EXCEPTION HANDLING")
            .end()
            .log("routeBC - BEGIN")
            .to(ExchangePattern.InOut, "seda:routeY")
            .log("routeBC - END");


        from("seda:routeY")
            .onException(Exception.class)
            .log("routeY - EXCEPTION HANDLING")
            .end()
            .log("routeY - BEGIN")
            .process(ex -> {
                throw new RuntimeException("runtime exception in routeY");
            })
            .log("routeY - END");

        from("direct:routeZ")
            .onException(Exception.class)
            .log("routeZ - EXCEPTION HANDLING")
            .end()
            .log("routeZ - BEGIN")
            .process(ex -> {
                throw new RuntimeException("runtime exception in routeZ");
            })
            .log("routeZ - END");


    }
}
