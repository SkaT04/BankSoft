package applicationCore.objects;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "moneyAmount")
    private Double moneyAmount;

    public AccountEntity() {
    }

    public AccountEntity(Long id, Long userId, Double moneyAmount){
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    public AccountEntity(Long userId, Double moneyAmount){
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public Long getId() {
        return id;
    }

}
