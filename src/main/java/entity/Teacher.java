package entity;

import lombok.Data;

@Data
public class Teacher {
    String no;
    String name;
    String sex;
    int age;
    String teb;
    String tpt;
    int level;
    String password;

    public Teacher() {
    }

    public Teacher(String no, String name, String sex, int age, String teb, String tpt, int level, String password) {
        this.no = no;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.teb = teb;
        this.tpt = tpt;
        this.level = level;
        this.password = password;
    }
}
