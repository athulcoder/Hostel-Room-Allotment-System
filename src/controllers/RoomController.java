package controllers;

import dao.RoomDAO;
import dao.StudentDAO;
import models.Room;
import ui.screen.panels.RoomPanel;
import utils.SessionManager;

import java.util.ArrayList;

public class RoomController {
    private RoomPanel roomPanel;
    private RoomDAO roomDAO;
    private StudentDAO studentDAO;
    public RoomController(RoomPanel roomPanel){
        this.roomPanel = roomPanel;
        roomDAO = new RoomDAO();
        studentDAO = new StudentDAO();

        roomPanel.setAvailableRoomsCard(roomDAO.getAvailableRooms().size());
        roomPanel.setTotalRoomCard(roomDAO.getAllRooms().size());
        roomPanel.setTotalStudents(studentDAO.getAllStudents(SessionManager.getCurrentAdmin().getHostelId()).size());
       //allotted rooms equal total - available
        roomPanel.setRoomAllottedCard(roomDAO.getAllRooms().size()-roomDAO.getAvailableRooms().size());

        roomPanel.getRefreshBtn().addActionListener(e->handleGetAllRooms());

        handleGetAllRooms();
    }



    private void handleGetAllRooms(){
        roomPanel.setAvailableRoomsCard(roomDAO.getAvailableRooms().size());
        roomPanel.setTotalRoomCard(roomDAO.getAllRooms().size());
        roomPanel.setTotalStudents(studentDAO.getAllStudents(SessionManager.getCurrentAdmin().getHostelId()).size());
        //allotted rooms equal total - available
        roomPanel.setRoomAllottedCard(roomDAO.getAllRooms().size()-roomDAO.getAvailableRooms().size());

        ArrayList<Room> rooms = roomDAO.getAllRooms();
        roomPanel.setRooms(rooms);

    }




}
