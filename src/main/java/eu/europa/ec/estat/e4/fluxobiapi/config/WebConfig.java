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
                        "https://c93270466bef.ngrok-free.app/",
                        "https://fluxo-bi-ui.sp1.br.saveincloud.net.br",
                        // URLs dos túneis Cloudflare
                        "https://graphs-semiconductor-thomson-beginning.trycloudflare.com", // fluxo-bi-ui-dev
                        "https://syndication-thereafter-cement-junior.trycloudflare.com"   // fluxo-bi-ui-staging
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}