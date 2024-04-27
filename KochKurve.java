// Einsendeaufgabencode: B-GOPB01-XX2-K04
// Bearbeiter: Maxim Heibach
// Matrikelnummer: 909442
//
// Aufgabe 4, Koch-Kurve (Java):
// Das Tool visualisiert Koch-Kurven für einen vorgegebenen Iterationsschritt. Es kann maximal eine Koch-Kurve 
// der Stufe 5 dargestellt werden. 
//
// Anleitung:
// In Konsole KochKurve.java mit javac kompilieren und mit Hilfe von java KochKurve <Zahl> (z.B. 
// java KockKurve 5) aufrufen.

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Polygon;
import java.awt.Graphics;

public class KochKurve extends JFrame {
    
    private static Polygon returnPolygon = new Polygon();

    // Aufgabenteil a) Polygon initialisieren und zeichnen
    public KochKurve(int steps) {    
        Polygon polygon = new Polygon();
        
        initializePolygon(polygon);
        subdivide(polygon, steps);
        paintPolygon(returnPolygon);    
    }

    private Polygon initializePolygon(Polygon polygon) {
        polygon.addPoint(250, 50);
        polygon.addPoint(400, 350);
        polygon.addPoint(100, 350);  
        return polygon; 
    }
   
    // Aufgabenteil c) Aufruf der Methode subdivide(Polygon polygon) bis steps = 0
    public static Polygon subdivide(Polygon polygon, int steps) {
        while (steps > 0) {   
            polygon = subdivide(polygon);
            steps--; 
        }
        returnPolygon = polygon;
        return returnPolygon;
    }

    // Aufgabenteil b) Koch-Kurve berechnen
    static Polygon subdivide(Polygon polygon) {
        // ein lokales Polygon ist erforderlich, um nur die neu errechneten Punkte zurückzugeben
        Polygon localPolygon = new Polygon();
        int numPoints = polygon.npoints;        
        
        for(int i = 0; i < numPoints; i++) {
            int currentX = polygon.xpoints[i];
            int currentY = polygon.ypoints[i];

            int nextX = polygon.xpoints[(i + 1) % numPoints];
            int nextY = polygon.ypoints[(i + 1) % numPoints];
            
            Vector2 pointA = new Vector2(currentX, currentY);
            Vector2 pointE = new Vector2(nextX, nextY);

            // Punkt B berechnen
            Vector2 pointB = pointA.scale(2.0 / 3.0).add(pointE.scale(1.0 / 3.0));

            // Punkt C berechnen
            Vector2 pointM = pointA.linearInterpolation(pointE, 0.5);
            Vector2 vectorAE = pointE.subtract(pointA);
            Vector2 vectorN = new Vector2(vectorAE.y, -vectorAE.x).normalized();
            double lengthD = Math.sqrt(1.0 / 12.0)*vectorAE.length();  // d^2 = b^2 - e^2 = (1/3)^2 - (1/6)^2 = 3/36 = 1/12
            Vector2 pointC = pointM.add(vectorN.scale(lengthD));

            // Punkt D berechnen
            Vector2 pointD = pointA.scale(1.0 / 3.0).add(pointE.scale(2.0 / 3.0));

            // Punkt A-D dem lokalen Polygon hinzufügen
            localPolygon.addPoint((int) pointA.x, (int) pointA.y);
            localPolygon.addPoint((int) pointB.x, (int) pointB.y);
            localPolygon.addPoint((int) pointC.x, (int) pointC.y);
            localPolygon.addPoint((int) pointD.x, (int) pointD.y);      
        }
        return localPolygon;
    }

    private void paintPolygon(Polygon returnPolygon) {
        JPanel panel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                g.drawPolygon(returnPolygon);
            }
        };
        add(panel);
        pack();

        setSize(500, 500);
        setTitle("Koch-Kurve");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } 
    

    public static void main(String[] args) {
        int steps = 3;
        
        if(args.length > 0) {
            try {
                steps = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.out.println("Ungültige Eingabe. Standardwert \"" + steps + "\" wird verwendet.");
            }
        } else {
            System.out.println("Keine Eingabe. Standardwert \"" + steps + "\" wird verwendet.");
        }

        new KochKurve(steps);
    }
}