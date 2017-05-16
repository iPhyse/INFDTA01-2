package org.iphyse.infdta012.assignment;

import org.iphyse.infdta012.prediction.DoubleExponentialSmoothing;
import org.iphyse.infdta012.prediction.Graphs;
import org.iphyse.infdta012.prediction.SimpleExponentialSmoothing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import org.iphyse.infdta012.general.TryToParseAny;

/**
 *
 * @author RICKRICHTER
 */
public class Assignment3_oralCheck {

    private static final int MONTHS = 12;
    private static final int WEEKS = 52;
    
    //private static final int PREDICTION = 49;
    private static final int PREDICTION = 143 + 8; //8 weeks to predict
    private static String csvFile;

    public Assignment3_oralCheck(String csvFile) {
        Assignment3_oralCheck.csvFile = csvFile;
    }

    public static void executeAssignment() throws FileNotFoundException {
        //List<Double> values = parseFile(csvFile);
        List<Double> values = parseFileWallmart(csvFile);
        JFrame frame = new JFrame();
        frame.setTitle("Assignment 3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(getGraphs(values));
        frame.setSize(800, 450);

        //Center screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(
                ((dim.width / 2) - (frame.getSize().width / 2)),
                ((dim.height / 2) - (frame.getSize().height / 2))
        );

        frame.setVisible(true);
    }

    private static List<Double> parseFile(String file) throws FileNotFoundException {
        List<Double> values = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(file))) {
            
            while (scanner.hasNextDouble()) {
                values.add(scanner.nextDouble());
            }
        }
        return values;
    }
    
    private static List<Double> parseFileWallmart(String file) throws FileNotFoundException {
        List<Double> values = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(file))){
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                    String[] lineData = line.split(",");
                    if(lineData[0].equals("1") && lineData[1].equals("2")){
                        values.add(Double.parseDouble(lineData[3]));
                    }
            }
        }
        return values;
    }

    private static Graphs getGraphs(List<Double> values) {
        SimpleExponentialSmoothing ses = new SimpleExponentialSmoothing(values, WEEKS); //final WEEKS or final MONTS
        double factor = ses.getBestSmoothingFactor();
        double[] forecastSES = ses.getPredictions(factor, PREDICTION);
        DoubleExponentialSmoothing des = new DoubleExponentialSmoothing(values);
        double[] factors = des.getBestFactors();
        double[] forecastDES = des.getPredictions(factors[0], factors[1], PREDICTION);
        String sesStats = "Smoothing factor: " + factor + " - Error measure: " + ses.getErrorMeasure();
        String desStats = "\u03B1: " + factors[0] + " \u03B2: " + factors[1] + " - Error measure: " + des.getErrorMeasure();
        return new Graphs(values, forecastSES, sesStats, forecastDES, desStats);
    }

    private Object getSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
