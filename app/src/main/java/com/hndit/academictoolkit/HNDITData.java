package com.hndit.academictoolkit;

import java.util.ArrayList;
import java.util.List;

public class HNDITData {

    public static final int CREDITS_SEMESTER_1 = 20;
    public static final int CREDITS_SEMESTER_2 = 20;
    public static final int CREDITS_SEMESTER_3 = 20;
    public static final int CREDITS_SEMESTER_4 = 20;
    public static final int TOTAL_PROGRAM_CREDITS = 80;

    public static final List<Subject> SEMESTER_1 = new ArrayList<>();
    public static final List<Subject> SEMESTER_2 = new ArrayList<>();
    public static final List<Subject> SEMESTER_3 = new ArrayList<>();
    public static final List<Subject> SEMESTER_4 = new ArrayList<>();

    static {
        SEMESTER_1.add(new Subject("HNDIT1012", "Visual Application Programming", 1, 4, 40));
        SEMESTER_1.add(new Subject("HNDIT1022", "Web Design", 1, 4, 40));
        SEMESTER_1.add(new Subject("HNDIT1032", "Computer and Network Systems", 1, 3, 40));
        SEMESTER_1.add(new Subject("HNDIT1042", "Information Management and Information Systems", 1, 4, 40));
        SEMESTER_1.add(new Subject("HNDIT1052", "ICT Project (Individual)", 1, 3, 40));
        SEMESTER_1.add(new Subject("HNDIT1062", "Communication Skills", 1, 2, 40));

        SEMESTER_2.add(new Subject("HNDIT2012", "Fundamentals of Programming", 2, 4, 40));
        SEMESTER_2.add(new Subject("HNDIT2022", "Software Development", 2, 3, 40));
        SEMESTER_2.add(new Subject("HNDIT2032", "System Analysis and Design", 2, 3, 40));
        SEMESTER_2.add(new Subject("HNDIT2042", "Data Communication and Computer Networks", 2, 3, 40));
        SEMESTER_2.add(new Subject("HNDIT2052", "Principles of User Interface Design", 2, 3, 40));
        SEMESTER_2.add(new Subject("HNDIT2062", "ICT Project (Group)", 2, 2, 40));
        SEMESTER_2.add(new Subject("HNDIT2072", "Technical Writing", 2, 2, 40));
        SEMESTER_2.add(new Subject("HNDIT2082", "Human Value & Professional Ethics", 2, 0, 40));

        SEMESTER_3.add(new Subject("HNDIT3012", "Object Oriented Programming", 3, 4, 40));
        SEMESTER_3.add(new Subject("HNDIT3022", "Web Programming", 3, 4, 40));
        SEMESTER_3.add(new Subject("HNDIT3032", "Data Structures and Algorithms", 3, 2, 40));
        SEMESTER_3.add(new Subject("HNDIT3042", "Database Management Systems", 3, 3, 40));
        SEMESTER_3.add(new Subject("HNDIT3052", "Operating Systems", 3, 2, 40));
        SEMESTER_3.add(new Subject("HNDIT3062", "Information and Computer Security", 3, 2, 40));
        SEMESTER_3.add(new Subject("HNDIT3072", "Statistics for IT", 3, 3, 40));

        SEMESTER_4.add(new Subject("HNDIT4012", "Software Engineering", 4, 3, 40));
        SEMESTER_4.add(new Subject("HNDIT4022", "Software Quality Assurance", 4, 3, 40));
        SEMESTER_4.add(new Subject("HNDIT4032", "IT Project Management", 4, 3, 40));
        SEMESTER_4.add(new Subject("HNDIT4042", "Professional World", 4, 3, 40));
        SEMESTER_4.add(new Subject("HNDIT4052", "Individual Project", 4, 2, 40));
        SEMESTER_4.add(new Subject("HNDIT4212", "Machine Learning", 4, 3, 40));
        SEMESTER_4.add(new Subject("HNDIT4252", "Mobile Application Development (Elective)", 4, 3, 40));
        SEMESTER_4.add(new Subject("HNDIT4262", "Business Analysis Practice (Elective)", 4, 3, 40));
    }

    public static List<Subject> getSubjectsForSemester(int semester) {
        switch (semester) {
            case 1:
                return SEMESTER_1;
            case 2:
                return SEMESTER_2;
            case 3:
                return SEMESTER_3;
            case 4:
                return SEMESTER_4;
            default:
                return new ArrayList<>();
        }
    }

    public static List<Subject> getAllSubjects() {
        List<Subject> all = new ArrayList<>();
        all.addAll(SEMESTER_1);
        all.addAll(SEMESTER_2);
        all.addAll(SEMESTER_3);
        all.addAll(SEMESTER_4);
        return all;
    }

    public static int getTotalCreditsForSemester(int semester) {
        switch (semester) {
            case 1:
                return CREDITS_SEMESTER_1;
            case 2:
                return CREDITS_SEMESTER_2;
            case 3:
                return CREDITS_SEMESTER_3;
            case 4:
                return CREDITS_SEMESTER_4;
            default:
                return 0;
        }
    }

    public static int calculateTotalCredits(List<Subject> subjects) {
        if (subjects == null || subjects.isEmpty()) {
            return 0;
        }

        int totalCredits = 0;
        boolean hasMobileDev = false;
        boolean hasBusinessAnalysis = false;

        for (Subject subject : subjects) {
            totalCredits += subject.getCredits();

            if ("HNDIT4252".equalsIgnoreCase(subject.getCode())) {
                hasMobileDev = true;
            } else if ("HNDIT4262".equalsIgnoreCase(subject.getCode())) {
                hasBusinessAnalysis = true;
            }
        }

        if (hasMobileDev && hasBusinessAnalysis) {
            totalCredits -= 2;
        }

        return totalCredits;
    }

    public static int getTotalProgramCredits() {
        return TOTAL_PROGRAM_CREDITS;
    }

    public static final String[] GRADE_LABELS = {
            "N/A (Skip / Not Taken)", "A+ / A (4.0)", "A- (3.7)", "B+ (3.3)", "B (3.0)",
            "B- (2.7)", "C+ (2.3)", "C (2.0)", "C- (1.7)", "D+ (1.3)", "D (1.0)", "E (0.0)"
    };

    public static final double[] GRADE_POINTS = {
            -1, 4.0, 3.7, 3.3, 3.0, 2.7, 2.3, 2.0, 1.7, 1.3, 1.0, 0.0
    };
}