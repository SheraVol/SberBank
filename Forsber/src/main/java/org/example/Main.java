package org.example;

import Mail.Endcardml;
import Mapper.MainMapper;
import Mapper.MainMapperCard;
import model.BankCard;
import model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;
@Component
public class Main {
    private static JdbcTemplate jdbcTemplate;
    private static FileWriter writer;


    @Autowired
    public Main(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    private static final String url = "jdbc:mysql://localhost:3306/clientbankcard?serverTimezone=Europe/Moscow";
    private static final String user = "root";
    private static final String password = "OkaRuto24!";
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(url, user, password);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }


    public static void main(String[] args) throws SQLException, IOException {


        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Map<Integer, Client> clientCache = new HashMap<>();

        Map<Integer, BankCard> cardsCache = new HashMap<>();


try {
    writer = new FileWriter("ut.txt");
    Endcardml endl = new Endcardml(connection,writer);
    endl.start();
} catch (IOException e) {
    throw new RuntimeException(e);
}

        String delcardst = "SELECT * FROM bank_cards WHERE expiry_date < NOW()";



        try (Statement stdelcard = connection.createStatement();
             ResultSet resdelcard = stdelcard.executeQuery(delcardst))
        {
            while (resdelcard.next()) {

                int delcardNumber = resdelcard.getInt("card_number");
                int delclietntNumber = resdelcard.getInt("client_number");
                deleteCard(connection, delcardNumber);


             addDeletedClient(connection, delclietntNumber);


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }








            String newcard = "SELECT DISTINCT client_number FROM carddel";
        try(Statement stnewcard = connection.createStatement();
            ResultSet resnewcard = stnewcard.executeQuery(newcard))
        {
            while (resnewcard.next()){

                int clientnewcard = resnewcard.getInt("client_number");
                createNewCard(connection, clientnewcard);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String deloldid = "DELETE FROM carddel";


        try(Statement stdeloldid = connection.createStatement() )
            {
                stdeloldid.executeUpdate(deloldid);
            } catch (SQLException e) {
            e.printStackTrace();
        }
        while (true) {
                System.out.println("Добавить нового клиента? (Да/Нет)");
                String addClient = scanner.next();
                if (addClient.equalsIgnoreCase("Да")) {

                    System.out.println("Введите индивидуальный номер клиента: ");
                    int clientNumber = Integer.parseInt(scanner.next());
                    PreparedStatement findId = connection.prepareStatement("SELECT * FROM clients WHERE client_number= ?");

                    findId.setInt(1, clientNumber);
                    ResultSet rs = findId.executeQuery();
                    if (rs.next()) {
                        System.err.println("Клиент с таким номером уже существует! Ему будет сгенирирована новый номер");
                        break;
                    } else {

                        System.out.println("Введите имя клиента:");
                        String fullName = scanner.next();
                        System.out.println("Введите дату рождения клиента в формате дд.мм.гггг:");
                        String birthdateStr = scanner.next();
                        Date dateOfBirth;
                        try {
                            dateOfBirth = dateFormat.parse(birthdateStr);
                        } catch (ParseException e) {
                            System.out.println("Ошибка ввода даты рождения: " + e.getMessage());
                            continue;
                        }
                        System.out.println("Введите email клиента:");
                        String email = scanner.next();

                        Client newClient = new Client(clientNumber, fullName, dateOfBirth, email);
                        clientCache.put(newClient.clientNumber, newClient);

                        System.out.println("Введите количество карточек для этого клиента: ");
                        int cardCount = scanner.nextInt();
                        for (int i = 1; i <= cardCount; i++) {
                            System.out.println("Введите ID карточки: ");
                            int cardNumber = scanner.nextInt();
                            PreparedStatement findcard = connection.prepareStatement("SELECT * FROM bank_cards WHERE card_number= ?");
                            findcard.setInt(1, cardNumber);
                            ResultSet rscard = findcard.executeQuery();
                            if (rscard.next()) {
                                System.err.println("Карта с таким номером уже существует!");
                                break;
                            } else {

                                System.out.print("Введите дату начала действия карты (в формате дд.мм.гггг): ");
                                String Start = scanner.next();
                                Date issueDate;
                                try {
                                    issueDate = dateFormat.parse(Start);
                                } catch (ParseException e) {
                                    System.out.println("Ошибка ввода даты начала действия карты: " + e.getMessage());
                                    continue;
                                }
                                System.out.print("Введите дату окончания действия карты (в формате дд.мм.гггг): ");
                                String finish = scanner.next();
                                Date expiryDate;
                                try {
                                    expiryDate = dateFormat.parse(finish);
                                }
                                catch (ParseException e) {
                                    System.out.println("Ошибка ввода даты начала действия карты: " + e.getMessage());
                                    continue;
                                }
                                BankCard newBankCard = new BankCard(clientNumber, cardNumber, issueDate, expiryDate);
                                cardsCache.put(Integer.valueOf(newBankCard.cardNumber), newBankCard);

                                }

                        }
                    }

                }

                else if (addClient.equalsIgnoreCase("Нет")) {
                    break;
                } else {
                    System.out.println("Некорректный ввод! Введите 'Да' или 'Нет'");
                }

            }



            for (Client client : clientCache.values()) {
                // Записываем клиента
                String insertClient = "INSERT INTO clients (client_number, full_name, date_of_birth, email) " +
                        "VALUES (?, ?, ?, ?);";
                PreparedStatement insertClientStmt = connection.prepareStatement(insertClient);
                insertClientStmt.setInt(1, client.clientNumber);
                insertClientStmt.setString(2, client.fullName);
                insertClientStmt.setDate(3, new java.sql.Date(client.dateOfBirth.getTime()));
                insertClientStmt.setString(4, client.email);
                insertClientStmt.executeUpdate();

                // Записываем карточки клиента
                for (BankCard bankCard : cardsCache.values()) {
                    if (bankCard.clientNumber == client.clientNumber) {
                        String insertCard = "INSERT INTO bank_cards (client_number, card_number, issue_date, expiry_date) " +
                                "VALUES (?, ?, ?, ?);";
                        PreparedStatement insertCardStmt = connection.prepareStatement(insertCard);
                        insertCardStmt.setInt(1, bankCard.clientNumber);
                        insertCardStmt.setInt(2, bankCard.cardNumber);
                        insertCardStmt.setDate(3, new java.sql.Date(bankCard.issueDate.getTime()));
                        insertCardStmt.setDate(4, new java.sql.Date(bankCard.expiryDate.getTime()));
                        insertCardStmt.executeUpdate();
                    }


                }

            }




            while (true) {
                System.out.println("Удалить карту клиента? (Да/Нет)");
                String addCard = scanner.next();
                if (addCard.equalsIgnoreCase("Да")) {
                    System.out.print("Введите номер карты для удаления: ");
                    int cardNull = scanner.nextInt();


                    String deleteCard = "DELETE FROM bank_cards WHERE card_number =? ";

                    PreparedStatement delet = connection.prepareStatement(deleteCard);
                    delet.setInt(1,cardNull);
                    int del = delet.executeUpdate();






                }
                else if (addCard.equalsIgnoreCase("Нет")) {
                    break;
                } else {
                    System.out.println("Некорректный ввод! Введите 'Да' или 'Нет'");
                }


            }



            scanner.close();
// Получение клиентов из кэша

    }

public  Map<Integer, Client> allclient()  {

    return (Map<Integer, Client>) jdbcTemplate.query("SELECT * FROM clients",new MainMapper());
}
public Map<Integer, BankCard> allcard(){
        return (Map<Integer, BankCard>)  jdbcTemplate.query("SELECT * FROM bank_cards", new MainMapperCard());
}
public Map<Integer, BankCard>  findcard(int cardNumber){
        return (Map<Integer, BankCard>) jdbcTemplate.query("SELECT * FROM bank_cards WHERE card_number=?",new Object[]{cardNumber},new MainMapperCard()).stream().findAny().orElse(null);

}


    private static void deleteCard(Connection connection, int cardNumber) throws SQLException {

        String sql = "DELETE FROM bank_cards WHERE card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cardNumber);
            statement.executeUpdate();
        }
    }
    
           





    private static void addDeletedClient(Connection connection, int clientNumber) throws SQLException {

        String sql = "INSERT INTO carddel (client_number) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientNumber);
            statement.executeUpdate();
        }
    }
    private static void createNewCard(Connection connection, int clientNumber) throws SQLException {
        String newcard = "INSERT INTO bank_cards (client_number, card_number, issue_date, expiry_date) VALUES (?, ?, ?, ?)";
        // Случайное число для номера карты
        String cardNumber = generateCardNumber();

        // Сегодняшняя дата для даты выдачи
        java.sql.Date issueDate = new java.sql.Date(System.currentTimeMillis());

        // Дата окончания через 5 лет
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        calendar.add(Calendar.YEAR, 5);
        java.sql.Date expirationDate = new java.sql.Date(calendar.getTimeInMillis());
        try (PreparedStatement stnewcard = connection.prepareStatement(newcard)) {
            stnewcard.setInt(1, clientNumber);
            stnewcard.setString(2, cardNumber);
            stnewcard.setDate(3, issueDate);
            stnewcard.setDate(4, expirationDate);
            stnewcard.executeUpdate();
        }


    }

    // Метод для генерации случайного номера карты
    private static String generateCardNumber() {
        // Генерация номера карты из 16 цифр
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int digit = (int) (Math.random() * 10);
            sb.append(digit);
        }
        return sb.toString();
    }
}