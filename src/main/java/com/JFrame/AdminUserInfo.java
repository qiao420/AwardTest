package com.JFrame;

import com.dao.UserDao;
import com.entity.User;
import com.utils.Druid;
import com.utils.toolUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
/**
 * @Description 管理员用户管理页面
 * @Author: 尹科蘭
 */
public class AdminUserInfo extends JFrame{
        private JFrame jf;
        private JTable table;
        private DefaultTableModel model;        //表格模型
        Druid druid=new Druid();                //连接池
        UserDao userDao=new UserDao();          //文件与数据库进行交互
        private JTextField textField_1;         //修改的文本框
        private JTextField textField_2;
        private JTextField textField_3;
        private JTextField textField_4;
        private JTextField textField_5;
        private int id=0;                       //根据id删除
        public AdminUserInfo(){
            jf=new JFrame("管理员界面");
            jf.setBounds(400,100,600,500);          //设置部件位置，宽高
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JMenuBar menuBar = new JMenuBar();                        //菜单栏
            jf.setJMenuBar(menuBar);                                 //将菜单栏放置在jf的顶部。

            JMenu menu1 = new JMenu("用户管理");
            menuBar.add(menu1);                                      //添加菜单

            JMenuItem m1_item1=new JMenuItem("用户信息");
            menu1.add(m1_item1);                                    //添加菜单项

            JMenuItem m1_item2=new JMenuItem("借阅信息");
            m1_item2.addMouseListener(new MouseAdapter(){            //通过继承适配器MouseAdapter类定义监听器
                public void mousePressed(MouseEvent e){             //鼠标单击事件
                    jf.dispose();                                                     //退出该页面
                    new AdminBorrowInfo();                                            //跳转借阅信息
                }
            } );
            menu1.add(m1_item2);                                        //添加菜单项

            JMenu menu2=new JMenu("书籍管理");                      //菜单
            menuBar.add(menu2);

            JMenuItem m2_item1=new JMenuItem("书籍添加");               //菜单项
            m2_item1.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    jf.dispose();
                   new AdminBookAdd();                               //鼠标单击事件，书籍添加
                }
            });
            menu2.add(m2_item1);

            JMenuItem m2_item2=new JMenuItem("书籍修改");
            m2_item2.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    jf.dispose();
                    new AdminBookEdit();                              //鼠标单击事件，关闭页面，跳转书籍修改
                }
            });
            menu2.add(m2_item2);

            JMenu menu3=new JMenu("退出系统");
            menu3.addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e){         //退出系统关闭
                    JOptionPane.showMessageDialog(null,"欢迎再次使用");
                    jf.dispose();
                }
            });
            menuBar.add(menu3);

            JPanel panel = new JPanel();                                 //固定的面板
            //设置边框及边框文字
            panel.setBorder(new TitledBorder(null, "用户信息", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
            panel.setBounds(20, 10, 540, 74);
            jf.getContentPane().add(panel);                             //面板添加到窗口
            panel.setLayout(null);                                      //不使用布局管理器

            JLabel lblbook = new JLabel("用户名：");               //显示静态文本
            lblbook.setFont(new Font("宋体", Font.BOLD, 17));
            lblbook.setBounds(35, 22, 130, 32);
            panel.add(lblbook);                                          //添加到面板

            JTextField jtextField = new JTextField();                    //单行文本框
            jtextField.setBounds(130, 27, 155, 25);
            panel.add(jtextField);
            jtextField.setColumns(10);                                      //宽度

            JButton btnSelect = new JButton("查询");                     //建立查询按钮
            btnSelect.addActionListener(new ActionListener() {              //添加单击事件
                @Override
                public void actionPerformed(ActionEvent e) {                //重写这个方法
                    String userName = jtextField.getText();
                    User user = new User();
                    user.setUserName(userName);                             //获取文本框输入的名字
                    putDates(user);                                         //在表中显示
                }
            });
            btnSelect.setBounds(300,27,70,25);
            panel.add(btnSelect);

            JButton breturn=new JButton("返回");
            breturn.setBounds(480,27,70,25);
            breturn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {                //搜索后返回，显示所有信息
                    putDates(new User());
                }
            });
            panel.add(breturn);

            //用户信息表
            String[] title={"用户编号","用户名","密码","性别","电话"};       //表头
            String[][] userDates={};                                   //具体数据
            model=new DefaultTableModel(userDates,title);               //实例化表对象
            table=new JTable(model);
            putDates(new User());                                   //获取数据库数据到tableUser

            JScrollPane jscrollpane = new JScrollPane();                //带有滚动条的面板
            jscrollpane.setBounds(30, 60, 515, 200);
            jscrollpane.setViewportView(table);                       //滚动面板
            panel.add(jscrollpane);
            jf.getContentPane().add(panel);

            //信息框
            JLabel labelid=new JLabel("用户编号");
            labelid.setFont(new Font("楷体", Font.BOLD, 17));
            labelid.setBounds(50, 270, 80, 45);
            panel.add(labelid);

            textField_1=new JTextField();
            textField_1.setBounds(120, 280, 120, 25);
            panel.add(textField_1);
            textField_1.setColumns(10);

            JLabel labelname=new JLabel("用户名");
            labelname.setFont(new Font("楷体", Font.BOLD, 17));
            labelname.setBounds(300, 270, 80, 45);
            panel.add(labelname);

            textField_2=new JTextField();
            textField_2.setBounds(355, 280, 130, 25);
            panel.add(textField_2);
            textField_2.setColumns(10);

            JLabel labelpass=new JLabel("密码");
            labelpass.setFont(new Font("楷体", Font.BOLD, 17));
            labelpass.setBounds(50, 315, 80, 45);
            panel.add(labelpass);

            textField_3=new JTextField();
            textField_3.setBounds(120, 320, 120, 25);
            panel.add(textField_3);
            textField_3.setColumns(10);

            JLabel labelsex=new JLabel("性别");
            labelsex.setFont(new Font("楷体", Font.BOLD, 17));
            labelsex.setBounds(300, 315, 80, 45);
            panel.add(labelsex);

            textField_4=new JTextField();
            textField_4.setBounds(355, 320, 120, 25);
            panel.add(textField_4);
            textField_4.setColumns(10);

            JLabel labelphone=new JLabel("手机号");
            labelphone.setFont(new Font("楷体", Font.BOLD, 17));
            labelphone.setBounds(50, 355, 80, 45);
            panel.add(labelphone);

            textField_5=new JTextField();
            textField_5.setBounds(120, 365, 150, 25);
            panel.add(textField_5);
            textField_5.setColumns(10);

            JButton btnDelete = new JButton("删除");
            btnDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int i=delDates(id);                             //删除
                    User user = new User();
                    putDates(user);
                }
            });

            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int rowDel=table.getSelectedRow();                  //选中该行删除
                    id=(int)table.getValueAt(rowDel,0);          //返回-1时没有选择行数，返回其他,行数已选，然后将表格中的数据，传入到相应的文本框中
                }
            });
            btnDelete.setBounds(390,27,70,25);
            panel.add(btnDelete);

            JButton buttonUpdate=new JButton("修改");
            buttonUpdate.addActionListener(new ActionListener() {           //修改按钮，单击事件
                @Override
                public void actionPerformed(ActionEvent e) {
                    String userId = textField_1.getText();                  //获取文本框信息
                    String userName = textField_2.getText();
                    String password = textField_3.getText();
                    String sex=textField_4.getText();
                    String phone=textField_5.getText();
                    if (toolUtil.isEmpty(userName) || toolUtil.isEmpty(password)||toolUtil.isEmpty(sex)||toolUtil.isEmpty(phone)) {
                        JOptionPane.showMessageDialog(null, "请输入相关信息");
                        return;                                                 //文本框为空提示
                    }
                    User user = new User();                                     //修改信息覆盖
                    user.setUserId(Integer.parseInt(userId));
                    user.setUserName(userName);
                    user.setPassword(password);
                    user.setSex(sex);
                    user.setPhone(phone);
                    Connection con = null;
                    try {
                        con = Druid.getConnection();                            //获取连接，修改数据库
                        int i = userDao.update(con, user);                      //返回操作数据库更新的行数
                        if (i == 1) {
                            JOptionPane.showMessageDialog(null, "修改成功");
                            putDates(new User());
                        } else {                                                //i=0
                            JOptionPane.showMessageDialog(null, "修改失败");
                        }
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "修改异常");
                    }finally{
                        try {
                            con.close();
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }
            });
            buttonUpdate.setFont(new Font("隶书",Font.BOLD,15));
            buttonUpdate.setBounds(355,370,70,25);
            panel.add(buttonUpdate);


            jf.setVisible(true);        //显示
            jf.setResizable(true);      //大小不可变
        }

        private void putDates(User user) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);                //刷新JTable

            Connection con = null;
            try {
                con = druid.getConnection();
                ResultSet list = userDao.list(con, user);
                while (list.next()) {
                    Vector rowData = new Vector();                 //Vector为向量类，可以存放不同的数据类型，频繁增减数据。相当与一个可以实现动态存储的数组
                    rowData.add(list.getInt("id"));
                    rowData.add(list.getString("username"));
                    rowData.add(list.getString("password"));
                    rowData.add(list.getString("sex"));
                    rowData.add(list.getString("phone"));
                    model.addRow(rowData);              //新增数据到表格
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        public int delDates(int id){                            //根据id删除用户
            int i=userDao.deldate(new Object[]{id});            //数组放传入的id，Object类型的的数组定义构建初始化一体
            return i;                                           //i>0删除成功
        }
        public static void main(String[] args) {
            new AdminUserInfo();
        }
    }

