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
import model.Polygon;
import model.Punkten;
import utilities.Einleser;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.lang.reflect.Method;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import view.Ausgabe;

/**
 *
 * @author alh
 */
public class Main extends Application
{
       @Override
    public void start(Stage primaryStage)
    {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>()
        {
            
            @Override
            public void handle(ActionEvent event)
            {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 

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
        launch(args);
        callParsar();
       
    }

    public static void anzeigen()
    {
        Anzeige anzeige = new Anzeige();
        anzeige.frame.setTitle(anzeige.titel);
        anzeige.frame.add(anzeige);
        anzeige.frame.pack();
        anzeige.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        anzeige.frame.setLocationRelativeTo(null);
        anzeige.frame.setResizable(false);
        anzeige.frame.setVisible(true);
        anzeige.start();
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
                listPoly.forEach((Polygon t) ->
                {
                    System.out.println(t);
                });
                
            } catch (IOException ausnahme)
            {
                Logger.getLogger(Einleser.class.getName()).log(Level.SEVERE, null, ausnahme);
                System.exit(ausnahme.hashCode());
            }
            Ausgabe.oberfleacheAusgeben();
            anzeigen();
            
           // System.exit(0);
        } else
        {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, "Sie haben keinen File ausgewählt!");
            System.exit(0);

        }

}

}


