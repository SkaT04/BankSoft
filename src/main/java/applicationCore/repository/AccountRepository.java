package applicationCore.repository;

import applicationCore.aop.annotations.annotationLoggers.LoggerMark;
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
    public void createAccount(AccountEntity account){
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
    public List<AccountEntity> getAllAccounts(Long userId){
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("select a from AccountEntity a where userId = " + userId, AccountEntity.class).list();
        }
    }

    @LoggerMark
    public AccountEntity updateAccount(AccountEntity newAccount){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            AccountEntity account = session.find(AccountEntity.class, newAccount.getId());
            account.setMoneyAmount(newAccount.getMoneyAmount());
            session.getTransaction().commit();
            return newAccount;
        }
    }

    @LoggerMark
    public AccountEntity deleteById(Long id){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            AccountEntity account = session.find(AccountEntity.class, id);
            if(account == null){
                throw new IllegalArgumentException("Account not found");
            }
            session.remove(account);
            session.getTransaction().commit();
            return account;
        }
    }




}
