public class ItemVenda {
    private int nro;
    private int codigoProduto;
    private int quantidade;
    private double valorVendido;

    public ItemVenda(int nroItem,int codigoProduto, int quantidade, double valorVendido) {
        this.nro = nroItem;
        this.codigoProduto = codigoProduto;
        this.quantidade = quantidade;
        this.valorVendido = valorVendido;
    }

    public int getNro() {
        return nro;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorVendido() {
        return valorVendido;
    }
}
