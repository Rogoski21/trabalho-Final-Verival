package com.quarteto.o.servico.imposto;

import com.quarteto.o.entidade.ItemVenda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegraImpostoComprasGrandesTest {
	private List<ItemVenda> lista;
	private RegraImposto regraImposto;

	@BeforeEach
	void setUp() {
		lista = new ArrayList<>();
		regraImposto = new RegraImpostoComprasGrandes();
	}

	@Test
	public void calcularDevolveImpostoSobreALista() {
		lista.add(new ItemVenda(3, 10, 10, 4));
		lista.add(new ItemVenda(1, 10, 10, 6));
		lista.add(new ItemVenda(5, 10, 10, 2));
		lista.add(new ItemVenda(4, 10, 10, 3));
		lista.add(new ItemVenda(2, 10, 10, 5));

		double imposto = regraImposto.calcular(lista);
		assertEquals(17.5D, imposto);
	}

	@Test
	public void calcularDevolveImpostoSobreListaComDoisItens() {
		lista.add(new ItemVenda(1, 10, 10, 6));
		lista.add(new ItemVenda(1, 10, 10, 5));

		double imposto = regraImposto.calcular(lista);
		assertEquals(11D, imposto);
	}

	@Test
	public void calcularDevolveImpostoSobreListaVazia() {
		double imposto = regraImposto.calcular(lista);

		assertEquals(0, imposto);
	}
}