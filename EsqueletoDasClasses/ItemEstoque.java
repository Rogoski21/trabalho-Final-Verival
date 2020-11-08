public class ItemEstoque {
    private int nroItem;
    private int codigoProduto;
    private int quantidade;

    public ItemEstoque(int codigoProduto, int quantidade) {
        // TO DO:
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
        // TO DO:
        return true;
    }

    // Soma a quantidade no estoque atual
    public void entrada(int quantidade) {
        // TO DO:
    }

    // Subtrai a quantidade do estoque atual
    // Gera exceção se a quantidade atual é insuficiente
    public void saida(int quantidade) {
        // TO DO:
    }
}

