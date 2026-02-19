package com.uniovi.sdi.grademanager.validators;

import com.uniovi.sdi.grademanager.entities.Department;
import com.uniovi.sdi.grademanager.services.DepartmentsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddOrEditDepartmentValidator implements Validator {
    private final DepartmentsService departmentsService;

    public AddOrEditDepartmentValidator(DepartmentsService departmentsService) {
        this.departmentsService = departmentsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Department.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Department department = (Department) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codeString", "Error.empty");
        if (department.getCodeString() == null || department.getCodeString().length() != 9) {
            errors.rejectValue("codeString", "Error.department.codeString.length");
        } else {
            if (!Character.isLetter(department.getCodeString().charAt(department.getCodeString().length() - 1))) {
                errors.rejectValue("codeString", "Error.department.codeString.letter");
            }
            Department existing = departmentsService.getDepartmentByCodeString(department.getCodeString());
            if (existing != null && !existing.getCode().equals(department.getCode())) {
                errors.rejectValue("codeString", "Error.department.codeString.duplicate");
            }
        }
        if (department.getName() == null || department.getName().isBlank()) {
            errors.rejectValue("name", "Error.department.name.length");
        }
        if (department.getFaculty() == null || department.getFaculty().isBlank()) {
            errors.rejectValue("faculty", "Error.department.faculty.length");
        }
        if (department.getPhone() == null || department.getPhone().isBlank()) {
            errors.rejectValue("phone", "Error.department.phone.length");
        }
    }
}