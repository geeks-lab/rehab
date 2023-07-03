package org.example;

import java.util.List;

public class Courses {

    private final List<Course> courses;

    public Courses(List<Course> courses) {
        this.courses = courses;
    }

    public double multiplyCreditAndCourseGrade() {
        return courses.stream()
                .mapToDouble(Course::multiplyCreditAndCourseGrade)
                .sum();
        /**
         * as-is(2)
         * double mulitpliedCreditAndCourseGrade = 0;
         *         for (Course course : courses) {
         *             mulitpliedCreditAndCourseGrade += course.multiplyCreditAndCourseGrade();
         *         }
         *         return mulitpliedCreditAndCourseGrade;
         *
         * as-is(1)
         * for (Course course : courses) {
         *             mulitpliedCreditAndCourseGrade += course.getCredit() * course.getGradeToNumber();
         *         }
         * 이전에는 getter를 통해서 정보를 가져와서 처리했는데 이렇게 되면 뭔가 수정이 생기면 게터로 가지고 온
         * 메서드마다 수정이 필요하게 되므로 응집도가 약하다고 볼 수 있다.
         * 때문에 해당 정보를 가진 객체에게 메세지를 전달해서 (작업을 위임해서)
         * 객체에서 작업을 처리하게 하여 변화에 유연한 코드로 리팩토링 했다.
         * */

    }

    public int calculateTotalCompletedCredit() {
        return courses.stream()
                .mapToInt(Course::getCredit)
                .sum();

    }
}
