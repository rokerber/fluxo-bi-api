package eu.europa.ec.estat.e4.fluxobiapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:4200",
                        "https://cut-calculation-proud-omissions.trycloudflare.com",
                        "https://harvard-commissions-feel-starting.trycloudflare.com",
                        "https://cut-calculation-proud-omissions.trycloudflare.com",
                        "https://harvard-commissions-feel-starting.trycloudflare.com",
                        "https://safer-rick-scenic-architect.trycloudflare.com",
                        "https://speaks-reveals-meaningful-negotiation.trycloudflare.com"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}