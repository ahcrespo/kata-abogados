package com.abogado3;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class Estudio {

	List<Abogado> abogados = Lists.newArrayList();
	List<Caso> casos = Lists.newArrayList();
	
	public void contratarA(Abogado abogado) {
		abogados.add(abogado);
	}

	public void darPermisoDeLectura(Abogado autorizador, Abogado porAutorizar, final String casoId) {
		
		try {
			Caso caso = Iterables.find(casos, new Predicate<Caso>() {
				public boolean apply(Caso arg0) {
					return arg0.getId().equals(casoId);
				}
			});
			
			if(caso.isManejadoPor(autorizador)) {
				caso.darPermisoParaLecturaA(porAutorizar);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void aceptarCaso(Caso caso) {
		casos.add(caso);
	}
	
	public Caso encontrarCasoRequeridoPor(final String casoId, final Abogado abogado) {
		
		try {
			Caso caso = Iterables.find(casos, new Predicate<Caso>() {
				public boolean apply(Caso arg0) {
					return arg0.getId().equals(casoId) && arg0.puedeSerAccedidoPor(abogado);
				}
			});
			return caso;
		} catch (Exception e) {
			throw new RuntimeException("El caso no esta disponible para el abogado que lo ha requerido");
		}
	}

	public void asignarCasoA(Abogado abogado, Caso caso) {
		caso.agregarManejador(abogado);
	}

	public void darAccesoTotal(Abogado autorizador, Abogado porAutorizar, final String casoId) {
		try {
			Caso caso = Iterables.find(casos, new Predicate<Caso>() {
				public boolean apply(Caso arg0) {
					return arg0.getId().equals(casoId);
				}
			});
			
			if(caso.isManejadoPor(autorizador)) {
				caso.darAccesoTotalA(porAutorizar);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
