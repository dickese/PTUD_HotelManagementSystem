package gui.base;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
