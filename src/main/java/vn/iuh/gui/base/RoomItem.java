package vn.iuh.gui.base;

import vn.iuh.dto.response.BookingResponse;
import vn.iuh.gui.panel.BookingFormPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class RoomItem extends JPanel {
    private BookingResponse bookingResponse;

    public BookingResponse getBookingResponse() {
        return bookingResponse;
    }

    public RoomItem(BookingResponse bookingResponse) {
        this.bookingResponse = bookingResponse;

        createUI();
        createActionListener();
    }

    private void createActionListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String cardName = "Đặt phòng";
                BookingFormPanel bookingFormPanel = new BookingFormPanel(bookingResponse);

                Main.addCard(bookingFormPanel, cardName);
                Main.showCard(cardName);
            }
        });
    }

    private void createUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 130));
//        setBackground(bg);
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Phần tiêu đề số phòng + sức chứa
        JPanel header = new JPanel(new GridLayout(1, 2, 20, 0));
        header.setOpaque(false);

        JLabel lblRoomName = new JLabel("Phòng " + bookingResponse.getRoomName());
        lblRoomName.setFont(new Font("Arial", Font.BOLD, 16));
        lblRoomName.setForeground(Color.WHITE);

        JLabel lblRoomCapacity = new JLabel("Sức chứa: " + bookingResponse.getNumberOfCustomers());
        lblRoomCapacity.setFont(new Font("Arial", Font.BOLD, 16));
        lblRoomCapacity.setForeground(Color.WHITE);

        header.add(lblRoomName);
        header.add(lblRoomCapacity);

        // Phần nội dung trạng thái và loại phòng
        JPanel content = new JPanel(new GridLayout(2, 1));
        content.setOpaque(false);

        JLabel lblStatus = new JLabel(bookingResponse.getRoomStatus(), SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 20));
        lblStatus.setForeground(Color.WHITE);

        JLabel lblType = new JLabel(bookingResponse.getRoomType(), SwingConstants.CENTER);
        lblType.setFont(new Font("Arial", Font.PLAIN, 14));
        lblType.setForeground(Color.WHITE);

        content.add(lblStatus);
        content.add(lblType);

        // Phần bottom dựa trên trạng thái phòng
        JPanel bottom = new JPanel(new GridLayout(3, 1));
        bottom.setOpaque(false);

        switch (bookingResponse.getRoomStatus()) {
            case "Còn trống" -> {
                JLabel lblHourlyPrice = new JLabel("Giá theo giờ: " + bookingResponse.getHourlyPrice());
                lblHourlyPrice.setFont(new Font("Arial", Font.PLAIN, 14));
                lblHourlyPrice.setForeground(Color.WHITE);

                JLabel lblDailyPrice = new JLabel("Giá theo ngày: " + bookingResponse.getDailyPrice());
                lblDailyPrice.setFont(new Font("Arial", Font.PLAIN, 14));
                lblDailyPrice.setForeground(Color.WHITE);

                bottom.add(new JLabel());
                bottom.add(lblHourlyPrice);
                bottom.add(lblDailyPrice);
                setBackground(CustomUI.lightGreen);
            }
            case "Đang kiểm tra", "Đang sử dụng", "Đặt trước" -> {
                JLabel lblCustomer = new JLabel("Tên KH: " + bookingResponse.getCustomerName());
                lblCustomer.setFont(new Font("Arial", Font.BOLD, 16));
                lblCustomer.setForeground(Color.WHITE);

                JLabel lblTimeIn = new JLabel("Checkin: " + bookingResponse.getTimeIn());
                lblTimeIn.setFont(new Font("Arial", Font.PLAIN, 14));
                lblTimeIn.setForeground(Color.WHITE);

                JLabel lblTimeOut = new JLabel("Checkout: " + bookingResponse.getTimeIn());
                lblTimeOut.setFont(new Font("Arial", Font.PLAIN, 14));
                lblTimeOut.setForeground(Color.WHITE);

                bottom.add(lblCustomer);
                bottom.add(lblTimeIn);
                bottom.add(lblTimeOut);
                setBackground(CustomUI.lightBlue);
            }

            case "Trả phòng trễ" -> {
                JLabel lblCustomer = new JLabel(bookingResponse.getCustomerName(), SwingConstants.CENTER);
                lblCustomer.setFont(new Font("Arial", Font.BOLD, 16));
                lblCustomer.setForeground(Color.WHITE);

                JLabel lblTimeIn = new JLabel("Checkin: " + bookingResponse.getTimeIn());
                lblTimeIn.setFont(new Font("Arial", Font.PLAIN, 14));
                lblTimeIn.setForeground(Color.WHITE);

                JLabel lblTimeOut = new JLabel("Checkout: " + bookingResponse.getTimeIn());
                lblTimeOut.setFont(new Font("Arial", Font.PLAIN, 14));
                lblTimeOut.setForeground(Color.WHITE);

                bottom.add(lblCustomer);
                bottom.add(lblTimeIn);
                bottom.add(lblTimeOut);
                setBackground(CustomUI.red);
            }
            default -> {
                bottom.setOpaque(false);
                setBackground(CustomUI.gray);
            }
        }


        add(header, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    public String getRoomId() {
        return bookingResponse.getRoomId();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof String)
            return Objects.equals(bookingResponse.getRoomId(), o);


        if (!(o instanceof RoomItem roomItem)) return false;
        return Objects.equals(bookingResponse, roomItem.bookingResponse);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bookingResponse);
    }
}
