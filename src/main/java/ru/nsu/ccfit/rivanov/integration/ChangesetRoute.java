package ru.nsu.ccfit.rivanov.integration;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.rivanov.jaxb.XMLReader;

import java.io.InputStream;

@Component
public class ChangesetRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer://config?fixedRate=true&period=600")
//        from("timer://config?fixedRate=true&period=86400000")
//        from("direct:start")
                .to("http://api.openstreetmap.org/api/0.6/changesets")
                .setHeader(Exchange.HTTP_METHOD, simple("get"))
                .setHeader(Exchange.HTTP_QUERY, simple("bbox=52.6734314,52.673501,80.872674,80.8727167"))

                .process(exchange -> {
                    String body = exchange.getIn().getBody(String.class);
                    System.out.println("!!!");
                })
                .to("file:/home/roman/advanced-java-2/src/main/resources/out/ch.xml");
//        from("jetty:direct:start")
//                .setHeader(Exchange.HTTP_URI, simple("http://api.openstreetmap.org/api/0.6/changesets"))
//                .setHeader(Exchange.HTTP_METHOD, simple("get"))
//                .setHeader(Exchange.HTTP_QUERY, simple("bbox=52.6734314,52.673501,80.872674,80.8727167"))
//                .process(exchange -> System.out.println("!!! it works !!!"))
//                .process(exchange -> {
//                    exchange.getIn().getBody(String.class);
//                    new XMLReader()
//                })
//        .to("file:/home/roman/advanced-java-2/src/main/resources/out");
    }
}
