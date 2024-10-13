package utils;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MyContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("webService start");
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("webService stop");
        try {
            while(DriverManager.getDrivers().hasMoreElements()) {
                DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
            }
            System.out.println("jdbc Driver close");
            AbandonedConnectionCleanupThread.checkedShutdown();
            System.out.println("clean thread success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
