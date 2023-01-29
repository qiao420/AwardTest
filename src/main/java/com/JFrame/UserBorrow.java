package com.JFrame;

import com.dao.BookDao;
import com.dao.BorrowReturnDao;
import com.entity.Book;
import com.entity.BorrowReturn;
import com.utils.Druid;
import com.utils.toolUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
/**
 * @Description 借书页面：添加用户借书信息
 * @Author: 乔世聪
 */
public class UserBorrow extends JFrame {
    private JFrame jf;
    private JPanel panel;
    private JTextField textField_1;
    private JComboBox comboBox;
    private JTextField textField_2;
    private JTextField textField_3;
    BookDao bookDao=new BookDao();
    private JTable BookTable;
    private DefaultTableModel BookModel;
    BorrowReturnDao borrowReturnDao = new BorrowReturnDao();
    public UserBorrow() {
        jf=new JFrame();
        jf.setTitle("借书界面");
        //jf.setBounds(250,100,700,500);
        jf.setBounds(600,300,700,500);
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(null);

        panel = new JPanel();
        panel.setBounds(23, 20, 651, 500);
        jf.getContentPane().add(panel);
        panel.setLayout(null);

        comboBox = new JComboBox();
        comboBox.setFont(new Font("宋体", Font.BOLD, 15));
        comboBox.setBounds(123, 26, 109, 24);
        comboBox.addItem("书籍名称");
        comboBox.addItem("书籍作者");
        panel.add(comboBox);

        textField_1 = new JTextField();
        textField_1.setColumns(12);
        textField_1.setBounds(252, 25, 140, 27);
        panel.add(textField_1);

        JButton button = new JButton("查询");
        button.setBounds(408,25,80,25);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = comboBox.getSelectedIndex();
                if(index==0){
                    String bookName = textField_1.getText();
                    Book book = new Book();
                    book.setBookName(bookName);//设置书籍名称
                    putDates(book);//调用方法
                }else{
                    String authorName = textField_1.getText();
                    Book book = new Book();
                    book.setAuthor(authorName);//设置作者名称
                    putDates(book);//调用方法
                }
            }
        });
        panel.add(button);

        JButton button2 = new JButton("返回");//查询全部
        button2.setBounds(500,25,80,25);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                putDates(new Book());
            }
        });
        panel.add(button2);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(22, 74, 607, 250);
        panel.add(scrollPane);
        String[] BookTitle={"编号", "书名", "类型", "作者", "描述" };
        Object [][] rowData = new Object [100][8];

        BookModel=new DefaultTableModel(rowData,BookTitle);
        BookTable=new JTable(BookModel);
        putDates(new Book());
        BookTable.setBounds(297, 179, -279, -124);
        BookTable.setRowHeight(30);                          //设置行高
        BookTable.getColumnModel().getColumn(0).setPreferredWidth(110); //第一列列宽
        BookTable.setPreferredScrollableViewportSize(new Dimension(580 ,300));    //设置滚动面板视口大小（超过该大小的行数据需要拖动滚动条）
        scrollPane.setViewportView(BookTable);

        JLabel label = new JLabel("编号：");
        label.setFont(new Font("宋体", Font.BOLD, 15));
        label.setBounds(50, 340, 48, 33);
        panel.add(label);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(100, 340, 135, 27);
        panel.add(textField_2);

        JLabel label1 = new JLabel("书名：");
        label1.setFont(new Font("宋体", Font.BOLD, 15));
        label1.setBounds(250, 340, 48, 33);
        panel.add(label1);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(300, 340, 135, 27);
        panel.add(textField_3);

        JButton button1 = new JButton("借书");
        button1.setBounds(460, 340, 60, 27);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bookId = textField_2.getText();
                String bookName = textField_3.getText();
                if (toolUtil.isEmpty(bookId) || toolUtil.isEmpty(bookName)) {
                    JOptionPane.showMessageDialog(null, "请选择相关书籍");
                    return;
                }
                BorrowReturn br = new BorrowReturn();
                br.setUserId(LoginFrm.currentUser.getUserId());
                br.setBookId(Integer.parseInt(bookId));
                br.setStatus(1);
                br.setBorrowTime(toolUtil.getTime());
                Connection conn = null;
                try {
                    conn = Druid.getConnection();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    //先查询是否有该书
                    ResultSet list = borrowReturnDao.find(conn, br);
                    while(list.next()){
                        JOptionPane.showMessageDialog(null, "该书已在借,请先还再借");
                        return;
                    }
                    int i = borrowReturnDao.add(conn, br);
                    if (i == 1) {
                        JOptionPane.showMessageDialog(null, "借书成功");
                    } else {
                        JOptionPane.showMessageDialog(null, "借书失败");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "借书异常");
                }finally{
                    try {
                        conn.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        panel.add(button1);
        jf.getContentPane().add(panel);

        JButton button3 = new JButton("退出");
        button3.setBounds(530, 340, 60, 27);
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new UserMenuFrm();
            }
        });
        panel.add(button3);



        jf.setVisible(true);
    }

    private void putDates(Book book) {
        DefaultTableModel model = (DefaultTableModel) BookTable.getModel();
        model.setRowCount(0);
        Connection conn = null;
        try {
            conn = Druid.getConnection();
            ResultSet list = bookDao.find(book);

            while (list.next()) {
                Vector rowData = new Vector();
                rowData.add(list.getInt("id"));
                rowData.add(list.getString("book_name"));
                rowData.add(list.getString("type_name"));
                rowData.add(list.getString("author"));
                rowData.add(list.getString("remark"));
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                Druid.close(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new UserBorrow();
    }
}
