package models;

public class Hostel {
    private String hostelName;
    private String hostelId;
    private String type;
    private int totalRoomCount;
    private int totalFloorCount;
    private int maxCapacity;

    //Constructor

   public Hostel(){

    }
   public Hostel(String hostelName, String type, int totalRoomCount, int totalFloorCount, int maxCapacity, String hostelId){
        this.hostelName = hostelName;
        this.type = type;
        this.totalFloorCount = totalFloorCount;
        this.totalRoomCount = totalRoomCount;
        this.maxCapacity = maxCapacity;
        this.hostelId = hostelId;
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

    public  String getHostelId(){
        return hostelId;
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
    public void setHostelId(String hostelId){
        this.hostelId = hostelId;
    }
}
