/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.application.Application;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import model.*;
import utilities.Einleser;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.lang.reflect.Method;

import view.Ausgabe;

/**
 *
 * @author alh
 */
public class Main
{

    /**
     * Mit Hilfe von java.io.File und JFileChooser kann eine professionale Suche
     * erfolgen.
     *
     * @param translation A vector of the delta for each coordinate.
     */
    private static File sucheFile()
    {
        File myFile = null;
        int antwort = 0;

        //(".") sagt, dass die Suche vom Projektordner stattfinden soll.
        JFileChooser waehler = new JFileChooser(".");
        waehler.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        antwort = waehler.showOpenDialog(null);
        if (antwort == JFileChooser.APPROVE_OPTION)
        {
            myFile = waehler.getSelectedFile();
            return myFile;
        } else
        {
            return null;
        }

    }

    /**
     * Entry point of program
     *
     * @param arg ignored
     */
    public static void main(String[] args)
    {

        callParsar();
        VolumenUndFlaecheRechner.berechnen();

    }

    private static void callParsar()
    {
        File myFile = sucheFile();
        if (myFile == null)
        {
            // anstatt einfach durch system.out.println() eine Nachricht anzuzeigen, benutzt wird hier Logger
            //in Logger wird viele Infos gespeichrt und wenn es zu einem Fehler kommt, kann die gespeicherte Infos lesen
            //bevor man mit debuggen anfängt.
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, "Der Benutzer hat das Fenster geschlossen!");
            System.exit(0);

        } else if (myFile.isFile())
        {

            try
            {
                List<Polygon> listPoly = Einleser.parseSTLFile(myFile.toPath());
                VolumenUndFlaecheRechner.setPolyeder(listPoly);
                listPoly.forEach((Polygon t) ->
                {
                    System.out.println(t);
                });

            } catch (IOException ausnahme)
            {
                Logger.getLogger(Einleser.class.getName()).log(Level.SEVERE, null, ausnahme);
                System.exit(ausnahme.hashCode());
            }

            // System.exit(0);
        } else
        {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, "Sie haben keinen File ausgewählt!");
            System.exit(0);

        }

    }

}
