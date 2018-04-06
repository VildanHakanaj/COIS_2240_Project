package Classes;

import Controllers.EventController;

import java.sql.*;
import java.time.LocalDate;

public class Event {

<<<<<<< HEAD
    //EventController eventController = new EventController();
    private Database db = new Database();
=======
    private String strt;
>>>>>>> master
    private String titleField;
    private String date;
    private int duration;
    private String descriptionField;
    private String privacy;
    private String thirty;
    private String hour;
    private String day;
    private String week;
    private String repeat;
    private String colour;
    private int start;
    private String end;
    private int ID;
    private int userId;
    private String getStrt;

    public Event() throws SQLException {

    }

    public void setID(int ID) {
        this.ID = ID;
    }

    //There is bug with this code.
    //Result set will return a
    //This will pull the event from the database;
    public Event(int ID) throws SQLException {
        try {
            ResultSet rs = db.selectEventById(ID);
            while(rs.next()) {
                titleField = rs.getString("Title");
                date = rs.getString("Date");
                duration = rs.getInt("Duration");
                descriptionField = rs.getString("Description");
                privacy = rs.getString("Privacy");
                thirty = rs.getString("Thirty");
                hour = rs.getString("Hour");
                day = rs.getString("Day");
                week = rs.getString("Week");
                repeat = rs.getString("Repeat");
                colour = rs.getString("Colour");
                start = rs.getInt("Start");
                end = rs.getString("End");
                ID = rs.getInt("ID");
            }
    public Event(int ID) throws SQLException {

        String url = "jdbc:sqlite:src/data.db";
        Connection conn = DriverManager.getConnection(url);

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Event WHERE ID=" + ID);
            while (rs.next()) {
                titleField = rs.getString("Title");
                date = rs.getString("Date");
                duration = rs.getInt("Duration");
                descriptionField = rs.getString("Description");
                privacy = rs.getString("Privacy");
                thirty = rs.getString("Thirty");
                hour = rs.getString("Hour");
                day = rs.getString("Day");
                week = rs.getString("Week");
                repeat = rs.getString("Repeat");
                colour = rs.getString("Colour");
                start = rs.getInt("Start");
                strt = rs.getString("Strt");
                end = rs.getString("End");
                ID = rs.getInt("ID");

                System.out.println(titleField +"\n"+ date +"\n"+ duration +"\n"+ descriptionField
                        +"\n"+ privacy +"\n"+ thirty +"\n"+ hour +"\n"+ day +"\n"+ week +"\n"+
                        repeat +"\n"+ colour +"\n"+ ID +"\n");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public Event(String titleField, String date, int duration, String descriptionField, String privacy, String thirty, String hour, String day, String week, String repeat, String colour, int start, String end, int ID) throws SQLException {
        this.titleField = titleField;
        this.date = date;
        this.duration = duration;
        this.descriptionField = descriptionField;
        this.privacy = privacy;
        this.thirty = thirty;
        this.hour = hour;
        this.day = day;
        this.week = week;
        this.repeat = repeat;
        this.colour = colour;
        this.start = start;
        this.end = end;
        this.ID = ID;
        this.strt = strt;
    }

    public String getTitleField() {
        return titleField;
    }

    public String getDate() {
        return date;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescriptionField() {
        return descriptionField;
    }

    public String getPrivacy() {
        return privacy;
    }

    public String getThirty() {
        return thirty;
    }

    public String getHour() {
        return hour;
    }

    public String getDay() {
        return day;
    }

    public String getWeek() {
        return week;
    }

    public String getRepeat() {
        return repeat;
    }

    public String getColour() {
        return colour;
    }

    public int getID() {
        return ID;
    }

    public int getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getStrt(){
        return strt;
    }
}
