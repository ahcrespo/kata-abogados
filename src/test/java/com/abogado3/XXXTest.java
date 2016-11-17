package com.abogado3;

import org.junit.Test;

import junit.framework.Assert;

public class XXXTest {

	/*
	 * Dado un abogado Juan con los casos A y B 
	 * Y un abogado Pedro 
	 * Y ambos pertenecen al mismo estudio
	 * 
	 * Cuando juan le da a Pedro permiso de lectura para el caso A
	 * 
	 * Entonces Pedro puede acceder al caso A Pero no puede acceder al caso B
	 * 
	 */
	@Test
	public void xxx() {
		
		Abogado juan = new Abogado();
		Caso casoA = new Caso("Caso A");
		Caso casoB = new Caso("Caso B");
		Abogado pedro = new Abogado();
		
		Estudio estudio = new Estudio();
		
		estudio.contratarA(juan);
		estudio.contratarA(pedro);
		estudio.aceptarCaso(casoA);
		estudio.aceptarCaso(casoB);
		estudio.asignarCasoA(juan, casoA);
		
		//When
		estudio.darPermisoDeLectura(juan, pedro, "Caso A");
		
		//Then
		Caso casoEncontrado = estudio.encontrarCasoRequeridoPor("Caso A", pedro);
		
		Assert.assertEquals(casoA, casoEncontrado);
		
		try {
			estudio.encontrarCasoRequeridoPor("Caso B", pedro);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("El caso no esta disponible para el abogado que lo ha requerido", e.getMessage());
		}
	}
	
	/*
	   	Dado un abogado Juan con los casos A y B
		Y un abogado Pedro
		Y ambos pertenecen al mismo estudio
		
		Cuando juan le da a Pedro acceso total para el caso A
		
		Entonces Pedro puede acceder y escribir sobre el caso A
		Pero no puede acceder al caso B
	 */
	
	@Test
	public void xxx2() {
		
		Abogado juan = new Abogado();
		Caso casoA = new Caso("Caso A");
		Caso casoB = new Caso("Caso B");
		Abogado pedro = new Abogado();
		
		Estudio estudio = new Estudio();
		
		estudio.contratarA(juan);
		estudio.contratarA(pedro);
		estudio.aceptarCaso(casoA);
		estudio.aceptarCaso(casoB);
		estudio.asignarCasoA(juan, casoA);
		
		//When
		estudio.darAccesoTotal(juan, pedro, "Caso A");
		
		//Then
		Caso casoEncontrado = estudio.encontrarCasoRequeridoPor("Caso A", pedro);
		
		Assert.assertEquals(casoA, casoEncontrado);
		
		try {
			estudio.encontrarCasoRequeridoPor("Caso B", pedro);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("El caso no esta disponible para el abogado que lo ha requerido", e.getMessage());
		}
				
	}
}
