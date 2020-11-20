package com.quarteto.o.servico.imposto;

import com.quarteto.o.entidade.ItemVenda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegraImpostoOriginalTest {
	private List<ItemVenda> lista;
	private RegraImposto regraImposto;

	@BeforeEach
	void setUp() {
		lista = new ArrayList<>();
		regraImposto = new RegraImpostoOriginal();
	}

	@Test
	public void calcularDevolveImpostoSobreOSubtotal() {
		lista.add(new ItemVenda(1, 10, 4, 2));
		lista.add(new ItemVenda(1, 10, 5, 3));
		lista.add(new ItemVenda(1, 10, 6, 4));

		double imposto = regraImposto.calcular(lista);

		assertEquals(4.7D, imposto);
	}

	@Test
	public void calcularDevolveImpostoZero() {
		double imposto = regraImposto.calcular(lista);

		assertEquals(0, imposto);
	}


}