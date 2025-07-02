import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

// GUI class to manage gym members interactively
public class GymGUI extends JFrame {
    private ArrayList<GymMember> members = new ArrayList<>();  // List of all gym members
    private JTextField idField, nameField, locationField, phoneField, emailField,
            referralField, paidAmountField, removalReasonField, trainerField;
    private JTextField regularPriceField, premiumChargeField, discountField, loyaltyPointsField;
    private JRadioButton maleRadio, femaleRadio;               // Gender selection
    private JComboBox<String> planCombo, dayCombo, monthCombo, yearCombo,
            startDayCombo, startMonthCombo, startYearCombo;    // Date and plan selectors
    private JButton regularModeButton, premiumModeButton, addMemberButton, activateButton, deactivateButton,
            markAttendanceButton, upgradePlanButton, calcDiscountButton, revertRegularButton, revertPremiumButton,
            payDueButton, displayButton, clearButton, saveButton, readButton;
    private JLabel referralLabel, planLabel, regularPriceLabel, trainerLabel, paidAmountLabel,
            premiumChargeLabel, discountLabel, loyaltyPointsLabel;
    private boolean isPremiumMode = false;                     // Tracks current mode
    private RegularMember tempRegularMember;                   // Temporary object for plan pricing
    private JPanel mainPanel;                                  // Main panel for layout

    // Constructor to set up the GUI
    public GymGUI() {
        setTitle("Gym Management");
        setSize(500, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        tempRegularMember = new RegularMember(0, "", "", "", "", "", "", "", "");

        // Row 0: Mode buttons
        regularModeButton = new JButton("Regular Member");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainPanel.add(regularModeButton, gbc);

        premiumModeButton = new JButton("Premium Member");
        gbc.gridx = 1;
        mainPanel.add(premiumModeButton, gbc);

        // Row 1: ID
        JLabel idLabel = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(idLabel, gbc);

        idField = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(idField, gbc);

        // Row 2: Name
        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(nameLabel, gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);

        // Row 3: Location
        JLabel locationLabel = new JLabel("Location:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(locationLabel, gbc);

        locationField = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(locationField, gbc);

        // Row 4: Phone
        JLabel phoneLabel = new JLabel("Phone:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(phoneLabel, gbc);

        phoneField = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(phoneField, gbc);

        // Row 5: Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(emailLabel, gbc);

        emailField = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(emailField, gbc);

        // Row 6: Gender
        JLabel genderLabel = new JLabel("Gender:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(genderLabel, gbc);

        maleRadio = new JRadioButton("Male");
        gbc.gridx = 1;
        mainPanel.add(maleRadio, gbc);

        femaleRadio = new JRadioButton("Female");
        gbc.gridx = 2;
        mainPanel.add(femaleRadio, gbc);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        // Row 7: DOB
        JLabel dobLabel = new JLabel("DOB:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(dobLabel, gbc);

        dayCombo = new JComboBox<>();
        for (int i = 1; i <= 31; i++) dayCombo.addItem(String.valueOf(i));
        gbc.gridx = 1;
        mainPanel.add(dayCombo, gbc);

        monthCombo = new JComboBox<>();
        for (int i = 1; i <= 12; i++) monthCombo.addItem(String.valueOf(i));
        gbc.gridx = 2;
        mainPanel.add(monthCombo, gbc);

        yearCombo = new JComboBox<>();
        for (int i = 1980; i <= 2025; i++) yearCombo.addItem(String.valueOf(i));
        gbc.gridx = 3;
        mainPanel.add(yearCombo, gbc);

        // Row 8: Start Date
        JLabel startDateLabel = new JLabel("Start Date:");
        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(startDateLabel, gbc);

        startDayCombo = new JComboBox<>();
        for (int i = 1; i <= 31; i++) startDayCombo.addItem(String.valueOf(i));
        gbc.gridx = 1;
        mainPanel.add(startDayCombo, gbc);

        startMonthCombo = new JComboBox<>();
        for (int i = 1; i <= 12; i++) startMonthCombo.addItem(String.valueOf(i));
        gbc.gridx = 2;
        mainPanel.add(startMonthCombo, gbc);

        startYearCombo = new JComboBox<>();
        for (int i = 1980; i <= 2025; i++) startYearCombo.addItem(String.valueOf(i));
        gbc.gridx = 3;
        mainPanel.add(startYearCombo, gbc);

        // Row 9 onwards: Mode-specific fields (added at initialization)
        referralLabel = new JLabel("Referral:");
        gbc.gridx = 0;
        gbc.gridy = 9;
        mainPanel.add(referralLabel, gbc);
        referralField = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(referralField, gbc);

        planLabel = new JLabel("Plan:");
        gbc.gridx = 0;
        gbc.gridy = 10;
        mainPanel.add(planLabel, gbc);
        planCombo = new JComboBox<>(new String[]{"basic", "standard", "deluxe"});
        gbc.gridx = 1;
        mainPanel.add(planCombo, gbc);

        regularPriceLabel = new JLabel("Regular Price:");
        gbc.gridx = 0;
        gbc.gridy = 11;
        mainPanel.add(regularPriceLabel, gbc);
        regularPriceField = new JTextField("6500.0");
        regularPriceField.setEditable(false);
        gbc.gridx = 1;
        mainPanel.add(regularPriceField, gbc);

        trainerLabel = new JLabel("Trainer:");
        gbc.gridx = 0;
        gbc.gridy = 9;  // Same row as referral, will toggle visibility
        mainPanel.add(trainerLabel, gbc);
        trainerField = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(trainerField, gbc);

        paidAmountLabel = new JLabel("Paid Amount:");
        gbc.gridx = 0;
        gbc.gridy = 10;  // Same row as plan, will toggle visibility
        mainPanel.add(paidAmountLabel, gbc);
        paidAmountField = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(paidAmountField, gbc);

        premiumChargeLabel = new JLabel("Premium Charge:");
        gbc.gridx = 0;
        gbc.gridy = 11;  // Same row as regular price, will toggle visibility
        mainPanel.add(premiumChargeLabel, gbc);
        premiumChargeField = new JTextField("50000.0");
        premiumChargeField.setEditable(false);
        gbc.gridx = 1;
        mainPanel.add(premiumChargeField, gbc);

        discountLabel = new JLabel("Discount:");
        gbc.gridx = 0;
        gbc.gridy = 12;
        mainPanel.add(discountLabel, gbc);
        discountField = new JTextField("0.0");
        discountField.setEditable(false);
        gbc.gridx = 1;
        mainPanel.add(discountField, gbc);

        // Row 13: Removal Reason
        JLabel removalReasonLabel = new JLabel("Removal Reason:");
        gbc.gridx = 0;
        gbc.gridy = 13;
        mainPanel.add(removalReasonLabel, gbc);
        removalReasonField = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(removalReasonField, gbc);

        // Row 14: Loyalty Points
        loyaltyPointsLabel = new JLabel("Loyalty Points:");
        gbc.gridx = 0;
        gbc.gridy = 14;
        mainPanel.add(loyaltyPointsLabel, gbc);
        loyaltyPointsField = new JTextField("0.0");
        loyaltyPointsField.setEditable(false);
        gbc.gridx = 1;
        mainPanel.add(loyaltyPointsField, gbc);

        // Row 15: Buttons (first row)
        addMemberButton = new JButton("Add Regular");
        gbc.gridx = 0;
        gbc.gridy = 15;
        mainPanel.add(addMemberButton, gbc);
        activateButton = new JButton("Activate");
        gbc.gridx = 1;
        mainPanel.add(activateButton, gbc);
        deactivateButton = new JButton("Deactivate");
        gbc.gridx = 2;
        mainPanel.add(deactivateButton, gbc);

        // Row 16: Buttons (second row)
        markAttendanceButton = new JButton("Mark Attendance");
        gbc.gridx = 0;
        gbc.gridy = 16;
        mainPanel.add(markAttendanceButton, gbc);
        upgradePlanButton = new JButton("Upgrade Plan");
        gbc.gridx = 1;
        mainPanel.add(upgradePlanButton, gbc);
        calcDiscountButton = new JButton("Calc Discount");
        gbc.gridx = 1;
        mainPanel.add(calcDiscountButton, gbc);

        // Row 17: Buttons (third row)
        revertRegularButton = new JButton("Revert Regular");
        gbc.gridx = 0;
        gbc.gridy = 17;
        mainPanel.add(revertRegularButton, gbc);
        revertPremiumButton = new JButton("Revert Premium");
        gbc.gridx = 0;
        mainPanel.add(revertPremiumButton, gbc);
        payDueButton = new JButton("Pay Due");
        gbc.gridx = 1;
        mainPanel.add(payDueButton, gbc);

        // Row 18: Buttons (fourth row)
        displayButton = new JButton("Display");
        gbc.gridx = 0;
        gbc.gridy = 18;
        mainPanel.add(displayButton, gbc);
        clearButton = new JButton("Clear");
        gbc.gridx = 1;
        mainPanel.add(clearButton, gbc);

        // Row 19: Buttons (fifth row)
        saveButton = new JButton("Save to File");
        gbc.gridx = 0;
        gbc.gridy = 19;
        mainPanel.add(saveButton, gbc);
        readButton = new JButton("Read from File");
        gbc.gridx = 1;
        mainPanel.add(readButton, gbc);

        add(new JScrollPane(mainPanel));

        // Action listeners without lambda functions
        planCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedPlan = (String) planCombo.getSelectedItem();
                double price = tempRegularMember
                .getPlanPrice(selectedPlan);
                regularPriceField.setText(String.valueOf(price));
            }
        });

        regularModeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isPremiumMode = false;
                updateUIMode();
            }
        });

        premiumModeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isPremiumMode = true;
                updateUIMode();
            }
        });

        addMemberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isPremiumMode) addPremium();
                else addRegular();
            }
        });

        activateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activate();
            }
        });

        deactivateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deactivate();
            }
        });

        markAttendanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markAttendance();
            }
        });

        upgradePlanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                upgradePlan();
            }
        });

        calcDiscountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcDiscount();
            }
        });

        revertRegularButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                revertRegular();
            }
        });

        revertPremiumButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                revertPremium();
            }
        });

        payDueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                payDue();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                display();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });

        readButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                readFromFile();
            }
        });

        // Initially set visibility based on default mode (Regular)
        updateUIMode();
        setVisible(true);
    }

    // Updates the UI based on Regular or Premium mode by toggling visibility
    private void updateUIMode() {
        // Toggle visibility for Regular mode components
        referralLabel.setVisible(!isPremiumMode);
        referralField.setVisible(!isPremiumMode);
        planLabel.setVisible(!isPremiumMode);
        planCombo.setVisible(!isPremiumMode);
        regularPriceLabel.setVisible(!isPremiumMode);
        regularPriceField.setVisible(!isPremiumMode);
        upgradePlanButton.setVisible(!isPremiumMode);
        revertRegularButton.setVisible(!isPremiumMode);

        // Toggle visibility for Premium mode components
        trainerLabel.setVisible(isPremiumMode);
        trainerField.setVisible(isPremiumMode);
        paidAmountLabel.setVisible(isPremiumMode);
        paidAmountField.setVisible(isPremiumMode);
        premiumChargeLabel.setVisible(isPremiumMode);
        premiumChargeField.setVisible(isPremiumMode);
        discountLabel.setVisible(isPremiumMode);
        discountField.setVisible(isPremiumMode);
        calcDiscountButton.setVisible(isPremiumMode);
        payDueButton.setVisible(isPremiumMode);
        revertPremiumButton.setVisible(isPremiumMode);

        // Update button text
        addMemberButton.setText(isPremiumMode ? "Add Premium" : "Add Regular");
    }

    // Adds a regular member to the list
    private void addRegular() {
        try {
            if (idField.getText().isEmpty() || nameField.getText().isEmpty() || locationField.getText().isEmpty() ||
                    phoneField.getText().isEmpty() || emailField.getText().isEmpty() || referralField.getText().isEmpty() ||
                    (!maleRadio.isSelected() && !femaleRadio.isSelected())) {
                JOptionPane.showMessageDialog(this, "Field is empty.");
                return;
            }
            int id = Integer.parseInt(idField.getText());
            if (isIdTaken(id)) {
                JOptionPane.showMessageDialog(this, "Duplicate member found.");
                return;
            }
            String name = nameField.getText();
            String location = locationField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String gender = maleRadio.isSelected() ? "Male" : "Female";
            String dob = dayCombo.getSelectedItem() + "/" + monthCombo.getSelectedItem() + "/" + yearCombo.getSelectedItem();
            String startDate = startDayCombo.getSelectedItem() + "/" + startMonthCombo.getSelectedItem() + "/" + startYearCombo.getSelectedItem();
            String referral = referralField.getText();
            String plan = (String) planCombo.getSelectedItem();

            RegularMember member = new RegularMember(id, name, location, phone, email, gender, dob, startDate, referral);
            member.upgradePlan(plan);
            members.add(member);
            JOptionPane.showMessageDialog(this, "Regular member added successfully.");
            clear();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    // Adds a premium member to the list
    private void addPremium() {
        try {
            if (idField.getText().isEmpty() || nameField.getText().isEmpty() || locationField.getText().isEmpty() ||
                    phoneField.getText().isEmpty() || emailField.getText().isEmpty() || trainerField.getText().isEmpty() ||
                    (!maleRadio.isSelected() && !femaleRadio.isSelected())) {
                JOptionPane.showMessageDialog(this, "Field is empty.");
                return;
            }
            int id = Integer.parseInt(idField.getText());
            if (isIdTaken(id)) {
                JOptionPane.showMessageDialog(this, "Duplicate member found.");
                return;
            }
            String name = nameField.getText();
            String location = locationField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String gender = maleRadio.isSelected() ? "Male" : "Female";
            String dob = dayCombo.getSelectedItem() + "/" + monthCombo.getSelectedItem() + "/" + yearCombo.getSelectedItem();
            String startDate = startDayCombo.getSelectedItem() + "/" + startMonthCombo.getSelectedItem() + "/" + startYearCombo.getSelectedItem();
            String trainer = trainerField.getText();

            PremiumMember member = new PremiumMember(id, name, location, phone, email, gender, dob, startDate, trainer);
            members.add(member);
            JOptionPane.showMessageDialog(this, "Premium member added successfully.");
            clear();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    // Activates a member's membership, checking if already active
    private void activate() {
        try {
            int id = Integer.parseInt(idField.getText());
            GymMember member = findMember(id);
            if (member != null) {
                if (member.isActiveStatus()) {
                    JOptionPane.showMessageDialog(this, "Membership already activated.");
                } else {
                    member.activateMembership();
                    JOptionPane.showMessageDialog(this, "Membership activated successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Member not found.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    // Deactivates a member's membership, checking if already inactive
    private void deactivate() {
        try {
            int id = Integer.parseInt(idField.getText());
            GymMember member = findMember(id);
            if (member != null) {
                if (!member.isActiveStatus()) {
                    JOptionPane.showMessageDialog(this, "Membership already deactivated.");
                } else {
                    member.deactivateMembership();
                    JOptionPane.showMessageDialog(this, "Membership deactivated successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Member not found.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    // Marks attendance for a member
    private void markAttendance() {
        try {
            int id = Integer.parseInt(idField.getText());
            GymMember member = findMember(id);
            if (member != null && member.isActiveStatus()) {
                member.markAttendance();
                JOptionPane.showMessageDialog(this, "Attendance marked successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Member not found or not active.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    // Upgrades a regular member's plan
    private void upgradePlan() {
        try {
            int id = Integer.parseInt(idField.getText());
            GymMember member = findMember(id);
            if (member instanceof RegularMember) {
                String plan = (String) planCombo.getSelectedItem();
                String result = ((RegularMember) member).upgradePlan(plan);
                JOptionPane.showMessageDialog(this, result);
            } else {
                JOptionPane.showMessageDialog(this, "Not a regular member.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    // Calculates discount for a premium member
    private void calcDiscount() {
        try {
            int id = Integer.parseInt(idField.getText());
            GymMember member = findMember(id);
            if (member instanceof PremiumMember) {
                String result = ((PremiumMember) member).calculateDiscount();
                discountField.setText(String.valueOf(((PremiumMember) member).getDiscountAmount()));
                JOptionPane.showMessageDialog(this, result);
            } else {
                JOptionPane.showMessageDialog(this, "Not a premium member.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    // Reverts a regular member
    private void revertRegular() {
        try {
            int id = Integer.parseInt(idField.getText());
            GymMember member = findMember(id);
            if (member instanceof RegularMember) {
                ((RegularMember) member).revertRegularMember(removalReasonField.getText());
                JOptionPane.showMessageDialog(this, "Regular member reverted successfully.");
                clear();
            } else {
                JOptionPane.showMessageDialog(this, "Not a regular member.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    // Reverts a premium member
    private void revertPremium() {
        try {
            int id = Integer.parseInt(idField.getText());
            GymMember member = findMember(id);
            if (member instanceof PremiumMember) {
                ((PremiumMember) member).revertPremiumMember();
                JOptionPane.showMessageDialog(this, "Premium member reverted successfully.");
                clear();
            } else {
                JOptionPane.showMessageDialog(this, "Not a premium member.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    // Processes payment for a premium member
    private void payDue() {
        try {
            int id = Integer.parseInt(idField.getText());
            double amount = Double.parseDouble(paidAmountField.getText());
            GymMember member = findMember(id);
            if (member instanceof PremiumMember) {
                String result = ((PremiumMember) member).payDueAmount(amount);
                discountField.setText(String.valueOf(((PremiumMember) member).getDiscountAmount()));
                JOptionPane.showMessageDialog(this, result);
            } else {
                JOptionPane.showMessageDialog(this, "Not a premium member.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID or amount.");
        }
    }

    // Displays members based on current mode
    private void display() {
        JFrame displayFrame = new JFrame(isPremiumMode ? "Premium Members" : "Regular Members");
        displayFrame.setSize(600, 400);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        for (GymMember m : members) {
            if ((isPremiumMode && m instanceof PremiumMember) || (!isPremiumMode && m instanceof RegularMember)) {
                area.append(m.display());
                area.append("----------------\n");
            }
        }
        displayFrame.add(new JScrollPane(area));
        displayFrame.setVisible(true);
    }

    // Clears all input fields
    private void clear() {
        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        phoneField.setText("");
        emailField.setText("");
        referralField.setText("");
        paidAmountField.setText("");
        removalReasonField.setText("");
        trainerField.setText("");
        maleRadio.setSelected(false);
        femaleRadio.setSelected(false);
        dayCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(0);
        startDayCombo.setSelectedIndex(0);
        startMonthCombo.setSelectedIndex(0);
        startYearCombo.setSelectedIndex(0);
        planCombo.setSelectedIndex(0);
        regularPriceField.setText("6500.0");
        discountField.setText("0.0");
        loyaltyPointsField.setText("0.0");
    }

    // Saves member details to a file
    private void saveToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("MemberDetails.txt"));
            writer.write(String.format("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-10s %-15s %-10s %-15s %-15s %-15s\n",
                    "ID", "Name", "Location", "Phone", "Email", "Start Date", "Plan", "Price", "Attendance", "Loyalty Points",
                    "Active", "Full Payment", "Discount", "Paid Amount"));
            for (GymMember m : members) {
                if (m instanceof RegularMember rm) {
                    writer.write(String.format("%-5d %-15s %-15s %-15s %-25s %-20s %-10s %-10.2f %-10d %-15.2f %-10b %-15s %-15s %-15s\n",
                            rm.getId(), rm.getName(), rm.getLocation(), rm.getPhone(), rm.getEmail(), rm.getMembershipStartDate(),
                            rm.getPlan(), rm.getPrice(), rm.getAttendance(), rm.getLoyaltyPoints(), rm.isActiveStatus(),
                            "N/A", "0.0", "0.0"));
                } else if (m instanceof PremiumMember pm) {
                    writer.write(String.format("%-5d %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-10d %-15.2f %-10b %-15b %-15.2f %-15.2f\n",
                            pm.getId(), pm.getName(), pm.getLocation(), pm.getPhone(), pm.getEmail(), pm.getMembershipStartDate(),
                            "N/A", "N/A", pm.getAttendance(), pm.getLoyaltyPoints(), pm.isActiveStatus(),
                            pm.isFullPayment(), pm.getDiscountAmount(), pm.getPaidAmount()));
                }
            }
            writer.close();
            JOptionPane.showMessageDialog(this, "Member details saved to MemberDetails.txt successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving to file: " + ex.getMessage());
        }
    }

    // Reads and displays member details from a file
    private void readFromFile() {
        JFrame readFrame = new JFrame("Member Details from File");
        readFrame.setSize(800, 400);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        try {
            BufferedReader reader = new BufferedReader(new FileReader("MemberDetails.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                area.append(line + "\n");
            }
            reader.close();
        } catch (IOException ex) {
            area.append("Error reading file: " + ex.getMessage());
        }
        readFrame.add(new JScrollPane(area));
        readFrame.setVisible(true);
    }

    // Checks if an ID is already in use
    private boolean isIdTaken(int id) {
        for (GymMember m : members) {
            if (m.getId() == id) return true;
        }
        return false;
    }

    // Finds a member by ID
    private GymMember findMember(int id) {
        for (GymMember m : members) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    // Launches the application
    public static void main(String[] args) {
        new GymGUI();
    }
}
