package com.abogado3;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class XXXTest {

	private Estudio estudio;

	@Before
	public void setup() {
		estudio = crearEstudio();
	}

	/*
	 * Dado un abogado Juan con los casos A y B
	 * 
	 * Y un abogado Pedro
	 * 
	 * Y ambos pertenecen al mismo estudio
	 * 
	 * Cuando juan le da a Pedro permiso de lectura para el caso A
	 * 
	 * Entonces Pedro puede acceder al caso A Pero no puede acceder al caso B
	 * 
	 */
	@Test
	public void xxx() {

		Abogado juan = encontrarAJuan();
		Abogado pedro = encontrarAPedro();

		// When
		estudio.darPermisoDeLectura(juan, pedro, "Caso A");

		List<Caso> casosAccesiblesPorPedro = estudio.encontrarCasosAccesiblesPor(pedro);
		Assert.assertEquals(1, casosAccesiblesPorPedro.size());
		Assert.assertEquals("Caso A", casosAccesiblesPorPedro.get(0).getId());
	}

	/*
	 * Dado un abogado Juan con los casos A y B
	 * 
	 * Y un abogado Pedro
	 * 
	 * Y ambos pertenecen al mismo estudio
	 * 
	 * Cuando juan le da a Pedro acceso total para el caso A
	 * 
	 * Entonces Pedro puede acceder y escribir sobre el caso A
	 * 
	 * Pero no puede acceder al caso B
	 */

	@Test
	public void xxx2() {

		Abogado juan = encontrarAJuan();
		Abogado pedro = encontrarAPedro();

		// When
		estudio.darAccesoTotal(juan, pedro, "Caso A");

		// Then
		List<Caso> casosAccesiblesPorPedro = estudio.encontrarCasosAccesiblesPor(pedro);
		Assert.assertEquals(1, casosAccesiblesPorPedro.size());
		Assert.assertEquals("Caso A", casosAccesiblesPorPedro.get(0).getId());
	}

	/**
	 * Dado un abogado Juan con los casos A y B
	 * 
	 * Y un abogado Pedro
	 * 
	 * Y un abogado Pepe Y ambos pertenecen al mismo estudio
	 * 
	 * Cuando Pedro le da a Pepe permiso de lectura para el caso A
	 * 
	 * Entonces Pedro obtiene el error:
	 * "No tiene permisos para realizar esta operacion. Contactase con el abogado que maneja el caso"
	 * 
	 */
	@Test
	public void xxx3() {

		Abogado pedro = encontrarAPedro();
		Abogado pepe = contratarAPepe();

		try {

			// When
			estudio.darPermisoDeLectura(pedro, pepe, "Caso A");
			Assert.fail();
		} catch (Exception e) {

			// Then
			Assert.assertTrue(e.getMessage().contains(
					"No tiene permisos para realizar esta operacion. Contactase con el abogado que maneja el caso"));
		}
	}

	/**
	 * Dado un abogado Juan con los casos A y B
	 * 
	 * Y un abogado Pedro
	 * 
	 * Y un abogado Pepe
	 * 
	 * Y ambos pertenecen al mismo estudio
	 * 
	 * Cuando Pedro le da a Pepe acceso total para el caso A
	 * 
	 * Entonces Pedro obtiene el error:
	 * "No tiene permisos para realizar esta operacion. Contactase con el abogado que maneja el caso"
	 * .
	 */
	@Test
	public void xxx4() {

		Abogado pedro = encontrarAPedro();
		Abogado pepe = contratarAPepe();

		try {
			// When
			estudio.darAccesoTotal(pedro, pepe, "Caso A");
			Assert.fail();
		} catch (Exception e) {

			// Then
			Assert.assertTrue(e.getMessage().contains(
					"No tiene permisos para realizar esta operacion. Contactase con el abogado que maneja el caso"));
		}
	}

	/**
	 * Dado un abogado Juan con los casos A y B
	 * 
	 * Y un abogado Pedro
	 * 
	 * Y ambos pertenecen al mismo estudio
	 * 
	 * Cuando Juan le da a Pedro permisos globales de lectura
	 * 
	 * Entonces Pedro puede acceder a el caso A y B
	 * 
	 */
	@Test
	public void xxx5() {

		Abogado juan = encontrarAJuan();
		Abogado pedro = encontrarAPedro();

		// When
		estudio.darPermisosGlobalesDeLectura(juan, pedro);

		// Then
		List<Caso> casosAccesiblesPorPedro = estudio.encontrarCasosAccesiblesPor(pedro);

		Assert.assertEquals(casosAccesiblesPorPedro.size(), 2);
		Assert.assertEquals(casosAccesiblesPorPedro.get(0).getId(), "Caso A");
		Assert.assertEquals(casosAccesiblesPorPedro.get(1).getId(), "Caso B");
	}

	/**
	 * 
	 * Dado un abogado Juan con los casos A y B
	 * 
	 * Y un abogado Pedro
	 * 
	 * Y ambos pertenecen al mismo estudio
	 * 
	 * Y Juan le da a Pedro permisos globales de lectura
	 * 
	 * Cuando Juan le da a Pedro acceso total sobre el caso A
	 * 
	 * Entonces Pedro puede escribir sobre el caso A y solo acceder al caso B
	 * 
	 */

	@Test
	public void xxx6() {

		Abogado juan = encontrarAJuan();
		Abogado pedro = encontrarAPedro();
		estudio.darPermisosGlobalesDeLectura(juan, pedro);

		// When
		estudio.darAccesoTotal(juan, pedro, "Caso A");

		// Then
		List<Caso> casosAccesiblesPorPedro = estudio.encontrarCasosAccesiblesPor(pedro);

		Assert.assertEquals(casosAccesiblesPorPedro.size(), 2);
		Assert.assertEquals(casosAccesiblesPorPedro.get(0).getId(), "Caso A");
		Assert.assertEquals(casosAccesiblesPorPedro.get(1).getId(), "Caso B");

		List<Caso> casosDeAccesoTotalParaPedro = estudio.encontrarCasosDeAccesoTotalPara(pedro);

		Assert.assertEquals(casosDeAccesoTotalParaPedro.size(), 1);
		Assert.assertEquals(casosDeAccesoTotalParaPedro.get(0).getId(), "Caso A");
	}

	/**
	 * Dado un abogado Juan con los casos A y B
	 * 
	 * Y un abogado Pedro
	 * 
	 * Y ambos pertenecen al mismo estudio
	 * 
	 * Y Juan le da a Pedro permisos globales de lectura
	 * 
	 * Cuando Juan le deniega a Pedro el acceso sobre el caso A
	 * 
	 * Entonces Pedro puede acceder al caso B pero no puede acceder al caso A
	 * 
	 */

	@Test
	public void xxx7() {
		Abogado juan = encontrarAJuan();
		Abogado pedro = encontrarAPedro();
		estudio.darPermisosGlobalesDeLectura(juan, pedro);

		// When
		estudio.denegarAcceso(juan, pedro, "Caso A");

		// Then
		List<Caso> casosAccesiblesPorPedro = estudio.encontrarCasosAccesiblesPor(pedro);

		Assert.assertEquals(casosAccesiblesPorPedro.size(), 1);
		Assert.assertEquals(casosAccesiblesPorPedro.get(0).getId(), "Caso B");

		List<Caso> casosDeAccesoTotalParaPedro = estudio.encontrarCasosDeAccesoTotalPara(pedro);
		Assert.assertEquals(casosDeAccesoTotalParaPedro.size(), 0);
	}

	private Abogado encontrarAPepe() {
		return estudio.encontrarAbogado("MAT-30000");
	}

	private Abogado encontrarAPedro() {
		return estudio.encontrarAbogado("MAT-20000");
	}

	private Abogado contratarAPepe() {
		estudio.contratarA("Pepe", "Muleiro", "MAT-30000");
		return encontrarAPepe();
	}

	private Abogado encontrarAJuan() {
		return estudio.encontrarAbogado("MAT-10000");
	}

	private Estudio crearEstudio() {

		Estudio estudio = new Estudio();

		CasoPotencial casoPotencialA = new CasoPotencial("Caso A");
		CasoPotencial casoPotencialB = new CasoPotencial("Caso B");

		estudio.contratarA("Juan", "Gutierrez", "MAT-10000");
		estudio.contratarA("Pedro", "Sanchez", "MAT-20000");
		estudio.aceptarCaso(casoPotencialA);
		estudio.aceptarCaso(casoPotencialB);

		return estudio;
	}
}
