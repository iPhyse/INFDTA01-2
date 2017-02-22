package org.iphyse.infdta012;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.iphyse.infdta012.assignment.Assignment1;
import org.iphyse.infdta012.assignment.Assignment2;
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
        System.out.println("[1] Assignment - Clustering");
        System.out.println("[2] Assignment - Genetic Algorithm");
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
                System.out.println("\r\n--------------------[Result assignment 1]--------------------");
                Assignment1 wineOrder = new Assignment1(clusters, iterations, csvWine);
                wineOrder.executeAssignment();
                System.out.println("---------------------------[ END ]---------------------------\r\n");
                break;
            case 2:
                System.out.print("Crossover rate (have to be between 0 and 1: ");
                double crossoverRate = TryToParseAny.ttpaDouble(br.readLine(), 1);
                System.out.print("Mutation rate (Have to be between 0 and 1: ");
                double mutationRate = TryToParseAny.ttpaDouble(br.readLine(), 1);
                System.out.print("Use elitism (true of false): ");
                boolean elitism = TryToParseAny.ttpaBool(br.readLine(), true);
                System.out.print("Population size (by default is 10): ");
                int population = TryToParseAny.ttpaInt(br.readLine(), 10);
                System.out.print("Amount of iterations (by default is 200: ");
                int iteration = TryToParseAny.ttpaInt(br.readLine(), 200);
                System.out.println("\r\n--------------------[Result assignment 2]--------------------");
                Assignment2 retailMart = new Assignment2(crossoverRate, mutationRate, elitism, population, iteration);
                retailMart.executeAssignment();
                System.out.println("---------------------------[ END ]---------------------------\r\n");
                break;
        }
        main(new String[]{});
        
    }
}
