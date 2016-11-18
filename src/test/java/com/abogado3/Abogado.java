package com.abogado3;

public class Abogado {

	private String nombre;
	private String apellido;
	private String matricula;

	public Abogado(String nombre, String apellido, String matricula) {
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

	public String getMatricula() {
		return matricula;
	}

}
