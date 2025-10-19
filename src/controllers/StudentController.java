package controllers;

import dao.StudentDAO;
import models.Student;
import ui.screen.panels.StudentPanel;
import utils.SessionManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;

public class StudentController {
   private StudentPanel view;
   private StudentDAO studentDAO ;

   public StudentController(StudentPanel view, JPanel mainUI){
       this.view = view;

       studentDAO = new StudentDAO();
       handleGetAllStudents();

       view.getRefreshBtn().addActionListener(e->handleGetAllStudents());


   }



   private  void handleAddNewStudent(Student s){
       studentDAO.addStudent(s);
       handleGetAllStudents();
   }

   public void handleGetAllStudents(){


       ArrayList<Student> students =studentDAO.getAllStudents(SessionManager.getCurrentAdmin().getHostelId());
       students.sort((s1, s2) -> {
           int year1 = Integer.parseInt(s1.getStudentId().substring(0, 2));
           int year2 = Integer.parseInt(s2.getStudentId().substring(0, 2));

           // Compare year first
           if (year1 != year2) return Integer.compare(year1, year2);

           // Extract last 3 digits for roll number
           int roll1 = Integer.parseInt(s1.getStudentId().replaceAll("\\D", "").substring(2));
           int roll2 = Integer.parseInt(s2.getStudentId().replaceAll("\\D", "").substring(2));

           return Integer.compare(roll1, roll2);
       });
    view.setStudents(students);
   }






}
