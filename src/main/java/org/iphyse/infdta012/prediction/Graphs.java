package org.iphyse.infdta012.prediction;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.function.Function;

import javax.swing.JPanel;

public class Graphs extends JPanel {

    private static final long serialVersionUID = 4364260612752198578L;
    private static final int X_OFFSET = 50;
    private static final int Y_OFFSET = 35;
    private final int xMax;
    private double yMax;
    private double yMin;
    private final List<Double> originalValues;
    private final double[] sesValues;
    private final double[] desValues;
    private final String sesStats;
    private final String desStats;

    public Graphs(List<Double> originalValues, double[] sesValues, String sesStats, double[] desValues, String desStats) {
        xMax = Math.max(originalValues.size(), sesValues.length);
        yMin = Double.POSITIVE_INFINITY;
        yMax = Double.NEGATIVE_INFINITY;
        for (double d : originalValues) {
            yMin = Math.min(d, yMin);
            yMax = Math.max(d, yMax);
        }
        for (double d : sesValues) {
            yMin = Math.min(d, yMin);
            yMax = Math.max(d, yMax);
        }
        for (double d : desValues) {
            yMin = Math.min(d, yMin);
            yMax = Math.max(d, yMax);
        }
        this.originalValues = originalValues;
        this.sesValues = sesValues;
        this.desValues = desValues;
        this.sesStats = sesStats;
        this.desStats = desStats;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawString(sesStats, X_OFFSET, Y_OFFSET);
        g.drawString(desStats, X_OFFSET, Y_OFFSET * 2);
        
        g.drawString("BLACK: ORIGINAL", getWidth() - 150, getHeight() - 50);
        g.setColor(Color.BLUE);
        g.drawString("BLUE: SES", getWidth() - 150, getHeight() - 35);
        g.setColor(Color.MAGENTA);
        g.drawString("MAGENTA: DES", getWidth() - 150, getHeight() - 20);
        g.setColor(Color.BLACK);
        
        double width = getWidth() - X_OFFSET;
        double height = getHeight() - Y_OFFSET;
        drawAxis(g, width, height);
        double xMult = width / xMax;
        double yMult = height / (yMax - yMin);
        drawLine(g, xMult, yMult, Color.BLACK, originalValues.size(), originalValues::get);
        drawLine(g, xMult, yMult, Color.BLUE, sesValues.length, i -> sesValues[i]);  //SES
        drawLine(g, xMult, yMult, Color.MAGENTA, desValues.length, i -> desValues[i]);//DES
    }

    private void drawAxis(Graphics g, double width, double height) {
        int stepX = (int) (width / xMax);
        int scaleX = Math.max((int) Math.ceil(g.getFontMetrics().stringWidth(String.valueOf(xMax)) * xMax / width), 1);
        for (int i = 0; i <= xMax; i += scaleX) {
            g.drawString(String.valueOf(i), i * stepX + X_OFFSET, getHeight());
        }
        double stepY = height / (yMax - yMin);
        int scaleY = Math.max((int) Math.ceil(g.getFontMetrics().getHeight() * yMax / height), 1);
        for (int i = (int) Math.floor(yMin); i < yMax; i += scaleY) {
            g.drawString(String.valueOf(i), 0, (int) ((yMax - i) * stepY));
        }
    }

    private void drawLine(Graphics g, double xMult, double yMult, Color color, int size, Function<Integer, Double> values) {
        g.setColor(color);
        int x1 = X_OFFSET;
        int y1 = (int) ((yMax - values.apply(0)) * yMult);
        for (int i = 1; i < size; i++) {
            int x2 = (int) (i * xMult) + X_OFFSET;
            int y2 = (int) ((yMax - values.apply(i)) * yMult);;
            g.drawLine(x1, y1, x2, y2);
            x1 = x2;
            y1 = y2;
        }
    }
}
