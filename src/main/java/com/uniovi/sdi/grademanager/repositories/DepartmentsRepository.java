package com.uniovi.sdi.grademanager.repositories;

import com.uniovi.sdi.grademanager.entities.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentsRepository extends CrudRepository<Department, Long> {
}
