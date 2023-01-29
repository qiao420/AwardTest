package com.JFrame;

import com.dao.BorrowReturnDao;
import com.entity.BorrowReturn;
import com.utils.Druid;
import com.utils.toolUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
/**
 * @Description 管理员主页面
 * @Author: 尹科蘭
 */
public class AdminBorrowInfo extends JFrame{
    private JFrame jf;
    private JTable table;
    private DefaultTableModel model;
    Druid druid=new Druid();
    BorrowReturnDao borrowReturnDao=new BorrowReturnDao();          //文件与数据库进行交互
    public AdminBorrowInfo(){
        jf=new JFrame("管理员界面");
        jf.setBounds(400,100,580,470);          //设置部件位置，宽高
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();                        //菜单栏
        jf.setJMenuBar(menuBar);                                 //将菜单栏放置在jf的顶部。

        JMenu menu1 = new JMenu("用户管理");
        menuBar.add(menu1);                                      //添加菜单

        JMenuItem m1_item1=new JMenuItem("用户信息");
        m1_item1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jf.dispose();
                new AdminUserInfo();
            }
        });
        menu1.add(m1_item1);                                    //添加菜单项


        JMenuItem m1_item2=new JMenuItem("借阅信息");
        m1_item2.addMouseListener(new MouseAdapter(){            //通过继承适配器MouseAdapter类定义监听器
            public void mousePressed(MouseEvent e){              //鼠标按下事件
                jf.dispose();                                    //退出该页面
                new AdminBorrowInfo();
            }
        } );
        menu1.add(m1_item2);                    //添加菜单项

        JMenu menu2=new JMenu("书籍管理");
        menuBar.add(menu2);

        JMenuItem m2_item1=new JMenuItem("书籍添加");
        m2_item1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jf.dispose();
                //                   new AdminBookAdd();
            }
        });
        menu2.add(m2_item1);

        JMenuItem m2_item2=new JMenuItem("书籍修改");
        m2_item2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jf.dispose();
                //                   new AdminBookEdit();
            }
        });
        menu2.add(m2_item2);

        JMenu menu3=new JMenu("退出系统");
        menu3.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                JOptionPane.showMessageDialog(null,"欢迎再次使用");
                jf.dispose();
            }
        });
        menuBar.add(menu3);

        JPanel panel = new JPanel();                                 //固定的面板
        //设置边框及边框文字
        panel.setBorder(new TitledBorder(null, "借阅信息", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
        panel.setBounds(20, 10, 540, 74);
        jf.getContentPane().add(panel);                         //面板添加到窗口
        panel.setLayout(null);                                  //不使用布局管理器

        //一维数组表头栏数据
        String[] title={"借书用户","书名","状态","借书时间","还书时间"};
        //二维数据 借阅记录
        String[][] dates={};
        model=new DefaultTableModel(dates,title);
        table=new JTable(model);
        putDates(new BorrowReturn());                           //获取数据库数据放置table中
        JScrollPane jscrollpane = new JScrollPane();            //滚动条面板
        jscrollpane.setBounds(20, 22, 538, 314);
        jscrollpane.setViewportView(table);
        panel.add(jscrollpane);
        jf.getContentPane().add(panel);                         //表添加到窗口

        jf.setVisible(true);
        jf.setResizable(true);
    }
    private void putDates(BorrowReturn borrowReturn) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);                                       //刷新JTable
        Connection con = null;
        try {
            con = druid.getConnection();                            //获取数据库连接
            ResultSet list = borrowReturnDao.read(con, borrowReturn);
            while (list.next()) {
                Vector rowData = new Vector();                          //Vector为向量类，可以存放不同的数据类型，频繁增减数据。相当与一个可以实现动态存储的数组
                rowData.add(list.getString("username"));
                rowData.add(list.getString("book_name"));
                int status = list.getInt("status");         //获取借书状态
                if (status == 1) {
                    rowData.add("在借");
                } else {
                    rowData.add("已还");
                }
                rowData.add(toolUtil.getDateByTime(list.getLong("borrow_time")));           //借书时间
                if (status == 2) {
                    rowData.add(toolUtil.getDateByTime(list.getLong("return_time")));           //存在还书时间
                }
                model.addRow(rowData);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                con.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new AdminBorrowInfo();
    }
}
