package controllers;

import dao.StudentDAO;
import models.Student;
import ui.screen.components.StudentForm;
import utils.SessionManager;

import javax.swing.*;
import java.time.LocalDateTime;

public class StudentFormController {
    StudentForm view;
    StudentDAO studentDAO;

    public StudentFormController(StudentForm form){
        this.view = form;
        studentDAO = new StudentDAO();

        form.getSaveBtn().addActionListener(e->handleAddNewStudent());
        form.getUpdateBtn().addActionListener(e->handleUpdateStudent());
        form.getDeleteBtn().addActionListener(e->handleDeleteStudent());
    }



    private  void handleUpdateStudent(){
        Student newStudent = new Student();
        newStudent.setStudentId(view.getStudentIdField().getText());
        newStudent.setName(view.getNameField().getText());
        newStudent.setAge(Integer.parseInt(view.getAgeField().getText()));
        newStudent.setGender(String.valueOf(view.getGenderCombo().getSelectedItem()));
        newStudent.setDepartment(String.valueOf(view.getDeptCombo().getSelectedItem()));
        newStudent.setAcademicYear(String.valueOf(view.getYearCombo().getSelectedItem()));
        newStudent.setContactNumber(view.getContactNumberField().getText());
        newStudent.setEmail(view.getEmailField().getText());
        newStudent.setAssignedRoom(view.getRoomField().getText());
        newStudent.setDateOfAdmission(LocalDateTime.now());
        newStudent.setGuardianName(view.getGuardianNameField().getText());
        newStudent.setGuardianPhone(view.getGuardianPhoneField().getText());
        newStudent.setSleepType(String.valueOf(view.getSleepTypeCombo().getSelectedItem()));
        newStudent.setPreferredRoomType(String.valueOf(view.getRoomTypeCombo().getSelectedItem()));
        newStudent.setHostelId(SessionManager.getCurrentAdmin().getHostelId());

        System.out.println(newStudent.getDepartment());

        if(!studentDAO.updateStudent(newStudent)) JOptionPane.showMessageDialog(null,"Error Occurred while updating student! check the studentId ");
        else  {
            JOptionPane.showMessageDialog(null,"Student Updated ");
            view.getDialog().dispose();

        }

    }
    private  void handleAddNewStudent(){
        Student newStudent = new Student();

        if(view.getNameField().getText().isEmpty() || view.getNameField().getText().isEmpty() || view.getStudentIdField().getText().isEmpty() || view.getContactNumberField().getText().isEmpty() || view.getEmailField().getText().isEmpty()||view.getGuardianNameField().getText().isEmpty() ||view.getGuardianPhoneField().getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"All fields are required except room number","Warning",JOptionPane.WARNING_MESSAGE);
        }

        else{


        newStudent.setStudentId(view.getStudentIdField().getText());
        newStudent.setName(view.getNameField().getText());
        newStudent.setAge(Integer.parseInt(view.getAgeField().getText()));
        newStudent.setGender(String.valueOf(view.getGenderCombo().getSelectedItem()));
        newStudent.setDepartment(String.valueOf(view.getDeptCombo().getSelectedItem()));
        newStudent.setAcademicYear(String.valueOf(view.getYearCombo().getSelectedItem()));
        newStudent.setContactNumber(view.getContactNumberField().getText());
        newStudent.setEmail(view.getEmailField().getText());
        newStudent.setAssignedRoom(view.getRoomField().getText());
        newStudent.setDateOfAdmission(LocalDateTime.now());
        newStudent.setGuardianName(view.getGuardianNameField().getText());
        newStudent.setGuardianPhone(view.getGuardianPhoneField().getText());
        newStudent.setSleepType(String.valueOf(view.getSleepTypeCombo().getSelectedItem()));
        newStudent.setPreferredRoomType(String.valueOf(view.getRoomTypeCombo().getSelectedItem()));
        newStudent.setHostelId(SessionManager.getCurrentAdmin().getHostelId());

        System.out.println(newStudent.getDepartment());




            if(!studentDAO.addStudent(newStudent)) JOptionPane.showMessageDialog(null,"Student with given Id already exists","already Exists",JOptionPane.ERROR_MESSAGE);
            else  {
                JOptionPane.showMessageDialog(null,"Student added to the hostel :) ", "Student Added",JOptionPane.INFORMATION_MESSAGE);
                view.getDialog().dispose();

            }
        }

    }



    private void handleDeleteStudent(){

        int choice =JOptionPane.showConfirmDialog(null,"Student will be removed from the hostel. are you sure? ","Delete Student",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

        switch (choice){
            case JOptionPane.OK_OPTION: studentDAO.deleteStudent(view.getStudentIdField().getText());
                                        JOptionPane.showMessageDialog(null,"Student deleted !");
                                        break;
            case JOptionPane.CANCEL_OPTION: break;
        }

        view.getDialog().dispose();
    }
    }


