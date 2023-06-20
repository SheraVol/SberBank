package org.example;

import model.BankCard;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;


public class Regi extends JDialog{
    private static final String url = "jdbc:mysql://localhost:3306/clientbankcard/clients?serverTimezone=Europe/Moscow";
    private static final String user = "root";
    private static final String password = "OkaRuto24!";
    private JPanel Panel;
    private JTable allCard;
    private JTable allClient;
    private JTextField datebef;
    private JTextField numberCard;
    private JTextField dataold;
    private JTextField Numberclient;
    private JButton delcard;
    private JButton addNewCard;
    private JTextField Number;
    private JTextField FIO;
    private JTextField Date;
    private JTextField mail;
    private JButton registr;

    public Regi(){

        setMaximumSize(new Dimension(450,475));
        setModal(true);

        setContentPane(Panel);
        setVisible(true);
}public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Regi().setVisible(true);
            }
        });
        Regi regi = new Regi();
    }
    private void table(){

    }
    private void createUIComponents(java.awt.event.ActionEvent evt) {
        Map<Integer, Client> clientCache = new HashMap<>();

        Map<Integer, BankCard> cardsCache = new HashMap<>();

        Date dateOfBirth= java.sql.Date.valueOf(Date.getText());
         int cardNumber =Integer.parseInt(numberCard.getText());
        Date expiryDate =java.sql.Date.valueOf(dataold.getText());
       int clientNumber= Integer.parseInt(Numberclient.getText());
       int clientNumbers =Integer.parseInt(Number.getText());
       String fullName= FIO.getText();
       Date issueDate=java.sql.Date.valueOf( datebef.getText());
       String email= mail.getText();
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

                // Записываем клиента
                String insertClient = "INSERT INTO clients (client_number, full_name, date_of_birth, email) " +
                        "VALUES (?, ?, ?, ?);";
                PreparedStatement insertClientStmt = connection.prepareStatement(insertClient);
                insertClientStmt.setInt(1,  clientNumbers);
                insertClientStmt.setString(2, fullName);
                insertClientStmt.setDate(3, dateOfBirth);
                insertClientStmt.setString(4, email);
                insertClientStmt.executeUpdate();

                // Записываем карточки клиента


                        String insertCard = "INSERT INTO bank_cards (client_number, card_number, issue_date, expiry_date) " +
                                "VALUES (?, ?, ?, ?);";
                        PreparedStatement insertCardStmt = connection.prepareStatement(insertCard);
                        insertCardStmt.setInt(1, clientNumber);
                        insertCardStmt.setInt(2, cardNumber);
                        insertCardStmt.setDate(3, issueDate);
                        insertCardStmt.setDate(4, expiryDate);
                        insertCardStmt.executeUpdate();





        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



}
