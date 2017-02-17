package org.iphyse.infdta012;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.iphyse.infdta012.assignment.Assignment1;
import org.iphyse.infdta012.general.TryToParseAny;

/**
 *
 * @author RICKRICHTER
 */
public class Main {
   private static final String csvWine = "./src/resources/WineData.csv";
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("INFDAT01-2 - Data mining");
        System.out.println("[1] Assignment - Wine order(s)");
        System.out.println();
        
        System.out.print("Enter Assignment number: ");
        
        int assignment = TryToParseAny.ttpaInt(br.readLine(), 1);
        
        switch(assignment){
            case 1:
            default:
                //Assignment 1
                //Input for the amount of clusters
                System.out.print("Specify the amount of Clusters: ");
                int clusters = TryToParseAny.ttpaInt(br.readLine(), 3);
                //Input for the amount of iterations
                System.out.print("Specify the amount of iterations: ");
                int iterations = TryToParseAny.ttpaInt(br.readLine(), 10);
                //Load-in WineOrder and execute
                Assignment1 wineOrder = new Assignment1(clusters, iterations, csvWine);
                wineOrder.executeAssignment();
                break;
        }
        main(new String[]{});
        
    }
}
