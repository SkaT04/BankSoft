package applicationCore.repository;

import applicationCore.objects.Account;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AccountRepository {
    private final Map<Long, Account> mapIdAccount;

    public AccountRepository(){
        mapIdAccount = new HashMap<>();
    }

    public Map<Long, Account> getMapIdAccount() {
        return mapIdAccount;
    }

}
