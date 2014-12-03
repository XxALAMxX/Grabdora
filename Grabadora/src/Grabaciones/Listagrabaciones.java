package Grabaciones;

public class Listagrabaciones {
	private String nombre, archivo;
	
	public String getNombre()
	{
		return nombre;
	}
	
	public void setNombre(String Nombre)
	{
		this.nombre = Nombre;
	}
	
	public String getArchivo()
	{
		return archivo;
	}
	
	public void setArchivo(String Archivo)
	{
		this.archivo = Archivo;
	}
	
	public Listagrabaciones(String Nombre, String Archivo)
	{
		super();
		this.nombre = Nombre;
		this.archivo = Archivo;
	}
	public String toString()
	{
		return this.nombre;
	}
}
