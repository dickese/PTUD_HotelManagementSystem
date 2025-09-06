package vn.iuh.gui.panel;

import vn.iuh.gui.base.CustomUI;
import vn.iuh.gui.base.GridRoomPanel;
import vn.iuh.gui.base.RoomItem;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class ReservationManagementPanel extends JPanel {
    private JLabel lblTop;
    private GridRoomPanel gridRoomPanels;
    private JPanel pnlTop;
    private JScrollPane scrollPane;


    public ReservationManagementPanel() {
        setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        init();
    }
    private void init(){
        createTopPanel();
        this.add(Box.createVerticalStrut(300));
        createCenterPanel();
    }
    private void createTopPanel(){
        pnlTop = new JPanel();
        lblTop = new JLabel("Quản lý đặt phòng");
        lblTop.setForeground(CustomUI.white);
        pnlTop.setBackground(CustomUI.lightBlue);
        pnlTop.add(lblTop);
        pnlTop.setPreferredSize(new Dimension(0, 50));
        add(pnlTop);
    }

    private void createCenterPanel(){
        List<RoomItem> rooms = List.of(
                new RoomItem("101", "Trống", "Phòng Thường Giường Đơn", null, null, new Color(100, 200, 100)),
                new RoomItem("102", "Trống", "Phòng VIP Giường Đôi", null, null, new Color(100, 200, 100)),
                new RoomItem("105", "Đang thuê", "Phòng Thường Giường Đơn", "Lê Thị B", "02-12-2024 12:18", new Color(80, 170, 250)),
                new RoomItem("109", "Trống", "Phòng Thường Giường Đơn", null, null, new Color(100, 200, 100)),
                new RoomItem("203", "Trống", "Phòng Thường Giường Đơn", null, null, new Color(100, 200, 100)),
                new RoomItem("101", "Trống", "Phòng Thường Giường Đơn", null, null, new Color(100, 200, 100)),
                new RoomItem("102", "Trống", "Phòng VIP Giường Đôi", null, null, new Color(100, 200, 100)),
                new RoomItem("105", "Đang thuê", "Phòng Thường Giường Đơn", "Lê Thị B", "02-12-2024 12:18", new Color(80, 170, 250)),
                new RoomItem("109", "Trống", "Phòng Thường Giường Đơn", null, null, new Color(100, 200, 100)),
                new RoomItem("203", "Trống", "Phòng Thường Giường Đơn", null, null, new Color(100, 200, 100)),
                new RoomItem("101", "Trống", "Phòng Thường Giường Đơn", null, null, new Color(100, 200, 100)),
                new RoomItem("102", "Trống", "Phòng VIP Giường Đôi", null, null, new Color(100, 200, 100)),
                new RoomItem("105", "Đang thuê", "Phòng Thường Giường Đơn", "Lê Thị B", "02-12-2024 12:18", new Color(80, 170, 250)),
                new RoomItem("109", "Trống", "Phòng Thường Giường Đơn", null, null, new Color(100, 200, 100)),
                new RoomItem("203", "Trống", "Phòng Thường Giường Đơn", null, null, new Color(100, 200, 100)),
                new RoomItem("101", "Trống", "Phòng Thường Giường Đơn", null, null, new Color(100, 200, 100)),
                new RoomItem("102", "Trống", "Phòng VIP Giường Đôi", null, null, new Color(100, 200, 100)),
                new RoomItem("105", "Đang thuê", "Phòng Thường Giường Đơn", "Lê Thị B", "02-12-2024 12:18", new Color(80, 170, 250)),
                new RoomItem("109", "Trống", "Phòng Thường Giường Đơn", null, null, new Color(100, 200, 100)),
                new RoomItem("203", "Trống", "Phòng Thường Giường Đơn", null, null, new Color(100, 200, 100))
        );
        gridRoomPanels = new GridRoomPanel(rooms);
        scrollPane = new JScrollPane(gridRoomPanels,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        setOpaque(true);
        add(scrollPane);
    }
}
