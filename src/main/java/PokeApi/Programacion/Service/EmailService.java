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
        <div style="font-family: 'Trebuchet MS', Arial, sans-serif;
                    background: linear-gradient(135deg, #ff1a1a, #b30000);
                    padding:40px;
                    text-align:center;">

            <div style="background:white;
                        max-width:500px;
                        margin:auto;
                        padding:30px;
                        border-radius:15px;
                        box-shadow:0 0 20px rgba(0,0,0,0.4);
                        border:6px solid #000;">

                <h1 style="color:#ff0000;
                           margin-bottom:10px;
                           font-size:28px;">
                    ⚡ ¡Bienvenido Entrenador! ⚡
                </h1>

                <h2 style="color:#000; margin-top:0;">
                    Tu aventura en PokéApp está por comenzar
                </h2>

                <p style="font-size:16px; color:#333;">
                    Presiona el botón para activar tu PokéCuenta
                    y comenzar tu viaje.
                </p>

                <a href="%s"
                   style="display:inline-block;
                          margin-top:20px;
                          padding:15px 25px;
                          background: linear-gradient(180deg, #ff0000, #cc0000);
                          color:white;
                          font-weight:bold;
                          text-decoration:none;
                          border-radius:50px;
                          border:3px solid black;
                          box-shadow:0 5px 0 #660000;
                          font-size:16px;">
                   🔴 Activar Cuenta
                </a>

                <p style="margin-top:25px;
                          font-size:13px;
                          color:#555;">
                   ⏳ Este enlace expirará en 15 minutos.<br>
                   ¡No dejes que se escape como un Pokémon legendario!
                </p>

            </div>

        </div>
        """.formatted(enlace);

            helper.setText(contenido, true);
            mailSender.send(mensaje);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
