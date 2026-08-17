package applicationCore.objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private final Long  id;
    private final String login;
    private final List<Long> accounts;

    public User(Long id, String login){
        this.id = id;
        this.login = login;
        accounts = new ArrayList<>();
    }

    public Long getId(){
        return id;
    }

    public String getLogin(){
        return login;
    }

    public List<Long> getAccountsId(){
        return accounts;
    }

    @Override
    public boolean equals(Object object){
        if (this == object) return true;
        if(object == null || this.getClass() != object.getClass()) return false;

        User user = (User)object;
        return Objects.equals(login, user.getLogin());
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, login);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
