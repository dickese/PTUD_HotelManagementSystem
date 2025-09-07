package vn.iuh.gui.base;

import vn.iuh.dto.response.BookingResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoomItem extends JPanel {
    private void init(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
    }

    public RoomItem(BookingResponse bookingResponse) {
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

    public RoomItem(String roomID, String status, String type, String customer, String time, Color bg) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 100));
//        setBackground(bg);
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Phần tiêu đề (số phòng)
        JLabel lblRoom = new JLabel("Phòng " + roomID);
        lblRoom.setFont(new Font("Arial", Font.BOLD, 16));
        lblRoom.setForeground(Color.WHITE);

        // Phần nội dung
        JLabel lblStatus = new JLabel(status, SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 20));
        lblStatus.setForeground(Color.WHITE);

        JLabel lblType = new JLabel(type, SwingConstants.CENTER);
        lblType.setFont(new Font("Arial", Font.PLAIN, 14));
        lblType.setForeground(Color.WHITE);

        // Nếu có khách
        JPanel content = new JPanel(new GridLayout(3, 1));
        content.setOpaque(false);
        content.add(lblStatus);
        if (customer != null) {
            content.add(new JLabel(customer, SwingConstants.CENTER));
            content.add(new JLabel(time, SwingConstants.CENTER));
            setBackground(CustomUI.lightBlue);
        } else {
            content.add(lblType);
            setBackground(CustomUI.lightGreen);
        }

        add(lblRoom, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }
}
