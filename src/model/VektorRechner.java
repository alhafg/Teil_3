
package model;
import utilities.Einleser;

/**
 * Verschiedene Operationen an Vektoren
 * @author Alireza and Reza
 */
public class VektorRechner
{

    /**
     * Die Methode berechnet die Skalarprodukt
     * @param vektor
     * @return
     */
    public static double skalerProdukt(Punkten vektor)
    {
        return Math.sqrt(Math.pow(vektor.x, 2) + Math.pow(vektor.y, 2) + Math.pow(vektor.z, 2));
    }

    /**
     * zwei Punkten werden addiert.
     *
     * @param punktA Punkt A
     * @param punktB Punkt B
     * @return vector
     */
    public static Punkten addieren(Punkten punktA, Punkten punktB)
    {
        return new Punkten(punktA.x + punktB.x, punktA.y + punktB.y, punktA.z + punktB.z);
    }

    /**
     * Länge gerechnt mit importing parameter
     *
     * @param verktor
     * @return Länge
     */
    public static double LaengeVektorBerechnen(Punkten verktor)
    {
        return Math.sqrt(verktor.x * verktor.x + verktor.y * verktor.y + verktor.z * verktor.z);
    }

    /**
     * Eine zahl wird mit vektor multiplizieren
     *
     * @param zahl zahl
     * @param vektor Vektor
     * @return vektro
     */
    public static Punkten mulZahlMitVek(double zahl, Punkten vektor)
    {
        return new Punkten(vektor.x * zahl, vektor.y * zahl, vektor.z * zahl);
    }

    /**
     * Es wird den Winkel zwischen 2 Vektoren berechnet
     *
     * @param vektorA Erste Vektor
     * @param vektorB Zweite Vektror
     * @return Das Resultat ist der Winkel zwischen 2 Vektoren in Radian.
     */
    public static double BerechneWinkel(Punkten vektorA, Punkten vektorB)
    {
        double AdotB = vektorA.punktProdukt(vektorB);
        double A = length(vektorA);
        double B = length(vektorB);
        return Math.acos(AdotB / (A * B));
    }

 

    /**
     * Die Länge eines Verkors wird gerechnet
     *
     * @param punkten
     * @return Länge
     */
    public static double length(Punkten punkten)
    {
        return Math.sqrt(punkten.x * punkten.x + punkten.y * punkten.y + punkten.z * punkten.z);
    }

    /**
     * Kreuzprodukt
     *
     * @param vektorA erste Vektor
     * @param vektorB zweite Vektro
     * @return
     */
    public static Punkten kreuzProdukt(Punkten vektorA, Punkten vektorB)
    {
        double nx = vektorA.y * vektorB.z - vektorA.z * vektorB.y;
        double ny = vektorA.z * vektorB.x - vektorA.x * vektorB.z;
        double nz = vektorA.x * vektorB.y - vektorA.y * vektorB.x;
        return new Punkten(nx, ny, nz);
    }

    /**
     * punkt A wird von Punkt B subtrahiert
     *
     * @param punktA Punkt A
     * @param punktB Punkt B
     * @return Vektor
     */
    public static Punkten subtrahieren(Punkten punktA, Punkten punktB)
    {
        return new Punkten(punktA.x - punktB.x, punktA.y - punktB.y, punktA.z - punktB.z);
    }

    /**
     * kreuzprodukt von zwei vektoren werden gerechnet
     *
     * @param vektorA
     * @param vektorB the second vector
     * @return The kreuz product
     */
    public static Punkten kreuz(Punkten vektorA, Punkten vektorB)
    {
        double nX;
        double nY;
        nX = vektorA.y * vektorB.z - vektorA.z * vektorB.y;
        nY = vektorB.x * vektorA.z - vektorB.z * vektorA.x;
        return new Punkten(vektorA.x * vektorB.y - vektorA.y * vektorB.x, nX, nY);
    }

    /**
     * Multiplies this vector by the specified scalar value.
     *
     * @param faktor the scalar value
     * @param punkten
     * @return The multiplied vector
     */
    public Punkten multiplizieren(double faktor, Punkten punkten)
    {
        return new Punkten(punkten.x * faktor, punkten.y * faktor, punkten.z * faktor);
    }
    
}
