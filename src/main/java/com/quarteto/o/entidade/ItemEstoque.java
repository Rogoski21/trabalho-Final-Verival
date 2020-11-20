package com.quarteto.o.entidade;

import com.quarteto.o.excecoes.SistVendasException;

public class ItemEstoque {
    private static int nro = 1;
    private int nroItem;
    private int codigoProduto;
    private int quantidade;

    public ItemEstoque(int codigoProduto, int quantidade) {
        if (quantidade <= 0) {
            throw new SistVendasException(SistVendasException.Causa.QUANTIDADE_INVALIDA);
        } else {
            this.nroItem = nro;
            nro++;
            this.codigoProduto = codigoProduto;
            this.quantidade = quantidade;
        }
    }

    public int getNroItem(){
        return nroItem;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public boolean disponivel(int quantidade){
        if (quantidade <= 0) {
            throw new SistVendasException(SistVendasException.Causa.QUANTIDADE_INVALIDA);
        } else {
            return this.quantidade >= quantidade;
        }
    }

    public void entrada(int quantidade) {
        if (quantidade <= 0) {
            throw new SistVendasException(SistVendasException.Causa.QUANTIDADE_INVALIDA);
        } else {
            this.quantidade += quantidade;
        }
    }

    public void saida(int quantidade) {
        if (quantidade <= 0) {
            throw new SistVendasException(SistVendasException.Causa.QUANTIDADE_INVALIDA);
        } else {
            if (this.quantidade - quantidade < 0) {
                throw new SistVendasException(SistVendasException.Causa.QUANTIDADE_INSUFICIENTE);
            } else {
                this.quantidade -= quantidade;
            }
        }
    }
}
