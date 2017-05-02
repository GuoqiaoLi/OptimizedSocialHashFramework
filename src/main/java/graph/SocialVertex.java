package graph;

import java.util.List;

/**
 * Created by weixin1 on 01/05/2017.
 * implement this interface for social vertex.
 */

public interface SocialVertex {
    //TODO: Implement this method to get the neighbours of a vertex.
    public List<SocialVertex> getNeighbours();
    public void addNeighbour(SocialVertex v);
}
