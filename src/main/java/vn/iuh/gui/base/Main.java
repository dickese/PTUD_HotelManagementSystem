//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package vn.iuh.gui.base;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import gui.panel.ReservationManagementPanel;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private JPanel pMain;
    private Menu cmpMenu;
    private JScrollPane scrollPanel;
    private JPanel cmpTopHeading;
    private JPanel pnlLeftWrapper;
    private JPanel pnlTopWrapper;
    private JPanel pnlLeftTopWrapper;
    private CardLayout mainLayout;
    private JPanel pnlCenter;
    private UserInfoPanel userInfo;

    public void init() {
        //Set hiển thị mặc định toàn màn hình
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //Hiển thị chính giữa
        this.setLocationRelativeTo(null);
        //Tắt ứng dụng khi tắt giao diện
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public Main() {
        this.init();
        //Là wrapperPanel bọc tất cả thành phần trong ứng dụng, nằm ngay trên JFrame
        this.pMain = new JPanel();
        this.pMain.setLayout(new BorderLayout());

        createASideBar();
        createTopCounting();
        createCenterPanel();
        initializeMainPanels();
        
        this.add(this.pMain);
    }

    private void createCenterPanel(){
        //Tạo panelCenter dùng để cho phần thao tác chính, chứa các màn hình khác với cardLayout 
        mainLayout = new CardLayout();
        pnlCenter = new JPanel(mainLayout);
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5,5));
        this.pMain.add(pnlCenter, BorderLayout.CENTER);
    }

    private void createASideBar() {
        //Tạo 1 panel bên trái bọc 2 thành phần userInfor và Menu
        pnlLeftWrapper = new JPanel();
        pnlLeftWrapper.setLayout(new BorderLayout());
        
        //Tạo phần menu
        cmpMenu = new Menu(this);
        
        //Tạo phần thanh cuộn cho menu
        scrollPanel = new JScrollPane(cmpMenu,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBorder(null);
        scrollPanel.setBorder(BorderFactory.createEmptyBorder(0,4,0,0));
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        
        //Tạo phần khung nhỏ hiển thị tài khoảng đang đăng nhập
        userInfo = new UserInfoPanel("Quản Lý", "Dickese Ng");
        
        //Bọc phần userInfor bằng 1 panel khác để có thể tùy chỉnh border cách đều
        pnlLeftTopWrapper = new JPanel();
        pnlLeftTopWrapper.add(userInfo);
        
        
        pnlLeftWrapper.add(pnlLeftTopWrapper, BorderLayout.NORTH);
        pnlLeftWrapper.add(scrollPanel);
        
        
        this.pMain.add(pnlLeftWrapper, BorderLayout.WEST);
    }
    private void createTopCounting(){
        //Tạo phần top bằng 1 panel được custom lại
        cmpTopHeading = new HeadingTop();
        this.pMain.add(cmpTopHeading, BorderLayout.NORTH);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatMacLightLaf());
        SwingUtilities.invokeLater(() ->{
            (new Main()).setVisible(true);
        });
    }

    //Truyển vào tên của panel, khi gọi sẽ hiển thị nó lên phía trên trong cardLayout
    public void showCard(String name) {
        System.out.println(name);
        this.mainLayout.show(pnlCenter, name);
    }


    //Tạo các màn hình con cho cardLayout (màn hình chức năng)
    public void initializeMainPanels(){
        JPanel pink = new JPanel();
        pink.setBackground(Color.cyan);
        JPanel blue = new JPanel();
        blue.setBackground(Color.blue);
        ReservationManagementPanel tmp = new ReservationManagementPanel();
        pnlCenter.add(pink, "dsadsa");
        pnlCenter.add(blue, "Inbox");
        pnlCenter.add(tmp, "Read");
    }

}
