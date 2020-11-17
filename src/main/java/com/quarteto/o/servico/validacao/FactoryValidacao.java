package com.quarteto.o.servico.validacao;

import java.time.LocalTime;

public class FactoryValidacao {
    private LocalTime agora;

    // Deve receber o horário corrente (agora) como parâmetro
    public FactoryValidacao(LocalTime agora){
        this.agora = agora;
    }

    public RegraValidacao getRegraValidacao(){
        LocalTime comercialInicio = LocalTime.of(8,0);
        LocalTime comercialFim = LocalTime.of(18,0);
        if(agora.isAfter(comercialInicio.minusMinutes(1)) && agora.isBefore(comercialFim.plusMinutes(1)))
            return new ValidacaoHorarioComercial();
        else
            return new ValidacaoForaHorarioComercial();
    } 
}
