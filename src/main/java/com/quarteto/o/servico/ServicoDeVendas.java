package com.quarteto.o.servico;

import com.quarteto.o.entidade.ItemVenda;
import com.quarteto.o.servico.imposto.RegraImposto;
import com.quarteto.o.servico.validacao.FactoryValidacao;
import com.quarteto.o.repositorio.Estoque;
import com.quarteto.o.repositorio.Produtos;

import java.util.List;

public class ServicoDeVendas {
    private RegraImposto regraImposto;
    private Produtos produtos;
    private Estoque estoque;
    private FactoryValidacao factoryValidacao;

    public ServicoDeVendas(Produtos produtos, Estoque estoque, RegraImposto regraImposto, FactoryValidacao factoryValidacao) {
        this.produtos = produtos;
        this.estoque = estoque;
        this.regraImposto = regraImposto;
        this.factoryValidacao = factoryValidacao;
    }

    public void valida(List<ItemVenda> itens) {
        factoryValidacao.getRegraValidacao().valida(produtos, estoque, itens);
    }

    public Integer calculaSubtotal(List<ItemVenda> itens) {
        int soma = 0;
        for (ItemVenda item : itens) {
            soma += item.getValorVendido() * item.getQuantidade();
        }
        return soma;
    }

    public Integer calculaImpostos(List<ItemVenda> itens) {
        double imposto = regraImposto.calcular(itens);

        return (int)imposto + calculaSubtotal(itens);
    }

    public Integer calculaPrecoFinal(List<ItemVenda> itens) {
        // TO DO:
        return 0;
    }

    public Integer[] todosValores(List<ItemVenda> itens) {
        // TO DO:
        return null;
    }
}
