package models;

public class Hostel {
    private String hostelName;
    private String type;
    private int totalRoomCount;
    private int totalFloorCount;
    private int maxCapacity;

    //Constructor

    Hostel(){

    }
    Hostel(String hostelName, String type, int totalRoomCount, int totalFloorCount, int maxCapacity){
        this.hostelName = hostelName;
        this.type = type;
        this.totalFloorCount = totalFloorCount;
        this.totalRoomCount = totalRoomCount;
        this.maxCapacity = maxCapacity;
    }

    //Getters

    public String getHostelName()
    {
        return hostelName;
    }

    public String getType()
    {
        return type;
    }

    public int getTotalRoomCount()
    {
        return totalRoomCount;
    }

    public int getTotalFloorCount()
    {
        return totalFloorCount;
    }

    public int getMaxCapacity()
    {
        return maxCapacity;
    }


    //Setters
    public void setHostelName(String hostelName)
    {
        this.hostelName = hostelName;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public void setTotalRoomCount(int totalRoomCount)
    {
        this.totalRoomCount = totalRoomCount;
    }
    public void setTotalFloorCount(int totalFloorCount)
    {
        this.totalFloorCount = totalFloorCount;
    }
    public void setMaxCapacity(int maxCapacity)
    {
        this.maxCapacity = maxCapacity;
    }
}
