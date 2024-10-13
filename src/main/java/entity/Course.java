package entity;

import lombok.Data;

@Data
public class Course {

    String no;

    String name;

    String pno;

    String pnoName; // 先修课程名称

    int credit;

    String type;



    public Course(String no, String name, String pno, int credit , String type,String pnoName) {
        this.name = name;
        this.no = no;
        this.credit = credit;
        this.pno = pno;
        this.type = type;
        this.pnoName = pnoName;
    }

    public Course(String no, String name, String pno, int credit, String type) {
        this.no = no;
        this.name = name;
        this.pno = pno;
        this.credit = credit;
        this.type = type;
    }

    public Course() {

    }
}
