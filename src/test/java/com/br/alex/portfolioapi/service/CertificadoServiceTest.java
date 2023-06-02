package com.br.alex.portfolioapi.service;

import com.br.alex.portfolioapi.exceptions.RegraNegocioException;
import com.br.alex.portfolioapi.model.dao.CertificadoRepository;
import com.br.alex.portfolioapi.model.entity.Certificado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("CertificadoServiceTest")
class CertificadoServiceTest {

    @Mock
    private CertificadoRepository repositoryMock;

    @InjectMocks
    private CertificadoService certificadoService;

    @Test
    @DisplayName("Deve validar a chamada do metodo findAll() ao buscar certificados")
    void buscarCertificadosTest() {
        certificadoService.buscarCertificados();

        verify(repositoryMock, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar URL válida e validar a chamada do metodo save()")
    void armazenarCertificadoUrlValidaTest() {
        Certificado certificado = configurarCertificado("spring_com_mockito", "testes", "alura");
        when(repositoryMock.save(certificado)).thenReturn(certificado);
        certificadoService.armazenarCertificado(certificado);

        verify(repositoryMock, times(1)).save(certificado);
        assertEquals("testes\\spring_com_mockito_alura.pdf", certificado.getUrlArquivo());
    }

    @Test
    @DisplayName("Deve retornar URL válida alterando espaco por underline e validar a chamada do metodo save()")
    void armazenarCertificadoTratarEspacosUrlTest() {
        Certificado certificado = configurarCertificado("spring com mockito", "testes", "alura");
        when(repositoryMock.save(certificado)).thenReturn(certificado);
        certificadoService.armazenarCertificado(certificado);

        verify(repositoryMock, times(1)).save(certificado);
        assertEquals("testes\\spring_com_mockito_alura.pdf", certificado.getUrlArquivo());
    }

    @Test
    @DisplayName("Deve lançar uma RegraNegocioException com mensagem: [Nome de certificado já registrado] e não chamar metodo save()")
    void armazenarCertificadoNomeJaCadastradoTest() {
        Certificado certificado = configurarCertificado("spring_com_mockito", "testes", "alura");
        when(repositoryMock.save(certificado)).thenReturn(certificado);
        when(repositoryMock.findByNome(certificado.getNome())).thenReturn(certificado);
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, ()-> {
            certificadoService.armazenarCertificado(certificado);
        });

        verify(repositoryMock, times(0)).save(certificado);
        assertEquals("Nome de certificado já registrado", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar uma RegraNegocioException com mensagem: [URL de certificado já registrada] e não chamar metodo save()")
    void armazenarCertificadoUrlJaCadastradaTest() {
        Certificado certificado = configurarCertificado("spring_com_mockito", "testes", "alura");
        when(repositoryMock.save(certificado)).thenReturn(certificado);
        when(repositoryMock.findByUrlArquivo(certificado.getUrlArquivo())).thenReturn(certificado);
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, ()-> {
            certificadoService.armazenarCertificado(certificado);
        });

        verify(repositoryMock, times(0)).save(certificado);
        assertEquals("URL de certificado já registrada", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar uma RegraNegocioException com mensagem: [URL de certificado já registrada] e não chamar metodo save()")
    void armazenarCertificadoBuscarCertificadoNaoCadastradoTest() {
        when(repositoryMock.findById(1)).thenThrow(RegraNegocioException.class);
        assertThrows(RegraNegocioException.class, ()-> {
            certificadoService.buscarCertificado(1);
        });

        verify(repositoryMock, times(1)).findById(1);
    }

    private Certificado configurarCertificado(String nome, String assunto, String emissor) {
        Certificado certificado = new Certificado();
        certificado.setNome(nome);
        certificado.setAssunto(assunto);
        certificado.setEmissorDoc(emissor);
        return certificado;
    }

}
