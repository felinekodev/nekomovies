package com.autocine.service.controller;

import com.autocine.service.entity.UserCine;
import com.autocine.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

  private UserService usuarioService;


  @Autowired
  public void setUserService(UserService userService) {
    this.usuarioService = userService;
  }

  @GetMapping("/register")
  public String mostrarFormularioRegistro(Model model) {
    model.addAttribute("usuario", new UserCine());
    return "register";
  }

  @PostMapping("/register")
  public String registrarUsuarioString(@ModelAttribute("usuario") UserCine usuario) {
    usuarioService.registrarUsuario(usuario);
    return "redirect:/login";
  }

  @GetMapping("/login")
  public String mostrarFormularioLogin() {
    return "login";
  }

  @GetMapping("/")
  public String listarPeliculas(Model model) {

    List<Link> routesNav = new ArrayList<>();

    routesNav.add(new Link("Incio", "/"));
    routesNav.add(new Link("Peliculas", "/movies"));
    routesNav.add(new Link("Promociones", "/promotions"));
    routesNav.add(new Link("Dulcerias", "/sweets"));
    routesNav.add(new Link("Login", "/login"));

    model.addAttribute("routesNav", routesNav);

    return "index";
  }

  @PostMapping("/login")
  public String procesarInicioSesion(@RequestParam String email, @RequestParam String password, Model model) {
    try {
      UserDetails userDetails = usuarioService.loadUserByUsername(email);

      if (userDetails != null && usuarioService.passwordMatches(password, userDetails.getPassword())) {
        // Usuario y contraseña válidos
        return "redirect:/";
      } else {
        // Usuario o contraseña incorrectos
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "login";
      }
    } catch (Exception e) {
      // Cualquier otro error
      model.addAttribute("error", "Ocurrió un error durante el inicio de sesión");
      return "login";
    }
  }

  public record Link(String name, String route) {
  }
}

