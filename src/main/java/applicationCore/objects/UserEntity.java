package applicationCore.objects;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Column(name = "loginUser", unique = true)
    private String login;

    public UserEntity(){
    }

    public UserEntity(Long id, String login){
        this.id = id;
        this.login = login;
    }

    public Long getId(){
        return id;
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
