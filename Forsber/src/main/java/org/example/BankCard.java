package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.util.Date;

class BankCard {
    int clientNumber;
    int cardNumber;
    Date issueDate;
    Date expiryDate;
    boolean blocked = false;

    public BankCard(int clientNumber, int cardNumber, Date issueDate, Date expiryDate) {
        this.clientNumber = clientNumber;
        this.cardNumber = cardNumber;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }



    @Override
    public String toString() {
        return "BankCard{" +
                "clientNumber=" + clientNumber +
                ", cardNumber='" + cardNumber + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
    @Configuration

    public class SpringJdbcConfig {
        @Bean
        public DataSource mysqlDataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/clientbankcard");
            dataSource.setUsername("root");
            dataSource.setPassword("OkaRuto24!");
            return dataSource;
        }
        @Bean
        public JdbcTemplate jdbcTemplate(){
            return  new JdbcTemplate(mysqlDataSource());
        }
    }

}
