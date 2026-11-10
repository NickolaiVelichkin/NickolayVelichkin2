package hw7;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentRegistryTest {

    @Test
    void findStudentsByGradeRangeWorks() {
        Map<Integer, Student> map = new HashMap<>();
        Student s1 = new Student(1, "Ivan", 5.0);
        Student s2 = new Student(2, "Anna", 8.5);
        Student s3 = new Student(3, "Oleg", 10.0);
        map.put(s1.getId(), s1);
        map.put(s2.getId(), s2);
        map.put(s3.getId(), s3);

        List<Student> res = StudentRegistry.findStudentsByGradeRange(map, 5.0, 8.5);
        assertEquals(2, res.size());
        assertTrue(res.contains(s1));
        assertTrue(res.contains(s2));

        assertTrue(StudentRegistry.findStudentsByGradeRange(new HashMap<>(), 0.0, 10.0).isEmpty());
        assertThrows(IllegalArgumentException.class, () -> StudentRegistry.findStudentsByGradeRange(map, 9.0, 5.0));
        assertThrows(IllegalArgumentException.class, () -> StudentRegistry.findStudentsByGradeRange(map, -1.0, 5.0));
        assertThrows(IllegalArgumentException.class, () -> StudentRegistry.findStudentsByGradeRange(map, 0.0, 11.0));
    }

    @Test
    void getTopNStudentsByIdWorks() {
        NavigableMap<Integer, Student> treeMap = new TreeMap<>(Collections.reverseOrder());
        Student s1 = new Student(1, "Ivan", 5.0);
        Student s2 = new Student(5, "Anna", 8.5);
        Student s3 = new Student(3, "Oleg", 10.0);
        treeMap.put(s1.getId(), s1);
        treeMap.put(s2.getId(), s2);
        treeMap.put(s3.getId(), s3);

        assertEquals(List.of(s2, s3), StudentRegistry.getTopNStudentsById(treeMap, 2));
        assertEquals(List.of(s2, s3, s1), StudentRegistry.getTopNStudentsById(treeMap, 10));
        assertTrue(StudentRegistry.getTopNStudentsById(treeMap, 0).isEmpty());
        assertThrows(IllegalArgumentException.class, () -> StudentRegistry.getTopNStudentsById(treeMap, -1));
    }
}