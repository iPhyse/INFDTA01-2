package org.iphyse.infdta012.general;

import org.iphyse.infdta012.clustering.ClusterPoint;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author RICKRICHTER
 */
public class PointParser {

    private List<ClusterPoint> points;
    private final File file;

    public PointParser(String file) {
        this.file = new File(file);
    }

    public List<ClusterPoint> parsePoints() throws FileNotFoundException {
        points = new ArrayList<>();
        List<String[]> lines = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line != null && !line.isEmpty()) {
                    //check if comma seperated
                    if (line.contains(",")) { 
                        lines.add(line.split(","));
                    } else { 
                        //a2.txt, space separated..
                        if (Character.isWhitespace(line.charAt(0))) {
                            line = line.replaceFirst("   ", "");
                        }
                        lines.add(line.split("   "));
                    }
                }
            }
        }
        parseLines(lines);
        return points;
    }

    private void parseLines(List<String[]> lines) {

        Boolean byRow = false;
        if (byRow) {
            for (int j = 0; j < lines.get(0).length; j++) {
                double[] newPoint = new double[lines.size()];
                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i)[j].isEmpty()) {
                        continue;
                    }
                    newPoint[i] = Double.parseDouble(lines.get(i)[j]);

                }
                points.add(new ClusterPoint(newPoint));
            }

        } else {
            for (int i = 0; i < lines.size(); i++) {
                double[] values = new double[lines.get(0).length];
                for (int j = 0; j < values.length; j++) {
                    if (lines.get(i)[j].isEmpty()) {
                        continue;
                    }
                    values[j] = Double.parseDouble(lines.get(i)[j]);
                }
                points.add(new ClusterPoint(values));
            }
        }

    }
}
