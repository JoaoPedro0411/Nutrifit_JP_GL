package br.edu.nutrifit.service;

import br.edu.nutrifit.model.Treino;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.repository.TreinoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TreinoService {

    private final TreinoRepository repo;

    public TreinoService(TreinoRepository repo) {
        this.repo = repo;
    }

    public List<Treino> listarPorUsuario(Usuario usuario) {
        return repo.findByUsuarioOrderByNomeAsc(usuario);
    }

    public Treino buscarPorIdEUsuario(Long id, Usuario usuario) {
        return repo.findByIdAndUsuario(id, usuario).orElseThrow(() -> new RuntimeException("Treino não encontrado"));
    }

    public Treino salvar(Treino treino, Usuario usuario) {
        treino.setUsuario(usuario);
        // validarTreino could be added here
        return repo.save(treino);
    }

    public void excluir(Long id, Usuario usuario) {
        Treino t = buscarPorIdEUsuario(id, usuario);
        repo.delete(t);
    }
}
