package org.example;

import java.util.List;

public class GradeCalculator {
    //1급 collection
    private final Courses courses;

    public GradeCalculator(List<Course> courses) {
        this.courses = new Courses(courses);
    }

    //평균학점 계산 방법 = (학점수×교과목 평점)의 합계 / 수강신청 총학점 수
    public double calculateGrade() {
        // (학점수*교과목 평점)의 합계
        double totalMultipliedCreditAndCourseGrade = courses.multiplyCreditAndCourseGrade();
        // 수강신청 총학점 수
        int totalCompletedCredit = courses.calculateTotalCompletedCredit();

        return totalMultipliedCreditAndCourseGrade / totalCompletedCredit;
    }
}
