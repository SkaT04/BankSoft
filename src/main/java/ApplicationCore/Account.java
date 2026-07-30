package ApplicationCore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class Account {
    private final Long id;
    private final Long userId;

    @Value("${account.moneyAmount}")
    private Double moneyAmount;

    public Account(Long id,
                   Long userId
    ){
        this.id = id;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void changeAmountBy(Double amount){
        moneyAmount+=amount;
    }
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
