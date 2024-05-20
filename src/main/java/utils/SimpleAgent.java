package utils;


import mbeans.MissPercentage;
import mbeans.MissPercentageMBean;
import mbeans.PointsCounter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.management.MBeanServer;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import java.io.Serializable;
import java.lang.management.ManagementFactory;

@Named
@ApplicationScoped
public class SimpleAgent implements Serializable {

    @Inject
    private PointsCounter pointsCounter;



    @Inject
    private MissPercentage missPercentage;
    @PostConstruct
    public void init() {

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        try {
            ObjectName pointsCounterName = new ObjectName("SimpleAgent:type=PointsCounter");
            mbs.registerMBean(pointsCounter, pointsCounterName);

            // Регистрация слушателя уведомлений
            NotificationListener listener = (notification, handback) -> System.out.println("Received notification: " + notification.getMessage());
            mbs.addNotificationListener(pointsCounterName, listener, null, null);

            missPercentage = new MissPercentage(pointsCounter);
            ObjectName missPercentageName = new ObjectName("SimpleAgent:type=MissPercentage");
            StandardMBean missPercentageMBean = new StandardMBean(missPercentage, MissPercentageMBean.class);
            mbs.registerMBean(missPercentageMBean, missPercentageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logSimpleAgentStarted() {
        System.out.println("SimpleAgent.logSimpleAgentStarted");
    }

    public void startup(@Observes @Initialized(ApplicationScoped.class) Object context) {
        SimpleAgent a = new SimpleAgent();
        a.logSimpleAgentStarted();
    }
}

