package br.com.bandtec.avaliacaorecuperacao3s.controller;

import br.com.bandtec.avaliacaorecuperacao3s.entity.Passageiro;
import br.com.bandtec.avaliacaorecuperacao3s.modelo.Cpf;
import br.com.bandtec.avaliacaorecuperacao3s.repository.CpfApi;
import br.com.bandtec.avaliacaorecuperacao3s.repository.PassageiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bilhete-unico")
public class PassageiroController {

    @Autowired
    private CpfApi cpfApi;

    @Autowired
    private PassageiroRepository passageiroRepository;

    @GetMapping
    public ResponseEntity getPassgeiros() throws IOException {
//        Cpf cpf = cpfApi.getCpf("Bearer zy4AjWuzeifGAGpTZHPom4FY78mKlNNP5H7WOGcS");

        List<Passageiro> passageiroList = passageiroRepository.findAll();

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(HttpHeaders.ACCEPT, "application/json");
//        httpHeaders.add(HttpHeaders.CONTENT_TYPE,"application/json");
//        httpHeaders.add(HttpHeaders.AUTHORIZATION,"Bearer zy4AjWuzeifGAGpTZHPom4FY78mKlNNP5H7WOGcS");

        return ResponseEntity
                .ok()
//                .headers(httpHeaders)
                .body(passageiroList);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPassageiroId(@PathVariable int id){
        Optional<Passageiro> passageiro = passageiroRepository.findById(id);

        return passageiro.isPresent() ?
                ResponseEntity.status(200).body(passageiro) :
                ResponseEntity.status(404).build();
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity getPassageiroByCpf(@PathVariable String cpf){
        return ResponseEntity.status(200).body(passageiroRepository.findByCpf(cpf));
    }


    @PostMapping
    public ResponseEntity postPassageiro(@Valid @RequestBody Passageiro passageiro){
//        Cpf cpf = cpfApi.getCpf("Bearer zy4AjWuzeifGAGpTZHPom4FY78mKlNNP5H7WOGcS");

        List<Passageiro> passageiroQuantidade = passageiroRepository.findByCpf(passageiro.getCpf());

        if (passageiroQuantidade.size() >= 2) {
            return ResponseEntity.status(404).body("Este CPF já tem 2 BUs!");
        }else {
            passageiroRepository.save(passageiro);
            return ResponseEntity.status(201).build();
        }

    }

    @PostMapping("/{id}/recarga/{valorRecarga}")
    public ResponseEntity novaRecarga(@PathVariable Integer id, @PathVariable Double valorRecarga){

        Optional<Passageiro> passageiro = passageiroRepository.findById(id);
        if (!passageiro.isPresent()) {
            return ResponseEntity.status(404).build();
        }else{
            if (valorRecarga <= 1.00) {
                return ResponseEntity.status(400).body("Valor da recarga deve ser a partir de R$1.00");
            }
            if ((passageiro.get().getSaldo()+valorRecarga) > 230.00) {
                double valorRestante = (230.00-passageiro.get().getSaldo());
                return ResponseEntity
                        .status(400)
                        .body("Recarga não efetuada! Passaria do limite de R$230,00. Você ainda pode carregar até R$"+valorRestante);
            }
            passageiro.map(passageiroTemp -> {
                passageiroTemp.setPassageiro(passageiro.get().getPassageiro());
                passageiroTemp.setNascimento(passageiro.get().getNascimento());
                passageiroTemp.setCpf(passageiro.get().getCpf());
                passageiroTemp.setSaldo(passageiro.get().getSaldo()+valorRecarga);
                passageiroRepository.save(passageiroTemp);
                return "Recarga efetuada";
            });
        }

        return ResponseEntity.status(204).build();
    }

//    @PostMapping("/{id}/passagem/{idTipo}")
//    public ResponseEntity postPassgem(){}




}
