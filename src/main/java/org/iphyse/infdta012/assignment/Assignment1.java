package org.iphyse.infdta012.assignment;

import org.iphyse.infdta012.general.PointParser;
import org.iphyse.infdta012.clustering.Cluster;
import org.iphyse.infdta012.clustering.ClusterCreate;
import org.iphyse.infdta012.clustering.ClusterPoint;
import org.iphyse.infdta012.clustering.KMean;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import org.iphyse.infdta012.similarity.EucledianDistance;

/**
 *
 * @author RICKRICHTER
 */
public class Assignment1 {
    private static int nClusters, nIterations, currCluster;
    private static String csvFile;
    private static boolean readByColumn;

    public Assignment1(int clusters, int iterations, String csvFile, boolean readByColumn){
        Assignment1.nClusters = clusters;
        Assignment1.nIterations = iterations;
        Assignment1.csvFile = csvFile;
        Assignment1.readByColumn = readByColumn;
    }

    public static void main(String[] args) { }
    
    public void executeAssignment() throws FileNotFoundException{
        int amountClusters = nClusters;
        int amountIterations = nIterations;
        Assignment1.currCluster = 0;
        List<ClusterPoint> points = new PointParser(csvFile).parsePoints(readByColumn);
        ClusterCreate creator = new ClusterCreate(points, new KMean(new EucledianDistance()));
        Cluster[] clusters = creator.createClusters(amountClusters, amountIterations);
        
        try {
            Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(
                        new File("./src/resources/SSEOutput.txt")
                ), "utf-8")
            );
            writer.write("SSE: " + creator.getSSE(clusters));
            writer.close();
        } catch(Exception e){
            System.out.println("COULDN'T WRITE TO FILE");
        }
        for(Cluster c : clusters) {
            postProcess(c);
        }
        
        System.out.println("\u001B[32mResult SSE: " + creator.getSSE(clusters));
        //System.out.println("points devided by cluster: " + (points.size() / clusters.length));
    }

    private static void postProcess(Cluster cluster) {
        Assignment1.currCluster++;
        //Reserve positions in wineCount, for quantity of sales 
        int[] wineCount = new int[cluster.getPosition().getProperties().length];        
        
        for(ClusterPoint point : cluster.getPoints()) {
            for(int i = 0; i < wineCount.length; i++) {
                //increase wineCount if point is 1 ( greater than 0 )
                if(point.getProperties()[i] > 0) {
                    wineCount[i]++;
                }
            }
        }
        
        System.out.println(MessageFormat.format("Cluster centroid: #{0}", currCluster));
        System.out.println(MessageFormat.format("Total buyers: {0}", cluster.getPoints().size()));
        
        Set<ClusterPoint> cps = cluster.getPoints();
        
        int clientCount = 0;
        for (ClusterPoint cp : cps) {
            clientCount++;
            String ClientBought = MessageFormat.format("Client #{0} buys offer(s) ", clientCount);
            
            double[] wines = cp.getProperties();
            int count = 0;
            for(int i = 0; i < wines.length; i++){
                if(wines[i] == 1){
                    count ++;
                    ClientBought = ClientBought + (i + 1) + ",";
                }
            }
            if(count > 0)
                System.out.println(MessageFormat.format("{0}.", ClientBought.substring(0, ClientBought.length()-1)));
        }
        for(int i = 0; i < wineCount.length; i++) {
            if(wineCount[i] > 0) {
                System.out.println(MessageFormat.format("OFFER {0} --> bought {1} times", i + 1, wineCount[i]));
            }
        }
        System.out.println();
    }
}

