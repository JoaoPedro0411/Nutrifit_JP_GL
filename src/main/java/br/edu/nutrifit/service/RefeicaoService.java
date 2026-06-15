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

    private final RefeicaoRepository repository;

    public RefeicaoService(RefeicaoRepository repository) {
        this.repository = repository;
    }

    public List<Refeicao> listarPorUsuario(Usuario usuario) {
        return repository.findByUsuarioOrderByNomeAsc(usuario);
    }

    public Refeicao buscarPorIdEUsuario(Long id, Usuario usuario) {
        return repository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Refeição não encontrada."));
    }

    public Refeicao salvar(Refeicao refeicao, Usuario usuario) {
        refeicao.setUsuario(usuario);
        return repository.save(refeicao);
    }

    public void excluir(Long id, Usuario usuario) {
        Refeicao refeicao = buscarPorIdEUsuario(id, usuario);
        repository.delete(refeicao);
    }

    public int calcularTotalCalorias(List<Refeicao> refeicoes) {
        int total = 0;

        for (Refeicao refeicao : refeicoes) {
            if (refeicao.getCalorias() != null) {
                total += refeicao.getCalorias();
            }
        }

        return total;
    }
}
