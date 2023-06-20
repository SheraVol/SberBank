package Mail;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Endcardml extends Thread {
        private Connection connection;
        private FileWriter writer;

        public  Endcardml(Connection connection, FileWriter writer){
        this.connection = connection;
        this.writer=writer;
}
public void run(){
        try {
                Statement stmt = connection.createStatement();
                String sql = "SELECT * FROM bank_cards WHERE expiry_date < NOW()";
                ResultSet resultSet = stmt.executeQuery(sql);
                while (resultSet.next()){
                        String cardnamb = resultSet.getString("card_number");
                        String sql1 = "SELECT * FROM clients WHERE client_number = " + resultSet.getInt("client_number");
                        Statement statement1 =connection.createStatement();
                        ResultSet redult = statement1.executeQuery(sql1);
                        while (redult.next()){
                                String name = redult.getString("full_name");
                                String email =redult.getString("email");

                        writer.write("(Было отправлено на: "+email+ ")"+ "Здравствуйте, " + name+ ", ваша карта с номером: " + cardnamb + " была заблокирована.");
                        System.out.println("Клиенту было отправлено сообщение");
                        }
                }
                writer.close();

        } catch (Exception e) {
                e.printStackTrace();
        }
}
}