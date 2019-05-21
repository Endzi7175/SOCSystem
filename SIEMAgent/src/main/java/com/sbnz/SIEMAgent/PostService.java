package com.sbnz.SIEMAgent;

import com.sbnz.SIEMAgent.Log.LogEntry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Service
public class PostService {

    private String serverBaseUrl="http://localhost:8090/api/logs";

//    @Bean
//    public RestTemplate restTemplate() throws Exception {
//
////        String allPassword = "root123";
////        SSLContext sslContext = SSLContextBuilder
////                .create()
////                .loadKeyMaterial(ResourceUtils.getFile("classpath:keystore.jks"), allPassword.toCharArray(), allPassword.toCharArray())
////                .loadTrustMaterial(ResourceUtils.getFile("classpath:truststore.jks"), allPassword.toCharArray())
////                .build();
//
//        HttpClient client = HttpClients.custom()
////                .setSSLContext(sslContext)
//                .build();
//        HttpComponentsClientHttpRequestFactory factory =
//                new HttpComponentsClientHttpRequestFactory(client);
//
//        return new RestTemplate(factory);
//
//    }

    public void sendEntriesToSIEM(List<LogEntry> entries){
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForLocation(serverBaseUrl, entries);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(LogEntry entry : entries){
            System.out.println(entry);
        }
    }

}
