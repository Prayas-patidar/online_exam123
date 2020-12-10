package com.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.utils.Encryptor;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com")
@PropertySource(value = { "classpath:db.properties", "classpath:sqlStatements.properties", "classpath:util.properties",
		"classpath:log4j.properties", "classpath:email.properties" })
public class SpringConfigurator extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public JavaMailSenderImpl mailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		try {
			Properties prop = mailSender.getJavaMailProperties();
			prop.put("mail.transport.protocol", "smtp");
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.debug", "false");
			mailSender.setHost(env.getProperty("email.Host"));
			mailSender.setPort(Integer.parseInt(env.getProperty("email.Port")));
			mailSender.setUsername(env.getProperty("email.Username"));
			mailSender.setPassword(new Encryptor().decryptPassword(env.getProperty("email.Password")));
			mailSender.setProtocol(env.getProperty("email.Protocol"));

			return mailSender;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}