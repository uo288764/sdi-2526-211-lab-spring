package com.uniovi.sdi.grademanager.services;

import com.uniovi.sdi.grademanager.entities.Department;
import com.uniovi.sdi.grademanager.repositories.DepartmentsRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentsService {

    @Autowired
    private DepartmentsRepository departmentsRepository;

    @PostConstruct
    public void init() {
        // Inicializar con datos de prueba solo si la base de datos está vacía
        if (departmentsRepository.count() == 0) {
            departmentsRepository.save(new Department(null, "Computer Science", "Engineering", "985-123-456", 25));
            departmentsRepository.save(new Department(null, "Mathematics", "Sciences", "985-123-457", 18));
        }
    }

    public List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();
        departmentsRepository.findAll().forEach(departments::add);
        return departments;
    }

    public Department getDepartment(Long code) {
        return departmentsRepository.findById(code).orElse(null);
    }

    public void addDepartment(Department department) {
        departmentsRepository.save(department);
    }

    public void updateDepartment(Department department) {
        departmentsRepository.save(department);
    }

    public void deleteDepartment(Long code) {
        departmentsRepository.deleteById(code);
    }

    public Department getDepartmentByCodeString(String codeString) {
        return departmentsRepository.findByCodeString(codeString).orElse(null);
    }
}