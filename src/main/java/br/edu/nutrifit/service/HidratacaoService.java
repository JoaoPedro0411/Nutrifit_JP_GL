package br.edu.nutrifit.service;

import br.edu.nutrifit.model.Hidratacao;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.repository.HidratacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class HidratacaoService {

    private final HidratacaoRepository repo;

    public HidratacaoService(HidratacaoRepository repo) {
        this.repo = repo;
    }

    public List<Hidratacao> listarPorUsuario(Usuario usuario) {
        return repo.findByUsuarioOrderByDataDesc(usuario);
    }

    public Hidratacao buscarPorIdEUsuario(Long id, Usuario usuario) {
        return repo.findByIdAndUsuario(id, usuario).orElseThrow(() -> new RuntimeException("Registro de hidratação não encontrado"));
    }

    public Hidratacao buscarPorData(Usuario usuario, LocalDate data) {
        return repo.findByUsuarioAndData(usuario, data).orElse(null);
    }

    public Hidratacao buscarHoje(Usuario usuario) {
        return buscarPorData(usuario, LocalDate.now());
    }

    public Hidratacao salvar(Hidratacao h, Usuario usuario) {
        if (repo.existsByUsuarioAndDataAndIdNot(usuario, h.getData(), h.getId() == null ? -1L : h.getId())) {
            throw new RuntimeException("Já existe um registro para essa data");
        }
        h.setUsuario(usuario);
        return repo.save(h);
    }

    public Hidratacao registrarConsumoHoje(Usuario usuario, Integer consumidoMl, Integer metaMl) {
        LocalDate hoje = LocalDate.now();
        Hidratacao h = repo.findByUsuarioAndData(usuario, hoje).orElse(new Hidratacao());
        h.setUsuario(usuario);
        h.setData(hoje);
        if (metaMl != null) h.setMetaMl(metaMl);
        if (consumidoMl != null) h.setConsumidoMl(consumidoMl);
        return repo.save(h);
    }

    public void excluir(Long id, Usuario usuario) {
        Hidratacao h = buscarPorIdEUsuario(id, usuario);
        repo.delete(h);
    }
}
