package PokeApi.Programacion.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import PokeApi.Programacion.DAO.*;
import PokeApi.Programacion.JPA.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public void register(Usuario usuario) {

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setStatus(0); // no verificado
        usuario.setRolUsuario("USER");

        usuarioRepository.save(usuario);

        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUsuario(usuario);
        verificationToken.setExpirationDate(LocalDateTime.now().plusHours(24));

        tokenRepository.save(verificationToken);

        String link = "http://localhost:8080/verify?token=" + token;

        emailService.send(usuario.getCorreo(), link);
    }

    public String verify(String token) {

        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if (verificationToken == null) {
            return "Token inválido";
        }

        if (verificationToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            return "Token expirado";
        }

        Usuario usuario = verificationToken.getUsuario();
        usuario.setStatus(1); // activar cuenta

        usuarioRepository.save(usuario);

        return "Cuenta verificada correctamente";
    }
}
