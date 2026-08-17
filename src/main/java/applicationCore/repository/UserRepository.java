package applicationCore.repository;

import applicationCore.objects.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private final Map<Long, User> mapIdUser;
    private final Map<String, User> mapLoginUser;

    public UserRepository() {
        this.mapIdUser = new HashMap<>();
        this.mapLoginUser = new HashMap<>();
    }

    public Map<Long, User> getMapIdUser() {
        return mapIdUser;
    }

    public Map<String, User> getMapLoginUser(){
        return mapLoginUser;
    }

}
