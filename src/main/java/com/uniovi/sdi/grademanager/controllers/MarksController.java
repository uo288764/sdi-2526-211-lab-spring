package com.uniovi.sdi.grademanager.controllers;

import com.uniovi.sdi.grademanager.entities.Mark;
import com.uniovi.sdi.grademanager.services.MarksService;
import com.uniovi.sdi.grademanager.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class MarksController {
    private final MarksService marksService;
    private final UsersService usersService;
    public MarksController(MarksService marksService, UsersService usersService) {
        this.marksService = marksService;
        this.usersService = usersService;
    }
    // Modificamos los siguientes metodos
    @GetMapping("/mark/add")
    public String getMark(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/add";
    }
    @PostMapping("/mark/add")
    public String setMark(@ModelAttribute Mark mark) {
        marksService.addMark(mark);
        return "mark/add";
    }
    @GetMapping(value = "/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/edit";
    }
    @PostMapping(value = "/mark/edit/{id}")
    public String setEdit(@ModelAttribute Mark mark, @PathVariable Long id) {
        Mark originalMark = marksService.getMark(id);
        originalMark.setScore(mark.getScore());
        originalMark.setDescription(mark.getDescription());
        marksService.addMark(originalMark);
        return "redirect:/mark/details/" + id;
    }
    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id) {
        marksService.deleteMark(id);
        return "redirect:/mark/list";
    }
    @GetMapping ("/mark/list")
    public String getList(Model model) {
        model.addAttribute("marksList", marksService.getMarks());
        return "mark/list";
    }
    @GetMapping("/mark/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        return "mark/details";
    }



    @GetMapping("/mark/list/update")
    public String updateList(Model model){
        model.addAttribute("marksList", marksService.getMarks() );
        return "mark/list :: marksTable";
    }

}