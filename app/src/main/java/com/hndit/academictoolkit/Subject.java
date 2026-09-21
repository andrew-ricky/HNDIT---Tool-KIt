package com.hndit.academictoolkit;

public class Subject {

    private final String code;
    private final String name;
    private final int semester;
    private final int credits;
    private final int caWeightage;

    public Subject(String code, String name, int semester, int credits, int caWeightage) {
        this.code = code;
        this.name = name;
        this.semester = semester;
        this.credits = credits;
        this.caWeightage = caWeightage;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getSemester() {
        return semester;
    }

    public int getCredits() {
        return credits;
    }

    public int getCaWeightage() {
        return caWeightage;
    }

    public int getFinalExamWeightage() {
        return 100 - caWeightage;
    }

    @Override
    public String toString() {
        return code + " - " + name;
    }
}