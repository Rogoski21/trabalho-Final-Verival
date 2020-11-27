package com.quarteto.o.servico;

import com.quarteto.o.entidade.ItemEstoque;
import com.quarteto.o.entidade.ItemVenda;
import com.quarteto.o.entidade.Produto;
import com.quarteto.o.excecoes.SistVendasException;
import com.quarteto.o.repositorio.Estoque;
import com.quarteto.o.repositorio.Produtos;
import com.quarteto.o.servico.imposto.RegraImposto;
import com.quarteto.o.servico.imposto.RegraImpostoOriginal;
import com.quarteto.o.servico.validacao.FactoryValidacao;
import com.quarteto.o.servico.validacao.RegraValidacao;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicoDeVendasIT {
    private FactoryValidacao factoryValidacao;
    private ServicoDeVendas servicoDeVendas;
    private RegraImposto regraImposto;
    private Produtos produtos;
    private Estoque estoque;

    @BeforeEach
    void setUp() {
        produtos = mock(Produtos.class);
        estoque = mock(Estoque.class);
    }

    private List<ItemVenda> criaLista() {
        List<ItemVenda> lista = new ArrayList<>();

        ItemVenda p1 = new ItemVenda(1, 10, 1, 2);
        ItemVenda p2 = new ItemVenda(1, 30, 1, 3);
        ItemVenda p3 = new ItemVenda(1, 50, 2, 4);

        lista.add(p1);
        lista.add(p2);
        lista.add(p3);

        return lista;
    }

    @Test
    @DisplayName("Valida - garante integracao de classes do metodo valida horario comercial")
    void validaIntegracaoDeClassesDoMetodoValidaHorarioComercial() {
        LocalTime agora = LocalTime.of(9, 0);
        factoryValidacao = new FactoryValidacao(agora);

        regraImposto = new RegraImpostoOriginal();
        servicoDeVendas = new ServicoDeVendas(produtos, estoque, regraImposto, factoryValidacao);

        when(produtos.recupera(10)).thenReturn(new Produto(10, "Prod10", 1000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30, "Prod30", 2000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50, "Prod15", 1500.0));

        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50, 1));

        List<ItemVenda> lista = criaLista();

        assertThrows(SistVendasException.class, () -> servicoDeVendas.valida(lista));
    }

    @Test
    @DisplayName("Valida - garante integracao de classes do metodo valida fora do horario comercial")
    void validaIntegracaoDeClassesDoMetodoValidaForaDoHorarioComercial() {
        LocalTime agora = LocalTime.of(21, 0);
        factoryValidacao = new FactoryValidacao(agora);

        regraImposto = new RegraImpostoOriginal();
        servicoDeVendas = new ServicoDeVendas(produtos, estoque, regraImposto, factoryValidacao);

        when(produtos.recupera(10)).thenReturn(new Produto(10, "Prod10", 1000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30, "Prod30", 2000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50, "Prod15", 1500.0));

        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50, 1));

        List<ItemVenda> lista = criaLista();

        assertThrows(SistVendasException.class, () -> servicoDeVendas.valida(lista));
    }

    @Test
    @DisplayName("valida - garante que o imposto sera calculado para a lista")
    void validaIntegridadeDoCalculoDoImpostoDaLista() {
        LocalTime agora = LocalTime.of(10, 0);
        factoryValidacao = new FactoryValidacao(agora);
        regraImposto = new RegraImpostoOriginal();
        servicoDeVendas = new ServicoDeVendas(produtos, estoque, regraImposto, factoryValidacao);

        List<ItemVenda> lista = criaLista();

        Integer valorImposto = servicoDeVendas.calculaImpostos(lista);

        assertEquals(1, valorImposto);

    }
}