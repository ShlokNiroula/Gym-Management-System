// Abstract parent class representing a gym member with common attributes and methods
public abstract class GymMember {
    // Protected attributes for member details
    protected int id;                    // Unique identifier for the member
    protected String name;               // Full name of the member
    protected String location;           // Member's residential location
    protected String phone;              // Member's contact number
    protected String email;              // Member's email address
    protected String gender;             // Member's gender (Male/Female)
    protected String dob;                // Member's date of birth
    protected String startDate;          // Date when membership started
    protected boolean activeStatus;      // Indicates if membership is active
    protected int attendance;            // Tracks number of gym visits
    protected double loyaltyPoints;      // Points earned for loyalty rewards

    // Constructor to initialize a GymMember object with provided details
    public GymMember(int id, String name, String location, String phone, String email, 
                     String gender, String dob, String startDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.startDate = startDate;
        this.activeStatus = false;       // Default: membership inactive
        this.attendance = 0;             // Default: no attendance recorded
        this.loyaltyPoints = 0.0;        // Default: no loyalty points
    }

    // Getter methods formatted 
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getMembershipStartDate() {
        return startDate;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public int getAttendance() {
        return attendance;
    }

    public double getLoyaltyPoints() {
        return loyaltyPoints;
    }

    // Activates the member's membership
    public void activateMembership() {
        activeStatus = true;
    }

    // Deactivates the member's membership if currently active
    public void deactivateMembership() {
        if (activeStatus) {
            activeStatus = false;
        }
    }

    // Abstract method for marking attendance, to be implemented by subclasses
    public abstract void markAttendance();

    // Resets member details to initial state
    public void resetMember() {
        activeStatus = false;
        attendance = 0;
        loyaltyPoints = 0.0;
    }

    // Abstract method to display member details, to be implemented by subclasses
    public abstract String display();
}