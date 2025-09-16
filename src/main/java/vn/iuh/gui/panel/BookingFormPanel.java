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

public class BookingFormPanel extends JPanel {
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
    private JTextField txtSearchService;
    private JPanel serviceListPanel;
    private JLabel lblTotalServiceCost;
    private boolean isServicePanelVisible = false;
    private java.util.List<String> selectedServices = new java.util.ArrayList<>();

    // Action Buttons
    private JButton btnConfirmBooking;
    private JButton btnCancel;
    private JButton btnCalculatePrice;
    private JButton btnAddService;

    // Main content components
    private JPanel mainContentPanel;

    public BookingFormPanel(BookingResponse roomInfo) {
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
        btnConfirmBooking = new JButton("Xác nhận đặt phòng");
        btnCancel = new JButton("Hủy");
        btnCalculatePrice = new JButton("Tính giá");
        btnAddService = new JButton("Thêm dịch vụ");

        // Style buttons
        styleButton(btnConfirmBooking, CustomUI.lightGreen);
        styleButton(btnCancel, CustomUI.red);
        styleButton(btnCalculatePrice, CustomUI.lightBlue);
        styleButton(btnAddService, new Color(255, 165, 0)); // Orange color for service button

        // Initialize service components
        initializeServiceComponents();
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(CustomUI.normalFont);
        button.setFocusPainted(false);
//        button.setBorder(BorderFactory.createRaisedBorderBorder());
        button.setPreferredSize(new Dimension(150, 40));
    }

    private void setupLayout() {
        // Main title
        JLabel titleLabel = new JLabel("ĐẶT PHÒNG: " + selectedRoom.getRoomName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(CustomUI.darkBlue);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Create main content panel with overlay capability for service panel
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new OverlayLayout(mainContentPanel));
        mainContentPanel.setBackground(Color.WHITE);

        // Base content panel (your existing layout)
        JPanel basePanel = createBaseContentPanel();

        // Add both panels to main content
        mainContentPanel.add(serviceSelectionPanel); // Service panel on top (initially hidden)
        mainContentPanel.add(basePanel); // Base content underneath

        // Create scroll pane for the entire main content
        JScrollPane mainScrollPane = new JScrollPane(mainContentPanel);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setBorder(null);
        // Set scroll speed
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainScrollPane.getViewport().setBackground(Color.WHITE);

        // Add to main panel
        add(titleLabel, BorderLayout.NORTH);
        add(mainScrollPane, BorderLayout.CENTER);
    }

    private JPanel createBaseContentPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setOpaque(true);
        GridBagConstraints gbc = new GridBagConstraints();

        // LEFT COLUMN - Row 0: Booking info panel (WHITE background) - SWAPPED TO TOP
        JPanel bookingPanel = createBookingInfoPanel();
        bookingPanel.setBackground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.6; gbc.weighty = 0.6; // More height for booking info
        gbc.insets = new Insets(0, 0, 5, 5);
        contentPanel.add(bookingPanel, gbc);

        // RIGHT COLUMN - Row 0 & 1: Tall room info panel (blue) - spans both left rows
        JPanel rightRoomPanel = createRightRoomInfoPanel();
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2; // Span 2 rows to be tall like your design
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.4; gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 5, 10, 0);
        contentPanel.add(rightRoomPanel, gbc);

        // LEFT COLUMN - Row 1: Customer info panel (WHITE background) - SWAPPED TO BOTTOM with reduced height
        JPanel customerPanel = createCustomerInfoPanel();
        customerPanel.setBackground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1; // Reset to single row
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.6; gbc.weighty = 0.4; // Less height for customer info
        gbc.insets = new Insets(5, 0, 10, 5);
        contentPanel.add(customerPanel, gbc);

        // BOTTOM: Button panel (spans both columns)
        JPanel buttonPanel = createButtonPanel();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; gbc.weighty = 0.0;
        gbc.insets = new Insets(10, 0, 0, 0);
        contentPanel.add(buttonPanel, gbc);

        return contentPanel;
    }

    private JPanel createRoomInfoPanel() {
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
        gbc.insets = new Insets(15, 25, 15, 25); // Increased padding for better spacing
        gbc.anchor = GridBagConstraints.WEST;

        // Left column with more spacing
        addRoomInfoRowTwoColumns(panel, gbc, 0, 0, "Số phòng:", lblRoomNumber);
        addRoomInfoRowTwoColumns(panel, gbc, 1, 0, "Sức chứa:", lblRoomCapacity);
        addRoomInfoRowTwoColumns(panel, gbc, 2, 0, "Giá theo giờ:", lblHourlyPrice);

        // Right column with proper spacing
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
        gbc.insets = new Insets(15, 10, 15, 25); // Add space between label and value
        valueLabel.setFont(CustomUI.normalFont);
        valueLabel.setForeground(Color.WHITE);
        panel.add(valueLabel, gbc);

        // Reset insets for next iteration
        gbc.insets = new Insets(15, 25, 15, 25);
    }

    private JPanel createCustomerInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
//        panel.setBackground(new Color(255, 255, 204)); // Light yellow
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
            component.setPreferredSize(new Dimension(400, 35)); // Much larger text fields
            component.setMinimumSize(new Dimension(350, 35)); // Ensure minimum size
        } else if (component instanceof JSpinner) {
            component.setPreferredSize(new Dimension(300, 35)); // Larger spinners too
            component.setMinimumSize(new Dimension(250, 35));
        }
        panel.add(component, gbc);
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

        // Add column
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
        scrollNote.setPreferredSize(new Dimension(250, 80));
        panel.add(scrollNote, gbc);

        return panel;
    }


    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 0, 10);

        // Left: THÊM DỊCH VỤ button (cyan)
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        btnAddService.setText("THÊM DỊCH VỤ");
        btnAddService.setBackground(new Color(0, 206, 209)); // Cyan
        btnAddService.setPreferredSize(new Dimension(200, 50));
        panel.add(btnAddService, gbc);

        // Right: XÁC NHẬN button (green)
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        btnConfirmBooking.setText("XÁC NHẬN");
        btnConfirmBooking.setBackground(CustomUI.lightGreen);
        btnConfirmBooking.setPreferredSize(new Dimension(200, 50));
        panel.add(btnConfirmBooking, gbc);

        return panel;
    }

    private void setupEventHandlers() {
        btnConfirmBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleConfirmBooking();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCancel();
            }
        });

        btnCalculatePrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePrice();
            }
        });

        btnAddService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleServicePanel();
            }
        });
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

    private void calculatePrice() {
        try {
            java.util.Date checkIn = (java.util.Date) spnCheckInDate.getValue();
            java.util.Date checkOut = (java.util.Date) spnCheckOutDate.getValue();

            if (checkOut.before(checkIn)) {
                JOptionPane.showMessageDialog(this, "Ngày trả phòng phải sau ngày nhận phòng!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            long diffInMillis = checkOut.getTime() - checkIn.getTime();
            long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);

            if (diffInDays == 0) diffInDays = 1; // Minimum 1 day

            double totalPrice = diffInDays * selectedRoom.getDailyPrice();
            txtInitialPrice.setText(String.format("%.0f", totalPrice));

            JOptionPane.showMessageDialog(this,
                String.format("Tổng giá phòng: %.0f VNĐ (%d ngày)", totalPrice, diffInDays),
                "Tính giá", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tính giá: " + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
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
                handleCancel(); // Return to previous screen
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
                "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtCustomerName.requestFocus();
            return false;
        }

        if (txtPhoneNumber.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!",
                "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtPhoneNumber.requestFocus();
            return false;
        }

        if (txtCCCD.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập CCCD/CMND!",
                "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtCCCD.requestFocus();
            return false;
        }

        try {
            Double.parseDouble(txtInitialPrice.getText());
            Double.parseDouble(txtDepositPrice.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá phòng và tiền đặt cọc phải là số!",
                "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        java.sql.Timestamp checkIn = (java.sql.Timestamp) spnCheckInDate.getValue();
        java.sql.Timestamp checkOut = (java.sql.Timestamp) spnCheckOutDate.getValue();
        java.sql.Timestamp createAt = (java.sql.Timestamp) spnCreateAt.getValue();

        if (checkOut.before(checkIn)) {
            JOptionPane.showMessageDialog(this, "Ngày trả phòng phải sau ngày nhận phòng!",
                "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private BookingCreationEvent createBookingEvent() {
        String customerName = txtCustomerName.getText().trim();
        String phoneNumber = txtPhoneNumber.getText().trim();
        String cccd = txtCCCD.getText().trim();
        Timestamp reserveDate = new Timestamp(System.currentTimeMillis());
        String note = txtNote.getText().trim();
        Timestamp checkInDate = new Timestamp(((java.sql.Timestamp) spnCheckInDate.getValue()).getTime());
        Timestamp checkOutDate = new Timestamp(((java.sql.Timestamp) spnCheckOutDate.getValue()).getTime());
        Timestamp createAt = new Timestamp(((java.sql.Timestamp) spnCreateAt.getValue()).getTime());
        double initialPrice = Double.parseDouble(txtInitialPrice.getText());
        double depositPrice = Double.parseDouble(txtDepositPrice.getText());
        boolean isAdvanced = chkIsAdvanced.isSelected();
        List<String> roomIds = Arrays.asList(selectedRoom.getRoomId());
        List<String> serviceIds = Arrays.asList(); // Empty for now
        String shiftAssignmentId = "SHIFT001"; // You may need to get this from current session

        return new BookingCreationEvent(customerName, phoneNumber, cccd, reserveDate, note,
                checkInDate, checkOutDate, initialPrice, depositPrice, isAdvanced,
                roomIds, serviceIds, shiftAssignmentId, createAt, false);
    }

    private void handleCancel() {
        // Return to the previous panel - you may need to adjust this based on your navigation system
        Container parent = this.getParent();
        if (parent instanceof JPanel) {
            CardLayout layout = (CardLayout) parent.getLayout();
            layout.previous(parent);
        }
    }

    private void toggleServicePanel() {
        isServicePanelVisible = !isServicePanelVisible;
        serviceSelectionPanel.setVisible(isServicePanelVisible);

        if (isServicePanelVisible) {
            // Make the overlay panel fill the entire main content area
            serviceSelectionPanel.setBounds(0, 0, mainContentPanel.getWidth(), mainContentPanel.getHeight());

            // Center the service dialog within the overlay
            Component serviceDialog = serviceSelectionPanel.getComponent(0);
            int x = (mainContentPanel.getWidth() - serviceDialog.getWidth()) / 2;
            int y = (mainContentPanel.getHeight() - serviceDialog.getHeight()) / 2;
            serviceDialog.setLocation(x, y);

            mainContentPanel.setComponentZOrder(serviceSelectionPanel, 0); // Bring to front
        }

        mainContentPanel.revalidate();
        mainContentPanel.repaint();
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
        gbc.insets = new Insets(8, 15, 8, 15); // Reduced padding to prevent overflow
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Essential room information only - reduced content to prevent overflow
        addRoomInfoRowSingleColumn(panel, gbc, 0, "Số phòng:", lblRoomNumber);
        addRoomInfoRowSingleColumn(panel, gbc, 1, "Loại phòng:", lblRoomType);
        addRoomInfoRowSingleColumn(panel, gbc, 2, "Sức chứa:", lblRoomCapacity);
        addRoomInfoRowSingleColumn(panel, gbc, 3, "Trạng thái:", lblRoomStatus);
        addRoomInfoRowSingleColumn(panel, gbc, 4, "Giá theo giờ:", lblHourlyPrice);
        addRoomInfoRowSingleColumn(panel, gbc, 5, "Giá theo ngày:", lblDailyPrice);
        
        // Add some spacing before additional info with smaller font
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 15, 8, 15);
        JLabel lblAdditionalInfo = new JLabel("Thông tin bổ sung:");
        lblAdditionalInfo.setFont(new Font(CustomUI.normalFont.getName(), Font.BOLD, CustomUI.normalFont.getSize() - 1)); // Smaller font
        lblAdditionalInfo.setForeground(Color.WHITE);
        panel.add(lblAdditionalInfo, gbc);
        
        // Reset insets and add compact additional info
        gbc.insets = new Insets(5, 15, 5, 15); // Smaller spacing
        gbc.gridwidth = 1;

        // Create compact labels for additional info
        JLabel lblRoomId = new JLabel(selectedRoom.getRoomId());
        lblRoomId.setFont(new Font(CustomUI.normalFont.getName(), Font.PLAIN, CustomUI.normalFont.getSize() - 1));

        JLabel lblAmenities = new JLabel("Điều hòa, TV, WiFi");
        lblAmenities.setFont(new Font(CustomUI.normalFont.getName(), Font.PLAIN, CustomUI.normalFont.getSize() - 1));

        JLabel lblArea = new JLabel("25m²");
        lblArea.setFont(new Font(CustomUI.normalFont.getName(), Font.PLAIN, CustomUI.normalFont.getSize() - 1));

        JLabel lblFloor = new JLabel("Tầng " + extractFloorFromRoomName());
        lblFloor.setFont(new Font(CustomUI.normalFont.getName(), Font.PLAIN, CustomUI.normalFont.getSize() - 1));

        addRoomInfoRowSingleColumn(panel, gbc, 7, "Mã phòng:", lblRoomId);
        addRoomInfoRowSingleColumn(panel, gbc, 8, "Tiện nghi:", lblAmenities);
        addRoomInfoRowSingleColumn(panel, gbc, 9, "Diện tích:", lblArea);
        addRoomInfoRowSingleColumn(panel, gbc, 10, "Tầng:", lblFloor);

        return panel;
    }

    private void addRoomInfoRowSingleColumn(JPanel panel, GridBagConstraints gbc, int row, String labelText, JLabel valueLabel) {
        gbc.gridy = row;

        gbc.gridx = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font(CustomUI.normalFont.getName(), Font.PLAIN, CustomUI.normalFont.getSize() - 1)); // Smaller font
        label.setForeground(Color.WHITE);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        valueLabel.setFont(new Font(CustomUI.normalFont.getName(), Font.PLAIN, CustomUI.normalFont.getSize() - 1)); // Smaller font
        valueLabel.setForeground(Color.WHITE);
        panel.add(valueLabel, gbc);
    }

    private String extractFloorFromRoomName() {
        // Extract floor number from room name (e.g., "101" -> "1", "205" -> "2")
        String roomName = selectedRoom.getRoomName();
        if (roomName != null && roomName.length() >= 2) {
            return roomName.substring(0, 1);
        }
        return "1";
    }
    
    private void initializeServiceComponents() {
        // Create a full-screen overlay panel that blocks all interactions
        serviceSelectionPanel = new JPanel();
        serviceSelectionPanel.setLayout(null); // Use null layout for absolute positioning
        serviceSelectionPanel.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent black overlay
        serviceSelectionPanel.setVisible(false);
        serviceSelectionPanel.setOpaque(false);

        // Add mouse listeners to consume all mouse events and prevent them from reaching underlying components
        serviceSelectionPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Consume the event to prevent it from reaching underlying components
                e.consume();
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                e.consume();
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                e.consume();
            }
        });

        serviceSelectionPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                e.consume();
            }

            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                e.consume();
            }
        });

        // Create the actual service dialog panel
        JPanel serviceDialog = new JPanel(new BorderLayout());
        serviceDialog.setBackground(new Color(255, 255, 204)); // Light yellow background like your design
        serviceDialog.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        serviceDialog.setSize(600, 500);

        // Header panel with title and close button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 255, 204));

        JLabel titleLabel = new JLabel("GỌI DỊCH VỤ", SwingConstants.CENTER);
        titleLabel.setFont(new Font(CustomUI.normalFont.getName(), Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Close button (X)
        JButton closeButton = new JButton("✕");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.setPreferredSize(new Dimension(30, 30));
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.addActionListener(e -> toggleServicePanel());

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(closeButton, BorderLayout.EAST);

        // Main content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(255, 255, 204));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Search field at the top
        txtSearchService = new JTextField("Tìm kiếm");
        txtSearchService.setPreferredSize(new Dimension(350, 40)); // Made larger
        txtSearchService.setFont(CustomUI.normalFont);
        txtSearchService.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0;
        contentPanel.add(txtSearchService, gbc);

        // Service list panel
        serviceListPanel = new JPanel();
        serviceListPanel.setLayout(new BoxLayout(serviceListPanel, BoxLayout.Y_AXIS));
        serviceListPanel.setBackground(new Color(255, 255, 204));

        // Add sample services with larger size
        addServiceItem("Phần Ăn Sáng", "100.000");
        addServiceItem("Gọi xe đưa đón", "50.000");
        addServiceItem("Massage", "200.000");
        addServiceItem("Giặt ủi", "30.000");
        addServiceItem("<NAME>", "<MONEY>");

        JScrollPane serviceScrollPane = new JScrollPane(serviceListPanel);
        serviceScrollPane.setPreferredSize(new Dimension(350, 250)); // Made larger
        serviceScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        serviceScrollPane.setBackground(new Color(255, 255, 204));
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(serviceScrollPane, gbc);

        // Total cost label
        lblTotalServiceCost = new JLabel("Tổng tiền:                                        <Total>");
        lblTotalServiceCost.setFont(CustomUI.normalFont);
        lblTotalServiceCost.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        lblTotalServiceCost.setPreferredSize(new Dimension(350, 40)); // Made larger
        lblTotalServiceCost.setOpaque(true);
        lblTotalServiceCost.setBackground(Color.WHITE);
        gbc.gridy = 2;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(lblTotalServiceCost, gbc);

        // Confirm button
        JButton btnConfirmService = new JButton("XÁC NHẬN");
        btnConfirmService.setBackground(new Color(144, 238, 144)); // Light green
        btnConfirmService.setForeground(Color.WHITE);
        btnConfirmService.setFont(CustomUI.normalFont);
        btnConfirmService.setPreferredSize(new Dimension(350, 50)); // Made larger
        btnConfirmService.addActionListener(e -> confirmServiceSelection());
        gbc.gridy = 3;
        contentPanel.add(btnConfirmService, gbc);

        serviceDialog.add(headerPanel, BorderLayout.NORTH);
        serviceDialog.add(contentPanel, BorderLayout.CENTER);

        // Add the service dialog to the overlay panel
        serviceSelectionPanel.add(serviceDialog);
    }

    private void addServiceItem(String serviceName, String price) {
        JPanel serviceItem = new JPanel(new BorderLayout());
        serviceItem.setBackground(Color.WHITE);
        serviceItem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        serviceItem.setPreferredSize(new Dimension(350, 45));

        JLabel nameLabel = new JLabel(serviceName);
        nameLabel.setFont(CustomUI.normalFont);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // More padding

        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(CustomUI.normalFont);
        priceLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // More padding
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        serviceItem.add(nameLabel, BorderLayout.WEST);
        serviceItem.add(priceLabel, BorderLayout.EAST);

        // Add click functionality
        serviceItem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (!serviceName.equals("<NAME>")) {
                    selectService(serviceName, price);
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                serviceItem.setBackground(CustomUI.lightBlue);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                serviceItem.setBackground(Color.WHITE);
            }
        });

        serviceListPanel.add(serviceItem);
        serviceListPanel.add(Box.createVerticalStrut(5)); // Larger gap between items
    }

    private void selectService(String serviceName, String priceStr) {
        // Use the service dialog as parent to ensure proper modal behavior
        Component serviceDialog = serviceSelectionPanel.getComponent(0);

        String quantityStr = JOptionPane.showInputDialog(serviceDialog,
            "Nhập số lượng cho dịch vụ: " + serviceName,
            "Chọn số lượng",
            JOptionPane.QUESTION_MESSAGE);

        if (quantityStr != null && !quantityStr.trim().isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityStr.trim());
                if (quantity > 0) {
                    selectedServices.add(serviceName + " (x" + quantity + ")");
                    updateTotalServiceCost();

                    // Ensure service panel stays on top after dialog closes
                    SwingUtilities.invokeLater(() -> {
                        mainContentPanel.setComponentZOrder(serviceSelectionPanel, 0);
                        mainContentPanel.repaint();
                    });
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(serviceDialog, "Vui lòng nhập số hợp lệ!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Ensure service panel stays on top after any dialog interaction
        SwingUtilities.invokeLater(() -> {
            mainContentPanel.setComponentZOrder(serviceSelectionPanel, 0);
            mainContentPanel.repaint();
        });
    }

    private void updateTotalServiceCost() {
        // Simple calculation - in real implementation, you'd calculate based on actual services and quantities
        double total = selectedServices.size() * 75000; // Sample calculation
        lblTotalServiceCost.setText("Tổng tiền:                                        " + String.format("%.0f", total));
    }

    private void confirmServiceSelection() {
        if (!selectedServices.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Đã chọn " + selectedServices.size() + " dịch vụ",
                "Xác nhận", JOptionPane.INFORMATION_MESSAGE);
        }
        toggleServicePanel(); // Close the service panel
    }
}
