package PokeApi.Programacion.Controller;

import PokeApi.Programacion.DAO.RolDAO;
import PokeApi.Programacion.DAO.UsuarioDAO;
import PokeApi.Programacion.ML.Rol;
import PokeApi.Programacion.ML.Usuario;
import PokeApi.Programacion.Service.EmailService;
import PokeApi.Programacion.Service.JwtService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private RolDAO rolDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "Registro";
    }

    @PostMapping
    public String registrar(@ModelAttribute Usuario usuario,
                            RedirectAttributes redirectAttributes) {

        Optional<Usuario> usuarioExistente = usuarioDAO.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "El correo ya está registrado");
            return "redirect:/registro";
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        usuario.setEnabled(0);

        Rol rolUser = rolDAO.findByNombre("ROLE_USER"); // Usa el nombre exacto en tu BD
        usuario.getRoles().add(rolUser); // 🔥 NO usar Set.of()

        usuarioDAO.save(usuario);

        String token = jwtService.generarTokenVerificacion(usuario.getEmail());

        emailService.enviarCorreoVerificacion(usuario.getEmail(), token);

        redirectAttributes.addFlashAttribute("success",
                "Cuenta creada. Revisa tu correo para activarla.");

        return "redirect:/Login";
    }
}
