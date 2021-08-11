/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import java.util.ArrayList;
import utilities.Einleser;
import model.*;
public class Ausgabe
{
    public static void oberfleacheAusgeben()
    {
        System.out.println("Oberfläche: " + Polygon.get_fleache());
        System.out.println("Volumen: " + Polygon.get_volumen());
    }
  
}
