package br.com.cod3r.cm.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class CampoTeste {

	private Campo campo;
	
	@BeforeEach
	void adicionarCampo() {
		campo = new Campo(3, 3);
	}
	
	@Test
	void testeVizinhoDistancia1Esquerda() {
		Campo vizinhoEsquerda = new Campo(3, 2);
		 boolean resultado = campo.adicionarVizinho(vizinhoEsquerda);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia1Direita() {
		Campo vizinhoDireita = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinho(vizinhoDireita);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia1EmCima() {
		Campo vizinhoEmCima = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinho(vizinhoEmCima);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia1Embaixo() {
		Campo vizinhoEmbaixo = new Campo(4, 3);
		boolean resultado = campo.adicionarVizinho(vizinhoEmbaixo);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia2() {
		Campo vizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeNaoVizinho() {
		Campo vizinho = new Campo(1, 1);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	
	@Test
	void testeValorPadraoAtributoMarcacao() {
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacao2Chamadas() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}
	
	@Test
	void testeAbrirNaoMinadoMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirMinadoMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirMinadoNaoMarcado() {
		campo.minar();
		
		assertThrows(ExplosaoException.class, () -> {
			campo.abrir();
		});
	}
	
	@Test
	void testeAbrirComVizinhos1() {
		Campo campo11 = new Campo(1, 1);
		Campo campo22 = new Campo(2, 2);
		
		campo22.adicionarVizinho(campo11);
		campo.adicionarVizinho(campo22);
		
		campo.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isAberto());
	}
	
	@Test
	void testeAbrirComVizinhos2() {
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 2);
		campo12.minar();
		
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isFechado());
	}
	
	@Test
	void teste2MinasNaVizinhanca() {
		Campo campo23 = new Campo(2, 3);
		Campo campo32 = new Campo(3, 2);
		Campo campo34 = new Campo(3, 4);
		campo23.minar();
		campo32.minar();
		
		campo.adicionarVizinho(campo23);
		campo.adicionarVizinho(campo32);
		campo.adicionarVizinho(campo34);
		campo.abrir();
		
		assertEquals(2, campo.minasNaVizinhanca());
	}
	
	@Test
	void testeReiniciar2Campos() {
		campo.abrir();
		
		Campo campo23 = new Campo(2, 3);
		campo23.minar();
		campo23.alternarMarcacao();
		
		campo.reiniciar();
		campo23.reiniciar();
		
		assertFalse(campo.isAberto() && campo23.isMarcado());
	}
	
	@Test
	void testeImprimirCampoMarcado() {
		campo.alternarMarcacao();
		assertEquals("x", campo.toString());
	}
	
	@Test
	void testeImprimirCampoNaoAbertNaoMarcado() {
		assertEquals("?", campo.toString());
	}
	
	@Test
	void testeImprimirCampoComMinasNaVizinhanca() {
		Campo campo23 = new Campo(2, 3);
		Campo campo32 = new Campo(3, 2);
		Campo campo34 = new Campo(3, 4);
		campo23.minar();
		campo32.minar();
		
		campo.abrir();
		campo.adicionarVizinho(campo23);
		campo.adicionarVizinho(campo32);
		campo.adicionarVizinho(campo34);
		
		assertEquals("2", campo.toString());
	}
}
