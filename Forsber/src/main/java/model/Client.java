package model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.*;

public class Client {
    public static int clientNumber;
    public String fullName;
    public Date dateOfBirth;
    public String email;



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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return getClientNumber() == client.getClientNumber() && Objects.equals(getFullName(), client.getFullName()) && Objects.equals(getDateOfBirth(), client.getDateOfBirth()) && Objects.equals(getEmail(), client.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientNumber(), getFullName(), getDateOfBirth(), getEmail());
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
