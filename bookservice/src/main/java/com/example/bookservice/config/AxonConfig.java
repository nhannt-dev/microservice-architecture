package com.example.bookservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thoughtworks.xstream.XStream;

@Configuration
public class AxonConfig {
    // XStream: Dùng để kiểm tra tất cả các Lib theo 1 tiền tố
    @Bean
    public XStream xStream(){
        XStream xstream = new XStream();
        xstream.allowTypesByWildcard(new String[] {
            "com.example.**"
        });
        return xstream;
    }
}