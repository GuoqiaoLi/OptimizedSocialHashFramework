package graph;

import org.jgrapht.graph.builder.AbstractGraphBuilder;
import java.util.List;

/**
 * Created by weixin1 on 01/05/2017.
 */



public abstract class SocialGraphBuilder<B extends SocialGraphBuilder<B>> extends AbstractGraphBuilder {

    final protected SocialGraph graph;

    protected abstract B self();

    public SocialGraphBuilder(SocialGraph graph) {
        super(graph);
        this.graph = graph;
    }

    public B addEdges(List<SocialEdge> edges) {
        for(SocialEdge edge : edges) {
            this.addEdge(edge.getSource(), edge.getTarget(), edge);
            edge.getSource().addNeighbour((edge.getTarget()));
            edge.getTarget().addNeighbour((edge.getSource()));
        }

        return this.self();
    }

    public B addEdges(SocialEdge ... edges) {
        for(SocialEdge edge : edges) {
            this.addEdge(edge.getSource(), edge.getTarget(), edge);
            edge.getSource().addNeighbour((edge.getTarget()));
            edge.getTarget().addNeighbour((edge.getSource()));
        }

        return this.self();
    }

}
