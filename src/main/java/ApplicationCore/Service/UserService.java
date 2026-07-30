package ApplicationCore.Service;

import ApplicationCore.Repository.UserRepository;
import ApplicationCore.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    private long userId;


    public UserService(UserRepository userRepository, AccountService accountService){
        this.userId = 0;
        this.userRepository = userRepository;
        this.accountService = accountService;
    }


    public void createUser(String login){
        User newUser = new User(++userId, login);
        if(userRepository.getMapLoginUser().putIfAbsent(login, newUser) != null){
            System.out.println("\n---Такой пользователь уже есть---\n");
            return;
        }
        userRepository.getMapIdUser().put(userId, newUser);
        System.out.println("\n---User successfully create---\n");
        accountService.createAccount(userId);
    }

    public void showAllUsers(){
        Map<Long, User> map = userRepository.getMapIdUser();
        for(User ref : map.values()){
            System.out.println(ref.toString());
        }
    }

    public void removeUser(Long userId){
        User user = userRepository.getMapIdUser().remove(userId);
        if(user == null){
            System.out.println("\n---User not found---\n");
            return;
        }
        for(Long accountId : user.getAccounts()){
            accountService.closeAccount(accountId);
        }
        System.out.println("\n---User successfully remove---\n");
    }

    public Long userLoginToUserId(String userLogin) throws NullPointerException{
        User user = userRepository.getMapLoginUser().get(userLogin);
        if(user == null){
            System.out.println("\n---User-login not found---\n");
        }
        return Objects.requireNonNull(user).getId();
    }
}
