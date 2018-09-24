package in.deepstudio;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@EnableAutoConfiguration
@ComponentScan("in.deepstudio")
public class MultiPartFileUploadConfig extends SpringBootServletInitializer{

	@Bean
    MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("1024KB");
        factory.setMaxRequestSize("1024KB");
        return factory.createMultipartConfig();
    }

    @Bean
    CommonsMultipartResolver multipartResolver(){
        return new CommonsMultipartResolver();
    }
	
}
