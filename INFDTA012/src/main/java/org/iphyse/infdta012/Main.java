package org.iphyse.infdta012;

import java.io.FileNotFoundException;
import org.iphyse.infdta012.assignment.Assignment1;

/**
 *
 * @author RICKRICHTER
 */
public class Main {
    private static final String CSVWINE = "./src/resources/WineData.csv";
    
    public static void main(String args[]) throws FileNotFoundException{
        Assignment1 wine = new Assignment1(4, 25, CSVWINE);
        wine.executeAssignment();
    }
}
