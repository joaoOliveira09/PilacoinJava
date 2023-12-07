package ufsm.csi.pilacoin.controller;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;

import ufsm.csi.pilacoin.model.PilaCoin;
import ufsm.csi.pilacoin.model.Usuario;
import ufsm.csi.pilacoin.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

     @GetMapping()
    public ArrayList<Usuario> getUsuarios() {
        return (ArrayList<Usuario>) this.usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Usuario getUsuario(@PathVariable("id") Long id) {
        return this.usuarioRepository.findById(id).get();
    }
    
}
