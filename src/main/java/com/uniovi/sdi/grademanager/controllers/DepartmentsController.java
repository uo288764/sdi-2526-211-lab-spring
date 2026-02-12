package com.uniovi.sdi.grademanager.controllers;

import com.uniovi.sdi.grademanager.entities.Department;
import com.uniovi.sdi.grademanager.services.DepartmentsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class DepartmentsController {

    @Autowired //Inyectar el servicio
    private DepartmentsService departmentsService;

    @GetMapping(value = "/department/add")
    public String getDepartment() {
        return "department/add";
    }

    @PostMapping("/department/add")
    public String setDepartment(@ModelAttribute Department department) {
        departmentsService.addDepartment(department);
        return "redirect:/departments";
    }

    @RequestMapping("/department/delete/{code}")
    public String deleteDepartment(@PathVariable Long code) {
        departmentsService.deleteDepartment(code);
        return "redirect:/departments";
    }

    @RequestMapping(value="/departments", method = RequestMethod.GET)
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

    @PostMapping(value="/department/edit/{code}")
    public String setEdit(@ModelAttribute Department department, @PathVariable Long code){
        department.setCode(code);
        departmentsService.updateDepartment(department);
        return "redirect:/department/details/"+code;
    }
}