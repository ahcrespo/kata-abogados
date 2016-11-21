package com.abogado3;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class CasoTest {

	@Before
	public void setup() {
	}

	@Test
	public void elManagerDelCasoDebeTenerAccesoTest() {

		Lawyer juan = new Lawyer("Juan", "Gutierrez", "MAT-10000");
		PotencialCase casoPotencial = new PotencialCase("Caso A");
		Case caso = Case.create(casoPotencial, juan);
		Assert.assertEquals(true, caso.hasReadOnlyAccessFor(juan));
	}

	@Test
	public void elManagerDelCasoDebeTenerAccesoTotalTest() {

		Lawyer juan = new Lawyer("Juan", "Gutierrez", "MAT-10000");
		PotencialCase casoPotencial = new PotencialCase("Caso A");
		Case caso = Case.create(casoPotencial, juan);

		Assert.assertEquals(true, caso.hasFullAccessFor(juan));
	}

	@Test
	public void unAbogadoNoPuedeAccederAUnCasoSinPermisoDeLecturaTest() {

		Lawyer juan = new Lawyer("Juan", "Gutierrez", "MAT-10000");
		Lawyer pepe = new Lawyer("Pepe", "Muleiro", "MAT-20000");

		PotencialCase casoPotencial = new PotencialCase("Caso A");
		Case caso = Case.create(casoPotencial, juan);

		Assert.assertEquals(false, caso.hasReadOnlyAccessFor(pepe));
	}

	@Test
	public void unAbogadoNoPuedeAccederAUnCasoSinPermisoDeEscrirturaTest() {

		Lawyer juan = new Lawyer("Juan", "Gutierrez", "MAT-10000");
		Lawyer pepe = new Lawyer("Pepe", "Muleiro", "MAT-20000");

		PotencialCase casoPotencial = new PotencialCase("Caso A");
		Case caso = Case.create(casoPotencial, juan);

		Assert.assertEquals(false, caso.hasFullAccessFor(pepe));
	}

	@Test
	public void unAbogadoNoPuedeAccederAUnCasoSiLeFueDenegadoTest() {

		Lawyer juan = new Lawyer("Juan", "Gutierrez", "MAT-10000");
		Lawyer pepe = new Lawyer("Pepe", "Muleiro", "MAT-20000");

		PotencialCase casoPotencial = new PotencialCase("Caso A");
		Case caso = Case.create(casoPotencial, juan);

		caso.denyAccessTo(pepe);

		Assert.assertEquals(false, caso.hasReadOnlyAccessFor(pepe));
	}

	@Test
	public void unAbogadoTieneAccesoTotalAUnCasoCuandoLeFueDadoElPermisoTest() {

		Lawyer juan = new Lawyer("Juan", "Gutierrez", "MAT-10000");
		Lawyer pepe = new Lawyer("Pepe", "Muleiro", "MAT-20000");

		PotencialCase casoPotencial = new PotencialCase("Caso A");
		Case caso = Case.create(casoPotencial, juan);

		caso.grantFullAccessTo(pepe);

		Assert.assertEquals(true, caso.hasReadOnlyAccessFor(pepe));
	}

	@Test
	public void unAbogadoNoPuedeTenerAccesoTotalSiSoloTienePermisoDeLecturaTest() {

		Lawyer juan = new Lawyer("Juan", "Gutierrez", "MAT-10000");
		Lawyer pepe = new Lawyer("Pepe", "Muleiro", "MAT-20000");

		PotencialCase casoPotencial = new PotencialCase("Caso A");
		Case caso = Case.create(casoPotencial, juan);

		caso.grantReadOnlyAccessTo(pepe);

		Assert.assertEquals(false, caso.hasFullAccessFor(pepe));
	}

	@Test
	public void unAbagadoPuedeEscribirEnUnCasoSiTieneAccesoTotalTest() {

		Lawyer juan = new Lawyer("Juan", "Gutierrez", "MAT-10000");
		Lawyer pepe = new Lawyer("Pepe", "Muleiro", "MAT-20000");

		PotencialCase casoPotencial = new PotencialCase("Caso A");
		Case caso = Case.create(casoPotencial, juan);

		caso.grantFullAccessTo(pepe);

		Assert.assertEquals(true, caso.hasFullAccessFor(pepe));
	}

	@Test
	public void elPermisoMasRecienteTienePrecedenciaSobreCualquierOtroPermisoPrevioTest() {

		Lawyer juan = new Lawyer("Juan", "Gutierrez", "MAT-10000");
		Lawyer pepe = new Lawyer("Pepe", "Muleiro", "MAT-20000");

		PotencialCase casoPotencial = new PotencialCase("Caso A");
		Case caso = Case.create(casoPotencial, juan);

		caso.grantFullAccessTo(pepe);
		caso.grantReadOnlyAccessTo(pepe);

		Assert.assertEquals(false, caso.hasFullAccessFor(pepe));
	}

	@Test
	public void elPermisoMasRecienteTienePrecedenciaSobreCualquierOtroPermisoPrevio2Test() {

		Lawyer juan = new Lawyer("Juan", "Gutierrez", "MAT-10000");
		Lawyer pepe = new Lawyer("Pepe", "Muleiro", "MAT-20000");

		PotencialCase casoPotencial = new PotencialCase("Caso A");
		Case caso = Case.create(casoPotencial, juan);

		caso.grantFullAccessTo(pepe);
		caso.denyAccessTo(pepe);

		Assert.assertEquals(false, caso.hasFullAccessFor(pepe));
	}

	@Test
	public void elPermisoMasRecienteTienePrecedenciaSobreCualquierOtroPermisoPrevio3Test() {

		Lawyer juan = new Lawyer("Juan", "Gutierrez", "MAT-10000");
		Lawyer pepe = new Lawyer("Pepe", "Muleiro", "MAT-20000");

		PotencialCase casoPotencial = new PotencialCase("Caso A");
		Case caso = Case.create(casoPotencial, juan);

		caso.denyAccessTo(pepe);
		caso.grantReadOnlyAccessTo(pepe);

		Assert.assertEquals(true, caso.hasReadOnlyAccessFor(pepe));
	}

	@Test
	public void elPermisoMasRecienteTienePrecedenciaSobreCualquierOtroPermisoPrevio4Test() {

		Lawyer juan = new Lawyer("Juan", "Gutierrez", "MAT-10000");
		Lawyer pepe = new Lawyer("Pepe", "Muleiro", "MAT-20000");

		PotencialCase casoPotencial = new PotencialCase("Caso A");
		Case caso = Case.create(casoPotencial, juan);

		caso.denyAccessTo(pepe);
		caso.grantFullAccessTo(pepe);

		Assert.assertEquals(true, caso.hasFullAccessFor(pepe));
	}
}
