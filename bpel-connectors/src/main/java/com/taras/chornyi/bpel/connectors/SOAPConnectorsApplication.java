package com.taras.chornyi.bpel.connectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SOAPConnectorsApplication {

    public static void main(String[] args) {
        new SpringApplication(SOAPConnectorsApplication.class).run();
    }
}
