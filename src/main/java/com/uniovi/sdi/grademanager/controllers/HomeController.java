package com.uniovi.sdi.grademanager.controllers;

import com.uniovi.sdi.grademanager.entities.Mark;
import com.uniovi.sdi.grademanager.entities.User;
import com.uniovi.sdi.grademanager.services.MarksService;
import com.uniovi.sdi.grademanager.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class HomeController {

    private final UsersService usersService;
    private final MarksService marksService;

    @Autowired
    public HomeController(UsersService usersService, MarksService marksService) {
        this.usersService = usersService;
        this.marksService = marksService;
    }

    @GetMapping("/home")
    public String home(Model model, Pageable pageable, Principal principal) {
        String dni = principal.getName();
        User user = usersService.getUserByDni(dni);
        Page<Mark> marks = marksService.getMarksForUser(pageable, user);
        model.addAttribute("marksList", marks.getContent());
        model.addAttribute("page", marks);
        return "home";
    }

    @GetMapping("/home/update")
    public String updateHome(Model model, Pageable pageable, Principal principal) {
        String dni = principal.getName();
        User user = usersService.getUserByDni(dni);
        Page<Mark> marks = marksService.getMarksForUser(pageable, user);
        model.addAttribute("marksList", marks.getContent());
        return "fragments/tableMarks :: marksTableContent";
    }


}
