package models;

public class Room {

    private String roomNumber;
    private String roomType;
    private int floorNumber;
    private int capacity;
    private int occupancy;

    private boolean isFull;

    public Room() {

    }

    public Room(String roomNumber, String roomType, int floorNumber, int capacity) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.floorNumber = floorNumber;
        this.capacity = capacity;

        this.occupancy = 0;
        this.isFull = false;
    }

    // Getters
    public String getRoomNumber() {
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

    public boolean getRoomFull() {
        isFull = (occupancy >= capacity);
        return isFull;
    }

    //Setters
    public void setRoomNumber(String roomNumber){
        this.roomNumber = roomNumber;
    }
    public void setRoomType (String roomType){
        this.roomType = roomType;
    }
    public void setFloorNumber(int floorNumber){
        this.floorNumber = floorNumber;

    }
    public void setCapacity(int capacity){
        this.capacity = capacity;

    }
    public void setOccupancy(int occupancy){
        this.occupancy = occupancy;
        if(occupancy==this.capacity)
        {
            setRoomFull(true);
        }
    }
    public void setRoomFull(boolean isFull){
        this.isFull = isFull;
    }



}
