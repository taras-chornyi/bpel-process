package com.taras.chornyi.bpel.connectors.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "connectors", ignoreUnknownFields = false)
public class ConnectorsProperties {

    private Webservice webservice = new Webservice();
    private Retry retry = new Retry();

    @Data
    public static class Webservice {
        private String packages;
        private String bpelUrl;
    }

    @Data
    public static class Retry {

        private Attempts attempts = new Attempts();
        private boolean skipRetry;

        @Data
        public static class Attempts {
            private int count;
            private long interval;
        }
    }
}
