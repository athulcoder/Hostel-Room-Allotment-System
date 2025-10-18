package controllers;

import dao.StudentDAO;
import ui.screen.panels.StudentPanel;
import utils.SessionManager;

import javax.swing.*;

public class StudentController {
   private StudentPanel view;
   private StudentDAO studentDAO ;

   public StudentController(StudentPanel view, JPanel mainUI){
       this.view = view;

       studentDAO = new StudentDAO();
       view.setStudents( studentDAO.getAllStudents(SessionManager.getCurrentAdmin().getHostelId()));
   }
}
