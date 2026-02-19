package com.uniovi.sdi.grademanager.controllers;

import com.uniovi.sdi.grademanager.entities.Department;
import com.uniovi.sdi.grademanager.services.DepartmentsService;
import com.uniovi.sdi.grademanager.validators.AddOrEditDepartmentValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class DepartmentsController {

    private final DepartmentsService departmentsService;
    private final AddOrEditDepartmentValidator addOrEditDepartmentValidator;

    public DepartmentsController(DepartmentsService departmentsService,
                                 AddOrEditDepartmentValidator addOrEditDepartmentValidator) {
        this.departmentsService = departmentsService;
        this.addOrEditDepartmentValidator = addOrEditDepartmentValidator;
    }

    @GetMapping(value = "/department/add")
    public String getDepartment(Model model) {
        model.addAttribute("department", new Department());
        return "department/add";
    }

    @PostMapping("/department/add")
    public String setDepartment(@ModelAttribute Department department, BindingResult result) {
        addOrEditDepartmentValidator.validate(department, result);
        if (result.hasErrors()) {
            return "department/add";
        }
        departmentsService.addDepartment(department);
        return "redirect:/departments";
    }

    @RequestMapping("/department/delete/{code}")
    public String deleteDepartment(@PathVariable Long code) {
        departmentsService.deleteDepartment(code);
        return "redirect:/departments";
    }

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    public String getList(Model model) {
        model.addAttribute("departmentList", departmentsService.getDepartments());
        return "department/list";
    }

    @GetMapping("/department/details/{code}")
    public String getDetail(Model model, @PathVariable Long code) {
        model.addAttribute("department", departmentsService.getDepartment(code));
        return "department/details";
    }

    @GetMapping(value = "/department/edit/{code}")
    public String getEdit(Model model, @PathVariable Long code) {
        model.addAttribute("department", departmentsService.getDepartment(code));
        return "department/edit";
    }

    @PostMapping(value = "/department/edit/{code}")
    public String setEdit(@ModelAttribute Department department, BindingResult result, @PathVariable Long code) {
        department.setCode(code);
        addOrEditDepartmentValidator.validate(department, result);
        if (result.hasErrors()) {
            return "department/edit";
        }
        departmentsService.updateDepartment(department);
        return "redirect:/department/details/" + code;
    }
}