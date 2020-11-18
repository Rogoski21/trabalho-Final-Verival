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
        return (int) (itens.stream().mapToDouble(it -> it.getValorVendido() * it.getQuantidade()).sum());
    }

    public Integer calculaImpostos(List<ItemVenda> itens) {
        return (int) regraImposto.calcular(itens);
    }

    public Integer calculaPrecoFinal(List<ItemVenda> itens) {
        return calculaSubtotal(itens) + calculaImpostos(itens);
    }

    public Integer[] todosValores(List<ItemVenda> itens) {
        Integer[] valores = new Integer[3];
        valores[0] = calculaSubtotal(itens);
        valores[1] = calculaImpostos(itens);
        valores[2] = calculaPrecoFinal(itens);
        return valores;
    }
}