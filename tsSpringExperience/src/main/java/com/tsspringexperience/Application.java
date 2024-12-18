package com.tsspringexperience;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.tsspringexperience.config.CidFilter;
import com.tsspringexperience.config.Interceptor;
import com.tsspringexperience.multi.tenant.CustomRoutingDataSource;
import com.tsspringexperience.multi.tenant.TenantDataSource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableJpaAuditing
@ComponentScan(basePackages = "com.tsspringexperience")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.tsspringexperience")
public class Application {

    @Autowired
    private CidFilter cidFilter;
    
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
    public DataSource dataSource(){
        CustomRoutingDataSource customDataSource=new CustomRoutingDataSource();
        customDataSource.setTargetDataSources(TenantDataSource.getDataSourceHashMap());
        return customDataSource;
    } 

	@Bean
    public Interceptor Interceptor() {
        return new Interceptor();
    }
	
	@Bean
	public FilterRegistrationBean < CidFilter > filterRegistrationBean() {
		FilterRegistrationBean <CidFilter> registrationBean = new FilterRegistrationBean<CidFilter>();
	
		registrationBean.setFilter(cidFilter);
		registrationBean.addUrlPatterns("/api/*");
		return registrationBean;
	}
}
