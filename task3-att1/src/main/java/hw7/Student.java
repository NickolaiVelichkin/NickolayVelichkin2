package hw7;

import java.util.Objects;

public final class Student {
    private final int id;
    private final String name;
    private final double grade;

    public Student(int id, String name, double grade) {
        if (id <= 0 || name == null || name.isBlank() || grade < 0.0 || grade > 10.0 || Double.isNaN(grade)) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id
                && Double.compare(student.grade, grade) == 0
                && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade);
    }
}