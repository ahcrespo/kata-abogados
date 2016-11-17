package com.abogado3;

import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class Caso {

	private String id;
	private Abogado manejador;
	private List<Abogado> abogadosAutorizadosParaLectura = Lists.newArrayList();
	private List<Abogado> abogadosAutorizadosParaAccesoTotal = Lists.newArrayList();
	private List<Abogado> abogadosConAccesoDenegado = Lists.newArrayList();
	
	
	public Caso(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void agregarManejador(Abogado abogado) {
		this.manejador = abogado;
	}
	
	public boolean isManejadoPor(Abogado autorizador) {
		return manejador.equals(autorizador);
	}

	
	public void darPermisoParaLecturaA(Abogado abogadoPorAutorizar) {
		abogadosAutorizadosParaLectura.add(abogadoPorAutorizar);
	}

	public boolean puedeSerAccedidoPor(Abogado abogado) {
		return Iterables.contains(abogadosAutorizadosParaLectura, abogado);
	}

	public void darAccesoTotalA(Abogado porAutorizar) {
		abogadosAutorizadosParaAccesoTotal.add(porAutorizar);
	}
}
