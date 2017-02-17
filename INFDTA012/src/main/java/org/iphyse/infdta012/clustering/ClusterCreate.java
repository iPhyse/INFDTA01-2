package org.iphyse.infdta012.clustering;

import java.util.List;

/**
 *
 * @author RICKRICHTER
 */
public class ClusterCreate {
    private final List<ClusterPoint> POINTS;
	private final ClusterAlgorithm ALGORITHM;

	public ClusterCreate(List<ClusterPoint> points, ClusterAlgorithm algorithm) {
            this.POINTS = points;
            this.ALGORITHM = algorithm;
	}

	private Cluster[] createClusters(int amountClusters) {
            Cluster[] clusters = new Cluster[amountClusters];
            ALGORITHM.initClusters(clusters, POINTS);
            int previous;
            int hash = 0;
            do {
                previous = hash;
                for(Cluster cluster : clusters) {
                    cluster.getPoints().clear();
                }
                ALGORITHM.assignPoints(POINTS, clusters);
                ALGORITHM.setCenters(clusters);
            } while((hash = getHash(clusters)) != previous);
            return clusters;
	}

	private int getHash(Cluster[] clusters) {
            int hash = 0;
            for(Cluster cluster : clusters) {
                hash *= 31;
                hash += cluster.getPoints().hashCode();
            }
            return hash;
	}

	public Cluster[] createClusters(int amountClusters, int amountIterations) {
            double positiveDouble = Double.POSITIVE_INFINITY;
            Cluster[] out = null;
            while(amountIterations > 0) {
                Cluster[] clusters = createClusters(amountClusters);
                double clusterSSE = getSSE(clusters);
                if(clusterSSE < positiveDouble) {
                    positiveDouble = clusterSSE;
                    out = clusters;
                }
                amountIterations--;
            }
            return out;
	}

	public double getSSE(Cluster[] clusters) {
            double sum = 0;
            for(Cluster cluster : clusters) {
                sum += cluster.getSquaredErrors();
            }
            return sum;
	}
}
