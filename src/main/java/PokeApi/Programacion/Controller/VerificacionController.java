package PokeApi.Programacion.Controller;

import PokeApi.Programacion.DAO.UsuarioDAO;
import PokeApi.Programacion.ML.Usuario;
import PokeApi.Programacion.Service.JwtService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VerificacionController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping("/verify")
    public String verificarCuenta(@RequestParam("token") String token,
                                  RedirectAttributes redirectAttributes) {

        try {
            // 1️⃣ Obtener email del token
            String email = jwtService.validarToken(token);

            // 2️⃣ Buscar usuario
            Optional<Usuario> usuarioOpt = usuarioDAO.findByEmail(email);

            if (usuarioOpt.isPresent()) {

                Usuario usuario = usuarioOpt.get();

                // 3️⃣ Activar usuario
                usuario.setEnabled(1);

                // 4️⃣ Guardar cambios
                usuarioDAO.save(usuario);

                redirectAttributes.addFlashAttribute("success",
                        "Cuenta verificada correctamente. Ya puedes iniciar sesión.");

                return "redirect:/Login";
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Token inválido o expirado.");
        }

        return "redirect:/Login";
    }
}