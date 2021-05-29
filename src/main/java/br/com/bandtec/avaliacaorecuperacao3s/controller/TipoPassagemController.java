package br.com.bandtec.avaliacaorecuperacao3s.controller;


import br.com.bandtec.avaliacaorecuperacao3s.entity.TipoPassagem;
import br.com.bandtec.avaliacaorecuperacao3s.repository.TipoPassagemRepository;
import org.hibernate.boot.jaxb.hbm.spi.TableInformationContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipo-passagem")
public class TipoPassagemController {

    @Autowired
    private TipoPassagemRepository tipoPassagemRepository;

    @GetMapping
    public ResponseEntity getTipoPassagem(){
        List<TipoPassagem> tipoPassagems = tipoPassagemRepository.findAll();

        return tipoPassagems.isEmpty() ? ResponseEntity.status(404).build() :
                ResponseEntity.status(200).body(tipoPassagems);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTipoPassagemById(@PathVariable Integer id){
        Optional<TipoPassagem> tipoPassagem = tipoPassagemRepository.findById(id);

        return tipoPassagem.isPresent() ? ResponseEntity.status(200).body(tipoPassagem) :
                ResponseEntity.status(404).build();
    }

    @PostMapping
    public ResponseEntity postTipoPassagem(@Valid @RequestBody TipoPassagem tipoPassagem){

        List<TipoPassagem> tipoPassagemList = tipoPassagemRepository.findAll();
        boolean igual = false;
        for (int i = 0; i < tipoPassagemList.size(); i++) {
            TipoPassagem tipoPassagemDescricao = tipoPassagemList.get(i);
            if (tipoPassagemDescricao.getDescricao().equals(tipoPassagem.getDescricao())) {
                igual = true;
            }else {
                igual = false;
            }
        }
        if (igual) {
            return ResponseEntity.status(404).body("Este tipo de passagem já está cadastrado!");
        }else {
            return ResponseEntity.status(201).body(tipoPassagemRepository.save(tipoPassagem));
        }
    }

}
