package com.uniovi.sdi.grademanager.controllers;

import com.uniovi.sdi.grademanager.entities.Department;
import com.uniovi.sdi.grademanager.services.DepartmentsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@ResponseBody
public class DepartmentsController {

    @Autowired
    private DepartmentsService departmentsService;

    @GetMapping(value = "/department/add")
    public String getDepartment() {
        return "añadir";
    }

    @PostMapping("/department/add")
    public String setDepartment(@ModelAttribute Department department) {
        departmentsService.addDepartment(department);
        return "Department Added";
    }

    @RequestMapping("/department/delete/{code}")
    public String deleteDepartment(@PathVariable Long code) {
        departmentsService.deleteDepartment(code);
        return "Department deleted";
    }

    @RequestMapping(value="/departments", method = RequestMethod.GET)
    public String getList() {
        return departmentsService.getDepartments().toString();
    }

    @GetMapping("/department/details/{code}")
    public String getDetail(@PathVariable Long code) {
        return departmentsService.getDepartment(code).toString();
    }

    @GetMapping(value = "/department/edit/{code}")
    public String getEdit(@PathVariable Long code) {
        return departmentsService.getDepartment(code).toString();
    }

    @PostMapping(value="/department/edit/{code}")
    public String setEdit(@ModelAttribute Department department, @PathVariable Long code){
        department.setCode(code);
        departmentsService.addDepartment(department);
        return "Department edited";
    }
}