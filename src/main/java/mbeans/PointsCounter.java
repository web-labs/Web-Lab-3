package mbeans;

import model.AreaCheckerBean;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Named;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;
import java.util.LinkedList;

@Named
@ApplicationScoped
public class PointsCounter extends NotificationBroadcasterSupport implements PointsCounterMBean, Serializable {
    private Integer totalPoints = 0;
    private Integer missedPoints = 0;
    private LinkedList<AreaCheckerBean> results;

    public PointsCounter() {
    }

    public PointsCounter(LinkedList<AreaCheckerBean> results){
        this.results = results;
        initializeCounts();

    }

    private void initializeCounts() {
        totalPoints = 0;
        missedPoints = 0;
        for (AreaCheckerBean result : results) {
            totalPoints ++;
            if (!result.isResult()) {
                missedPoints ++;
            }
        }
    }

    public void resetCounts(LinkedList<AreaCheckerBean> results){
        totalPoints = 0;
        missedPoints = 0;
        this.results = results;
        initializeCounts();
    }


    @Override
    public int getTotalPoints() {
        return totalPoints;
    }

    @Override
    public int getMissedPoints() {
        return missedPoints;
    }

    @Override
    public void incrementTotalPoints() {
        System.out.println("totalPointsBEFORE: " + totalPoints);
        totalPoints++;
        System.out.println("totalPointsAFTER: " + totalPoints);
        if (totalPoints % 5 == 0){
            System.out.println("totalPointsHERE: " + totalPoints);
            Notification n = new Notification("totalPoints", this, totalPoints++, System.currentTimeMillis(), "Количество точек стало кратно 5");
            try {
                checkSerializable(n);
                sendNotification(n);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private void checkSerializable(Object obj) throws Exception {
        if (!(obj instanceof Serializable)) {
            throw new Exception("Object is not serializable: " + obj.getClass().getName());
        }
    }

    @Override
    public void incrementMissedPoints() {
        missedPoints++;
    }
}
