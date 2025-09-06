package vn.iuh.gui.panel;

import vn.iuh.dao.RoomDAO;
import vn.iuh.entity.Room;
import vn.iuh.gui.base.CustomUI;
import vn.iuh.gui.base.GridRoomPanel;
import vn.iuh.gui.base.RoomItem;
import vn.iuh.servcie.RoomService;
import vn.iuh.servcie.impl.RoomServiceImpl;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class ReservationManagementPanel extends JPanel {
    private JLabel lblTop;
    private GridRoomPanel gridRoomPanels;
    private JPanel pnlTop;
    private JScrollPane scrollPane;

    private RoomService roomService;

    public ReservationManagementPanel() {
        roomService = new RoomServiceImpl();

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

        List<Room> test = roomService.getAll();
//        List<RoomItem> roomItems = mapRoomsToRoomItems(rooms);
//        gridRoomPanels = new GridRoomPanel(roomItems);

        gridRoomPanels = new GridRoomPanel(rooms);
        scrollPane = new JScrollPane(gridRoomPanels,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        setOpaque(true);
        add(scrollPane);
    }

    private List<RoomItem> mapRoomsToRoomItems(List<Room> rooms) {
        List<RoomItem> roomItems = new java.util.ArrayList<>();
        for (Room room : rooms) {
            String status = room.getRoomStatus().equals("available") ? "Trống" : "Đang thuê";
            String type = room.getRoomCategoryId(); // Giả sử bạn có phương thức để lấy tên loại phòng từ ID
            String customer = null; // Lấy tên khách nếu có
            String time = null; // Lấy thời gian đặt phòng nếu có
            Color bg = room.getRoomStatus().equals("available") ? new Color(100, 200, 100) : new Color(80, 170, 250);

            RoomItem roomItem = new RoomItem(
                    room.getRoomName(),
                    status,
                    type,
                    customer,
                    time,
                    bg
            );
            roomItems.add(roomItem);
        }
        return roomItems;
    }
}
