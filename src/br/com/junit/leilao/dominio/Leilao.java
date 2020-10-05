package br.com.junit.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

	private String descricao;
	private List<Lance> lances;

	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}

	public void propoe(Lance lance) {
	    if(lances.isEmpty() || podeDarLance(lance.getUsuario())) {
	        lances.add(lance);
	    }
	}

	private boolean podeDarLance(Usuario usuario) {
	    return !ultimoLance().getUsuario().equals(usuario) 
	        && quantidadeDeLances(usuario) < 5;
	}

	private int quantidadeDeLances(Usuario usuario) {
		int total = 0;
		for (Lance l : lances) {
			if (l.getUsuario().equals(usuario))
				total++;
		}
		return total;
	}

	private Lance ultimoLance() {
		return lances.get(lances.size() - 1);
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

}