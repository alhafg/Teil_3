
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import view.Ausgabe;
      

/**
 *
 * @author Alireza und Reza
 */
public class VolumenUndFlaecheRechner
        
{
    private final String OBERFLAECHE = "Oberfläche: ";
    private final String VOLUMEN = "Volumen: ";
    private final String FLPOLY = "Fläche eines Polygons: ";
    public static List listPoly = new ArrayList();
    public static double []fleache;
    public VolumenUndFlaecheRechner()
    {}
    public static void setPolyeder(List poly)
    {
        listPoly = poly;
    }
    
    public double VolumenBerechnen()
	{
		double volumen = 0;
		List<Polygon> poly = listPoly;

		for (int i = 0; i < poly.size() ; i++)
		{
			double flaecheninhalt = 0;
			Polygon polygon = poly.get(i);
			Punkten[] eckPunkten = polygon.getEcken();
			
			for (int j = 0; j < eckPunkten.length - 2; j++)
			{
				Punkten eckPunktA = eckPunkten[0];
				Punkten eckPunktB = eckPunkten[j + 1];
				Punkten eckPunktC = eckPunkten[j + 2];
				
				// Berechnet die Seitenlängen des Dreiecks als Abstand der Ecken
				double a = abstandRechnen(eckPunktA, eckPunktB);
				double b = abstandRechnen(eckPunktB, eckPunktC);
				double c = abstandRechnen(eckPunktC, eckPunktA);
                double s = 0.5 * (a + b + c);
					
				// Formel des Heron für den Flächeninhalt des allgemeinen Dreiecks
				flaecheninhalt += Math.sqrt(s * (s - a) * (s - b) * (s - c));
			}
				
			// Berechnet einen Normalenvektor für die Höhe der Pyramide
			Punkten punktA = eckPunkten[0];
			Punkten punktB = eckPunkten[1];
			Punkten punktC = eckPunkten[2];
			double x = -punktA.y*punktB.z + punktB.y*punktA.z - punktB.y*punktC.z + punktC.y*punktB.z - punktC.y*punktA.z + punktA.y*punktC.z;
			double y = -punktA.z*punktB.x + punktB.z*punktA.x - punktB.z*punktC.x + punktC.z*punktB.x - punktC.z*punktA.x + punktA.z*punktC.x;
			double z = -punktA.x*punktB.y + punktB.x*punktA.y - punktB.x*punktC.y + punktC.x*punktB.y - punktC.x*punktA.y + punktA.x*punktC.y;
			double faktor = (punktA.x * x + punktA.y * y + punktA.z * z) / (x * x + y * y + z * z);
			Punkten normalenVektor = new Punkten(faktor * x, faktor * y, faktor * z);
			double hoehe = LaengeBerechnen(normalenVektor);
				
			// Addiert das Volumen der Pyramide zum Gesamtvolumen
			double pyramidenVolumen = flaecheninhalt * hoehe / 3;
			volumen += pyramidenVolumen;
		}
                Ausgabe.print(VOLUMEN + volumen);
		return volumen;
	}
		
	// Berechnet den Oberflächeninhalt des Polyeders als Summe der Flächeninhalte der Seitenflächen
	public double[] FlaecheEinsPolygonsBerechnen()
	{
		List<Polygon> poly = listPoly;
		double[]  polys = new double[listPoly.size()];
			
		// Berechnet den Flächeninhalt des Polygons als Summe der Flächeninhalte von Dreiecken
		for (int i = 0; i < poly.size(); i++)
		{
			double flaecheninhalt = 0;
			Polygon polygon = poly.get(i);
			Punkten[] eckPunkten = polygon.getEcken();
			
			for (int j = 0; j < eckPunkten.length - 2; j++)
			{
				Punkten eckPunktA = eckPunkten[0];
				Punkten eckPunktB = eckPunkten[j + 1];
				Punkten eckPunktC = eckPunkten[j + 2];
				
				// Berechnet die Seitenlängen des Dreiecks als Abstand der Ecken
				double a = abstandRechnen(eckPunktA, eckPunktB);
				double b = abstandRechnen(eckPunktB, eckPunktC);
				double c = abstandRechnen(eckPunktC, eckPunktA);
                                double s = 0.5 * (a + b + c);
					
				// Formel des Heron für den Flächeninhalt des allgemeinen Dreiecks
				flaecheninhalt += Math.sqrt(s * (s - a) * (s - b) * (s - c));
			}
			polys[i] = Math.round(flaecheninhalt);	
			
		}
                Ausgabe.print(FLPOLY + Arrays.toString(polys));
                fleache = polys;
		return polys;
	}
        
        // Berechnet den Oberflächeninhalt des Polyeders als Summe der Flächeninhalte der Seitenflächen
	public double oberflaecheBerechnen()
	{
		List<Polygon> poly = listPoly;
                double flaecheninhalt = 0;
		// Berechnet den Flächeninhalt des Polygons als Summe der Flächeninhalte von Dreiecken
		for (int i = 0; i < poly.size(); i++)
		{
			
			Polygon polygon = poly.get(i);
			Punkten[] eckPunkten = polygon.getEcken();
			
			for (int j = 0; j < eckPunkten.length - 2; j++)
			{
				Punkten eckPunktA = eckPunkten[0];
				Punkten eckPunktB = eckPunkten[j + 1];
				Punkten eckPunktC = eckPunkten[j + 2];
				
				// Berechnet die Seitenlängen des Dreiecks als Abstand der Ecken
				double a = abstandRechnen(eckPunktA, eckPunktB);
				double b = abstandRechnen(eckPunktB, eckPunktC);
				double c = abstandRechnen(eckPunktC, eckPunktA);
                                double s = 0.5 * (a + b + c);
					
				// Formel des Heron für den Flächeninhalt des allgemeinen Dreiecks
				flaecheninhalt += Math.sqrt(s * (s - a) * (s - b) * (s - c));
			}
				
			
		}
                Ausgabe.print(OBERFLAECHE + flaecheninhalt);
		return flaecheninhalt;
	}
        
        
		
	// Berechnet die Länge des Vektors mit dem Satz des Pythagoras
	private double LaengeBerechnen(Punkten eckPunkt)
	{
		double x = eckPunkt.x;
		double y = eckPunkt.y;
		double z = eckPunkt.z;
		return Math.sqrt(x * x + y * y + z * z);
	}
        
        public static double[] getFlaecheAllePolygons()
        {
            return fleache;
        }
        public static List getPolygons ()
        {
            return listPoly;
        }
		
	// Berechnet den Abstand zwischen den beiden Ecken mit dem Satz des Pythagoras
	private double abstandRechnen(Punkten eckPunktA, Punkten eckPunktB)
	{
		double x = eckPunktA.x - eckPunktB.x;
		double y = eckPunktA.y - eckPunktB.y;
		double z = eckPunktA.z - eckPunktB.z;
		return Math.sqrt(x * x + y * y + z * z);
	}
        
        public static void berechnen()
        {
            VolumenUndFlaecheRechner rechner = new VolumenUndFlaecheRechner();
            rechner.FlaecheEinsPolygonsBerechnen();
            rechner.oberflaecheBerechnen();
            rechner.VolumenBerechnen();
            PolysCollection.collectionPolygons();
        }
                
    
    
    
    
    
    
    
}
