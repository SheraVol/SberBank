package org.example;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MainMapper implements RowMapper <Client>{


    @Override
    public Client mapRow(ResultSet resultSet, int i) throws SQLException {

            Client client = new Client(
                    resultSet.getInt("client_number"),
                    resultSet.getString("full_name"),
                    resultSet.getDate("date_of_birth"),
                    resultSet.getString("email"));

        return client;
    }
}
