package com.example.employee.context;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Configuration
public class AppContext {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(
                    HttpRequest request,
                    byte[] body,
                    ClientHttpRequestExecution execution) throws IOException {

                System.out.println("Request URI: " + request.getURI());
                System.out.println("Request Headers: " + request.getHeaders());
                System.out.println("Request Body: " + new String(body, StandardCharsets.UTF_8));

                ClientHttpResponse response = execution.execute(request, body);
                System.out.println("Response Status: " + response.getStatusCode());
                return response;
            }
        }));
        return restTemplate;
    }

}
