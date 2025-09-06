package gui.base;

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


    public RoomItem(String roomNumber, String status, String type, String customer, String time, Color bg) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 100));
//        setBackground(bg);
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Phần tiêu đề (số phòng)
        JLabel lblRoom = new JLabel("Phòng " + roomNumber);
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
