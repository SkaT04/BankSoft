package applicationCore.controller;


import applicationCore.aop.annotations.annotationExceptions.HandleExceptions;
import applicationCore.aop.annotations.annotationLoggers.LoggerMark;
import applicationCore.service.AccountService;
import applicationCore.service.LogConsoleManager;
import applicationCore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


@Controller
public class OperationsConsoleListener {
    private final LogConsoleManager consoleManager;
    private final UserService userService;
    private final AccountService accountService;
    private BufferedWriter writer;
    private BufferedReader reader;


    private OperationsConsoleListener self;

    @Autowired
    @Lazy
    public void setOperationsConsoleListener(OperationsConsoleListener self){
        this.self = self;
    }


    public OperationsConsoleListener(LogConsoleManager consoleManager, UserService userService, AccountService accountService) {
        this.consoleManager = consoleManager;
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
                    self.createUser();
                    break;
                }
                case "SHOW_ALL_USERS":{
                    self.showAllUsers();
                    break;
                }
                case "ACCOUNT_CREATE":{
                    self.createAccount();
                    break;
                }
                case "ACCOUNT_CLOSE": {
                    try{
                        self.accountClose();
                        break;
                    } catch (NumberFormatException e){
                        writer.write("\n---Incorrect Id---\n");
                        break;
                    }
                }
                case "USER_REMOVE":{
                    self.userRemove();
                    break;
                }
                case "ACCOUNT_DEPOSIT":{
                    try{
                        self.accountDeposit();
                        break;
                    } catch (NumberFormatException e){
                        writer.write("Incorrect value");
                        break;
                    }
                }
                case "ACCOUNT_WITHDRAW":{
                    try{
                        self.accountWithdraw();
                        break;
                    } catch (NumberFormatException e){
                        writer.write("Incorrect value");
                        break;
                    }
                }
                case "ACCOUNT_TRANSFER":{
                    try{
                        self.accountTransfer();
                        break;
                    } catch (NumberFormatException e){
                        writer.write("Incorrect value");
                        break;
                    }
                }
                case "consoleLog":{
                    self.closeAndOpenConsoleLog();
                    break;
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

    @LoggerMark
    public String inputLogin() throws Exception{
        writer.write("\nEnter login: ");
        writer.flush();
        return reader.readLine();
    }

    @LoggerMark
    @HandleExceptions
    public void createUser() throws Exception{
        userService.createUser(inputLogin());
        System.out.println("\n---Successfully user created---\n");
    }

    @LoggerMark
    @HandleExceptions
    public void showAllUsers() throws Exception{
        writer.flush();
        userService.showAllUsers();
    }

    @LoggerMark
    @HandleExceptions
    public void createAccount() throws Exception{
        Long userId = userService.getUserId(inputLogin());
        accountService.createAccount(userId);
        System.out.println("\n---Successfully account created---\n");
    }

    @LoggerMark
    @HandleExceptions
    public void accountClose() throws Exception{
        Long userId = userService.getUserId(inputLogin());
        String accounts = userService.userAccounts(userId);
        writer.write(accounts.isEmpty() ? "\n---No accounts---\n" : accounts);
        writer.write("\nEnter ID: ");
        writer.flush();
        String accountIdString = reader.readLine();
        Long accountId = Long.parseLong(accountIdString);
        accountService.closeAccount(userId, accountId);
        System.out.println("\n---Successfully account closed---\n");
    }

    @LoggerMark
    @HandleExceptions
    public void userRemove() throws Exception{
        Long userId = userService.getUserId(inputLogin());
        userService.removeUser(userId);
        System.out.println("\n---Successfully user removed---\n");
    }

    @LoggerMark
    @HandleExceptions
    public void accountDeposit() throws Exception{
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
    }

    @LoggerMark
    @HandleExceptions
    public void accountWithdraw() throws Exception{
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
    }

    @LoggerMark
    @HandleExceptions
    public void accountTransfer() throws Exception{
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
    }

    @LoggerMark
    @HandleExceptions
    public void closeAndOpenConsoleLog(){
        if(consoleManager.isOpen()){
            consoleManager.closeLogConsole();
        } else {
            consoleManager.openLogConsole();
        }
    }
}
