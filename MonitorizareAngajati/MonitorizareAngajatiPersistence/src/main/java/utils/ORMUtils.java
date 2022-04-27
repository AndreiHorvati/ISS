package utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ORMUtils {
    private static StandardServiceRegistry serviceRegistry;
    private volatile static SessionFactory instance = null;

    public static SessionFactory getSessionFactory() {
        if (instance == null) {
            createSessionFactory();
        }
        return instance;
    }

    private synchronized static void createSessionFactory() {
        if (instance != null) {
            return;
        }

        serviceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        instance = new MetadataSources( serviceRegistry ).buildMetadata().buildSessionFactory();
    }

    public static void closeSessionFactory() {
        if (instance != null) {
            instance.close();
        }
    }
}
