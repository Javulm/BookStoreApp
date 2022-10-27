package com.bridgelabz.bookstoreapp.configuration;

import com.bridgelabz.bookstoreapp.Util.EmailSenderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public EmailSenderService emailSenderService()
    {
        return new EmailSenderService();
    }
}
