package com.autocine.service.service;

import com.autocine.service.entity.UserCine;
import com.autocine.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private UserRepository usuarioRepository;

  private PasswordEncoder passwordEncoder;


  public boolean passwordMatches(String rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }

  @Autowired
  public void UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.usuarioRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserCine usuario = usuarioRepository.findByEmail(email);
    if (usuario == null) {
      throw new UsernameNotFoundException("Usuario no encontrado con el correo electrónico: " + email);
    }
    return new User(usuario.getUsername(), usuario.getPassword(), AuthorityUtils.createAuthorityList(usuario.getRole()));
  }



  public void registrarUsuario(UserCine usuario) {
    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    usuarioRepository.save(usuario);
  }
}
