package com.training.repo;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Producer;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mail.MailEndpoint;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import java.util.Date;

public class SendingMail extends RouteBuilder{

    public static void main(String[] args) throws Exception {
        Endpoint endpoint = new MailEndpoint("smtp://irinsklyar@gmail.com?password=vfrcahfq23");
        Exchange exchange = endpoint.createExchange();
        Message in = exchange.getIn();
        in.setBody("Hello + "+ new Date().getTime());
        in.addAttachment("select.txt", new DataHandler(new FileDataSource("/src/main/resources/select.txt")));
        Producer producer = endpoint.createProducer();
        producer.start();
        producer.process(exchange);
    }

    @Override
    public void configure() throws Exception {
       from("timer://sendEmail?fixedRate=true&amp;period=3600s")

               .to("smtps://smtp.gmail.com:465?username=irinsklyar@gmail.com&password=vfrcahfq23debugMode=true");
    }
    public void sendWeather() {
        System.out.println("gggg");
    }
}
