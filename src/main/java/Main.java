import lk.ijse.lavishStyloo.util.EmailUtil;
import lk.ijse.lavishStyloo.util.MailUtil;

import javax.mail.Session;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/24/2023
 */

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String body="<h1 style=\"font-size: 50px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; color: rgb(43, 180, 226);text-align: center;\">Lavish Stylo</h1>\n" +
                "               <p style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;\">\n" +
                "                 Lavish styloo is a luxury salon located in Galle.we provide you various treatments from head to toe using thebest product,advanced and affordable price</p>\n" +
                "               \n" +
                "               <h1 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: rgb(57, 53, 53); font-size: 20px; margin-top: 40px; margin-left: 30px;\">opens at 8.00 am</h1>\n" +
                "               <h1 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: rgb(57, 53, 53); font-size: 20px; margin-left: 30px;\">close at 8.00 pm</h1>\n" +
                "               <h3 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: #545252; margin-top: 40px;\">Bookings for treatments during the day can only be made between 8.00 am to 10.am. </h1>\n" +
                "               <h3 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: #545252;\">For later days, the salon is open that bookings can be made at any time</h4>\n" +
                "                \n" +
                "                              <h1 style=\"font-size: 50px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; color: rgb(17, 45, 54);text-align: center;\">THANK YOU</h1>\n" +
                "\n" ;

        MailUtil.sendEmail("prabodhathathsarani28@gmail.com","Lavish Stylo",body,"B001");
    }
}
