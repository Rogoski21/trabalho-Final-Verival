import java.time.LocalTime;

public class FactoryValidacao {
    private LocalTime agora;

    // Deve receber o horário corrente (agora) como parâmetro
    public FactoryValidacao(LocalTime agora){
        this.agora = agora;
    }

    public RegraValidacao getRegraValidacao(){
        return null;
    } 
}
