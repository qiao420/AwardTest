package com.JFrame;

import com.dao.BookDao;
import com.entity.Book;
import com.entity.BookType;
import com.utils.Druid;
import com.utils.toolUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * @Description ����Ա�鼮����ҳ��
 * @Author: �С��
 */
public class AdminBookAdd extends JDialog {
	private JTextField txtBooksName;// ����
	private JTextField txtAuthor;// ����
	private JTextField txtPress;// ������
	private JTextField txtNumber;// ���
	private JTextField txtBooksPrice;// �鼮�۸�
	private JTextField txtRemark;//�鼮����

	private JComboBox comboBox;
	BookDao bookDao=new BookDao();

	public AdminBookAdd() {

		JFrame homepage = new JFrame("����Ա����");
		homepage.setBounds(450, 130, 540, 675);
		homepage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homepage.getContentPane().setLayout(null);
		//�˵���
		JMenuBar menuBar=new JMenuBar();
		homepage.setJMenuBar(menuBar);

		JMenu lb2=new JMenu("�鼮����");
		menuBar.add(lb2);
		JMenuItem mbt3=new JMenuItem("�鼮���");
		lb2.add(mbt3);
		JMenuItem mbt4=new JMenuItem("�鼮�޸�");
		mbt4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				homepage.dispose();
				new AdminBookEdit();
			}
		});
		lb2.add(mbt4);

		JMenu lb3=new JMenu("�û�����");
		menuBar.add(lb3);

		JMenuItem mbt5=new JMenuItem("�û���Ϣ");
		mbt5.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				homepage.dispose();
				new AdminUserInfo();
			}
		});
		lb3.add(mbt5);
		JMenuItem mbt6=new JMenuItem("������Ϣ");
		mbt6.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				homepage.dispose();
				new AdminBorrowInfo();
			}
		});
		lb3.add(mbt6);

		JMenu lb4=new JMenu("�˳�ϵͳ");
		lb4.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(null,"��ӭ�ٴ�ʹ��");
				homepage.dispose();
			}
		});
		menuBar.add(lb4);
		homepage.setVisible(true);
		homepage.setResizable(true);


		JPanel jPanel = new JPanel();
		jPanel.setBorder(new TitledBorder(null, "�鼮���", 4, 2, null, Color.blue));
		jPanel.setBounds(20, 31, 500, 550);
		homepage.getContentPane().add(jPanel);
		jPanel.setLayout(null);

		JLabel lblBookName = new JLabel("����");
		lblBookName.setFont(new Font("������", Font.PLAIN, 15));
		lblBookName.setBounds(58, 31, 45, 27);
		jPanel.add(lblBookName);

		txtBooksName = new JTextField();
		txtBooksName.setColumns(10);
		txtBooksName.setBounds(101, 31, 129, 27);
		jPanel.add(txtBooksName);

		JLabel lblBookAuthor = new JLabel("����");
		lblBookAuthor.setFont(new Font("������", Font.PLAIN, 15));
		lblBookAuthor.setBounds(58, 81, 45, 27);
		jPanel.add(lblBookAuthor);

		txtAuthor = new JTextField();
		txtAuthor.setColumns(10);
		txtAuthor.setBounds(101, 81, 129, 27);
		jPanel.add(txtAuthor);

		JLabel books_press = new JLabel("������");
		books_press.setFont(new Font("������", Font.PLAIN, 15));
		books_press.setBounds(58, 131, 60, 27);
		jPanel.add(books_press);

		txtPress = new JTextField();
		txtPress.setColumns(10);
		txtPress.setBounds(101, 131, 129, 27);
		jPanel.add(txtPress);

		JLabel lblISBN = new JLabel("���");
		lblISBN.setFont(new Font("������", Font.PLAIN, 15));
		lblISBN.setBounds(58, 181, 45, 27);
		jPanel.add(lblISBN);

		txtNumber = new JTextField();
		txtNumber.setColumns(10);
		txtNumber.setBounds(101, 181, 129, 27);
		jPanel.add(txtNumber);

		JLabel book_price = new JLabel("�۸�");
		book_price.setFont(new Font("������", Font.PLAIN, 15));
		book_price.setBounds(58, 231, 45, 27);
		jPanel.add(book_price);

		txtBooksPrice = new JTextField();
		txtBooksPrice.setColumns(10);
		txtBooksPrice.setBounds(101, 231, 129, 27);
		jPanel.add(txtBooksPrice);

		JLabel leibie=new JLabel("���");
		leibie.setFont(new Font("������", Font.PLAIN, 15));
		leibie.setBounds(58,281,45,27);
		jPanel.add(leibie);

		JLabel lblBooks_remark = new JLabel("����");
		lblBooks_remark.setFont(new Font("������", Font.PLAIN, 15));
		lblBooks_remark.setBounds(58, 331, 45, 27);
		jPanel.add(lblBooks_remark);

		txtRemark = new JTextField();
		txtRemark.setColumns(10);
		txtRemark.setBounds(101, 331, 365, 27);
		jPanel.add(txtRemark);


		JButton bt1 = new JButton("���");
		bt1.setFont(new Font("������", Font.PLAIN, 15));
		bt1.setBounds(78,456,77,27);
		jPanel.add(bt1);
		//��ť��ӵļ�����
		bt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String bookName = txtBooksName.getText();
				String bookAuthor = txtAuthor.getText();
				String bookPress = txtPress.getText();
				String bookNumber = txtNumber.getText();
				String bookPrice=txtBooksPrice.getText();
				String bookRemark=txtRemark.getText();
				if(toolUtil.isEmpty(bookName)&& toolUtil.isEmpty(bookAuthor)&&toolUtil.isEmpty(bookPress)&&
						toolUtil.isEmpty(bookNumber)&&toolUtil.isEmpty(bookPrice)&&toolUtil.isEmpty(bookRemark))
				{
					JOptionPane.showMessageDialog(null,"�������������");
					return;
				}
				BookType bookType=(BookType)AdminBookAdd.this.comboBox.getSelectedItem();
				Integer id=bookType.getId();
				double price;
				Integer number;
				price= Double.parseDouble(bookPrice);
				number= Integer.parseInt(bookNumber);
				if(price<0||price>1000||number<0||number>1000){
					JOptionPane.showMessageDialog(null,"�鼮�۸���鼮��������0-1000֮�����");
					return;
				}
				if(number<0||number>10000){
					JOptionPane.showMessageDialog(null,"�鼮��������0-10000֮�����");
					return;
				}
				Book book=new Book();
				book.setBookName(bookName);
				book.setAuthor(bookAuthor);
//				book.setType(String.valueOf(id));
				book.setNumber(number);
				book.setPrice(price);
				book.setPublish(bookPress);
				book.setRemark(bookRemark);
				book.setStatus(1);

				try {
					Integer x=bookDao.add(book);
					if(x==1){
						JOptionPane.showMessageDialog(null,"��ӳɹ�");
					}
					else {
						JOptionPane.showMessageDialog(null,"���ʧ��");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null,"����쳣");
				}
			}
		});
		JButton bt2=new JButton("����");
		bt2.setFont(new Font("������", Font.PLAIN, 15));
		bt2.setBounds(328,456,77,27);
		jPanel.add(bt2);
		bt2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtBooksName.setText(" ");
				txtAuthor.setText(" ");
				txtPress.setText(" ");
				txtBooksPrice.setText(" ");
				txtRemark.setText(" ");
				txtNumber.setText(" ");
			}
		});
		comboBox = new JComboBox();
		comboBox.setBounds(101, 281, 128, 26);
		jPanel.add(comboBox);


		try {
			ResultSet list = bookDao.list( new BookType());
			while (list.next()) {
				BookType bookType = new BookType();
				bookType.setId(list.getInt("id"));
				bookType.setType(list.getString("type_name"));
				comboBox.addItem(bookType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new AdminBookAdd();
	}
}
