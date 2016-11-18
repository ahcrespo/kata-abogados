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

	public Caso(CasoPotencial casoPotencial, Abogado abogado) {
		this.id = casoPotencial.getId();
		this.manejador = abogado;
	}

	public Caso(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public boolean esManejadoPor(Abogado autorizador) {
		return manejador.equals(autorizador);
	}

	public void darPermisoParaLecturaA(Abogado abogado) {

		if (abogadosConAccesoDenegado.contains(abogado))
			throw new RuntimeException("El abogado" + " tiene denegado el acceso.");

		abogadosAutorizadosParaLectura.add(abogado);

		// TODO: Hacer el caso de test donde si doy permiso de lectura cuando
		// PREVIAMENTE tenia acceso total, entonces que se le saque el acceso
		// total
		if (abogadosAutorizadosParaAccesoTotal.contains(abogado))
			abogadosAutorizadosParaAccesoTotal.remove(abogado);
	}

	public void denegarPermisoA(Abogado abogado) {
		abogadosConAccesoDenegado.add(abogado);

		if (abogadosAutorizadosParaLectura.contains(abogado))
			abogadosAutorizadosParaLectura.remove(abogado);

		if (abogadosAutorizadosParaAccesoTotal.contains(abogado))
			abogadosAutorizadosParaAccesoTotal.remove(abogado);

	}

	public boolean puedeSerAccedidoPor(Abogado abogado) {
		return !abogadosConAccesoDenegado.contains(abogado)
				&& (Iterables.contains(abogadosAutorizadosParaLectura, abogado)
						|| Iterables.contains(abogadosAutorizadosParaAccesoTotal, abogado));
	}

	public boolean tieneAccesoTotalPara(Abogado abogado) {
		return Iterables.contains(abogadosAutorizadosParaAccesoTotal, abogado);
	}

	public void darAccesoTotalA(Abogado porAutorizar) {
		if (!abogadosAutorizadosParaLectura.contains(porAutorizar))
			abogadosAutorizadosParaLectura.add(porAutorizar);

		abogadosAutorizadosParaAccesoTotal.add(porAutorizar);
	}

	public Abogado getManejador() {
		return manejador;
	}
}
