package net.thempra.overmind;

import java.util.Arrays;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

public class Grafica_1 extends Activity {

	private XYPlot mySimpleXYPlot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grafica_1);

		// Inicializamos el objeto XYPlot búscandolo desde el layout:
		mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

		// Creamos dos arrays de prueba. En el caso real debemos reemplazar
		// estos datos por los que realmente queremos mostrar
		Number[] series1Numbers = { 1, 8, 5, 2, 7, 4 };
		Number[] series2Numbers = { 4, 6, 3, 8, 2, 10 };

		// Añadimos Línea Número UNO:
		XYSeries series1 = new SimpleXYSeries(Arrays.asList(series1Numbers), // Array
																				// de
																				// datos

				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Sólo valores
														// verticales
				"Serie 1"); // Nombre de la primera serie

		// Repetimos para la segunda serie
		XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers),
				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Serie 2");

		// Modificamos los colores de la primera serie
		LineAndPointFormatter series1Format = new LineAndPointFormatter(
				Color.rgb(0, 200, 0), // Color de la línea
				Color.rgb(0, 100, 0), // Color del punto
				Color.rgb(150, 190, 150)); // Relleno

		// Una vez definida la serie (datos y estilo), la añadimos al panel
		mySimpleXYPlot.addSeries(series1, series1Format);

		// Repetimos para la segunda serie
		mySimpleXYPlot.addSeries(
				series2,
				new LineAndPointFormatter(Color.rgb(0, 0, 200), Color.rgb(0, 0,
						100), Color.rgb(150, 150, 190)));

	}
}
