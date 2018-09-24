package in.deepstudio;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@EnableAutoConfiguration
@ComponentScan("in.deepstudio")
public class WebContextConfig extends SpringBootServletInitializer {

	@Bean
	public MessageSource messageSource() {
    	ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(new String[] {"classpath:messages","classpath:validationMessages"});
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
