package com.example.grabadora;

public class ListaGrabaciones {
	private String nombre, ruta;
	
	public String getNombre()
	{
		return nombre;
	}
	
	public void setNombre(String Nombre)
	{
		this.nombre = Nombre;
	}
	
	public String getRuta()
	{
		return ruta;
	}
	
	public void setRuta(String Ruta)
	{
		this.ruta = Ruta;
	}
	
	public ListaGrabaciones(String Nombre, String Ruta)
	{
		super();
		this.nombre = Nombre;
		this.ruta = Ruta;
	}
	
	public String toString()
	{
		return this.ruta;
	}
}
