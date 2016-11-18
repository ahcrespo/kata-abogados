package com.abogado3;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class Estudio {

	List<Abogado> abogados = Lists.newArrayList();
	List<Caso> casos = Lists.newArrayList();

	public void contratarA(String nombre, String apellido, String matricula) {
		Abogado abogado = new Abogado(nombre, apellido, matricula);
		abogados.add(abogado);
	}

	public void darPermisoDeLectura(Abogado autorizador, Abogado porAutorizar, final String casoId) {

		try {
			Caso caso = Iterables.find(casos, new Predicate<Caso>() {
				public boolean apply(Caso arg0) {
					return arg0.getId().equals(casoId);
				}
			});

			if (!caso.isManejadoPor(autorizador))
				throw new RuntimeException(
						"No tiene permisos para realizar esta operacion. Contactase con el abogado que maneja el caso");

			caso.darPermisoParaLecturaA(porAutorizar);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void aceptarCaso(CasoPotencial casoPotencial) {
		Abogado abogado = encontrarAbogadoDisponible();
		asignarCasoA(casoPotencial, abogado);
	}

	public List<Caso> encontrarCasosAccesiblesPor(final Abogado abogado) {
		try {
			Iterable<Caso> casosAccesiblesPorAbogado = Iterables.filter(casos, new Predicate<Caso>() {
				public boolean apply(Caso caso) {
					return caso.puedeSerAccedidoPor(abogado);
				}
			});
			return Lists.newArrayList(casosAccesiblesPorAbogado);
		} catch (Exception e) {
			throw new RuntimeException("No se encontraron casos accessibles por el abogado");
		}
	}

	public List<Caso> encontrarCasosDeAccesoTotalPara(final Abogado abogado) {

		try {
			Iterable<Caso> casosAccesiblesPorAbogado = Iterables.filter(casos, new Predicate<Caso>() {
				public boolean apply(Caso caso) {
					return caso.puedeSerAccedidoPor(abogado) && caso.tieneAccesoTotalPara(abogado);
				}
			});
			return Lists.newArrayList(casosAccesiblesPorAbogado);
		} catch (Exception e) {
			throw new RuntimeException("No se encontraron casos accessibles por el abogado");
		}
	}

	public void darAccesoTotal(Abogado autorizador, Abogado porAutorizar, final String casoId) {
		try {
			Caso caso = Iterables.find(casos, new Predicate<Caso>() {
				public boolean apply(Caso arg0) {
					return arg0.getId().equals(casoId);
				}
			});

			if (!caso.isManejadoPor(autorizador))
				throw new RuntimeException(
						"No tiene permisos para realizar esta operacion. Contactase con el abogado que maneja el caso");

			caso.darAccesoTotalA(porAutorizar);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void darPermisosGlobalesDeLectura(final Abogado autorizador, final Abogado porAutorizar) {

		List<Caso> casosDelAutorizador = Lists.newArrayList();
		for (Caso caso : casos) {
			if (caso.getManejador().equals(autorizador))
				casosDelAutorizador.add(caso);
		}

		for (Caso caso : casosDelAutorizador) {
			caso.darPermisoParaLecturaA(porAutorizar);
		}
	}

	public Abogado encontrarAbogado(final String matricula) {

		try {
			Abogado abogado = Iterables.find(abogados, new Predicate<Abogado>() {
				public boolean apply(Abogado abogado) {
					return abogado.getMatricula().equals(matricula);
				}
			});

			return abogado;
		} catch (Exception e) {
			throw new RuntimeException("El abogado matricula: " + matricula + " no pertenece al estudio");
		}
	}

	private Abogado encontrarAbogadoDisponible() {

		if (abogados.size() < 0)
			throw new RuntimeException("No hay abogados disponibles");

		// Debido a que no hay criterio particular para seleccionar abogado se
		// define arbitrariamente que se toma el primer abogado del pool
		Abogado abogado = abogados.get(0);
		return abogado;
	}

	private void asignarCasoA(CasoPotencial casoPotencial, Abogado abogado) {
		Caso caso = new Caso(casoPotencial, abogado);
		casos.add(caso);
	}
}
