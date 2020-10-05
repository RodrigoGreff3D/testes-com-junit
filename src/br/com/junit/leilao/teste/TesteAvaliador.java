package br.com.junit.leilao.teste;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.junit.leilao.builder.CriadorDeLeilao;
import br.com.junit.leilao.dominio.Lance;
import br.com.junit.leilao.dominio.Leilao;
import br.com.junit.leilao.dominio.Usuario;
import br.com.junit.leilao.servico.Avaliador;

public class TesteAvaliador {

	private Avaliador leiloeiro;
	private Usuario rodrigo;
	private Usuario antonia;
	private Usuario luiza;

	@Before
	public void criaAvaliador() {
		this.leiloeiro = new Avaliador();
		this.rodrigo = new Usuario("Rodrigo");
		this.antonia = new Usuario("Antonia");
		this.luiza = new Usuario("Luiza");
	}

	@Test(expected = RuntimeException.class)
	public void naoAvaliarLeilaoSemLance() {

		Leilao leilao = new CriadorDeLeilao().para("Outro item ").constroi();

		leiloeiro.avalia(leilao);

	}

	@Test
	public void main() {

		Leilao leilao = new Leilao("Coisa");

		leilao.propoe(new Lance(rodrigo, 300.0));
		leilao.propoe(new Lance(antonia, 400.0));
		leilao.propoe(new Lance(luiza, 500.0));

		leiloeiro.avalia(leilao);

		double maiorEsperado = 500;
		double menorEsperado = 300;
		double mediaEsperada = 400;

		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
		assertEquals(mediaEsperada, leiloeiro.getMedia(), 0.00001);

	}

	@Test
	public void UmLance() {

		Usuario novo = new Usuario("Novo");
		Leilao leilao = new Leilao("Outra coisa");

		leilao.propoe(new Lance(novo, 1000.0));

		leiloeiro.avalia(leilao);

		assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(1000.0, leiloeiro.getMenorLance(), 0.00001);

	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").lance(rodrigo, 100.0).lance(antonia, 200.0)
				.lance(rodrigo, 300.0).lance(antonia, 400.0).constroi();

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();
		assertEquals(3, maiores.size());
		assertEquals(400.0, maiores.get(0).getValor(), 0.00001);
		assertEquals(300.0, maiores.get(1).getValor(), 0.00001);
		assertEquals(200.0, maiores.get(2).getValor(), 0.00001);
	}
}
