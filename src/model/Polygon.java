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
