package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author Alireza und Reza
 */
public class PolysCollection
{

    public static List listPoly = new ArrayList();
    public static double[] fleache;

    public static void collectionPolygons()
    {
        setData();
        HashMap< String, Integer> polyTable = new HashMap<String, Integer>();
        List<Polygon> poly = listPoly;
        for (int i = 0; i < listPoly.size(); i++)
        {
            Polygon polygon = poly.get(i);
            polyTable.put(polygon.toString(), (int) fleache[i]);
        }
        polyTable.forEach((k, v) -> System.out.println(k + "=" + v));
        System.out.println("After Sorting by value");
        List<Entry<String, Integer>> list = new ArrayList<>(polyTable.entrySet());
        list.sort(Entry.comparingByValue());
        list.forEach(System.out::println);
    }

    public static void setData()
    {
        listPoly = VolumenUndFlaecheRechner.getPolygons();
        fleache = VolumenUndFlaecheRechner.getFlaecheAllePolygons();
    }

}
