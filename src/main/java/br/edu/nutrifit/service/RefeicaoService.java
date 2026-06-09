package br.edu.nutrifit.service;

import br.edu.nutrifit.model.Refeicao;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.repository.RefeicaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RefeicaoService {

    private final RefeicaoRepository repo;

    public RefeicaoService(RefeicaoRepository repo) {
        this.repo = repo;
    }

    public List<Refeicao> listarPorUsuario(Usuario usuario) {
        return repo.findByUsuarioOrderByNomeAsc(usuario);
    }

    public Refeicao buscarPorIdEUsuario(Long id, Usuario usuario) {
        return repo.findByIdAndUsuario(id, usuario).orElseThrow(() -> new RuntimeException("Refeição não encontrada"));
    }

    public Refeicao salvar(Refeicao refeicao, Usuario usuario) {
        refeicao.setUsuario(usuario);
        return repo.save(refeicao);
    }

    public void excluir(Long id, Usuario usuario) {
        Refeicao r = buscarPorIdEUsuario(id, usuario);
        repo.delete(r);
    }

    public int calcularTotalCalorias(List<Refeicao> refeicoes) {
        return refeicoes.stream().mapToInt(r -> r.getCalorias() != null ? r.getCalorias() : 0).sum();
    }
}
