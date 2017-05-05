package algo;

import graph.SocialEdge;
import graph.SocialGraph;
import graph.SocialGraphBuilder;
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

    private static double alpha;//parameters for objective function
    private static double gamma;//parameters for objective function
    private static int k;//number of partitions.
    private static double threshold;// threshold to run computing skip
    private static int capacity;// the capacity of each partition
    private static SocialGraph graph;// the graph withold.

    public static void setParameters(double a, double g, int num) {
        alpha = a;
        gamma = g;
        k = num;
    }

    public static void setGrpah(SocialGraph g) {
        graph = g;
    }

    public static void setThreshold(double thresh) {
        threshold = thresh;
    }

    public static void setCapacity(int c) {
        capacity = capacity;
    }

    //Initial scoring function to minimize edge cut and make the graph as balance as possible
    //Users can override this function they way they like

    public int scoringFuction(SocialVertex v, HashMap<Integer, List<SocialVertex>> partition) {
        double max = Double.MIN_VALUE;
        int maxPartition = 0;
        for(int i = 0; i < k; i ++) {
            HashSet<SocialVertex> neighbours = intersection(v.getNeighbours(),partition.get(i));
            double temp = neighbours.size() - alpha * gamma / 2 * (Math.pow(partition.get(i).size(), gamma - 1));
            if(temp > max) {
                max = temp;
                maxPartition = i;
            }
        }
        return maxPartition;
    }

    //Get the intersection of two lists.

    private static HashSet intersection(List neighbours, List partition) {
        HashSet<SocialVertex> result = new HashSet(neighbours);
        HashSet<SocialVertex> parti = new HashSet(partition);
        result.retainAll(parti);
        return result;
    }

    //Initial partition of a social graph with K-means algorithm

    public static HashMap<Integer, List<SocialVertex>> partition() {
        HashMap<Integer, List<SocialVertex>> map = new HashMap<>();
        KMeans km = new KMeans(k);
        List[] datasets = km.cluster(graph);
        for(int i = 0; i < k; i ++) {
            map.put(i, datasets[i]);

        }
        return map;
    }

    //Calculate whether to skip the

    public static boolean skip(SocialGraph graph, SocialVertex v) {
        return (double)(v.getAccChanges()/intersection(v.getNeighbours(), graph.getPartitions().get(v.getPartition())).size()) > threshold;
    }

    //Objective function for dynamically re-partition the graph

    public static double objectiveFunction(SocialVertex move, SocialVertex stay, HashMap<Integer, List<SocialVertex>> partition) {
        List<SocialVertex> movePartition = partition.get(move.getPartition());
        int moveNeighbour = intersection(move.getNeighbours(), movePartition).size();
        double score1 = moveNeighbour * (moveNeighbour - (double)((movePartition.size()-1) / capacity));
        List<SocialVertex> stayPartition = partition.get(stay.getPartition());
        int stayNeighbour = intersection(stay.getNeighbours(), partition.get(stay.getPartition())).size();
        double score2 = stayNeighbour * (stayNeighbour - (double)(stayPartition.size() / capacity));

        return (score1 > score2) ? score1 : score2;

    }

    public static HashMap<Integer, List<SocialVertex>> rePartition(List<SocialEdge> edges, List<SocialVertex> vertices) {

        HashMap<Integer, List<SocialVertex>> partition = graph.getPartitions();

        //replace SocialGraphBuilder with your own graph builder
        SocialGraphBuilder builder = new SocialGraphBuilder(graph) {
            @Override
            protected SocialGraphBuilder self() {
                return null;
            }
        };

        for(SocialVertex v : vertices) {
            builder.addVertex(v);
        }

        for(SocialEdge edge : edges) {
            edge.getSource().addNeighbour(edge.getTarget());
            edge.getTarget().addNeighbour(edge.getSource());
            if(edge.getSource().getPartition() != edge.getTarget().getPartition()) {
                edge.getSource().addAccChanges();
                edge.getTarget().addAccChanges();
            }
        }

        builder.addEdges(edges);

        HashSet<SocialVertex> candidates = new HashSet<>();
        for(SocialVertex v : graph.getVertices()) {
            if(skip(graph, v)) continue;
            candidates.add(v);
        }

        while(!candidates.isEmpty()) {
            for(SocialEdge e: edges) {
                if(candidates.contains(e.getTarget()) && candidates.contains(e.getSource())) {
                    double targetScore = objectiveFunction(e.getSource(), e.getTarget(), partition);
                    double sourceScore = objectiveFunction(e.getTarget(), e.getSource(), partition);
                    SocialVertex toMove = (targetScore > sourceScore) ? e.getTarget() : e.getSource();
                    SocialVertex toStay = (targetScore > sourceScore) ? e.getSource() : e.getTarget();
                    candidates.remove(toMove);
                    candidates.remove(toStay);
                    candidates.addAll(reAssignVertex(toMove, toStay, partition));
                } else if(candidates.contains(e.getTarget())) {
                    SocialVertex toMove = e.getTarget();
                    SocialVertex toStay = e.getSource();
                    candidates.remove(toMove);
                    candidates.remove(toStay);
                    candidates.addAll(reAssignVertex(toMove, toStay, partition));
                } else if(candidates.contains(e.getSource())) {
                    SocialVertex toMove = e.getSource();
                    SocialVertex toStay = e.getTarget();
                    candidates.remove(toMove);
                    candidates.remove(toStay);
                    candidates.addAll(reAssignVertex(toMove, toStay, partition));
                }
                else {
                    continue;
                }
            }
        }

        return graph.getPartitions();
    }

    public static List<SocialVertex> reAssignVertex(SocialVertex toMove,SocialVertex toStay, HashMap<Integer, List<SocialVertex>> partition) {
        List<SocialVertex> move = partition.get(toMove.getPartition());
        List<SocialVertex> stay = partition.get(toStay.getPartition());
        move.add(toMove);
        stay.remove(toMove);
        List<SocialVertex> newCandidates = toMove.getNeighbours();
        newCandidates.remove(toStay);
        return newCandidates;
    }

}
