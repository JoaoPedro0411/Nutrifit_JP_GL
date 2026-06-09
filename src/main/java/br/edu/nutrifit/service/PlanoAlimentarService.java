package br.edu.nutrifit.service;

import br.edu.nutrifit.model.PlanoAlimentar;
import br.edu.nutrifit.model.Refeicao;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.repository.PlanoAlimentarRepository;
import br.edu.nutrifit.repository.RefeicaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanoAlimentarService {

    private final PlanoAlimentarRepository repo;
    private final RefeicaoRepository refeicaoRepo;

    public PlanoAlimentarService(PlanoAlimentarRepository repo, RefeicaoRepository refeicaoRepo) {
        this.repo = repo;
        this.refeicaoRepo = refeicaoRepo;
    }

    public List<PlanoAlimentar> listarPorUsuario(Usuario usuario) {
        return repo.findByUsuarioOrderByDataInicioDesc(usuario);
    }

    public PlanoAlimentar buscarPorIdEUsuario(Long id, Usuario usuario) {
        return repo.findByIdAndUsuario(id, usuario).orElseThrow(() -> new RuntimeException("Plano não encontrado"));
    }

    public PlanoAlimentar salvar(PlanoAlimentar plano, Usuario usuario, List<Long> refeicoesIds) {
        plano.setUsuario(usuario);
        validarDatas(plano);
        plano.setRefeicoes(carregarRefeicoesDoUsuario(refeicoesIds, usuario));
        return repo.save(plano);
    }

    public void excluir(Long id, Usuario usuario) {
        PlanoAlimentar p = buscarPorIdEUsuario(id, usuario);
        repo.delete(p);
    }

    public void validarDatas(PlanoAlimentar plano) {
        if (plano.getDataFim() != null && plano.getDataFim().isBefore(plano.getDataInicio())) {
            throw new RuntimeException("Data fim não pode ser anterior à data início");
        }
    }

    public List<Refeicao> carregarRefeicoesDoUsuario(List<Long> ids, Usuario usuario) {
        if (ids == null) return new ArrayList<>();
        return ids.stream()
                .map(id -> refeicaoRepo.findByIdAndUsuario(id, usuario).orElseThrow(() -> new RuntimeException("Refeição não encontrada ou não pertence ao usuário")))
                .collect(Collectors.toList());
    }
}
