package com.abogado3;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class XXXTest {

	private LawFirmSystem lawFirmSystem;

	@Before
	public void setup() {
		lawFirmSystem = crearEstudio();
	}

	/**
	 * Dado un abogado Juan que pertenece al estudio
	 * 
	 * Y tiene asignados los casos A y B
	 * 
	 * Cuando se quiere identificar los casos a los que puede acceder Juan
	 * 
	 * Entonces se verifica que Juan solo puede acceder y escribir a los casos A
	 * y B
	 * 
	 */
	@Test
	public void yyy() {

		// Given
		Lawyer juan = encontrarAJuan();

		// When
		List<Case> casosAccesiblesPorPedro = lawFirmSystem.findCasesFor(juan);

		// Then
		Assert.assertEquals(2, casosAccesiblesPorPedro.size());
		Assert.assertEquals("Caso A", casosAccesiblesPorPedro.get(0).getId());
		Assert.assertEquals("Caso B", casosAccesiblesPorPedro.get(1).getId());
	}

	/**
	 * Dado un abogado Juan que pertenece al estudio
	 * 
	 * Y tiene asignados los casos A y B
	 * 
	 * Cuando se quiere identificar los casos a los que puede escribir Juan
	 * 
	 * Entonces se verifica que Juan solo puede escribir en los casos A y B
	 * 
	 */
	@Test
	public void yyy2() {

		// Given
		Lawyer juan = encontrarAJuan();

		// Whenx
		List<Case> casosDeAccesoTotalParaJuan = lawFirmSystem.findCasesWithFullAccessFor(juan);

		// Then
		Assert.assertEquals(2, casosDeAccesoTotalParaJuan.size());
		Assert.assertEquals("Caso A", casosDeAccesoTotalParaJuan.get(0).getId());
		Assert.assertEquals("Caso B", casosDeAccesoTotalParaJuan.get(1).getId());
	}

	/**
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

		Lawyer juan = encontrarAJuan();
		Lawyer pedro = encontrarAPedro();

		// When
		lawFirmSystem.grantReadAccess(juan, pedro, "Caso A");

		List<Case> casosAccesiblesPorPedro = lawFirmSystem.findCasesFor(pedro);
		Assert.assertEquals(1, casosAccesiblesPorPedro.size());
		Assert.assertEquals("Caso A", casosAccesiblesPorPedro.get(0).getId());
	}

	/**
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

		Lawyer juan = encontrarAJuan();
		Lawyer pedro = encontrarAPedro();

		// When
		lawFirmSystem.grantFullAccess(juan, pedro, "Caso A");

		// Then
		List<Case> casosAccesiblesPorPedro = lawFirmSystem.findCasesFor(pedro);
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

		Lawyer pedro = encontrarAPedro();
		Lawyer pepe = contratarAPepe();

		try {

			// When
			lawFirmSystem.grantReadAccess(pedro, pepe, "Caso A");
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

		Lawyer pedro = encontrarAPedro();
		Lawyer pepe = contratarAPepe();

		try {
			// When
			lawFirmSystem.grantFullAccess(pedro, pepe, "Caso A");
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

		Lawyer juan = encontrarAJuan();
		Lawyer pedro = encontrarAPedro();

		// When
		lawFirmSystem.grantGlobalReadOnlyAccess(juan, pedro);

		// Then
		List<Case> casosAccesiblesPorPedro = lawFirmSystem.findCasesFor(pedro);

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

		Lawyer juan = encontrarAJuan();
		Lawyer pedro = encontrarAPedro();
		lawFirmSystem.grantGlobalReadOnlyAccess(juan, pedro);

		// When
		lawFirmSystem.grantFullAccess(juan, pedro, "Caso A");

		// Then
		List<Case> casesAccessedByPedro = lawFirmSystem.findCasesFor(pedro);

		Assert.assertEquals(casesAccessedByPedro.size(), 2);
		Case caseA = casesAccessedByPedro.get(0);
		Case caseB = casesAccessedByPedro.get(1);

		Assert.assertEquals(caseA.getId(), "Caso A");
		Assert.assertEquals(true, caseA.hasFullAccessFor(pedro));
		Assert.assertEquals(true, caseA.hasReadOnlyAccessFor(pedro));
		Assert.assertEquals(false, caseA.hasAccessDeniedFor(pedro));

		Assert.assertEquals(caseB.getId(), "Caso B");
		Assert.assertEquals(true, caseB.hasReadOnlyAccessFor(pedro));
		Assert.assertEquals(false, caseB.hasFullAccessFor(pedro));
		Assert.assertEquals(false, caseB.hasAccessDeniedFor(pedro));
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

		// Given
		Lawyer juan = encontrarAJuan();
		Lawyer pedro = encontrarAPedro();
		lawFirmSystem.grantGlobalReadOnlyAccess(juan, pedro);

		// When
		lawFirmSystem.denyAccess(juan, pedro, "Caso A");

		// Then
		List<Case> casesAccessedByPedro = lawFirmSystem.findCasesFor(pedro);

		Assert.assertEquals(casesAccessedByPedro.size(), 1);
		Case caseB = casesAccessedByPedro.get(0);

		Assert.assertEquals(caseB.getId(), "Caso B");
		Assert.assertEquals(true, caseB.hasReadOnlyAccessFor(pedro));
		Assert.assertEquals(false, caseB.hasFullAccessFor(pedro));
		Assert.assertEquals(false, caseB.hasAccessDeniedFor(pedro));

		List<Case> casesAccessedByJuan = lawFirmSystem.findCasesFor(juan);
		Assert.assertEquals(casesAccessedByJuan.size(), 2);
		Case caseA = casesAccessedByJuan.get(0);

		Assert.assertEquals(caseA.getId(), "Caso A");
		Assert.assertEquals(true, caseA.hasAccessDeniedFor(pedro));
		Assert.assertEquals(false, caseA.hasFullAccessFor(pedro));
		Assert.assertEquals(false, caseA.hasFullAccessFor(pedro));

		List<Lawyer> lawyersForCaseA = lawFirmSystem.findLawyersFor("Caso A");
		Assert.assertEquals(1, lawyersForCaseA.size());
		Assert.assertEquals(juan, lawyersForCaseA.get(0));

		List<Lawyer> lawyersForCaseB = lawFirmSystem.findLawyersFor("Caso B");
		Assert.assertEquals(2, lawyersForCaseB.size());
		Assert.assertEquals(juan, lawyersForCaseB.get(0));
		Assert.assertEquals(pedro, lawyersForCaseB.get(1));
	}

	/**
	 * Dado un abogado Juan con los casos A y B
	 * 
	 * Y un abogado Pedro
	 * 
	 * Y ambos pertenecen al mismo estudio
	 * 
	 * Y Juan le da a Pedro permisos globales de lectura y escritura
	 * 
	 * Cuando Juan le deniega a Pedro el acceso sobre el caso A
	 * 
	 * Entonces Pedro tiene acceso total al caso B pero no puede acceder al caso
	 * A
	 * 
	 */

	@Test
	public void xxx8() {

		// Given
		Lawyer juan = encontrarAJuan();
		Lawyer pedro = encontrarAPedro();
		lawFirmSystem.grantGlobalFullAccess(juan, pedro);

		// When
		lawFirmSystem.denyAccess(juan, pedro, "Caso A");

		// Then
		List<Case> casesAccessedByPedro = lawFirmSystem.findCasesFor(pedro);

		Assert.assertEquals(casesAccessedByPedro.size(), 1);
		Case caseB = casesAccessedByPedro.get(0);

		Assert.assertEquals(caseB.getId(), "Caso B");
		Assert.assertEquals(true, caseB.hasReadOnlyAccessFor(pedro));
		Assert.assertEquals(true, caseB.hasFullAccessFor(pedro));
		Assert.assertEquals(false, caseB.hasAccessDeniedFor(pedro));

		List<Case> casesAccessedByJuan = lawFirmSystem.findCasesFor(juan);
		Assert.assertEquals(casesAccessedByJuan.size(), 2);
		Case caseA = casesAccessedByJuan.get(0);

		Assert.assertEquals(caseA.getId(), "Caso A");
		Assert.assertEquals(true, caseA.hasAccessDeniedFor(pedro));
		Assert.assertEquals(false, caseA.hasFullAccessFor(pedro));
		Assert.assertEquals(false, caseA.hasFullAccessFor(pedro));

		List<Lawyer> lawyersForCaseA = lawFirmSystem.findLawyersFor("Caso A");
		Assert.assertEquals(1, lawyersForCaseA.size());
		Assert.assertEquals(juan, lawyersForCaseA.get(0));

		List<Lawyer> lawyersForCaseB = lawFirmSystem.findLawyersFor("Caso B");
		Assert.assertEquals(2, lawyersForCaseB.size());
		Assert.assertEquals(juan, lawyersForCaseB.get(0));
		Assert.assertEquals(pedro, lawyersForCaseB.get(1));

	}

	/**
	 * Dado un abogado Juan con los casos A y B
	 * 
	 * Y un abogado Pedro
	 * 
	 * Y ambos pertenecen al mismo estudio
	 * 
	 * Y Juan le deniega a Pedro el acceso a todos sus casoos
	 * 
	 * Cuando Juan le da a Pedro acceso total sobre el caso B
	 * 
	 * Entonces Pedro tiene acceso total al caso B pero no puede acceder al caso
	 * A
	 * 
	 */
	@Test
	public void xxx9() {

		// Given
		Lawyer juan = encontrarAJuan();
		Lawyer pedro = encontrarAPedro();
		lawFirmSystem.grantGlobalDenyAccess(juan, pedro);

		// When
		lawFirmSystem.grantFullAccess(juan, pedro, "Caso B");

		// Then
		List<Case> casesAccessedByPedro = lawFirmSystem.findCasesFor(pedro);

		Assert.assertEquals(casesAccessedByPedro.size(), 1);
		Case caseB = casesAccessedByPedro.get(0);

		Assert.assertEquals(caseB.getId(), "Caso B");
		Assert.assertEquals(true, caseB.hasReadOnlyAccessFor(pedro));
		Assert.assertEquals(true, caseB.hasFullAccessFor(pedro));
		Assert.assertEquals(false, caseB.hasAccessDeniedFor(pedro));

		List<Case> casesAccessedByJuan = lawFirmSystem.findCasesFor(juan);
		Assert.assertEquals(casesAccessedByJuan.size(), 2);
		Case caseA = casesAccessedByJuan.get(0);

		Assert.assertEquals(caseA.getId(), "Caso A");
		Assert.assertEquals(true, caseA.hasAccessDeniedFor(pedro));
		Assert.assertEquals(false, caseA.hasFullAccessFor(pedro));
		Assert.assertEquals(false, caseA.hasFullAccessFor(pedro));

		List<Lawyer> lawyersForCaseA = lawFirmSystem.findLawyersFor("Caso A");
		Assert.assertEquals(1, lawyersForCaseA.size());
		Assert.assertEquals(juan, lawyersForCaseA.get(0));

		List<Lawyer> lawyersForCaseB = lawFirmSystem.findLawyersFor("Caso B");
		Assert.assertEquals(2, lawyersForCaseB.size());
		Assert.assertEquals(juan, lawyersForCaseB.get(0));
		Assert.assertEquals(pedro, lawyersForCaseB.get(1));
	}

	private Lawyer encontrarAPepe() {
		return lawFirmSystem.findLawyer("MAT-30000");
	}

	private Lawyer encontrarAPedro() {
		return lawFirmSystem.findLawyer("MAT-20000");
	}

	private Lawyer contratarAPepe() {
		lawFirmSystem.hireA("Pepe", "Muleiro", "MAT-30000");
		return encontrarAPepe();
	}

	private Lawyer encontrarAJuan() {
		return lawFirmSystem.findLawyer("MAT-10000");
	}

	private LawFirmSystem crearEstudio() {

		LawFirmSystem estudio = new LawFirmSystem();

		PotencialCase casoPotencialA = new PotencialCase("Caso A");
		PotencialCase casoPotencialB = new PotencialCase("Caso B");

		estudio.hireA("Juan", "Gutierrez", "MAT-10000");
		estudio.hireA("Pedro", "Sanchez", "MAT-20000");
		estudio.acceptCase(casoPotencialA);
		estudio.acceptCase(casoPotencialB);

		return estudio;
	}
}
