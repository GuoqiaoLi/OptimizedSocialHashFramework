package graph;

/**
 * Created by weixin1 on 01/05/2017.
 * Implement this interface for social graph edges
 */
public interface SocialEdge {

    //TODO: Implement these two methods to get the source and target of each edge
    public SocialVertex getSource();
    public SocialVertex getTarget();

}
