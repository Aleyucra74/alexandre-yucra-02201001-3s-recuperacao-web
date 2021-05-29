package br.com.bandtec.avaliacaorecuperacao3s.repository;

import br.com.bandtec.avaliacaorecuperacao3s.modelo.Cpf;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "apicpf", url = "https://gerador.app/api/cpf/generate")
public interface CpfApi {

    @GetMapping("/")
    Cpf getCpf(@RequestHeader("Authorization") String auth);

}