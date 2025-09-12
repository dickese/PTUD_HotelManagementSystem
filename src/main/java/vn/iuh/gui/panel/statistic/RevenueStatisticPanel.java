package vn.iuh.gui.panel.statistic;

import com.formdev.flatlaf.FlatClientProperties;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import vn.iuh.gui.base.CustomUI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;

public class RevenueStatisticPanel extends JPanel {
    private JPanel pnlTop;
    private JLabel lblTop;
    private JPanel pnlFilter;

    private void init(){
        createTopPanel();
        createFilterPanel();
    }

    public RevenueStatisticPanel() {
        setLayout(new BorderLayout());
        init();
    }
    private void createTopPanel(){
        pnlTop = new JPanel();
        lblTop = new JLabel("Thống kê doanh thu");
        lblTop.setForeground(CustomUI.white);
        pnlTop.setBackground(CustomUI.lightBlue);
        pnlTop.add(lblTop);
        pnlTop.setPreferredSize(new Dimension(0, 30));
        pnlTop.putClientProperty(FlatClientProperties.STYLE, " arc: 10");
        this.add(pnlTop, BorderLayout.NORTH);
    }

    private void createFilterPanel(){
        JPanel pnlFilter = new JPanel(new GridLayout(2, 2, 5, 5));

        // Ô [0,0] StartTime
        JPanel pnlStartTime = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblStartTime = new JLabel("Thời gian bắt đầu:");
        JSpinner spnStartTime = new JSpinner(new SpinnerDateModel());
        DatePickerSettings settingsStart = new DatePickerSettings();
        settingsStart.setFormatForDatesCommonEra("dd/MM/yyyy");
        DatePicker datePickerStart = new DatePicker(settingsStart);
        datePickerStart.setDateToToday();
        datePickerStart.setDate(LocalDate.now());

        pnlStartTime.add(lblStartTime);
        pnlStartTime.add(datePickerStart);
        pnlStartTime.setBackground(CustomUI.white);

        // Ô [1,0] EndTime
        JPanel pnlEndTime = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblEndTime = new JLabel("Thời gian kết thúc:");
        DatePickerSettings settingsEnd = new DatePickerSettings();
        settingsEnd.setFormatForDatesCommonEra("dd/MM/yyyy");
        DatePicker datePickerEnd = new DatePicker(settingsEnd);
        datePickerEnd.setDate(LocalDate.now());
        datePickerEnd.setDateToToday();
        pnlEndTime.add(lblEndTime);
        pnlEndTime.add(datePickerEnd);
        pnlEndTime.setBackground(CustomUI.white);

        // Ô [0,1] Employee + Button
        JPanel pnlEmployee = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> cmbEmployee = new JComboBox<>(new String[]{"Alice", "Bob", "Charlie"});
        JButton btnSearch = new JButton("Xuất file");
        JButton btnReLoad = new JButton("Tải lại");
        pnlEmployee.add(cmbEmployee);
        pnlEmployee.add(btnReLoad);
        pnlEmployee.add(btnSearch);
        pnlEmployee.setBackground(CustomUI.white);


        // Ô [1,1] RadioButton group
        JPanel pnlRadio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JRadioButton radGraph = new JRadioButton("Dạng biểu đồ");
        JRadioButton radTable = new JRadioButton("Dạng bảng");
        ButtonGroup group = new ButtonGroup();
        group.add(radGraph);
        group.add(radTable);
        pnlRadio.add(radGraph);
        pnlRadio.add(radTable);
        pnlRadio.setBackground(CustomUI.white);


        // Thêm tất cả vào pnlFilter
        pnlFilter.add(pnlStartTime);   // [0,0]
        pnlFilter.add(pnlEmployee);    // [0,1]
        pnlFilter.add(pnlEndTime);     // [1,0]
        pnlFilter.add(pnlRadio);       // [1,1]
        pnlFilter.add(Box.createVerticalStrut(300));

        pnlFilter.setBackground(CustomUI.white);
        this.add(pnlFilter, BorderLayout.CENTER);
    }
}
