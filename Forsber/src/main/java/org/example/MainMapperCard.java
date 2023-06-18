package org.example;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class MainMapperCard implements RowMapper <BankCard> {


    @Override
    public BankCard mapRow(ResultSet resultSet, int rowNum) throws SQLException {

            BankCard bankCard = new BankCard(
                    resultSet.getInt("client_number"),
                    resultSet.getInt("card_number"),
                    resultSet.getDate("issue_date"),
                    resultSet.getDate("expiry_date"));
            return bankCard;
    }
}
