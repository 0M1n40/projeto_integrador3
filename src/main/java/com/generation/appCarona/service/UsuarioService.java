package com.generation.appCarona.service;

//responsável por manipular as regras de negócio de usuário no sistema.
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.appCarona.model.Usuario;
import com.generation.appCarona.model.UsuarioLogin;
import com.generation.appCarona.repository.UsuarioRepository;
import com.generation.appCarona.security.JwtService;




@Service //classe de serviço
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;

	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();

		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		return Optional.of(usuarioRepository.save(usuario));
	
	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		
		if(usuarioRepository.findById(usuario.getId()).isPresent()) {

			Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

			if ( (buscaUsuario.isPresent()) && ( buscaUsuario.get().getId() != usuario.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

			usuario.setSenha(criptografarSenha(usuario.getSenha()));

			return Optional.ofNullable(usuarioRepository.save(usuario));
			
		}

		return Optional.empty();
	
	}	

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {
      
      // gera o obj de autenticação
		var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha());
		
      // autentica o usuario
		Authentication authentication = authenticationManager.authenticate(credenciais);
      
      // se a autenticação foi efetuada com sucesso
		if (authentication.isAuthenticated()) {

          // busca os dados do usuario
			Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

          // se o usuario foi encontrado
			if (usuario.isPresent()) {

              // preenche o obj usuarioLogin com os dados encontrados 
			   usuarioLogin.get().setId(usuario.get().getId());
              usuarioLogin.get().setNome(usuario.get().getNome());
              usuarioLogin.get().setFoto(usuario.get().getFoto());
              usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario()));
              usuarioLogin.get().setSenha("");
				
               // Retorna o obj preenchido
			   return usuarioLogin;
			
			}

      } 
          
		return Optional.empty();

  }

	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);

	}

	private String gerarToken(String usuario) {
		return "Bearer " + jwtService.generateToken(usuario);
	}

}