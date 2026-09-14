package applicationCore.objects;


public class Account {
    private final Long id;
    private final Long userId;
    private Double moneyAmount;

    public Account(Long id,
                   Long userId, Double moneyAmount
    ){
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
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
