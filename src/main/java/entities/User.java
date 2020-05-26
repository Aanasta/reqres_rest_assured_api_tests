package entities;

import lombok.Getter;

@Getter
public class User {

    private String name;
    private String job;

    public User(String name, String job){
        this.name = name;
        this.job = job;
    }
}
