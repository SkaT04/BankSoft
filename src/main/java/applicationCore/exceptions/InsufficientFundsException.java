package applicationCore.exceptions;

public class InsufficientFundsException extends Exception{
    public InsufficientFundsException(){
        super("\n---Insufficient funds---\n");
    }
    public InsufficientFundsException(String operation){
        super("\n---Insufficient funds for the transaction: " + operation + " ---\n");
    }

    public InsufficientFundsException(Double balance, Double amount){
        super(String.format("\n---Insufficient funds. Our balance %.2f, amount transaction %.2f---\n%n", balance, amount));
    }
}
