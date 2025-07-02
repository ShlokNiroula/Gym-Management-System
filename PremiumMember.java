// Subclass of GymMember for premium members with additional features
public class PremiumMember extends GymMember {
    private final double premiumCharge = 50000.0;  // Fixed charge for premium membership
    private String personalTrainer;                // Assigned trainer's name
    private double paidAmount;                     // Amount paid towards premium charge
    private boolean isFullPayment;                 // Indicates if full payment is made
    private double discountAmount;                 // Discount applied after full payment

    // Constructor to initialize a PremiumMember object
    public PremiumMember(int id, String name, String location, String phone, String email, 
                         String gender, String dob, String startDate, String personalTrainer) {
        super(id, name, location, phone, email, gender, dob, startDate);
        this.personalTrainer = personalTrainer;
        this.paidAmount = 0.0;
        this.isFullPayment = false;
        this.discountAmount = 0.0;
    }

    // Getter methods formatted as requested
    public String getPersonalTrainer() {
        return personalTrainer;
    }

    public double getPremiumCharge() {
        return premiumCharge;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public boolean isFullPayment() {
        return isFullPayment;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    // Marks attendance and awards more loyalty points for premium members
    @Override
    public void markAttendance() {
        attendance++;
        loyaltyPoints += 10.0;             // 10 points per visit
    }

    // Processes payment towards the premium charge
    public String payDueAmount(double amount) {
        if (isFullPayment) {
            return "Payment already full. No further payment required.";
        }
        double newPaidAmount = paidAmount + amount;
        if (newPaidAmount > premiumCharge) {
            return "Payment exceeds premium charge of " + premiumCharge + ". Please pay " + (premiumCharge - paidAmount) + " or less.";
        }
        paidAmount = newPaidAmount;
        isFullPayment = (paidAmount == premiumCharge);
        double remainingAmount = premiumCharge - paidAmount;
        if (isFullPayment) {
            calculateDiscount();
            return "Payment successful. Full amount paid. Discount applied: " + discountAmount;
        }
        return "Payment of " + amount + " successful. Remaining amount to pay: " + remainingAmount;
    }

    // Calculates discount if payment is full
    public String calculateDiscount() {
        if (isFullPayment) {
            discountAmount = premiumCharge * 0.1;  // 10% discount
            return "Discount calculated successfully: " + discountAmount;
        } else {
            discountAmount = 0.0;
            return "No discount applied. Payment not full.";
        }
    }

    // Reverts the premium member to initial state
    public void revertPremiumMember() {
        resetMember();
        personalTrainer = "";
        paidAmount = 0.0;
        isFullPayment = false;
        discountAmount = 0.0;
    }

    // Displays all details of the premium member
    @Override
    public String display() {
        String display = "Premium Member:\n" +
                         "ID: " + getId() + "\n" +
                         "Name: " + getName() + "\n" +
                         "Location: " + getLocation() + "\n" +
                         "Phone: " + getPhone() + "\n" +
                         "Email: " + getEmail() + "\n" +
                         "Gender: " + getGender() + "\n" +
                         "DOB: " + getDob() + "\n" +
                         "Start Date: " + getMembershipStartDate() + "\n" +
                         "Active Status: " + isActiveStatus() + "\n" +
                         "Attendance: " + getAttendance() + "\n" +
                         "Loyalty Points: " + getLoyaltyPoints() + "\n" +
                         "Personal Trainer: " + personalTrainer + "\n" +
                         "Paid Amount: " + paidAmount + "\n" +
                         "Premium Charge: " + premiumCharge + "\n" +
                         "Remaining Amount: " + (premiumCharge - paidAmount) + "\n" +
                         "Full Payment: " + isFullPayment + "\n";
        if (isFullPayment) {
            display += "Discount Amount: " + discountAmount + "\n";
        }
        return display;
    }
}