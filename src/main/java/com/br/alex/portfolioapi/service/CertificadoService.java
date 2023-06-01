package com.br.alex.portfolioapi.service;

import com.br.alex.portfolioapi.exceptions.RegraNegocioException;
import com.br.alex.portfolioapi.model.dao.CertificadoRepository;
import com.br.alex.portfolioapi.model.entity.Certificado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static com.br.alex.portfolioapi.constantes.Constantes.*;

@Service
public class CertificadoService {

    @Autowired
    private CertificadoRepository certificadoRepository;

    @Transactional
    public Certificado armazenarCertificado(Certificado certificado) {
        validarNomeCertificadoExistente(certificado);
        validarUrlCertificadoExistente(certificado);
        configurarUrlArquivo(certificado);
        return certificadoRepository.save(certificado);
    }

    public List<Certificado> buscarCertificados() {
        return certificadoRepository.findAll();
    }

    public Certificado buscarCertificado(Integer id) {
        return certificadoRepository.findById(id)
                .orElseThrow(()->new RegraNegocioException("Certificado com id: [ " + id + " ] não encontrado"));
    }

    private void configurarUrlArquivo(Certificado certificado) {
        StringBuilder urlArquivo = new StringBuilder();
        urlArquivo.append(certificado.getAssunto());
        urlArquivo.append(SEPARADOR_BARRA);
        urlArquivo.append(certificado.getNome());
        urlArquivo.append(SEPARADOR_UNDERLINE);
        urlArquivo.append(certificado.getEmissorDoc());
        urlArquivo.append(EXTENSAO_PDF);
        String urlArquivoFormatada = urlArquivo.toString().replaceAll("\\s", SEPARADOR_UNDERLINE);
        certificado.setUrlArquivo(urlArquivoFormatada);
    }

    private void validarNomeCertificadoExistente(Certificado certificado) {
        Certificado certificadoDB = certificadoRepository.findByNome(certificado.getNome());
        if (certificadoDB != null) {
            throw new RegraNegocioException("Nome de certificado já registrado");
        }
    }

    private void validarUrlCertificadoExistente(Certificado certificado) {
        if (certificadoRepository.findByUrlArquivo(certificado.getUrlArquivo()) != null) {
            throw new RegraNegocioException("URL de certificado já registrada");
        }
    }

}
