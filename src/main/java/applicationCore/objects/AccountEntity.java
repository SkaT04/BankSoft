package applicationCore.objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class AccountEntity {
    @Id
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
