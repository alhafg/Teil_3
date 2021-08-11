package model;

/**
 * @author Aireza and Reza
 */
public final class Punkten
{

    // x koordinaten
    public final double x;

    // y koordinaten
    public final double y;

    // z koordinaten
    public final double z;

    /**
     * Es wird ein Punkt erzeugt
     *
     * @param x x koordinaten
     * @param y y koordinaten
     * @param z z koordinaten
     */
    public Punkten(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * return = this - t1
     *
     * @param punktA
     * @return
     */
    public Punkten subtrahieren(Punkten punktA)
    {
        return VektorRechner.subtrahieren(this, punktA);
    }

    /**
     * Die Normale wird gerechnet
     *
     * @return
     */
    public Punkten normalizieren()
    {
        double faktor = 1.0 / VektorRechner.length(this);
        return new Punkten(
                this.x * faktor,
                this.y * faktor,
                this.z * faktor
        );
    }

    /**
     * Computes the punktProdukt product of this vector and vector v1.
     *
     * @param vektor vektor
     * @return eine Zahl
     */
    public double punktProdukt(Punkten vektor)
    {
        return this.x * vektor.x + this.y * vektor.y + this.z * vektor.z;
    }

    /**
     * .hashCode für einen Vektor wird erzeugt
     *
     */
    @Override
    public int hashCode()
    {
        long bits = 7L;
        bits = 31L * bits + Double.doubleToLongBits(x);
        bits = 31L * bits + Double.doubleToLongBits(y);
        bits = 31L * bits + Double.doubleToLongBits(z);
        return (int) (bits ^ (bits >> 32));
    }

    /**
     * wird überprüft, ob Punkten oder Vektor gleich sind
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        if (obj instanceof Punkten)
        {
            Punkten v = (Punkten) obj;
            return (x == v.x) && (y == v.y) && (z == v.z);
        }
        return false;
    }

    /**
     * Daten werden in String umgewandelt
     *
     */
    @Override
    public String toString()
    {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    /**
     * return = this + t1
     *
     * @param punktA punkt A
     * @return
     */
    public Punkten addieren(Punkten punktA, Punkten punkten)
    {
        return Punkten.this.addieren(punkten, punktA);
    }

}
