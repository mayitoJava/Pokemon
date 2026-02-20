package PokeApi.Programacion.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import PokeApi.Programacion.JPA.Usuario;
import PokeApi.Programacion.Service.UsuarioService;

@RestController
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public String register(@RequestBody Usuario usuario) {

        usuarioService.register(usuario);
        return "Revisa tu correo para verificar la cuenta";
    }

    @GetMapping("/verify")
    public String verify(@RequestParam String token) {

        return usuarioService.verify(token);
    }
}
