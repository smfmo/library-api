package com.samuel.libraryapi.repository;

import com.samuel.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    private final TransacaoService transacaoService;

    @Autowired
    public TransacoesTest(TransacaoService transacaoService) {
       this.transacaoService = transacaoService;
    }

    /**
     * commit -> Confirmar alterações
     * rollBack -> desfazer as alterações
     */
    @Test
    public void transacaoSimples() {
        transacaoService.executar();
    }

    @Test
    public void transacaoEstadoManaged(){
        transacaoService.ataulizacaoSemAtualizar();
    }
}
