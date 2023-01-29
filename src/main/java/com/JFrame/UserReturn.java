package com.JFrame;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
/**
 * @Description 还书页面：查询用户所借阅的图书，修改书籍借阅状态
 * @Author: 乔世聪
 */
public class UserReturn extends JFrame {
    private JFrame jf;
    private JTable BookTable;
    private DefaultTableModel BookModel;
    BorrowReturnDao borrowReturnDao = new BorrowReturnDao();
    public UserReturn(){
        jf = new JFrame("还书页面");
        jf.setBounds(600,300,700,500);
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(null);

//        JLabel lblNewLabel = new JLabel(new ImageIcon(UserReturn.class.getResource("/x.jpg")));
//        JLabel label1 = new JLabel();
//        ImageIcon icon =new ImageIcon("x.jpg");
//        Image image = icon.getImage();
//        image = image.getScaledInstance(250,100,Image.SCALE_DEFAULT);
//        icon.setImage(image);
//        label1.setIcon(icon);
//        jf.getContentPane().add(label1);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(22, 74, 607, 250);
        jf.add(scrollPane);
        String[] BookTitle={"编号", "书名", "状态", "借书时间", "还书时间" };
        Object [][] rowData = new Object [100][8];
        //是 TableModel 的一个实现，它使用一个 Vector 来存储单元格的值对象，该 Vector 由多个 Vector 组成。
        //构造方法之一：DefaultTableModel(Object[][] data, Object[] columnNames)
        //通过将 data 和 columnNames 传递到 setDataVector 方法来初始化该表。
        BookModel=new DefaultTableModel(rowData,BookTitle);
        BookTable=new JTable(BookModel);
        putDates(new BorrowReturn());
        BookTable.setBounds(297, 179, -279, -124);
        BookTable.setRowHeight(30);                          //设置行高
        BookTable.getColumnModel().getColumn(0).setPreferredWidth(50); //第一列列宽
        BookTable.setPreferredScrollableViewportSize(new Dimension(580 ,300));    //设置滚动面板视口大小（超过该大小的行数据需要拖动滚动条）
        scrollPane.setViewportView(BookTable);

        JLabel label = new JLabel("编号：");
        label.setFont(new Font("宋体", Font.BOLD, 15));
        label.setBounds(50, 340, 48, 33);
        jf.add(label);

        TextField textField= new TextField();
        textField.setColumns(10);
        textField.setBounds(100, 340, 135, 27);
        jf.add(textField);

        JButton button = new JButton("还书");
        button.setBounds(460, 340, 60, 27);
        jf.getContentPane().add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                if (toolUtil.isEmpty(text)) {
                    JOptionPane.showMessageDialog(null, "请选择图书");
                }
                BorrowReturn br = new BorrowReturn();
                br.setBorrowId(Integer.valueOf(text));
                br.setStatus(2);
                br.setReturnTime(toolUtil.getTime());
                Connection conn = null;
                try {
                    conn = Druid.getConnection();
                    int result = borrowReturnDao.update(conn, br);
                    if (result == 1) {
                        JOptionPane.showMessageDialog(null, "还书成功");
                        putDates(br);
                    } else {
                        JOptionPane.showMessageDialog(null, "还书失败");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "还书异常");
                } finally {
                    try {
                        Druid.close(conn);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        JButton button1 = new JButton("退出");
        button1.setBounds(530, 340, 60, 27);
        jf.getContentPane().add(button1);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new UserMenuFrm();
            }
        });


        jf.setVisible(true);

    }
    private void putDates(BorrowReturn borrowDetail) {
        DefaultTableModel model = (DefaultTableModel) BookTable.getModel();
        model.setRowCount(0);
        Integer userId = LoginFrm.currentUser.getUserId();
        Connection con = null;
        try {
            con = Druid.getConnection();
            borrowDetail.setUserId(userId);
            ResultSet list = borrowReturnDao.list(con, borrowDetail);
            while (list.next()) {
                Vector rowData = new Vector();
                rowData.add(list.getInt("id"));
                rowData.add(list.getString("book_name"));
                int status = list.getInt("status");
                if (status == 1) {
                    rowData.add("在借");
                }
                if(status ==2){
                    rowData.add("已还");
                }
                rowData.add(toolUtil.getDateByTime(list.getLong("borrow_time")));
                rowData.add(toolUtil.getDateByTime(list.getLong("return_time")));

                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                Druid.close(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new UserReturn();
    }
}
