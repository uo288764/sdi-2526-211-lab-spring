package com.uniovi.sdi.grademanager.services;

import com.uniovi.sdi.grademanager.entities.Department;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class DepartmentsService {
    private final List<Department> departmentsList = new LinkedList<>();

    @PostConstruct
    public void init() {
        departmentsList.add(new Department(1L, "Computer Science", "Engineering", "985-123-456", 25));
        departmentsList.add(new Department(2L, "Mathematics", "Sciences", "985-123-457", 18));
    }

    public List<Department> getDepartments() {
        return departmentsList;
    }

    public Department getDepartment(Long code) {
        return departmentsList.stream()
                .filter(department -> department.getCode().
                        equals(code)).findFirst().orElse(null);
    }

    public void addDepartment(Department department) {
        if (department.getCode() == null) {
            department.setCode(departmentsList.getLast().getCode() + 1);
        }
        departmentsList.add(department);
    }

    public void deleteDepartment(Long code) {
        departmentsList.removeIf(department -> department.getCode().equals(code));
    }
}