package com.uniovi.sdi.grademanager.repositories;

import com.uniovi.sdi.grademanager.entities.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DepartmentsRepository extends CrudRepository<Department, Long> {
    Optional <Department> findByCodeString(String codeString);

}
