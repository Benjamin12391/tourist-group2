package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Simple Hibernate SessionFactory provider using hibernate.cfg.xml
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() // reads hibernate.cfg.xml
                    .build();

            MetadataSources sources = new MetadataSources(registry);
            // If you prefer programmatic registration uncomment and add classes
            // sources.addAnnotatedClass(Hotel.class);
            // sources.addAnnotatedClass(occupancies.class);

            Metadata metadata = sources.getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}

