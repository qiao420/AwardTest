package com.dao;

import com.entity.BorrowReturn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowReturnDao {
    /**
     * ������
     * ���飺���Ҹ��û��Ƿ����ñ���
     */
    public static ResultSet find(Connection conn, BorrowReturn br) throws SQLException {
        String sql = "select * from borrowdetail where user_id = ? and book_id = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, br.getUserId());
        pstm.setInt(2, br.getBookId());
        ResultSet rs = pstm.executeQuery();
        return rs;
    }

    /**
     * ������
     * ���飺�����������ȫ���鼮���������
     * @param conn
     * @param br
     * @return
     * @throws SQLException
     */
    public static ResultSet list(Connection conn, BorrowReturn br) throws SQLException {
        //�����ѯbook_name
        String sql = "select bd.*,b.book_name from borrowdetail bd,book b where user_id=? and bd.book_id = b.id";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, br.getUserId());

        ResultSet rs = pstm.executeQuery();
        return rs;
    }


    /**
     * ������
     * ���飺���������������
     * @param conn
     * @param br
     * @return
     * @throws Exception
     */
    public int add(Connection conn, BorrowReturn br) throws Exception {
        String sql = "insert into borrowdetail (user_id,book_id,status,borrow_time) values (?,?,?,?)";

        PreparedStatement pstmt= conn.prepareStatement(sql);
        pstmt.setInt(1, br.getUserId());
        pstmt.setInt(2, br.getBookId());
        pstmt.setInt(3, br.getStatus());
        pstmt.setLong(4, br.getBorrowTime());
        int rs = pstmt.executeUpdate();
        return rs;
    }

    /**
     * ������
     * ���飺�޸�ͼ���״̬�ͻ���ʱ��
     * @param conn
     * @param br
     * @return
     * @throws SQLException
     */
    public int update(Connection conn, BorrowReturn br) throws SQLException {
        String sql = "update borrowdetail set status = ? ,return_time = ? where id = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,br.getStatus());
        pstm.setLong(2,br.getReturnTime());
        pstm.setInt(3,br.getBorrowId());
        int rs = pstm.executeUpdate();
        return rs;
    }

    /**
     * �����m
     * @param con
     * @param bor
     * @return
     * @throws Exception
     */
    public ResultSet read(Connection con,BorrowReturn bor)throws Exception {
        //׷�����ݵ���ǰStringBuffer�����ĩβ
        StringBuffer sb = new StringBuffer("select bd.*,u.username,b.book_name from borrowdetail bd,user u,book b where u.id=bd.user_id and b.id=bd.book_id");
        if (bor.getUserId() != null) {
            sb.append(" and u.id = ?");         //�û�id
        }
        if (bor.getStatus() != null) {
            sb.append(" and bd.status = ?");        //����״̬
        }
        if (bor.getBookId() != null) {
            sb.append(" and bd.book_id = ?");       //ͼ��id
        }
        sb.append("  order by bd.id");              //���ı����������
        PreparedStatement pstmt = con.prepareStatement(sb.toString());
        if (bor.getUserId() != null) {
            pstmt.setInt(1, bor.getUserId());
        }
        if (bor.getStatus() != null && bor.getBookId() != null) {
            pstmt.setInt(2, bor.getStatus());
            pstmt.setInt(3, bor.getBookId());
        }
        return pstmt.executeQuery();
    }
}
