package PokeApi.Programacion.Configuration;

import PokeApi.Programacion.DAO.UsuarioRepository;
import PokeApi.Programacion.JPA.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/pokedex", true)
                .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {

        return correo -> {

            Usuario usuario = usuarioRepository.findByCorreo(correo);

            if (usuario == null) {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }

            return org.springframework.security.core.userdetails.User
                    .withUsername(usuario.getCorreo())
                    .password(usuario.getPassword())
                    .disabled(usuario.getStatus() == 0)
                    .authorities(usuario.getRolUsuario())
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register", "/verify").permitAll()
                .anyRequest().authenticated()
                )
                .formLogin();

        return http.build();
    }
}
