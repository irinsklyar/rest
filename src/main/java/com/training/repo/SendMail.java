package com.training.repo;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.core.osgi.OsgiDefaultCamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.osgi.framework.BundleContext;

public class SendMail extends RouteBuilder {
//    private WeatherDaoImpl weatherDaoS = new WeatherDaoImpl(new CassandraConnector());
    private WeatherDaoImpl weatherDao;
    private CamelContext camel;
//    Exchange exchange = new DefaultExchange(camel);
    public SendMail(CamelContext camel, WeatherDaoImpl weatherDao) {
        super(camel);
        this.weatherDao = weatherDao;
        this.camel = camel;
    }

    public Processor processor = new Processor() {
        @Override
        public void process(Exchange exchange) throws Exception {
//           exchange.getIn().setBody(weatherDao.findWeather());
           exchange.getIn().setBody(weatherDao.findWeather());
//
//            exchange.getIn().setBody("gfffgf");

            System.out.println(weatherDao.findWeather());
        }
    };

    @Override
    public void configure() throws Exception {

        camel.addRoutes(this);

        from("timer://sendEmail?fixedRate=true&period=60s")
                .process(processor)
                .to("smtps://smtp.gmail.com:465?username=irinsklyar@gmail.com&password=vfrcahfq23");
        camel.start();
    }
}