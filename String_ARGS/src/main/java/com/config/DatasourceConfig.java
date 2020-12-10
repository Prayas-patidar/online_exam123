package com.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatasourceConfig {

	@Autowired
	private Environment environment;

	@Bean
	public DataSourceTransactionManager getDataSourceTransactionManagerForLocalDB(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public JdbcTemplate getJdbcTemplateForLocalDB(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public DataSource getDataSourceForLocalDB() {
		BasicDataSource basicDataSource = new BasicDataSource();

		basicDataSource.setDriverClassName(environment.getProperty("jdbc.local.driver"));
		basicDataSource.setUrl(environment.getProperty("jdbc.local.url"));

		basicDataSource.setUsername(environment.getProperty("db.local.username"));
		basicDataSource.setPassword(environment.getProperty("db.local.password"));
		return basicDataSource;
	}
	/**
	 * @author prayas
	 * @description commenting this because two separate DBs merge into One
	 * @since 05-11-2020
	 * 
	 * 
	 **/
	// @Bean
	// public DataSourceTransactionManager
	// getDataSourceTransactionManagerForMasterDB(
	// @Qualifier("masterDB") DataSource dataSource) {
	// return new DataSourceTransactionManager(dataSource);
	// }
	//
	// @Bean(name = "jdbcTemplateForMasterDB")
	// public JdbcTemplate getJdbcTemplateForMasterDB(@Qualifier("masterDB")
	// DataSource dataSource) {
	// return new JdbcTemplate(dataSource);
	// }
	//
	// @Bean(name = "masterDB")
	// public DataSource getDataSourceForMasterDB() {
	// BasicDataSource basicDataSource = new BasicDataSource();
	//
	// basicDataSource.setDriverClassName(environment.getProperty("jdbc.master.driver"));
	// basicDataSource.setUrl(environment.getProperty("jdbc.master.url"));
	//
	// basicDataSource.setUsername(environment.getProperty("db.master.username"));
	// basicDataSource.setPassword(environment.getProperty("db.master.password"));
	// return basicDataSource;
	// }

}