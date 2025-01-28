package allegro.agh.login_service.service;

import allegro.agh.login_service.database.user.dto.UserDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private final JavaMailSender javaMailSender;

  public static final String EMAIL_ADDRESS = "dragonglassdetailing@gmail.com";
  public static final String COMPANY_NAME = "Dragonglass Detailing";
  public static final String REGISTER_SUBJECT = "Witaj w Dragonglass Detailing";

  public EmailService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  public void sendRegisterConfirmationEmail(UserDto userDto) {

    String emailBody =
        String.format(
            """
                            Witaj %s %s,

                            Potwierdzenie rejestracji w %s.

                            Dziękujemy za rejestrację na naszej stronie.

                            Pozdrawiamy,
                            Zespół %s""",
            userDto.firstName(), userDto.lastName(), COMPANY_NAME, COMPANY_NAME);

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(String.format("%s <%s>", COMPANY_NAME, EMAIL_ADDRESS));
    message.setTo(userDto.email());
    message.setSubject(REGISTER_SUBJECT);
    message.setText(emailBody);
    javaMailSender.send(message);
  }
}
