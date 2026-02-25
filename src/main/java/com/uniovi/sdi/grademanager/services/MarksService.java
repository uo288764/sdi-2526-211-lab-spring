package com.uniovi.sdi.grademanager.services;

import com.uniovi.sdi.grademanager.entities.Mark;
import com.uniovi.sdi.grademanager.repositories.MarksRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class MarksService {
    @Autowired
    private MarksRepository marksRepository;

    private final HttpSession httpSession;

    public MarksService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public Mark getMark(Long id) {
        return marksRepository.findById(id).isPresent() ? marksRepository.findById(id).get() : new Mark();
    }

    @PostConstruct
    public List<Mark> getMarks() {
        List<Mark> marks = new ArrayList<Mark>();
        marksRepository.findAll().forEach(marks::add);
        return marks;
    }


    public void addMark(Mark mark) {
        marksRepository.save(mark);
    }
    public void deleteMark(Long id) {
        marksRepository.deleteById(id);
    }
}