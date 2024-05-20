package mbeans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class MissPercentage implements MissPercentageMBean{

    public MissPercentage() {
    }

    private PointsCounterMBean pointsCounter;

    public MissPercentage(PointsCounterMBean pointsCounter) {
        this.pointsCounter = pointsCounter;
    }

    @Override
    public double getMissPercentage() {
        int totalPoints = pointsCounter.getTotalPoints();
        int missedPoints = pointsCounter.getMissedPoints();
        System.out.println("hello nigga");
        if (totalPoints == 0) {
            return 0.0;
        }
        return (double) missedPoints / totalPoints * 100;
    }
}
