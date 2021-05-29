package br.com.bandtec.avaliacaorecuperacao3s.repository;

import br.com.bandtec.avaliacaorecuperacao3s.entity.Passageiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PassageiroRepository extends JpaRepository<Passageiro, Integer> {

    List<Passageiro> findByCpf(String cpf);
}
