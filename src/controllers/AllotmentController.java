package controllers;

import dao.RoomDAO;
import dao.StudentDAO;
import models.Student;
import service.AllotmentEngine;
import ui.screen.panels.AllotmentPanel;
import utils.SessionManager;

import javax.swing.*;
import java.util.List;

public class AllotmentController {
    private AllotmentPanel view;
    private StudentDAO studentDAO;
    private RoomDAO roomDAO;

    public AllotmentController(AllotmentPanel view) {
        this.view = view;
        this.studentDAO = new StudentDAO();
        this.roomDAO = new RoomDAO();

        // 1. Add listeners to the buttons
        view.getRefreshButton().addActionListener(e -> loadAllotmentData());
        view.getRunAllotmentButton().addActionListener(e -> runAllotmentProcess());
        view.getClearRoomsButton().addActionListener(e -> clearAllRoomAssignments());

        // 2. IMPORTANT: Load data when the panel is first shown!
        loadAllotmentData();
    }


    private void loadAllotmentData() {
        view.setUIEnabled(false); // Disable buttons while loading

        // The SwingWorker will perform database tasks on a background thread
        SwingWorker<Void, Void> worker = new SwingWorker<>() {

            // These variables will hold the data fetched from the background
            private List<Student> pendingStudents;
            private List<Student> allottedStudents;
            private int unassignedCount, assignedCount, availableCap;

            @Override
            protected Void doInBackground() throws Exception {
                // This runs on a BACKGROUND thread (safe for database calls)
                String hostelId = SessionManager.getCurrentAdmin().getHostelId();

                // Fetch all required data from the DAOs
                // NOTE: You must implement these methods in your DAOs
                pendingStudents = studentDAO.getStudentsWithUnAssignedRoom(hostelId);
                allottedStudents = studentDAO.getAllottedStudents(hostelId);
                availableCap = roomDAO.getAvailableRooms().size();

                unassignedCount = pendingStudents.size();
                assignedCount = allottedStudents.size();

                return null;
            }

            @Override
            protected void done() {
                // This runs back on the UI (Swing) thread, it's safe to update the UI
                try {
                    // This call updates the top metric cards (Total, Available, etc.)
                    view.setUnassignedStudents(unassignedCount);
                    view.setAssignedStudents(assignedCount);
                    view.setAvailableCapacity(availableCap);

                    // These two calls populate the left and right lists with student cards
                    view.setUnassignedStudentsList(pendingStudents);
                    view.setAllottedStudentsList(allottedStudents);

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(view, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    view.setUIEnabled(true); // Re-enable buttons
                }
            }
        };

        worker.execute(); // Start the background task
    }

    private void runAllotmentProcess() {
        view.setUIEnabled(false);
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                String hostelId = SessionManager.getCurrentAdmin().getHostelId();
                AllotmentEngine engine = new AllotmentEngine();
                engine.runAllotment(hostelId);
                return null;
            }

            @Override
            protected void done() {
                // When the engine finishes, refresh all data on the screen
                loadAllotmentData();
                JOptionPane.showMessageDialog(view, "Allotment process finished.", "Process Complete", JOptionPane.INFORMATION_MESSAGE);
                // The UI is re-enabled inside loadAllotmentData()
            }
        };
        worker.execute();
    }

    /**
     * Handles the "Clear Current Rooms" button.
     */
    private void clearAllRoomAssignments() {
        int choice = JOptionPane.showConfirmDialog(
                view,
                "Are you sure you want to un-assign ALL students from their rooms?\nThis cannot be undone.",
                "Confirm Clear Allotments",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {


            // You MUST create these methods in your DAOs/Services.

            boolean studentBool = studentDAO.clearAllAllotments(SessionManager.getCurrentAdmin().getHostelId());
            boolean roomBool =  roomDAO.removeAllOccupies(SessionManager.getCurrentAdmin().getHostelId());

            if(studentBool && roomBool){
                JOptionPane.showMessageDialog(null,"removed All students from their current Room ");
            }

            // After clearing, refresh the panel
            loadAllotmentData();
        }
    }
}
