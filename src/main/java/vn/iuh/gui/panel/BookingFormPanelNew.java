package vn.iuh.gui.panel;

import vn.iuh.dto.event.create.BookingCreationEvent;
import vn.iuh.dto.response.BookingResponse;
import vn.iuh.gui.base.CustomUI;
import vn.iuh.gui.base.Main;
import vn.iuh.servcie.BookingService;
import vn.iuh.servcie.impl.BookingServiceImpl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class BookingFormPanelNew extends JPanel {
    private BookingResponse selectedRoom;
    private BookingService bookingService;

    // Room Information Components
    private JLabel lblRoomNumber;
    private JLabel lblRoomType;
    private JLabel lblRoomCapacity;
    private JLabel lblHourlyPrice;
    private JLabel lblDailyPrice;
    private JLabel lblRoomStatus;

    // Customer Information Components
    private JTextField txtCustomerName;
    private JTextField txtPhoneNumber;
    private JTextField txtCCCD;

    // Booking Information Components
    private JSpinner spnCheckInDate;
    private JSpinner spnCheckOutDate;
    private JSpinner spnCreateAt;
    private JTextArea txtNote;
    private JTextField txtInitialPrice;
    private JTextField txtDepositPrice;
    private JCheckBox chkIsAdvanced;

    // Service Components
    private JPanel serviceSelectionPanel;
    private boolean isServicePanelVisible = false;

    // Action Buttons
    private JButton btnConfirmBooking;
    private JButton btnAddService;

    // Main content components
    private JPanel mainContentPanel;

    public BookingFormPanelNew(BookingResponse roomInfo) {
        this.selectedRoom = roomInfo;
        this.bookingService = new BookingServiceImpl();

        initializeComponents();
        setupLayout();
        setupEventHandlers();
        populateRoomInformation();
        setDefaultValues();
    }

    private void initializeComponents() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Room Information Labels
        lblRoomNumber = new JLabel();
        lblRoomType = new JLabel();
        lblRoomCapacity = new JLabel();
        lblHourlyPrice = new JLabel();
        lblDailyPrice = new JLabel();
        lblRoomStatus = new JLabel();

        // Customer Information Fields - increased width
        txtCustomerName = new JTextField(30);
        txtPhoneNumber = new JTextField(30);
        txtCCCD = new JTextField(30);

        // Booking Information Fields
        spnCheckInDate = new JSpinner(new SpinnerDateModel());
        spnCheckOutDate = new JSpinner(new SpinnerDateModel());
        spnCreateAt = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor checkInEditor = new JSpinner.DateEditor(spnCheckInDate, "dd/MM/yyyy");
        JSpinner.DateEditor checkOutEditor = new JSpinner.DateEditor(spnCheckOutDate, "dd/MM/yyyy");
        JSpinner.DateEditor createAtEditor = new JSpinner.DateEditor(spnCreateAt, "dd/MM/yyyy");
        spnCheckInDate.setEditor(checkInEditor);
        spnCheckOutDate.setEditor(checkOutEditor);
        spnCreateAt.setEditor(createAtEditor);

        txtNote = new JTextArea(4, 25);
        txtNote.setLineWrap(true);
        txtNote.setWrapStyleWord(true);

        txtInitialPrice = new JTextField(15);
        txtDepositPrice = new JTextField(15);
        chkIsAdvanced = new JCheckBox("Đặt phòng trước");

        // Buttons
        btnConfirmBooking = new JButton("XÁC NHẬN");
        btnAddService = new JButton("THÊM DỊCH VỤ");

        // Style buttons
        btnConfirmBooking.setBackground(new Color(144, 238, 144)); // Light green
        btnConfirmBooking.setForeground(Color.WHITE);
        btnConfirmBooking.setFont(CustomUI.normalFont);
        btnConfirmBooking.setFocusPainted(false);
        btnConfirmBooking.setPreferredSize(new Dimension(200, 50));

        btnAddService.setBackground(new Color(0, 206, 209)); // Cyan
        btnAddService.setForeground(Color.WHITE);
        btnAddService.setFont(CustomUI.normalFont);
        btnAddService.setFocusPainted(false);
        btnAddService.setPreferredSize(new Dimension(200, 50));

        // Initialize service components
        initializeServiceComponents();
    }

    private void initializeServiceComponents() {
        serviceSelectionPanel = new JPanel();
        serviceSelectionPanel.setBackground(new Color(255, 255, 255, 200));
        serviceSelectionPanel.setBorder(BorderFactory.createTitledBorder("Gọi dịch vụ"));
        serviceSelectionPanel.setVisible(false);

        // Add close button to service panel
        JButton closeServiceBtn = new JButton("X");
        closeServiceBtn.setPreferredSize(new Dimension(30, 30));
        closeServiceBtn.addActionListener(e -> toggleServicePanel());
        serviceSelectionPanel.add(closeServiceBtn);
    }

    private void setupLayout() {
        // Main title
        JLabel titleLabel = new JLabel("FORM ĐẶT PHÒNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(CustomUI.darkBlue);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Create main content panel with overlay capability for service panel
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new OverlayLayout(mainContentPanel));
        mainContentPanel.setBackground(Color.WHITE);

        // Base content panel following bookingInfo_layout
        JPanel basePanel = createBaseContentPanel();

        // Add both panels to main content
        mainContentPanel.add(serviceSelectionPanel); // Service panel on top (initially hidden)
        mainContentPanel.add(basePanel); // Base content underneath

        // Add to main panel
        add(titleLabel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);
    }

    private JPanel createBaseContentPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setOpaque(true);
        GridBagConstraints gbc = new GridBagConstraints();

        // Row 0: Top room info panel (Blue, spans both columns)
        JPanel topRoomInfoPanel = createTopRoomInfoPanel();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 10, 0);
        contentPanel.add(topRoomInfoPanel, gbc);

        // Row 1, Column 0: Left column (Customer + Booking stacked vertically)
        JPanel leftColumnPanel = new JPanel(new GridBagLayout());
        leftColumnPanel.setBackground(new Color(255, 255, 204)); // Light yellow
        leftColumnPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        GridBagConstraints leftGbc = new GridBagConstraints();

        // Customer panel (top of left column)
        JPanel customerPanel = createCustomerInfoPanel();
        customerPanel.setBackground(new Color(255, 255, 204)); // Light yellow
        leftGbc.gridx = 0; leftGbc.gridy = 0;
        leftGbc.fill = GridBagConstraints.BOTH;
        leftGbc.weightx = 1.0; leftGbc.weighty = 0.5;
        leftGbc.insets = new Insets(5, 5, 5, 5);
        leftColumnPanel.add(customerPanel, leftGbc);

        // Booking panel (bottom of left column)
        JPanel bookingPanel = createBookingInfoPanel();
        bookingPanel.setBackground(new Color(255, 255, 204)); // Light yellow

        leftGbc.gridy = 1;
        leftGbc.weighty = 0.5;
        leftColumnPanel.add(bookingPanel, leftGbc);

        // Add left column to main content
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.6; gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 10, 5);
        contentPanel.add(leftColumnPanel, gbc);

        // Row 1, Column 1: Right room info panel (Blue)
        JPanel rightRoomPanel = createRightRoomInfoPanel();
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 0.4;
        gbc.insets = new Insets(0, 5, 10, 0);
        contentPanel.add(rightRoomPanel, gbc);

        // Row 2: Button panel (spans both columns)
        JPanel buttonPanel = createButtonPanel();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; gbc.weighty = 0.0;
        gbc.insets = new Insets(10, 0, 0, 0);
        contentPanel.add(buttonPanel, gbc);

        return contentPanel;
    }

    private JPanel createTopRoomInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CustomUI.lightBlue);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(CustomUI.darkBlue, 2),
            "THÔNG TIN PHÒNG",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            CustomUI.normalFont,
            Color.WHITE
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 25, 15, 25);
        gbc.anchor = GridBagConstraints.WEST;

        // Left column
        addRoomInfoRowTwoColumns(panel, gbc, 0, 0, "Số phòng:", lblRoomNumber);
        addRoomInfoRowTwoColumns(panel, gbc, 1, 0, "Sức chứa:", lblRoomCapacity);
        addRoomInfoRowTwoColumns(panel, gbc, 2, 0, "Giá theo giờ:", lblHourlyPrice);

        // Right column
        addRoomInfoRowTwoColumns(panel, gbc, 0, 3, "Loại phòng:", lblRoomType);
        addRoomInfoRowTwoColumns(panel, gbc, 1, 3, "Trạng thái:", lblRoomStatus);
        addRoomInfoRowTwoColumns(panel, gbc, 2, 3, "Giá theo ngày:", lblDailyPrice);

        return panel;
    }

    private void addRoomInfoRowTwoColumns(JPanel panel, GridBagConstraints gbc, int row, int startCol, String labelText, JLabel valueLabel) {
        gbc.gridy = row;

        gbc.gridx = startCol;
        gbc.weightx = 0.0;
        JLabel label = new JLabel(labelText);
        label.setFont(CustomUI.normalFont);
        label.setForeground(Color.WHITE);
        panel.add(label, gbc);

        gbc.gridx = startCol + 1;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(15, 10, 15, 25);
        valueLabel.setFont(CustomUI.normalFont);
        valueLabel.setForeground(Color.WHITE);
        panel.add(valueLabel, gbc);

        gbc.insets = new Insets(15, 25, 15, 25);
    }

    private JPanel createCustomerInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(CustomUI.darkBlue, 2),
            "THÔNG TIN KHÁCH HÀNG",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            CustomUI.normalFont,
            CustomUI.darkBlue
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        addFormRow(panel, gbc, 0, "Tên khách hàng:", txtCustomerName);
        addFormRow(panel, gbc, 1, "Số điện thoại:", txtPhoneNumber);
        addFormRow(panel, gbc, 2, "CCCD/CMND:", txtCCCD);

        return panel;
    }

    private JPanel createBookingInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(CustomUI.darkBlue, 2),
            "THÔNG TIN ĐẶT PHÒNG",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            CustomUI.normalFont,
            CustomUI.darkBlue
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        addFormRow(panel, gbc, 0, "Ngày nhận phòng:", spnCheckInDate);
        addFormRow(panel, gbc, 1, "Ngày trả phòng:", spnCheckOutDate);
        addFormRow(panel, gbc, 2, "Giá ban đầu:", txtInitialPrice);
        addFormRow(panel, gbc, 3, "Tiền đặt cọc:", txtDepositPrice);

        // Advanced booking checkbox
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        chkIsAdvanced.setFont(CustomUI.smallFont);
        chkIsAdvanced.setBackground(Color.WHITE);
        panel.add(chkIsAdvanced, gbc);

        // Note area
        gbc.gridy = 5;
        JLabel lblNote = new JLabel("Ghi chú:");
        lblNote.setFont(CustomUI.smallFont);
        panel.add(lblNote, gbc);

        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        JScrollPane scrollNote = new JScrollPane(txtNote);
        scrollNote.setPreferredSize(new Dimension(250, 100));
        panel.add(scrollNote, gbc);

        return panel;
    }

    private JPanel createRightRoomInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CustomUI.lightBlue);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(CustomUI.darkBlue, 2),
            "THÔNG TIN PHÒNG",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            CustomUI.normalFont,
            Color.WHITE
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Single column layout for right panel
        addRoomInfoRowSingleColumn(panel, gbc, 0, "Loại phòng:", lblRoomType);
        addRoomInfoRowSingleColumn(panel, gbc, 1, "Sức chứa:", lblRoomCapacity);
        addRoomInfoRowSingleColumn(panel, gbc, 2, "Trạng thái:", lblRoomStatus);
        addRoomInfoRowSingleColumn(panel, gbc, 3, "Giá theo giờ:", lblHourlyPrice);
        addRoomInfoRowSingleColumn(panel, gbc, 4, "Giá theo ngày:", lblDailyPrice);

        return panel;
    }

    private void addRoomInfoRowSingleColumn(JPanel panel, GridBagConstraints gbc, int row, String labelText, JLabel valueLabel) {
        gbc.gridy = row;

        gbc.gridx = 0;
        gbc.weightx = 0.0;
        JLabel label = new JLabel(labelText);
        label.setFont(CustomUI.normalFont);
        label.setForeground(Color.WHITE);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        valueLabel.setFont(CustomUI.normalFont);
        valueLabel.setForeground(Color.WHITE);
        panel.add(valueLabel, gbc);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent component) {
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0;
        gbc.weightx = 0.0;
        JLabel label = new JLabel(labelText);
        label.setFont(CustomUI.smallFont);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        component.setFont(CustomUI.smallFont);
        if (component instanceof JTextField) {
            component.setPreferredSize(new Dimension(400, 35));
            component.setMinimumSize(new Dimension(350, 35));
        } else if (component instanceof JSpinner) {
            component.setPreferredSize(new Dimension(300, 35));
            component.setMinimumSize(new Dimension(250, 35));
        }
        panel.add(component, gbc);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 0, 10);

        // Left: THÊM DỊCH VỤ button (cyan)
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(btnAddService, gbc);

        // Right: XÁC NHẬN button (green)
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnConfirmBooking, gbc);

        return panel;
    }

    private void setupEventHandlers() {
        btnConfirmBooking.addActionListener(e -> handleConfirmBooking());
        btnAddService.addActionListener(e -> toggleServicePanel());
    }

    private void toggleServicePanel() {
        isServicePanelVisible = !isServicePanelVisible;
        serviceSelectionPanel.setVisible(isServicePanelVisible);
        mainContentPanel.repaint();
    }

    private void populateRoomInformation() {
        lblRoomNumber.setText(selectedRoom.getRoomName());
        lblRoomType.setText(selectedRoom.getRoomType());
        lblRoomCapacity.setText(String.valueOf(selectedRoom.getNumberOfCustomers()));
        lblHourlyPrice.setText(String.format("%.0f VNĐ", selectedRoom.getHourlyPrice()));
        lblDailyPrice.setText(String.format("%.0f VNĐ", selectedRoom.getDailyPrice()));
        lblRoomStatus.setText(selectedRoom.getRoomStatus());
    }

    private void setDefaultValues() {
        // Set default check-in date to today
        java.util.Date today = new java.util.Date();
        spnCheckInDate.setValue(today);

        // Set default check-out date to tomorrow
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(today);
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        spnCheckOutDate.setValue(cal.getTime());

        // Set initial price based on daily rate
        txtInitialPrice.setText(String.format("%.0f", selectedRoom.getDailyPrice()));
        txtDepositPrice.setText("0");
    }

    private void handleConfirmBooking() {
        try {
            // Validate input
            if (!validateInput()) {
                return;
            }

            // Create booking event
            BookingCreationEvent bookingEvent = createBookingEvent();

            // Call booking service
            boolean success = bookingService.createBooking(bookingEvent);

            if (success) {
                JOptionPane.showMessageDialog(this, "Đặt phòng thành công!",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
                Main.showCard("MainPanel"); // Return to main screen
            } else {
                JOptionPane.showMessageDialog(this, "Đặt phòng thất bại! Vui lòng thử lại.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đặt phòng: " + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateInput() {
        if (txtCustomerName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khách hàng!",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtCustomerName.requestFocus();
            return false;
        }

        if (txtPhoneNumber.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtPhoneNumber.requestFocus();
            return false;
        }

        if (txtCCCD.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập CCCD/CMND!",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtCCCD.requestFocus();
            return false;
        }

        // Validate dates
        java.sql.Timestamp checkIn = (java.sql.Timestamp) spnCheckInDate.getValue();
        java.sql.Timestamp checkOut = (java.sql.Timestamp) spnCheckOutDate.getValue();
        java.sql.Timestamp createAt = (java.sql.Timestamp) spnCreateAt.getValue();

        if (checkOut.before(checkIn)) {
            JOptionPane.showMessageDialog(this, "Ngày trả phòng phải sau ngày nhận phòng!",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private BookingCreationEvent createBookingEvent() {
        // Convert spinner dates to SQL dates
        Timestamp checkInDate = new Timestamp(((java.sql.Timestamp) spnCheckInDate.getValue()).getTime());
        Timestamp checkOutDate = new Timestamp(((java.sql.Timestamp) spnCheckOutDate.getValue()).getTime());
        Timestamp createAt = new Timestamp(((java.sql.Timestamp) spnCreateAt.getValue()).getTime());

        // Parse prices
        double initialPrice = Double.parseDouble(txtInitialPrice.getText().trim());
        double depositPrice = Double.parseDouble(txtDepositPrice.getText().trim());

        // Create room and service lists
        List<String> roomIds = List.of(selectedRoom.getRoomId());
        List<String> serviceIds = List.of(); // Empty for now

        //
        return new BookingCreationEvent(
            txtCustomerName.getText().trim(),
            txtPhoneNumber.getText().trim(),
            txtCCCD.getText().trim(),
            new Timestamp(Date.valueOf(LocalDate.now()).getTime()), // Booking date is today
            txtNote.getText(),
            checkInDate,
            checkOutDate,
            initialPrice,
            depositPrice,
            chkIsAdvanced.isSelected(),
            roomIds,
            serviceIds,
                (String) null,
                createAt,
                false
        );
    }
}
