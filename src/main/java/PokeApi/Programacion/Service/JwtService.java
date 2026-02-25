package PokeApi.Programacion.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service // Servicio administrado por Spring
public class JwtService {

    @Value("${jwt.secret}")
    // Clave secreta definida en application.properties
    private String secret;

    @Value("${jwt.expiration}")
    // Tiempo de expiración del token en milisegundos
    private long expiration;

    // Clave criptográfica usada para firmar y validar el token
    private Key key;

    @PostConstruct
    // Se ejecuta al iniciar el servicio
    // Convierte el String secret en una Key válida para HS256
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Genera un JWT usando el email como identificador principal
    public String generarTokenVerificacion(String email) {

        return Jwts.builder()
                .setSubject(email) // Guarda el email dentro del token
                .setIssuedAt(new Date()) // Fecha de creación
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256) // Firma con la clave secreta
                .compact(); // Devuelve el token como String
    }

    // Valida el token recibido
    public String validarToken(String token) {

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key) // Usa la misma clave para validar firma
                    .build()
                    .parseClaimsJws(token) // Verifica firma y expiración
                    .getBody();

            return claims.getSubject(); // Devuelve el email si es válido

        } catch (JwtException e) {
            return null; // Retorna null si el token es inválido o expiró
        }
    }
}
