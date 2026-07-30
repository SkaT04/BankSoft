package ApplicationCore.Controller;

import ApplicationCore.Service.AccountService;
import ApplicationCore.Service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

@Controller
public class OperationsConsoleListener {
    private final UserService userService;
    private final AccountService accountService;

    public OperationsConsoleListener(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    public void operationsConsoleListener() throws Exception{
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;
        while(!exit){
            writer.write("""
                    \n
                    Please enter one of operation type:
                    -ACCOUNT_CREATE
                    -SHOW_ALL_USERS
                    -ACCOUNT_CLOSE
                    -ACCOUNT_WITHDRAW
                    -ACCOUNT_DEPOSIT
                    -ACCOUNT_TRANSFER
                    -USER_CREATE
                    -EXIT
                    \n
                    """);
            writer.write(">-");
            writer.flush();
            String command = reader.readLine();

            switch(command){
                case "USER_CREATE":{
                    writer.write("\nEnter login: ");
                    writer.flush();
                    String login = reader.readLine();
                    if(login.isBlank() || login.isEmpty()){
                        writer.write("\n---Login is empty---\n");
                        writer.flush();
                        break;
                    }
                    writer.write("\n");
                    writer.flush();
                    userService.createUser(login);
                    break;
                }
                case "SHOW_ALL_USERS":{
                    writer.flush();
                    userService.showAllUsers();
                    break;
                }
                case "ACCOUNT_CREATE":{
                    writer.write("Enter our login: ");
                    writer.flush();
                    String login = reader.readLine();
                    if(login.isBlank() || login.isEmpty()){
                        writer.write("\n---Login is empty---\n");
                        writer.flush();
                        break;
                    }
                    Long userId = userService.userLoginToUserId(login);
                    if(userId == null){
                        writer.write("\n---User not found---\n");
                        break;
                    }
                    accountService.createAccount(userId);
                    writer.write("\n---Account successfully created---\n");
                    writer.flush();
                    break;
                }
                case "EXIT": {
                    writer.flush();
                    exit = true;
                    break;
                }
                default: {
                    writer.flush();
                    writer.write("\n Unknow command, please repeat command\n");
                }
            }

        }
        writer.close();
        reader.close();
    }

    @PostConstruct
    public void postConstruct() throws Exception {
        operationsConsoleListener();
    }

}
