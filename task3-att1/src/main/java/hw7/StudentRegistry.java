package hw7;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

public class StudentRegistry {

    public static List<Student> findStudentsByGradeRange(
            Map<Integer, Student> students,
            double minGrade,
            double maxGrade) {
        if (students == null || minGrade > maxGrade || minGrade < 0.0 || maxGrade > 10.0) {
            throw new IllegalArgumentException();
        }
        List<Student> result = new ArrayList<>();
        for (Student s : students.values()) {
            if (s.getGrade() >= minGrade && s.getGrade() <= maxGrade) {
                result.add(s);
            }
        }
        return result;
    }

    public static List<Student> getTopNStudentsById(
            NavigableMap<Integer, Student> students,
            int n) {
        if (students == null || n < 0) {
            throw new IllegalArgumentException();
        }
        List<Student> result = new ArrayList<>();
        int count = 0;
        for (Student s : students.values()) {
            if (count == n) {
                break;
            }
            result.add(s);
            count++;
        }
        return result;
    }
}