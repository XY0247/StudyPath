package entity;

import lombok.Data;


@Data
public class Student {
    String no;
    String name;
    String sex;
    int age;
    String dept;
    String password;
    int level;
    String phone;
    public Student() {

    }

    public Student(String no, String name, String sex, int age, String dept, String password, int level, String phone) {
        this.no = no;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.dept = dept;
        this.password = password;
        this.level = level;
        this.phone = phone;
    }
}
