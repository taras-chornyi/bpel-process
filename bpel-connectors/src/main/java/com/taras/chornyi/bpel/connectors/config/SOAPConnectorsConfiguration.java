package com.taras.chornyi.bpel.connectors.config;

import com.taras.chornyi.bpel.connectors.SOAPConnectorComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SOAPConnectorsConfiguration {

    @Autowired
    private ConnectorsProperties properties;
    
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(properties.getWebservice().getPackages());
        return marshaller;
    }

    @Bean
    public SOAPConnectorComponent soapConnector(Jaxb2Marshaller marshaller) {
        SOAPConnectorComponent connector = new SOAPConnectorComponent();
        connector.setDefaultUri(properties.getWebservice().getBpelUrl());
        connector.setMarshaller(marshaller);
        connector.setUnmarshaller(marshaller);
        return connector;
    }

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster
                = new SimpleApplicationEventMulticaster();

        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }

}
