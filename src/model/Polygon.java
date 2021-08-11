package model;

import java.util.Arrays;

/**
 * Das ist die Polygon. ein Polygon ist wie ein Dreieck.
 *
 * @author Alireza and Reza
 */
public class Polygon
{

    private final Punkten[] ecken;
    private final Punkten normal;
    public static double flaecheninhalt;
    public static double flaecheEinesPolygons;
    public static int anzahlPolygone;
    public static double volumenDes3dKoerper;

    /**
     * Es wird durch die drei Punkten das Polygon bzw das Dreieck erzeugt.
     *
     * @param importingPunktA Eckpunkt 1
     * @param importingPunktB Eckpunkt 2
     * @param importingPunktC Eckpunkt 3
     */
    public Polygon(Punkten importingPunktA, Punkten importingPunktB, Punkten importingPunktC)
    {
        ecken = new Punkten[3];
        ecken[0] = importingPunktA;
        ecken[1] = importingPunktB;
        ecken[2] = importingPunktC;
        Punkten EckPunktA = importingPunktB.subtrahieren(importingPunktA);
        Punkten eckPunktB = importingPunktC.subtrahieren(importingPunktA);
        normal = VektorRechner.kreuz(EckPunktA, eckPunktB).normalizieren();
        flaecheninhaltEinesPolygons(importingPunktA, importingPunktB, importingPunktC);
        volumenPyramideBerechnen(importingPunktA, importingPunktB, importingPunktC);
        anzahlPolygone += 1;
    }

    /**
     * Das Volumen des 3D-Körpers wird hier berechnet. um das Volumen zu
     * rechnen, muss so angenommen werden, dass der Körper aus mehreren
     * Pyramiden besteht.
     *
     * @param importingPunktA import parameter importingPunktA für position
     * eines Punktes
     * @param importingPunktB import parameter importingPunktB für position
     * eines Punktes
     * @param importingPunktC import parameter i_p3 für position eines Punktes
     * @return
     */
    public static double volumenPyramideBerechnen(Punkten importingPunktA, Punkten importingPunktB, Punkten importingPunktC)
    {   // Erstmal wird die Normalevektor berechnet
        double nx = -importingPunktA.y * importingPunktB.z + importingPunktB.y * importingPunktA.z - importingPunktB.y * importingPunktC.z + importingPunktC.y * importingPunktB.z - importingPunktC.y * importingPunktA.z + importingPunktA.y * importingPunktC.z;
        double ny = -importingPunktA.z * importingPunktB.x + importingPunktB.z * importingPunktA.x - importingPunktB.z * importingPunktC.x + importingPunktC.z * importingPunktB.x - importingPunktC.z * importingPunktA.x + importingPunktA.z * importingPunktC.x;
        double nz = -importingPunktA.x * importingPunktB.y + importingPunktB.x * importingPunktA.y - importingPunktB.x * importingPunktC.y + importingPunktC.x * importingPunktB.y - importingPunktC.x * importingPunktA.y + importingPunktA.x * importingPunktC.y;
        double faktor = (importingPunktA.x * nx + importingPunktA.y * ny + importingPunktA.z * nz) / (nx * nx + ny * ny + nz * nz);
        Punkten normalenVektor = new Punkten(faktor * nx, faktor * ny, faktor * nz);
        // mit Hilfe von Normalvektor wird die Höhe gerechnet
        double hoehe = VektorRechner.LaengeVektorBerechnen(normalenVektor);
        // Formel zu berechnen des Volumens --> V = G * H * 1/3
        double pyramidenVolumen = flaecheEinesPolygons * hoehe / 3;
        // um das Volumen des Körpers zu berechnen, müssen die PyramidenVolumen addiert werden.
        volumenBerechnen(pyramidenVolumen);
        // pyramidenVolumen als returning parameter
        return pyramidenVolumen;
    }

    /**
     * Volumen von Pyramiden werden hier miteinander addiert.
     *
     * @param pyramideVolumen
     * @return
     */
    public static double volumenBerechnen(double pyramideVolumen)
    {
        return volumenDes3dKoerper += pyramideVolumen;
    }

    /**
     * es wird die Fläche eines Polygon berechnet
     *
     * @param importingPunktA Eckpunkt A
     * @param importingPunktB Eckpunkt B
     * @param importingPunktC Eckpunkt C
     * @return die Fläche
     */
    public static double flaecheninhaltEinesPolygons(Punkten importingPunktA, Punkten importingPunktB, Punkten importingPunktC)
    {
        // Verktor bilden
        Punkten ersteVek = VektorRechner.subtrahieren(importingPunktB, importingPunktA);
        Punkten zweiteVek = VektorRechner.subtrahieren(importingPunktC, importingPunktB);

        // Kreuzprodukt
        Punkten kreuzVek = VektorRechner.kreuzProdukt(ersteVek, zweiteVek);
        // die Fläche wird berechnet
        double flaeche = 0.5 * VektorRechner.skalerProdukt(kreuzVek);
        oberflaechePolygon(flaeche);
        flaecheEinesPolygons = flaeche;
        return flaeche;
    }

    /**
     * Die Oberfläche wird berechnet
     *
     * @param flaeche fläche eines Polygons
     * @return Fläche des Körper
     */
    public static double oberflaechePolygon(double flaeche)
    {
        return flaecheninhalt += flaeche;
    }

    /**
     * gibt das Volumen des Körpers zurück
     *
     * @return
     */
    public static double get_volumen()
    {
        return volumenDes3dKoerper;
    }

    /**
     * gibt die Oberfläche des Körperszurück
     *
     * @return
     */
    public static double get_fleache()
    {
        return flaecheninhalt;
    }

    /**
     * um die Resultaten einfacher auszugeben, werden die Daten erstmal in
     * String umgewandelt.
     *
     * @return
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (Punkten v : ecken)
        {
            sb.append(v.toString());
        }
        return sb.toString();
    }

    /**
     * Die Eckpunkten werden zurückgegeben
     *
     * @return An array of ecken
     */
    public Punkten[] getEcken()
    {
        return ecken;
    }

    /**
     * Die normalvektor wird zurückgegeben.
     */
    public Punkten getNormal()
    {
        return normal;
    }

    /**
     * es wird überprüft ob zwei Polygons die gleiche eingenschaften haben
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Polygon other = (Polygon) obj;
        if (!Arrays.deepEquals(this.ecken, other.ecken))
        {
            return false;
        }
        return true;
    }

    /**
     * Es wird ein hashCode erzeugt.
     */
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + Arrays.deepHashCode(this.ecken);
        return hash;
    }
}
