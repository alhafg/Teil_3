package controller;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * Diese Klasse soll dazu dienen, dass man die 3D-Objekt sehen kann.
 *
 * @author Alireza and Reza
 */
public class Anzeige extends Canvas implements Runnable
{

    public Thread thread;
    public JFrame frame;
    public String titel = "3D Anzeige";
    private static final int BEREITE = 900;
    private static final int HOCH = 700;
    private static boolean runnin = false;

    /**
     * konstruktor
     */
    public Anzeige()
    {
        this.frame = new JFrame();
        Dimension size = new Dimension(BEREITE, HOCH);
        this.setPreferredSize(size);
    }

    /**
     * sagt, wie und wann die Anzeige gestatet wird
     */
    public synchronized void start()
    {
        runnin = true;
        this.thread = new Thread(this, "Anzeige");
        this.thread.start();
    }

    /**
     * spezifiziert, wann soll die Anzeige gestoppt werden
     */
    public synchronized void stop()
    {
        runnin = false;
        try
        {
            this.thread.join();

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

  @Override
    public void run()
    {
        long lasttime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60;
        double delta = 0;
        int frames = 0;
        //init();
        while (runnin)
        {
            long now = System.nanoTime();
            delta += (now - lasttime) / ns;
            lasttime = now;
            while (delta >= 1)
            {
                update();
                delta--;
                render();
                frames++;
            }

            if (System.currentTimeMillis() - timer > 1000)
            {
                this.frame.setTitle(titel + " | " + frames + " fps");
            }

        }

        stop();
    }

    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH * 2, HEIGHT * 2);

        //tera.render(g);
        g.dispose();
        bs.show();
    }

    private void update()
    {
        //this.tera.rotate(true, 1, 0, 1);
    }

}
