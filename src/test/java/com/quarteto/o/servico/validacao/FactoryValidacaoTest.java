package com.quarteto.o.servico.validacao;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class FactoryValidacaoTest {

	@ParameterizedTest
	@CsvSource({
			"8, 0",
			"12, 0",
			"18, 0"
	})
	void getRegraValidacaoDeveRetornarInstanciaHorarioComercial(int hour, int minute) {

		var agora = LocalTime.of(hour,minute);
		var factoryValidacao = new FactoryValidacao(agora);

		var result = factoryValidacao.getRegraValidacao();

		assertEquals(ValidacaoHorarioComercial.class, result.getClass());
	}

	@ParameterizedTest
	@CsvSource({
			"4, 0",
			"7, 59",
			"18, 1",
			"23, 0"
	})
	void getRegraValidacaoDeveRetornarInstanciaHorarioNaoComercial(int hour, int minute) {

		var agora = LocalTime.of(hour,minute);
		var factoryValidacao = new FactoryValidacao(agora);

		var result = factoryValidacao.getRegraValidacao();

		assertEquals(ValidacaoForaHorarioComercial.class, result.getClass());
	}
}