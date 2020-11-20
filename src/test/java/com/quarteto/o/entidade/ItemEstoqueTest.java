package com.quarteto.o.entidade;

import com.quarteto.o.excecoes.SistVendasException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ItemEstoqueTest {

    private ItemEstoque itemEstoque;

    @Test
    void entradaDeveAceitarEntrada() {
        itemEstoque = new ItemEstoque(101, 100);

        itemEstoque.entrada(20);
        assertEquals(120, itemEstoque.getQuantidade());
    }

    @Test
    void entradaDeveRejeitarEntrada() {
        itemEstoque = new ItemEstoque(101, 100);

        assertThrows(SistVendasException.class, () -> itemEstoque.entrada(-1));
    }

    @Test
    void saidaDeveAceitarSaida() {
        itemEstoque = new ItemEstoque(101, 100);

        itemEstoque.saida(50);
        assertEquals(50, itemEstoque.getQuantidade());
    }

    @Test
    void saidaDeveRejeitarSaidaQuantidadeInvalida() {
        itemEstoque = new ItemEstoque(101, 100);

        assertThrows(SistVendasException.class, () -> itemEstoque.saida(-1));
    }

    @Test
    void saidaDeveRejeitarSaidaQuantidadeInsuficiente() {
        itemEstoque = new ItemEstoque(101, 100);

        assertThrows(SistVendasException.class, () -> itemEstoque.saida(101));
    }

    @ParameterizedTest
    @CsvSource({
            "10, true",
            "5, true",
            "11, false",
    })
    void disponivelQuantidadeValida(int quantidade, boolean disponivel) {
        itemEstoque = new ItemEstoque(101, 10);
        assertEquals(disponivel, itemEstoque.disponivel(quantidade));
    }

    @Test
    void disponivelDeveRetornarFalsoQuantidadeInvalida() {
        itemEstoque = new ItemEstoque(101, 10);
        assertThrows(SistVendasException.class, () -> itemEstoque.disponivel(-1));
    }
}