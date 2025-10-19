package controllers;

import dao.RoomDAO;
import dao.StudentDAO;
import ui.screen.panels.RoomPanel;
import utils.SessionManager;

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
    }

}
