package com.abogado3;

public class Lawyer {

	private String nombre;
	private String apellido;
	private String matricula;

	public Lawyer(String nombre, String apellido, String matricula) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.matricula = matricula;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getLicense() {
		return matricula;
	}

}
