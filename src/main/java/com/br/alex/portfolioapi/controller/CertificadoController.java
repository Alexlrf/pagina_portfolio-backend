package com.br.alex.portfolioapi.controller;

import com.br.alex.portfolioapi.model.dto.CertificadoInput;
import com.br.alex.portfolioapi.model.entity.Certificado;
import com.br.alex.portfolioapi.service.CertificadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/siteportfolio/certificados")
public class CertificadoController {

    @Autowired
    CertificadoService certificadoService;

    @GetMapping(value="/teste")
    public String teste(){
        return "Teste certificado Ok!";
    }

    @PostMapping
    public ResponseEntity<Certificado> armazenarCertificado(@RequestBody @Valid CertificadoInput certificadoRequest){
        Certificado certificado = new Certificado();
        BeanUtils.copyProperties(certificadoRequest, certificado);
        Certificado certificadoResponse = certificadoService.armazenarCertificado(certificado);
        return new ResponseEntity<>(certificadoResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Certificado>> buscarCertificados() {
        return new ResponseEntity<>(certificadoService.buscarCertificados(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Certificado> buscarCertificado(@PathVariable Integer id) {
        return new ResponseEntity<>(certificadoService.buscarCertificado(id), HttpStatus.OK);
    }

}
