import java.util.List;

public interface RegraValidacao {
    public void valida(Produtos produtos, Estoque estoque, List<ItemVenda> itens) throws SistVendasException;
}
