import com.hanson.design.behavior.TemplateMethod;

import java.util.*;

/**
 * Created by Hanson on 2020/2/5 15:48
 * 子类不会继承父类的成员变量
 */
public class FieldTest {

    public static void main(String[] args) {
        ITeacher historyTeacher = new HistoryTeacher();
        ITeacher mathTeacher = new MathTeacher();
        ITeacher englishTeacher = new EnglishTeacher();

        Student student1 = new Student("小明","0001");
        Student student2 = new Student("韩梅梅","0002");

        Timetable timetable = new Timetable();
        timetable.addTeacher(historyTeacher).addTeacher(mathTeacher);

        historyTeacher.addStudent(student1).addStudent(student2);
        mathTeacher.addStudent(student1);

        //day one
        timetable.schoolTime();

        //modify name
        student1.setName("胡小明");

        //day two
        englishTeacher.addStudent(student1).addStudent(student2);
        HistoryInternshipTeacher historyInternshipTeacher = new HistoryInternshipTeacher();
        //加小强
        Student student = new Student("小强","003");
        historyInternshipTeacher.addStudent(student);
        timetable.addTeacher(englishTeacher).addTeacher(historyInternshipTeacher);

        timetable.schoolTime();

    }
}

interface ITeacher{
    List<Student> getMyStudents();
    ITeacher addStudent(Student student);
    Subject subject();
    double teach();
}

abstract class AbstractTeacher implements ITeacher {
    private List<Student> myStudents = new ArrayList<>();

    @Override
    public List<Student> getMyStudents() {
        return myStudents;
    }

    @Override
    public ITeacher addStudent(Student student) {
        myStudents.add(student);
        return this;
    }

    @Override
    public double teach(){
        return Math.random() * 10;
    }
}

class HistoryTeacher extends AbstractTeacher implements ITeacher{
    @Override
    public Subject subject() {
        return Subject.HISTORY;
    }
}

class MathTeacher extends AbstractTeacher implements ITeacher{
    @Override
    public Subject subject() {
        return Subject.MATH;
    }
}

class EnglishTeacher extends AbstractTeacher implements ITeacher{
    @Override
    public Subject subject() {
        return Subject.ENGLISH;
    }
}

class HistoryInternshipTeacher extends HistoryTeacher implements ITeacher{
    @Override
    public double teach() {
        return Math.random() * 5;
    }
}

class Timetable{
    //每天不同的老师上课
    private List<ITeacher> teachers = new ArrayList<ITeacher>();

    public Timetable addTeacher(ITeacher teacher){
        teachers.add(teacher);
        return this;
    }

    public void schoolTime(){
        teachers.forEach(teacher ->{
            teacher.getMyStudents().stream().forEach(student ->{
                student.learn(teacher);
            });
        });
    }
}


class Student{
    private String name;
    private String no;
    private Map<String,Double> scores = new HashMap<>();

    public void learn(ITeacher teacher){
        Subject subject = teacher.subject();
        double score = teacher.teach();
        String key = no+":"+subject.getCode();
        if(scores.containsKey(key)){
            scores.put(key,scores.get(key)+score);
        }else{
            scores.put(key,score);
        }
        System.out.println(name+":"+no+" learn:"+subject.getName()+" score:"+scores.get(key));
    }

    public Student(String name, String no) {
        this.name = name;
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}


enum Subject{
    HISTORY("历史",1),
    MATH("数学",2),
    ENGLISH("英语",3),
    ;

    private String name;
    private int code;

    Subject(String name,int code){
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
