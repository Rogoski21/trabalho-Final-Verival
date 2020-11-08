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
        // TO DO:
        return 0;
    }

    public Integer calculaImpostos(List<ItemVenda> itens) {
        // TO DO:
        return 0;
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
