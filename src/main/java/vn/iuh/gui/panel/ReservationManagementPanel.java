package vn.iuh.gui.panel;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import com.formdev.flatlaf.FlatClientProperties;
import vn.iuh.dto.response.BookingResponse;
import vn.iuh.gui.base.CustomUI;
import vn.iuh.gui.base.GridRoomPanel;
import vn.iuh.gui.base.RoomItem;
import vn.iuh.schedule.RoomStatusHandler;
import vn.iuh.servcie.BookingService;
import vn.iuh.servcie.impl.BookingServiceImpl;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationManagementPanel extends JPanel {
    private JLabel lblTop;
    private GridRoomPanel gridRoomPanels;
    private JPanel pnlTop;
    private JScrollPane scrollPane;

    private BookingService bookingService;

    public ReservationManagementPanel() {
        bookingService = new BookingServiceImpl();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        init();
    }

    private void init() {
        createTopPanel();
        this.add(Box.createVerticalStrut(300));
        createCenterPanel();
    }

    private void createTopPanel() {
        pnlTop = new JPanel();
        lblTop = new JLabel("Quản lý đặt phòng");
        lblTop.setForeground(CustomUI.white);
        pnlTop.setBackground(CustomUI.lightBlue);
        pnlTop.add(lblTop);
        pnlTop.setPreferredSize(new Dimension(0, 50));
        pnlTop.putClientProperty(FlatClientProperties.STYLE, " arc: 10");
        add(pnlTop);
    }

    private void createCenterPanel() {
        List<RoomItem> roomItems = new ArrayList<>();

        List<BookingResponse> bookingResponses = bookingService.getAllBookingInfo();
        for (BookingResponse bookingResponse : bookingResponses) {
            roomItems.add(new RoomItem(bookingResponse));
        }

        gridRoomPanels = new GridRoomPanel(roomItems);
        scrollPane = new JScrollPane(gridRoomPanels,
                                     JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                     JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        setOpaque(true);
        add(scrollPane);

        // Create cron job to update room status every 1 minute
//        createUpdateRoomStatusSchedule(gridRoomPanels);
    }

    private void createUpdateRoomStatusSchedule(GridRoomPanel gridRoomPanel) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("gridRoomPanel", gridRoomPanel);

            JobDetail jobDetail = JobBuilder.newJob(RoomStatusHandler.class)
                    .withIdentity("roomStatusUpdateJob", "group1")
                    .usingJobData(jobDataMap)
                    .build();

            Trigger trigger = org.quartz.TriggerBuilder.newTrigger()
                    .withIdentity("roomStatusUpdateTrigger", "group1")
                    .withSchedule(org.quartz.SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(10)
                            .repeatForever())
                    .build();

            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            System.out.println("Error creating schedule: " + e.getMessage());
        }
    }

}
