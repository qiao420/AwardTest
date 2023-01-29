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
 * @Description ����Ա��ҳ��
 * @Author: �����m
 */
public class AdminBorrowInfo extends JFrame{
    private JFrame jf;
    private JTable table;
    private DefaultTableModel model;
    Druid druid=new Druid();
    BorrowReturnDao borrowReturnDao=new BorrowReturnDao();          //�ļ������ݿ���н���
    public AdminBorrowInfo(){
        jf=new JFrame("����Ա����");
        jf.setBounds(400,100,580,470);          //���ò���λ�ã����
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();                        //�˵���
        jf.setJMenuBar(menuBar);                                 //���˵���������jf�Ķ�����

        JMenu menu1 = new JMenu("�û�����");
        menuBar.add(menu1);                                      //��Ӳ˵�

        JMenuItem m1_item1=new JMenuItem("�û���Ϣ");
        m1_item1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jf.dispose();
                new AdminUserInfo();
            }
        });
        menu1.add(m1_item1);                                    //��Ӳ˵���


        JMenuItem m1_item2=new JMenuItem("������Ϣ");
        m1_item2.addMouseListener(new MouseAdapter(){            //ͨ���̳�������MouseAdapter�ඨ�������
            public void mousePressed(MouseEvent e){              //��갴���¼�
                jf.dispose();                                    //�˳���ҳ��
                new AdminBorrowInfo();
            }
        } );
        menu1.add(m1_item2);                    //��Ӳ˵���

        JMenu menu2=new JMenu("�鼮����");
        menuBar.add(menu2);

        JMenuItem m2_item1=new JMenuItem("�鼮���");
        m2_item1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jf.dispose();
                //                   new AdminBookAdd();
            }
        });
        menu2.add(m2_item1);

        JMenuItem m2_item2=new JMenuItem("�鼮�޸�");
        m2_item2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jf.dispose();
                //                   new AdminBookEdit();
            }
        });
        menu2.add(m2_item2);

        JMenu menu3=new JMenu("�˳�ϵͳ");
        menu3.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                JOptionPane.showMessageDialog(null,"��ӭ�ٴ�ʹ��");
                jf.dispose();
            }
        });
        menuBar.add(menu3);

        JPanel panel = new JPanel();                                 //�̶������
        //���ñ߿򼰱߿�����
        panel.setBorder(new TitledBorder(null, "������Ϣ", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
        panel.setBounds(20, 10, 540, 74);
        jf.getContentPane().add(panel);                         //�����ӵ�����
        panel.setLayout(null);                                  //��ʹ�ò��ֹ�����

        //һά�����ͷ������
        String[] title={"�����û�","����","״̬","����ʱ��","����ʱ��"};
        //��ά���� ���ļ�¼
        String[][] dates={};
        model=new DefaultTableModel(dates,title);
        table=new JTable(model);
        putDates(new BorrowReturn());                           //��ȡ���ݿ����ݷ���table��
        JScrollPane jscrollpane = new JScrollPane();            //���������
        jscrollpane.setBounds(20, 22, 538, 314);
        jscrollpane.setViewportView(table);
        panel.add(jscrollpane);
        jf.getContentPane().add(panel);                         //����ӵ�����

        jf.setVisible(true);
        jf.setResizable(true);
    }
    private void putDates(BorrowReturn borrowReturn) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);                                       //ˢ��JTable
        Connection con = null;
        try {
            con = druid.getConnection();                            //��ȡ���ݿ�����
            ResultSet list = borrowReturnDao.read(con, borrowReturn);
            while (list.next()) {
                Vector rowData = new Vector();                          //VectorΪ�����࣬���Դ�Ų�ͬ���������ͣ�Ƶ���������ݡ��൱��һ������ʵ�ֶ�̬�洢������
                rowData.add(list.getString("username"));
                rowData.add(list.getString("book_name"));
                int status = list.getInt("status");         //��ȡ����״̬
                if (status == 1) {
                    rowData.add("�ڽ�");
                } else {
                    rowData.add("�ѻ�");
                }
                rowData.add(toolUtil.getDateByTime(list.getLong("borrow_time")));           //����ʱ��
                if (status == 2) {
                    rowData.add(toolUtil.getDateByTime(list.getLong("return_time")));           //���ڻ���ʱ��
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
