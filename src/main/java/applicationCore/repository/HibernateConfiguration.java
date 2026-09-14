package applicationCore.repository;

import applicationCore.objects.AccountEntity;
import applicationCore.objects.UserEntity;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfiguration {

    public HibernateConfiguration(){

    }
    @Bean
    //@Qualifier("sessionFactoryUser")
    public SessionFactory sessionFactoryUser(){
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.
                addPackage("applicationCore").
                addAnnotatedClass(UserEntity.class).
                setProperty("hibernate.connection.driver_class", "org.postgresql.Driver").
                setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres").
                setProperty("hibernate.connection.username", "postgres").
                setProperty("hibernate.connection.password", "ambrella3443").
                setProperty("hibernate.hbm2ddl.auto", "create-drop");
        return configuration.buildSessionFactory();
    }

    @Bean
    //@Qualifier("sessionFactoryAccount")
    public SessionFactory sessionFactoryAccount(){
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.
                addPackage("applicationCore").
                addAnnotatedClass(AccountEntity.class).
                setProperty("hibernate.connection.driver_class", "org.postgresql.Driver").
                setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres").
                setProperty("hibernate.connection.username", "postgres").
                setProperty("hibernate.connection.password", "ambrella3443").
                setProperty("hibernate.hbm2ddl.auto", "create-drop");
        return configuration.buildSessionFactory();
    }
}
