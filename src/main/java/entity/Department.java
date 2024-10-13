package entity;

import lombok.Data;

@Data
public class Department {
    String name;
    String no;
    String manager;

    public Department(String name, String no, String manager) {
        this.name = name;
        this.no = no;
        this.manager = manager;
    }

    public Department() {

    }

    public Department(Department departments) {
        this.name = departments.name;
        this.no = departments.no;
        this.manager = departments.manager;
    }
}
