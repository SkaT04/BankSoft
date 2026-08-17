package applicationCore.repository;

import applicationCore.objects.Account;
import applicationCore.objects.User;
import org.hibernate.SessionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfiguration {

    public HibernateConfiguration(){

    }
    @Bean
    public SessionFactory sessionFactoryUser(){
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.
                addPackage("applicationCore").
                addAnnotatedClasses(User.class, Account.class).
                setProperty("hibernate.connection.driver_class", "org.postgresql.Driver").
                setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres").
                setProperty("hibernate.connection.username", "postgres").
                setProperty("hibernate.connection.password", "ambrella3443").
                setProperty("hibernate.hbm2ddl.auto", "create-drop");
        return configuration.buildSessionFactory();
    }
}
