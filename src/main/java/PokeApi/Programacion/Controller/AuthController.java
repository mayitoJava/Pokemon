package PokeApi.Programacion.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/Login")
    public String login() {
        return "Login";
    }

   @GetMapping("/registro")
    public String mostrarRegistro() {
        return "Registro";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Index";
    }
}