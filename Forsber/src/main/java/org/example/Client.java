package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.*;

public class Client {
    int clientNumber;
    String fullName;
    Date dateOfBirth;
    String email;



    public Client(int clientNumber, String fullName, Date dateOfBirth, String email) {
        this.clientNumber = clientNumber;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }



    public int getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientNumber=" + clientNumber +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
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
