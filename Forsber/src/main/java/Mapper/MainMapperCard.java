package Mapper;

import model.BankCard;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
