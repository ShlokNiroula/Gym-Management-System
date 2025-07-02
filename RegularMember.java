 
// Subclass of GymMember for regular members with specific features
public class RegularMember extends GymMember {
    private final int attendanceLimit = 30;  // Max attendance for upgrade eligibility
    private boolean isEligibleForUpgrade;    // Indicates if member can upgrade
    private String removalReason;            // Reason for reverting membership
    private String referralSource;           // How the member was referred
    private String plan;                     // Current membership plan
    private double price;                    // Cost of the current plan

    // Constructor to initialize a RegularMember object
    public RegularMember(int id, String name, String location, String phone, String email, 
                         String gender, String dob, String startDate, String referralSource) {
        super(id, name, location, phone, email, gender, dob, startDate);
        this.referralSource = referralSource;
        this.isEligibleForUpgrade = false;
        this.plan = "basic";                // Default plan
        this.price = 6500.0;                // Default price for basic plan
        this.removalReason = "";
        
        //create a button, add action listener to it, on clicking it display I am Messi in a JOptionPane MessageDialog
      
        
    }

    // Getter methods formatted as requested
    public int get_attendanceLimit() {
        return attendanceLimit;
    }

    public boolean is_eligibleForUpgrade() {
        return isEligibleForUpgrade;
    }

    public String get_removalReason() {
        return removalReason;
    }

    public String getReferralSource() {
        return referralSource;
    }

    public String getPlan() {
        return plan;
    }

    public double getPrice() {
        return price;
    }

    // Marks attendance and updates loyalty points and upgrade eligibility
    @Override
    public void markAttendance() {
        attendance++;
        loyaltyPoints += 10.0;               // 5 points per visit
        if (getAttendance() >= attendanceLimit) {
            isEligibleForUpgrade = true;
        }
    }

    // Returns the price for a given plan
    public double getPlanPrice(String plan) {
        switch (plan.toLowerCase()) {
            case "basic": return 6500.0;
            case "standard": return 12500.0;
            case "deluxe": return 18500.0;
            default: return -1.0;           // Invalid plan
        }
    }

    // Upgrades the member's plan if eligible
    public String upgradePlan(String newPlan) {
        if (getAttendance() >= attendanceLimit) {
            isEligibleForUpgrade = true;
        }
        if (!isEligibleForUpgrade) {
            return "Member is not eligible for upgrade. Attendance must be at least " + attendanceLimit + ".";
        }
        if (newPlan.toLowerCase().equals(plan)) {
            return "Member is already subscribed to the " + newPlan + " plan.";
        }
        double newPrice = getPlanPrice(newPlan);
        if (newPrice == -1.0) {
            return "Invalid plan: " + newPlan + ". Available plans are: basic, standard, deluxe.";
        }
        this.plan = newPlan.toLowerCase();
        this.price = newPrice;
        return "Plan upgraded to " + newPlan + " for " + price;
    }

    // Reverts the regular member to initial state with a removal reason
    public void revertRegularMember(String reason) {
        resetMember();
        this.removalReason = reason;
        this.isEligibleForUpgrade = false;
        this.plan = "basic";
        this.price = 6500.0;
    }

    // Displays all details of the regular member
    @Override
    public String display() {
        String display = "Regular Member:\n" +
                         "ID: " + getId() + "\n" +
                         "Name: " + getName() + "\n" +
                         "Location: " + getLocation() + "\n" +
                         "Phone: " + getPhone() + "\n" +
                         "Email: " + getEmail() + "\n" +
                         "Gender: " + getGender() + "\n" +
                         "DOB: " + getDob() + "\n" +
                         "Start Date: " + getMembershipStartDate() + "\n" +
                         "Attendance: " + getAttendance() + "\n" +
                         "Loyalty Points: " + getLoyaltyPoints() + "\n" +
                         "Active: " + isActiveStatus() + "\n" +
                         "Referral: " + getReferralSource() + "\n" +
                         "Plan: " + getPlan() + "\n" +
                         "Price: " + getPrice() + "\n";
        if (!removalReason.isEmpty()) {
            display += "Removal Reason: " + removalReason + "\n";
        }
        return display;
    }
}