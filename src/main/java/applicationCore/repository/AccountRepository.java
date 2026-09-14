package applicationCore.repository;

import applicationCore.aop.annotations.annotationLoggers.LoggerMark;
import applicationCore.objects.Account;
import applicationCore.objects.AccountEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AccountRepository {
    SessionFactory sessionFactory;

    public AccountRepository(@Qualifier("sessionFactoryAccount") SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @LoggerMark
    public void saveAccount(AccountEntity account){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(account);
            session.getTransaction().commit();
        }
    }

    @LoggerMark
    public AccountEntity getById(Long id){
        try(Session session = sessionFactory.openSession()){
            return session.find(AccountEntity.class, id);
        }
    }

    @LoggerMark
    public List<AccountEntity> getAllAccounts(){
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("select a from AccountEntity a", AccountEntity.class).list();
        }
    }

    public AccountEntity updateAccount(AccountEntity newAccount){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

        }
    }




}
