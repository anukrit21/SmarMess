package com.demoApp.campus_module.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampusAnalyticsDTO {
    private int totalEvents;
    private Long totalStudents;
    private Long totalFaculty;
    private Long totalCourses;
    private Map<String, Long> studentsByDepartment;
    private Map<String, Long> coursesByDepartment;
    private Map<String, Long> facultyByDepartment;
    private Map<String, Double> averageGPA;
    private Map<String, Long> enrollmentBySemester;
    private Map<String, Long> graduationByYear;
    private Map<String, Long> eventTypeDistribution;
    private Map<String, Long> locationUtilization;
} 