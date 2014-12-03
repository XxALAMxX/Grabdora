package com.example.grabadora;

import java.io.File;
import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	private ListView lvGrabaciones;
    private ArrayList <ListaGrabaciones> lista = new ArrayList<ListaGrabaciones>();
	private MediaRecorder Grabadora;
    private MediaPlayer Reproductor;
    private Button btGrabar, btDetener, btReproducir;	
    private String path;
	private ListaGrabaciones nom;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Conexion con = new Conexion(this,"Grabaciones", null, 1);
		SQLiteDatabase db = con.getWritableDatabase();
		/*
		String sql = "INSERT INTO Grabacion (Nombre, Ruta) VALUES('Grabacion001','/Grabaciones/Prueba001.3gpp')";
		db.execSQL(sql);
		*/
		path = Environment.getExternalStorageDirectory() + "/tempRecord.3gpp";
		lvGrabaciones = (ListView) this.findViewById(R.id.lvGrabaciones);
		btGrabar = (Button) this.findViewById(R.id.btGrabar);
		btDetener = (Button) this.findViewById(R.id.btStop);
		btReproducir = (Button) this.findViewById(R.id.btReproducir);
		
		Conexion conexion = new Conexion(this,"Grabaciones", null, 1);
		db = conexion.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Grabacion", null);
		if(c.moveToFirst())
		{
			do
			{
				nom = new ListaGrabaciones(c.getString(1), c.getString(2));
				lista.add(nom);
			}
			while(c.moveToNext());
		}
		db.close();
		ArrayAdapter<ListaGrabaciones> adaptador = new ArrayAdapter<ListaGrabaciones>(getApplicationContext(),android.R.layout.simple_list_item_1, lista);
		lvGrabaciones.setAdapter(adaptador);
		lvGrabaciones.setOnItemClickListener(new OnItemClickListener() 
		{ 
			public void onItemClick(AdapterView<?> customerAdapter, View footer, int posicion, long selectedLong) 
			{ 
				try{
					
			    	if(Reproductor != null)
			    	{
			    		Reproductor.stop();
			    		Reproductor.release();
			    	}
			    	Toast.makeText(getApplicationContext(), lvGrabaciones.getItemAtPosition(posicion).toString(), Toast.LENGTH_SHORT).show();
			    	Reproductor = new MediaPlayer();
			    	path = Environment.getExternalStorageDirectory() + "/tempRecord" + posicion+".3gpp";
			    	Reproductor.setDataSource(path);
			    	Reproductor.prepare();
			    	Reproductor.start();
					btGrabar.setEnabled(true);
					btReproducir.setEnabled(false);
			    	}
					catch(Exception ex)
					{
						System.out.println(ex);
					}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void btGrabarOnClick(View v) throws Exception {
		if(Grabadora != null)
			{
			Grabadora.release();
			}
		File archivo = new File(path);
		if(archivo != null)
		{
			archivo.delete();
		}
		Grabadora = new MediaRecorder();
		Grabadora.setAudioSource(MediaRecorder.AudioSource.MIC);
		Grabadora.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		Grabadora.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		Grabadora.setOutputFile(path);
		Grabadora.prepare();
		Grabadora.start();
		Toast.makeText(getApplicationContext(),"Grabando",Toast.LENGTH_SHORT).show();
		btDetener.setEnabled(true);
		btGrabar.setEnabled(false);
    }

    public void btDetenerOnClick(View v) {
    	Grabadora.stop();
    	Grabadora.release();
    	Conexion conexion = new Conexion(this,"Grabaciones", null, 1);
		SQLiteDatabase db = conexion.getWritableDatabase();
		String sql = "INSERT INTO Grabacion (Nombre, Ruta) VALUES ('Grabacion_Lista', '" + Environment.getExternalStorageDirectory() + "/tempRecord" + lvGrabaciones.getCount() + ".3gpp" + "')";
		db.execSQL(sql);
		conexion = new Conexion(this,"Grabaciones", null, 1);
		db = conexion.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Grabacion", null);
		if(c.moveToLast())
		{
			do
			{
				nom = new ListaGrabaciones(c.getString(1), c.getString(2));
				lista.add(nom);
			}
			while(c.moveToNext());
		}
		
		ArrayAdapter<ListaGrabaciones> adaptador = new ArrayAdapter<ListaGrabaciones>(getApplicationContext(),android.R.layout.simple_list_item_1, lista);
		lvGrabaciones.setAdapter(adaptador);
		Toast.makeText(getApplicationContext(),"Grabacion Finalizada",Toast.LENGTH_SHORT).show();
		btReproducir.setEnabled(true);
		btDetener.setEnabled(false);
		db.close();
    }

    public void btReproducirOnClick(View v) throws Exception {
    	try{
    	if(Reproductor != null)
    	{
    		Reproductor.stop();
    		Reproductor.release();
    	}
    	Reproductor = new MediaPlayer();
    	Reproductor.setDataSource(path);
    	Reproductor.prepare();
    	Reproductor.start();
		btGrabar.setEnabled(true);
		//btReproducir.setEnabled(false);
    	}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		
    	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
