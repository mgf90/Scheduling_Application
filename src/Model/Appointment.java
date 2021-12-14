package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.ZonedDateTime;

public class Appointment {

    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    private final int ID;
    private final String title;
    private final String description;
    private final String location;
    private final int contact;
    private final int custID;
    private final ZonedDateTime start;
    private final ZonedDateTime end;

    public Appointment(int id, String title, String description, String location, int contact, int custID, ZonedDateTime start, ZonedDateTime end) {
        this.ID = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.custID = custID;
        this.start = start;
        this.end = end;
    }

    public ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getContact() {
        return contact;
    }

    public int getCustID() {
        return custID;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }
}
