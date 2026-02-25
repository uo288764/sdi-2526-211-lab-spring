package com.uniovi.sdi.grademanager.services;

import com.uniovi.sdi.grademanager.entities.Mark;
import com.uniovi.sdi.grademanager.entities.User;
import com.uniovi.sdi.grademanager.repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    public Mark getMark(Long id) {
        return marksRepository.findById(id).orElse(null);
    }

    public List<Mark> getMarks() {
        List<Mark> marks = new ArrayList<>();
        marksRepository.findAll().forEach(marks::add);
        return marks;
    }

    public void addMark(Mark mark) {
        marksRepository.save(mark);
    }

    public void deleteMark(Long id) {
        marksRepository.deleteById(id);
    }

    public void setMarkResend(boolean resend, Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        Mark mark = marksRepository.findById(id).orElse(null);

        if (mark != null && mark.getUser().getDni().equals(dni)) {
            marksRepository.updateResend(resend, id);
        }
    }

    public List<Mark> searchMarksByDescriptionAndNameForUser(String searchText, User user) {
        List<Mark> marks = new ArrayList<>();
        searchText = "%"+searchText+"%";
        if (user.getRole().equals("ROLE_STUDENT")) {
            marks = marksRepository.searchByDescriptionNameAndUser(searchText, user);
        }
        if (user.getRole().equals("ROLE_PROFESSOR")) {
            marks = marksRepository.searchByDescriptionAndName(searchText);
        }
        return marks;
    }

    public List<Mark> getMarksForUser(User user) {
        List<Mark> marks = new ArrayList<>();

        if ("ROLE_STUDENT".equals(user.getRole())) {
            marks = marksRepository.findAllByUser(user);
        } else if ("ROLE_PROFESSOR".equals(user.getRole())) {
        marks = getMarks();
    }

        return marks;
    }
}