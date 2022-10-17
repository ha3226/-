package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {

	public static Connection getCon() {

		Connection conn;

		try {
			Context initctx = new InitialContext();
			Context envctx = (Context) initctx.lookup("java:comp/env");
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool");
			conn = ds.getConnection();
			return conn;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void insertBoard(BoardBean bean) {

		Connection conn = getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int ref = 0;
		int re_step = 1;
		int re_level = 1;
		try {
			String refsql = "select max(ref) from board ";
			pstmt = conn.prepareStatement(refsql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ref = rs.getInt(1) + 1;
			}

			// String sql =" insert into board values( board_seq.NEXTVAL, ?, ? , ?, ? , )";

			String sql = " insert into BOARD (NUM, WRITER, EMAIL, SUBJECT, PASSWORD, REG_DATE, REF, ";
			sql += "	RE_STEP, RE_LEVEL, READCOUNT, CONTENT) ";
			sql += " values(board_seq.NEXTVAL, ? ,? , ?, ?, sysdate, ?, ?, ? , 0, ? ) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_step);
			pstmt.setInt(7, re_level);
			pstmt.setString(8, bean.getContent());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
	}

	private void closeAll(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getAllCount() {

		Connection conn = getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int count = 0;
		try {
			String sql = "select count(*) from board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return count;
	}

	public ArrayList<BoardBean> getAllBoard(int start, int end) {
		Connection conn = getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardBean> list = new ArrayList<>();

		try {
			String sql = "select *  from "
					+ " (select A.* , Rownum Rnum from (select * from board order by ref desc, re_level asc) A ) "
					+ " where Rnum >= ? and Rnum <= ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardBean bean = new BoardBean();
				bean.setNum(rs.getInt("num"));
				bean.setWriter(rs.getString("WRITER"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setSubject(rs.getString("SUBJECT"));
				bean.setPassword(rs.getString("PASSWORD"));
				bean.setReg_date(rs.getDate("REG_DATE").toString());
				bean.setRef(rs.getInt("ref"));
				bean.setRe_step(rs.getInt("RE_STEP"));
				bean.setRe_level(rs.getInt("RE_LEVEL"));
				bean.setReadcount(rs.getInt("READCOUNT"));
				bean.setContent(rs.getString("CONTENT"));

				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return list;
	}

	public BoardBean getOneBoard(int num) {
		Connection conn = getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardBean bean = new BoardBean();

		try {
			String readsql = "update board set readcount= readcount+1 where num=?";
			pstmt = conn.prepareStatement(readsql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();

			String sql = "select * from board where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				bean.setNum(rs.getInt("num"));
				bean.setWriter(rs.getString("WRITER"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setSubject(rs.getString("SUBJECT"));
				bean.setPassword(rs.getString("PASSWORD"));
				bean.setReg_date(rs.getDate("REG_DATE").toString());
				bean.setRef(rs.getInt("ref"));
				bean.setRe_step(rs.getInt("RE_STEP"));
				bean.setRe_level(rs.getInt("RE_LEVEL"));
				bean.setReadcount(rs.getInt("READCOUNT"));
				bean.setContent(rs.getString("CONTENT"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return bean;
	}

	public void reWriteBoard(BoardBean bean) {
		int ref = bean.getRef();
		int re_step = bean.getRe_step();
		int re_level = bean.getRe_level();

		// System.out.println("re_step :" +re_step + " : re_level :" +re_level);
		Connection conn = getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int maxLevel = 0;

		try {

			String levelsql = "update board set re_level =re_level+1 where ref=? and re_level > ?";
			pstmt = conn.prepareStatement(levelsql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, re_level);
			pstmt.executeUpdate();

			String sql = " insert into BOARD (NUM, WRITER, EMAIL, SUBJECT, PASSWORD, REG_DATE, REF, ";
			sql += "	RE_STEP, RE_LEVEL, READCOUNT, CONTENT) ";
			sql += " values(board_seq.NEXTVAL, ? ,? , ?, ?, sysdate, ?, ?, ? , 0, ? ) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_step + 1);
			pstmt.setInt(7, re_level + 1);
			pstmt.setString(8, bean.getContent());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}

	}

	public String getPass(int num) {

		Connection conn = getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String pass = "";
		System.out.println(num);

		try {
			String sql = "select password from board where num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				pass = rs.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return pass;
	}

	public void updateBoard(BoardBean bean) {
		Connection conn = getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "update board set subject=? , CONTENT=? where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getSubject());
			pstmt.setString(2, bean.getContent());
			pstmt.setInt(3, bean.getNum());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
	}

	public void deleteBoard(int num) {
		Connection conn = getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			String sql = "delete from board where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
	}

}
