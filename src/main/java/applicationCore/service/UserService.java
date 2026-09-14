package applicationCore.service;

import applicationCore.aop.annotations.annotationLoggers.LoggerMark;
import applicationCore.objects.UserEntity;
import applicationCore.repository.AccountRepository;
import applicationCore.repository.UserRepository;
import applicationCore.objects.User;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private long userId;


    public UserService(UserRepository userRepository, AccountService accountService, AccountRepository accountRepository){
        this.userId = 0;
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }


    @LoggerMark
    public void createUser(String login) throws NoSuchElementException, NullPointerException, PersistenceException {
            if(login == null || login.isBlank()){
                throw new NullPointerException("\n---Incorrect values---\n");
            }
            userRepository.createUser(new UserEntity(login));
            accountService.createAccount(userId);
    }

    @LoggerMark
    public void showAllUsers(){
        List<UserEntity> userEntityList = userRepository.getAllUser();
        for(UserEntity ref : userEntityList){
            System.out.println(userEntityToUser(ref).toString());
        }
    }

    @LoggerMark
    public void removeUser(Long userId) throws NoSuchElementException, NullPointerException{
        if(userId == null){
            throw new NullPointerException("\n---Incorrect values---\n");
        }
        userRepository.deleteUserById(userId);
    }


    @LoggerMark
    public Long getUserId(String login) throws NoSuchElementException, NullPointerException{
        if(login == null || login.isBlank()){
            throw new NullPointerException("\n---Login is empty---\n");
        }
        Long userId = userRepository.getUserID(login);
        if(userId == null){
            throw new NoSuchElementException("\n---User not found---\n");
        }
        return userId;
    }

    public User userEntityToUser(UserEntity userEntity){
        return new User(
                userEntity.getId(),
                userEntity.getLogin()
        );
    }

    public UserEntity userToUserEntity(User user){
        return new UserEntity(
                user.getId(),
                user.getLogin()
        );
    }
}
