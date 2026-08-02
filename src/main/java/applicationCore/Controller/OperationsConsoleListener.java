package applicationCore.Controller;

import applicationCore.Service.AccountService;
import applicationCore.Service.UserService;
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
    private BufferedWriter writer;
    private BufferedReader reader;
    public OperationsConsoleListener(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    public void operationsConsoleListener() throws Exception{
        writer = new BufferedWriter(new OutputStreamWriter(System.out));
        reader = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;
        while(!exit){
            writer.write("""
                    \n
                    Please enter one of operation type:
                    -USER_CREATE
                    -ACCOUNT_CREATE
                    -SHOW_ALL_USERS
                    -ACCOUNT_DEPOSIT
                    -ACCOUNT_WITHDRAW
                    -ACCOUNT_TRANSFER
                    -ACCOUNT_CLOSE
                    -USER_REMOVE
                    -EXIT
                    \n
                    """);
            writer.write(">-");
            writer.flush();
            String command = reader.readLine();

            switch(command){
                case "USER_CREATE":{
                    try{
                        userService.createUser(inputLogin());
                        System.out.println("\n---Successfully user created---\n");
                        break;
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        break;
                    }
                }
                case "SHOW_ALL_USERS":{
                    writer.flush();
                    userService.showAllUsers();
                    break;
                }
                case "ACCOUNT_CREATE":{
                    try{
                        Long userId = userService.getUserId(inputLogin());
                        accountService.createAccount(userId);
                        System.out.println("\n---Successfully account created---\n");
                        break;
                    } catch (Exception e){
                        writer.write(e.getMessage());
                        break;
                    }
                }
                case "ACCOUNT_CLOSE": {
                    try{
                        Long userId = userService.getUserId(inputLogin());
                        String accounts = userService.userAccounts(userId);
                        writer.write(accounts.isEmpty() ? "\n---No accounts---\n" : accounts);
                        writer.write("\nEnter ID: ");
                        writer.flush();
                        String accountIdString = reader.readLine();
                        Long accountId = Long.parseLong(accountIdString);
                        accountService.closeAccount(userId, accountId);
                        System.out.println("\n---Successfully account closed---\n");
                        break;
                    } catch (NumberFormatException e){
                        writer.write("\n---Incorrect Id---\n");
                        break;
                    } catch (Exception e){
                        writer.write(e.getMessage());
                        break;
                    }
                }
                case "USER_REMOVE":{
                    try{
                        Long userId = userService.getUserId(inputLogin());
                        userService.removeUser(userId);
                        System.out.println("\n---Successfully user removed---\n");
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                }
                case "ACCOUNT_DEPOSIT":{
                    try{
                        Long userId = userService.getUserId(inputLogin());
                        writer.write(userService.userAccounts(userId));
                        writer.write("\nInput number account: ");
                        writer.flush();
                        Long accountId = Long.parseLong(reader.readLine());
                        writer.write("\nInput amount: ");
                        writer.flush();
                        Double amount = Double.parseDouble(reader.readLine());
                        accountService.depositAccount(accountId, amount);
                        System.out.println("\n---Successfully deposit---\n");
                        break;
                    } catch (NumberFormatException e){
                        writer.write("Incorrect value");
                        break;
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        break;
                    }

                }
                case "ACCOUNT_WITHDRAW":{
                    try{
                        Long userId = userService.getUserId(inputLogin());
                        writer.write(userService.userAccounts(userId));
                        writer.write("\nInput number account: ");
                        writer.flush();
                        Long accountId = Long.parseLong(reader.readLine());
                        writer.write("\nInput amount: ");
                        writer.flush();
                        Double amount = Double.parseDouble(reader.readLine());
                        accountService.withdrawAccount(accountId, amount);
                        System.out.println("\n---Successfully withdraw---\n");
                    } catch (NumberFormatException e){
                        writer.write("Incorrect value");
                        break;
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        break;
                    }
                }
                case "ACCOUNT_TRANSFER":{
                    try{
                        Long fromUserId = userService.getUserId(inputLogin());
                        writer.write(userService.userAccounts(fromUserId));
                        writer.write("\nInput number fromAccount: ");
                        writer.flush();
                        Long fromAccountId = Long.parseLong(reader.readLine());
                        Long toUserId = userService.getUserId(inputLogin());
                        writer.write(userService.userAccounts(toUserId));
                        writer.write("\nInput number toAccount: ");
                        writer.flush();
                        Long toAccountId = Long.parseLong(reader.readLine());
                        writer.write("\nInput amount: ");
                        writer.flush();
                        Double amount = Double.parseDouble(reader.readLine());
                        accountService.transferAccount(fromAccountId, toAccountId, amount);
                        System.out.println("\n---Successfully transfer---\n");
                    } catch (NumberFormatException e){
                        writer.write("Incorrect value");
                        break;
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        break;
                    }
                }
                case "EXIT": {
                    writer.flush();
                    exit = true;
                    break;
                }
                default: {
                    writer.flush();
                    writer.write("\n---Unknow command, please repeat command---\n");
                }
            }

        }
        writer.close();
        reader.close();
    }

    private String inputLogin() throws Exception{
        writer.write("\nEnter login: ");
        writer.flush();
        return reader.readLine();
    }

    @PostConstruct
    public void postConstruct() throws Exception {
        operationsConsoleListener();
    }

}
