package applicationCore.repository;

import applicationCore.aop.annotations.annotationLoggers.LoggerMark;
import applicationCore.objects.UserEntity;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    SessionFactory sessionFactory;

    public UserRepository(@Qualifier("sessionFactoryUser") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @LoggerMark
    public void createUser(UserEntity userEntity) throws PersistenceException{
        try(Session session = sessionFactory.openSession();){
            session.beginTransaction();
            session.persist(userEntity);
            session.getTransaction().commit();

        } catch (PersistenceException e){
            throw new PersistenceException("Такой пользователь уже существует");
        }
    }


    @LoggerMark
    public UserEntity getUserById(Long id){
        try(Session session = sessionFactory.openSession()) {
            return session.find(UserEntity.class, id);
        }
    }

    @LoggerMark
    public List<UserEntity> getAllUser(){
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("select u from UserEntity u", UserEntity.class).list();
        }
    }

    @LoggerMark
    public Long getUserID(String login){
        try(Session session = sessionFactory.openSession()){
            UserEntity user =
                    session.createQuery("select u from UserEntity u where u.login = :login", UserEntity.class).setParameter("login", login).list().getFirst();
            if(user == null) {
                throw new IllegalArgumentException("Пользователь не найден");
            }
            return user.getId();
        }
    }


    @LoggerMark
    public UserEntity deleteUserById(Long id){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            UserEntity userEntity = session.find(UserEntity.class, id);
            if(userEntity == null){
                throw new IllegalArgumentException("User not found");
            }
            session.remove(userEntity);
            session.getTransaction().commit();
            return userEntity;
        }
    }

    @LoggerMark
    public UserEntity updateUser(UserEntity newEntity) throws IllegalArgumentException{
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            UserEntity oldEntity = session.find(UserEntity.class, newEntity.getId());
            if(oldEntity == null){
                throw new IllegalArgumentException("User not found");
            }
            oldEntity.setLogin(newEntity.getLogin());
            session.getTransaction().commit();
            return session.find(UserEntity.class, newEntity.getId());
        }
    }

}
