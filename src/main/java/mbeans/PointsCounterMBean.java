package mbeans;

import model.AreaCheckerBean;

import java.util.LinkedList;

public interface PointsCounterMBean {
    int getTotalPoints();

    int getMissedPoints();
    void resetCounts(LinkedList<AreaCheckerBean> results);
    void incrementTotalPoints();
    void incrementMissedPoints();
}
