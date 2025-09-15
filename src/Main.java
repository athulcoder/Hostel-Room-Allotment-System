

import dao.StudentDAO;
import models.Student;
import utils.DatabaseInitializer;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        DatabaseInitializer.initializeDatabase();

        StudentDAO studentDAO = new StudentDAO();

        System.out.println(studentDAO.getStudentByStudentId("S134").getName());
        System.out.println(studentDAO.getStudentByStudentId("S134").getStudentId());
        System.out.println(studentDAO.getStudentByStudentId("S134").getDepartment());
        System.out.println(studentDAO.getStudentByStudentId("S134").getName());
        System.out.println(studentDAO.getStudentByStudentId("S134").getName());
        System.out.println(studentDAO.getStudentByStudentId("S134").getName());
        System.out.println(studentDAO.getStudentByStudentId("S134").getName());
        System.out.println(studentDAO.getStudentByStudentId("S134").getName());
        System.out.println(studentDAO.getStudentByStudentId("S134").getName());
        System.out.println(studentDAO.getStudentByStudentId("S134").getName());
    }

}