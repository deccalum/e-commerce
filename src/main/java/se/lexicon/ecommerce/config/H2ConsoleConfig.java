package se.lexicon.ecommerce.config;

import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers the embedded H2 console servlet for local development.
 */
@Configuration
public class H2ConsoleConfig {

    /**
     * Exposes the H2 web console under /h2-console.
     *
     * @return servlet registration for {@link JakartaWebServlet}
     */
    @Bean
    ServletRegistrationBean<JakartaWebServlet> h2ServletRegistration() {
        ServletRegistrationBean<JakartaWebServlet> registration = new ServletRegistrationBean<>(new JakartaWebServlet(),
                "/h2-console/*");
        registration.addInitParameter("-webAllowOthers", "false");
        registration.addInitParameter("-trace", "false");
        registration.setLoadOnStartup(1);
        return registration;
    }
}