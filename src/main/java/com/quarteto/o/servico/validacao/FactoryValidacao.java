package com.quarteto.o.servico.validacao;

import java.time.LocalTime;

public class FactoryValidacao {
    private LocalTime agora;

    // Deve receber o horário corrente (agora) como parâmetro
    public FactoryValidacao(LocalTime agora){
        this.agora = agora;
    }

    public RegraValidacao getRegraValidacao(){
        if (LocalTime.parse("08:00").isAfter(agora) ||
                LocalTime.parse("18:00").isBefore(agora)){
            return new ValidacaoForaHorarioComercial();
        }else{
            return new ValidacaoHorarioComercial();
        }
    }
}
