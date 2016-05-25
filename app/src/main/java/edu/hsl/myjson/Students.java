package edu.hsl.myjson;

import java.util.List;

/**
 * Created by Administrator on 2016/5/25.
 */
public class Students {
    List<StudentsData> students;
    String             calss;

    public List<StudentsData> getStudents() {
        return students;
    }

    public void setStudents(List<StudentsData> students) {
        this.students = students;
    }

    public String getCalss() {
        return calss;
    }

    public void setCalss(String calss) {
        this.calss = calss;
    }

    //    @Override
//    public String toString() {
//        return "Students[{name="+name
//    }
}
