package algo;

import graph.SocialEdge;
import graph.SocialGraph;
import graph.SocialVertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.sf.javaml.clustering.KMeans;

/**
 * Created by weixin1 on 03/05/2017.
 * This class implements the graph partition algorithms
 */
public abstract class graphPartition {

    private double alpha;//parameters for objective function
    private double gamma;//parameters for objective function
    private int k;//number of partitions.

    public void setParameters(double alpha, double gamma, int k) {
        this.alpha = alpha;
        this.gamma = gamma;
        this.k = k;
    }

    public int objectiveFuction(SocialVertex v, HashMap<Integer, List<SocialVertex>> partition) {
        double max = Double.MIN_VALUE;
        int maxPartition = 0;
        for(int i = 0; i < k; i ++) {
            HashSet<SocialVertex> neighbours = new HashSet(v.getNeighbours());
            HashSet<SocialVertex> parti = new HashSet(partition.get(i));
            neighbours.retainAll(parti);
            double temp = neighbours.size() - alpha * gamma / 2 * (Math.pow(parti.size(), gamma - 1));
            if(temp > max) {
                max = temp;
                maxPartition = i;
            }
        }
        return maxPartition;
    }


    public HashMap<Integer, List<SocialVertex>> partition(SocialGraph graph) {
        HashMap<Integer, List<SocialVertex>> map = new HashMap<>();
        KMeans km = new KMeans(k);
        List[] datasets = km.cluster(graph);
        for(int i = 0; i < k; i ++) {
            map.put(i, datasets[i]);
        }
        return map;
    }

    public HashMap<Integer, List<SocialVertex>> rePartition(SocialGraph graph, List<SocialEdge> edges, List<SocialVertex> vertices) {
        
        return graph.getPartitions();
    }

}
