package PokeApi.Programacion.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoVerificacion(String destinatario, String token) {

        String enlace = "http://localhost:8080/verify?token=" + token;

        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject("🔥 Activa tu cuenta - PokéApp");

            String contenido = """
                <div style="font-family: Arial; text-align:center; padding:20px;">
                    <h2 style="color:#ff0000;">¡Bienvenido a PokéApp!</h2>
                    <p>Haz clic en el botón para activar tu cuenta:</p>
                    <a href="%s"
                       style="display:inline-block;
                              padding:12px 20px;
                              background-color:#ff0000;
                              color:white;
                              text-decoration:none;
                              border-radius:5px;">
                       Activar Cuenta
                    </a>
                    <p style="margin-top:20px; font-size:12px; color:gray;">
                       Este enlace expira en 15 minutos.
                    </p>
                </div>
                """.formatted(enlace);

            helper.setText(contenido, true);
            mailSender.send(mensaje);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}