
package com.example.backend.utils;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtils {

    // Phương thức gửi email
    public static void sendOtpEmail(String toEmail, String otp) {
        String fromEmail = "20130238@st.hcmuaf.edu.vn"; // Địa chỉ email gửi
        String emailPassword = "shei ceex gngf cfec"; // Mật khẩu email của bạn

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, emailPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Mã OTP của bạn");
            message.setText("Mã OTP của bạn là: " + otp);

            Transport.send(message);
            System.out.println("OTP đã được gửi thành công đến: " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        sendOtpEmail("nhutdny123@gmail.com","123");
    }
}
