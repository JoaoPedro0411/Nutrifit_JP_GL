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

    private final TreinoRepository repository;

    public TreinoService(TreinoRepository repository) {
        this.repository = repository;
    }

    public List<Treino> listarPorUsuario(Usuario usuario) {
        return repository.findByUsuarioOrderByNomeAsc(usuario);
    }

    public Treino buscarPorIdEUsuario(Long id, Usuario usuario) {
        return repository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado."));
    }

    public Treino salvar(Treino treino, Usuario usuario) {
        treino.setUsuario(usuario);
        return repository.save(treino);
    }

    public void excluir(Long id, Usuario usuario) {
        Treino treino = buscarPorIdEUsuario(id, usuario);
        repository.delete(treino);
    }
}
