package utilities;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Polygon;
import model.Punkten;

/**
 * Klasse zum Einlesen eine STL oder Binary file
 *
 * @author Alireza Hosseini and Reza
 */
public class Einleser
{

    /**
     * Zuerst wird zwischen Ascii und binary format entschieden.
     *
     * @param pfadZumFile der Ort wo der File liegt
     * @return eine Liste von Polygonen
     * @throws wenn der File nicht existiert oder kein File ist, wird eine
     * Ausnahme ausgeworfen.
     */
    public static List<Polygon> parseSTLFile(Path pfadZumFile) throws IOException
    {
        byte[] alleBytes = Files.readAllBytes(pfadZumFile);
        // Es gibt ein Trick um herauszufinden, ob ein File ASCII-Format hat.
        // das Wort "solid" kommt in unserem fall bei einem ASCII-Format vor
        boolean isASCIISTL = false;
        // die erste 512 charakter sollen erstmal eingelesen werden
        String buffer = leseEinenBereichEin(alleBytes, 0, 512);
        StringBuffer bufferString = new StringBuffer();
        int intZeile = leseEineZeileEin(buffer, bufferString, 0);
        String zeile = bufferString.toString();
        StringTokenizer strToken = new StringTokenizer(zeile);
        String token = strToken.nextToken();
        if (token.equals("solid"))
        {
            if (intZeile > -1)
            {
                bufferString = new StringBuffer();
                //lese nächste Zeile ein
                intZeile = leseEineZeileEin(buffer, bufferString, intZeile + 1);
                zeile = bufferString.toString();
                strToken = new StringTokenizer(zeile);
                token = strToken.nextToken();

                if (token.equals("endsolid"))
                {
                    //ASCII-File ist leer
                    isASCIISTL = true;

                } else if (token.equals("facet"))
                {   // File ist nicht leer
                    isASCIISTL = true;
                } else if (istBinary(alleBytes))
                {
                    //binary file	
                    isASCIISTL = false;
                }
            } else
            { 
                // Wenn keine Zeile im file sind
                if (istBinary(alleBytes))
                {
                    isASCIISTL = false;
                }
            }
        } else
        {    
            // Wenn nicht mit "solid" anfängt
            if (istBinary(alleBytes))
            {
                //binary file
                isASCIISTL = false;
            }
        }

        // read file to array of triangles
        List<Polygon> mesh;
        if (isASCIISTL)
        {
            Charset charset = Charset.forName("UTF-8");
            mesh = readASCII(charset.decode(ByteBuffer.wrap(alleBytes)).toString().toLowerCase());
        } else
        {
            mesh = readBinary(alleBytes);
        }
        return mesh;
    }

    public static String leseEinenBereichEin(byte[] alleBytes, int ofset, int lenge)
    {
        if (alleBytes.length - ofset < lenge)
        {
            lenge = alleBytes.length - ofset;
        }
        Charset charset = Charset.forName("UTF-8");
        CharBuffer decodiere = charset.decode(ByteBuffer.wrap(alleBytes, ofset, lenge));
        return decodiere.toString().toLowerCase();
    }

    public static int leseEineZeileEin(String buf, StringBuffer sb, int offset)
    {
        int n = buf.indexOf('\n', offset);
        if (n > -1)
        {
            sb.append(buf.substring(offset, n - 1));
        } else
        {
            sb.append(buf.substring(offset));
        }
        return n;
    }

    public static boolean istBinary(byte[] allBytes) throws IllegalArgumentException
    {
        if (allBytes.length < 84)
        {
            throw new IllegalArgumentException("invalid binary file, length<84");
        }
        int numtriangles = byteatoint(Arrays.copyOfRange(allBytes, 80, 84));
        if (allBytes.length >= 84 + numtriangles * 50)
        {
            return true; //is binary file
        } else
        {
            String msg = "invalid binary file, num triangles does not match length specs";
            throw new IllegalArgumentException(msg);
        }
    }

    // little endian
    public static int byteatoint(byte[] bytes)
    {
        assert (bytes.length == 4);
        int r = 0;
        r = bytes[0] & 0xff;
        r |= (bytes[1] & 0xff) << 8;
        r |= (bytes[2] & 0xff) << 16;
        r |= (bytes[3] & 0xff) << 24;
        return r;
    }

    /**
     * Reads an STL ASCII file content provided as a String
     *
     * @param content ASCII STL
     * @return A list of triangles representing all of the triangles in the STL
     * file.
     * @throws IllegalArgumentException Thrown if the STL is not properly
     * formatted
     */
    public static List<Polygon> readASCII(String content)
    {
        Logger.getLogger(Einleser.class.getName()).log(Level.FINEST, "Parsing ASCII STL format");
        // string is lowercase
        ArrayList<Polygon> triangles = new ArrayList<>();

        int position = 0;
        scan:
        {
            while (position < content.length() & position >= 0)
            {
                position = content.indexOf("facet", position);
                if (position < 0)
                {
                    break scan;
                }
                try
                {
                    Punkten[] vertices = new Punkten[3];
                    for (int v = 0; v < vertices.length; v++)
                    {
                        position = content.indexOf("vertex", position) + "vertex".length();
                        while (Character.isWhitespace(content.charAt(position)))
                        {
                            position++;
                        }
                        int nextSpace;
                        double[] vals = new double[3];
                        for (int d = 0; d < vals.length; d++)
                        {
                            nextSpace = position + 1;
                            while (!Character.isWhitespace(content.charAt(nextSpace)))
                            {
                                nextSpace++;
                            }
                            String value = content.substring(position, nextSpace);
                            vals[d] = Double.parseDouble(value);
                            position = nextSpace;
                            while (Character.isWhitespace(content.charAt(position)))
                            {
                                position++;
                            }
                        }
                        vertices[v] = new Punkten(vals[0], vals[1], vals[2]);
                    }
                    position = content.indexOf("endfacet", position) + "endfacet".length();
                    triangles.add(new Polygon(vertices[0], vertices[1], vertices[2]));
                } catch (Exception ex)
                {
                    int back = position - 128;
                    if (back < 0)
                    {
                        back = 0;
                    }
                    int forward = position + 128;
                    if (position > content.length())
                    {
                        forward = content.length();
                    }
                    throw new IllegalArgumentException("Malformed STL syntax near \"" + content.substring(back, forward) + "\"", ex);
                }
            }
        }

        return triangles;
    }

    /**
     * Parses binary STL file content provided as a byte array
     *
     * @param allBytes binary STL
     * @return A list of triangles representing all of the triangles in the STL
     * file.
     * @throws IllegalArgumentException Thrown if the STL is not properly
     * formatted
     */
    public static List<Polygon> readBinary(byte[] allBytes)
    {
        Logger.getLogger(Einleser.class.getName()).log(Level.FINEST, "Parsing binary STL format");
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(allBytes));
        ArrayList<Polygon> triangles = new ArrayList<>();
        try
        {
            // skip the header
            byte[] header = new byte[80];
            in.read(header);
            // get number triangles (not really needed)
            // WARNING: STL FILES ARE SMALL-ENDIAN
            int numberTriangles = Integer.reverseBytes(in.readInt());
            triangles.ensureCapacity(numberTriangles);
            // read triangles
            try
            {
                while (in.available() > 0)
                {
                    float[] nvec = new float[3];
                    for (int i = 0; i < nvec.length; i++)
                    {
                        nvec[i] = Float.intBitsToFloat(Integer.reverseBytes(in.readInt()));
                    }
                    Punkten normal = new Punkten(nvec[0], nvec[1], nvec[2]); // not used (yet)
                    Punkten[] vertices = new Punkten[3];
                    for (int v = 0; v < vertices.length; v++)
                    {
                        float[] vals = new float[3];
                        for (int d = 0; d < vals.length; d++)
                        {
                            vals[d] = Float.intBitsToFloat(Integer.reverseBytes(in.readInt()));
                        }
                        vertices[v] = new Punkten(vals[0], vals[1], vals[2]);
                    }
                    short attribute = Short.reverseBytes(in.readShort()); // not used (yet)
                    triangles.add(new Polygon(vertices[0], vertices[1], vertices[2]));
                }
            } catch (Exception ex)
            {
                throw new IllegalArgumentException("Malformed STL binary at triangle number " + (triangles.size() + 1), ex);
            }
        } catch (IOException ex)
        {
            // IO exceptions are impossible with byte array input streams, 
            // but still need to be caught
            Logger.getLogger(Einleser.class.getName()).log(Level.SEVERE, "HOLY SHIT! A ByteArrayInputStream threw an exception!", ex);
        }

        return triangles;
    }

}
