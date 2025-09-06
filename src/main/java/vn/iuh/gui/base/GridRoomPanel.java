package vn.iuh.gui.base;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GridRoomPanel extends JPanel {

    private void init(List<RoomItem> rooms){
        setLayout(new GridLayout(0,4, 10,10));
        for(RoomItem room : rooms){
            add(room);
        }
    }

    public GridRoomPanel(List<RoomItem> rooms) {
        init(rooms);
    }
}
