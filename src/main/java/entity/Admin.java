package entity;

import lombok.Data;

@Data
public class Admin {
    private String no;
    private String name;
    private String password;
    private int level;
}
