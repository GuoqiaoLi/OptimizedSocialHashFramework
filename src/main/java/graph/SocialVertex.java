package graph;

import java.util.List;
import net.sf.javaml.core.Instance;
/**
 * Created by weixin1 on 01/05/2017.
 * Implement this interface for social vertex.
 */

public interface SocialVertex extends Instance{
    //TODO: Implement this method to get the neighbours of a vertex.
    public List<SocialVertex> getNeighbours();
    public void addNeighbour(SocialVertex v);
    public void addAccChanges();
    public int getAccChanges();
    public void resetAccChanges();
    public int getPartition();
    public void setPartition(int partition);
}
