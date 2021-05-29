package br.com.bandtec.avaliacaorecuperacao3s.repository;

import br.com.bandtec.avaliacaorecuperacao3s.entity.TipoPassagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoPassagemRepository extends JpaRepository<TipoPassagem, Integer> {

    List<TipoPassagem> findByDescricao(String descricao);

}
