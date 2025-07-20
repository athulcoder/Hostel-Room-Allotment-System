package com.hostel.allotment.models;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private int roomNumber;
    private String roomType;
    private int floorNumber;
    private int capacity;
    private int occupancy;
    private List<Student> occupants;
    private boolean isFull;

    public Room() {
        this.occupants = new ArrayList<>();
    }

    public Room(int roomNumber, String roomType, int floorNumber, int capacity) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.floorNumber = floorNumber;
        this.capacity = capacity;
        this.occupants = new ArrayList<>();
        this.occupancy = 0;
        this.isFull = false;
    }

    // Getters
    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public List<Student> getOccupants() {
        return occupants;
    }

    public boolean getRoomStatus() {
        return isFull;
    }

    //Setters
}
