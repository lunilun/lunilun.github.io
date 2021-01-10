package com.aki.board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.aki.board.dto.BoardDTO;

public class BoardDAO {
	
	//����Ŭ ������ ������ ���� ����(���� ������ ��ӽ�Ű�� ����) -->���������� ���
	String driver = "oracle.jdbc.driver.OracleDriver"; //ojdbc�ȿ� ������
	String url="jdbc:oracle:thin:@localhost:1521:orcl";
	String uid = "mytest";
	String upw = "mytest";
	
	//�Խ��� ����Ʈ
	public List<BoardDTO> list(int begin, int end, String search) {
		
		//���� ��ü
		List<BoardDTO> list = null; //DB���� ������ �����͸� �ִ� ��ü
		
		//DB�������� ����� ��ü ����
		Connection con = null;//���� ��ü
		PreparedStatement pstmt = null;//���๮ ��ü
		ResultSet rs = null; //DB���� �����͸� �������� �����ϴ� ��ü(���̺� ����)
		try {
			//1. ����̹� Ȯ��
			Class.forName(driver); //static �������� �Ǿ��ֱ� ������(�پ��� static ��, �ʿ��� static�� �÷��� ����ϵ���)
			//2. ����
			con =  DriverManager.getConnection(url,uid,upw);
			//3. ó���� ����Ŭ ���� ����
			if(search==null || search=="") {
				String sql = 
					"select *  from "
					+ "(select rownum rn,no ,title, writer, use, to_char(writedate,'yyyy-mm-dd') writedate, hit  from"
					+ "(select * from board "+" order by no asc)) where rn between ? and ?";
					//4. ���� ��ü
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, begin);
				pstmt.setInt(2, end);
			}else {
				String sql2 = "select * from board where title LIKE '%"+search.trim()+"%'";
				pstmt = con.prepareStatement(sql2);
			}
			//5. ���� -> rs, select���̶� executeQuery()�� ��� --> ����� resultSet
			// insert, update = executeUpdate()�� ��� --> ����� int(0/1)
			rs = pstmt.executeQuery();
			//6. ǥ��
			if(rs !=null) {
				//list�� null�̸�(�̻���) �����ϱ� ���� if��(��  ó���� ����)
				while (rs.next()) {
					if(list == null) list = new ArrayList<>();
					BoardDTO dto = new BoardDTO();
					dto.setNo(rs.getLong("no"));
					dto.setTitle(rs.getString("title"));
					dto.setWriter(rs.getString("writer"));
					dto.setUse(rs.getNString("use"));
					dto.setWriteDate(rs.getString("writedate"));
					dto.setHit(rs.getInt("hit"));
					
					list.add(dto);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//7. �ݱ�
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("BoardDAO.list:"+list);
		return list;
	}//list()�� ��
	
	//=======================�Խ��� �ۺ���==============================
	public BoardDTO view(int no){
		
		//���� ��ü
		BoardDTO dto = null; //DB���� ������ �����͸� �ִ� ��ü
		
		//DB�������� ����� ��ü ����
		Connection con = null;//���� ��ü
		PreparedStatement pstmt = null;//���๮ ��ü
		ResultSet rs = null; //DB���� �����͸� �������� �����ϴ� ��ü(���̺� ����)
		
		try {
			//1. ����̹� Ȯ��
			Class.forName(driver); //static �������� �Ǿ��ֱ� ������(�پ��� static ��, �ʿ��� static�� �÷��� ����ϵ���)
			//2. ����
			con =  DriverManager.getConnection(url,uid,upw);
			//3. ó���� ����Ŭ ���� ����
			String sql = "select no ,title,content,writer, use, "
					+"to_char(writedate,'yyyy-mm-dd') writedate, hit"
					+ " from board where no=?";
			//4. ���� ��ü & ������ ����
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			//5. ���� -> rs, select���̶� executeQuery()�� ��� --> ����� resultSet
			// insert, update = executeUpdate()�� ��� --> ����� int(0/1)
			rs = pstmt.executeQuery();
			//6. ǥ��
			if(rs !=null && rs.next()) {
				//list�� null�̸�(�̻���) �����ϱ� ���� if��(��  ó���� ����)
					dto = new BoardDTO();
					dto.setNo(rs.getLong("no"));
					dto.setTitle(rs.getString("title"));
					dto.setContent(rs.getString("content"));
					dto.setWriter(rs.getString("writer"));
					dto.setUse(rs.getNString("use"));
					dto.setWriteDate(rs.getString("writedate"));
					dto.setHit(rs.getInt("hit"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//7. �ݱ�
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("BoardDAO.dto:"+dto);
		return dto;
	}//view()�� ��
	
	//==============�Խ��� �ۺ��� �� ��ȸ�� 1����=====================
	public void increase(int no) {
		
		//����Ŭ ���� ���� ������ ���������� ����
		
		//DB�������� ����� ��ü ����
		Connection con = null;//���� ��ü
		PreparedStatement pstmt = null;//���๮ ��ü
		
		try {
			//1. ����̹� Ȯ��
			Class.forName(driver); //static �������� �Ǿ��ֱ� ������(�پ��� static ��, �ʿ��� static�� �÷��� ����ϵ���)
			//2. ����
			con =  DriverManager.getConnection(url,uid,upw);
			//3. ó���� ����Ŭ ���� ����
			String sql = "update board set hit = hit+1 where no=?";
			//4. ���� ��ü & ������ ����
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			//5. ���� -> rs, select���̶� executeQuery()�� ��� --> ����� resultSet
			// insert, update = executeUpdate()�� ��� --> ����� int(0/1)
			pstmt.executeUpdate();
			//6. ǥ��
			System.out.println("�Խ��� �� ��ȸ�� 1����");
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//7. �ݱ�
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}//increase()�� ��
	
	//==============�Խ��� �۾���=====================
	public void write(BoardDTO dto) {
		
		//����Ŭ ���� ���� ������ ���������� ����
		
		//DB�������� ����� ��ü ����
		Connection con = null;//���� ��ü
		PreparedStatement pstmt = null;//���๮ ��ü
		
		try {
			//1. ����̹� Ȯ��
			Class.forName(driver); //static �������� �Ǿ��ֱ� ������(�پ��� static ��, �ʿ��� static�� �÷��� ����ϵ���)
			//2. ����
			con =  DriverManager.getConnection(url,uid,upw);
			//3. ó���� ����Ŭ ���� ����
			String sql = "insert into board(no,title,content,writer,use) "
					+ " values(board_seq.nextval, ?, ?, ?, ?)";
			//4. ���� ��ü & ������ ����
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getWriter());
			pstmt.setNString(4, dto.getUse());
			//5. ���� -> rs, select���̶� executeQuery()�� ��� --> ����� resultSet
			// insert, update = executeUpdate()�� ��� --> ����� int(0/1)
			pstmt.executeUpdate();
			//6. ǥ��
			System.out.println("�Խ��� ��� �Ϸ�");
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//7. �ݱ�
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}//write()�� ��
	
	public void delete(int no) {
		
		//����Ŭ ���� ���� ������ ���������� ����
		
		//DB�������� ����� ��ü ����
		Connection con = null;//���� ��ü
		PreparedStatement pstmt = null;//���๮ ��ü
		
		try {
			//1. ����̹� Ȯ��
			Class.forName(driver); //static �������� �Ǿ��ֱ� ������(�پ��� static ��, �ʿ��� static�� �÷��� ����ϵ���)
			//2. ����
			con =  DriverManager.getConnection(url,uid,upw);
			//3. ó���� ����Ŭ ���� ����
			String sql = "Delete From board where no=?";
			//4. ���� ��ü & ������ ����
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			//5. ���� -> rs, select���̶� executeQuery()�� ��� --> ����� resultSet
			// insert, update = executeUpdate()�� ��� --> ����� int(0/1)
			pstmt.executeUpdate();
			//6. ǥ��
			System.out.println("�Խñ� ���� �Ϸ�");
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//7. �ݱ�
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}//delete()�� ��
	
	public void update(BoardDTO dto, int no) {
		
		//DB�������� ����� ��ü ����
		Connection con = null;//���� ��ü
		PreparedStatement pstmt = null;//���๮ ��ü
		
		try {
			//1. ����̹� Ȯ��
			Class.forName(driver); //static �������� �Ǿ��ֱ� ������(�پ��� static ��, �ʿ��� static�� �÷��� ����ϵ���)
			//2. ����
			con =  DriverManager.getConnection(url,uid,upw);
			//3. ó���� ����Ŭ ���� ����		
			//String sql = "update board set title=+"+dto.getTitle().trim()+", content="+dto.getContent().trim()+ " where no=?";
			String sql = "update board set title = ?, use = ?, content = ? where no= ?";
			//4. ���� ��ü & ������ ����
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getUse());
			pstmt.setString(3, dto.getContent());
			pstmt.setLong(4, no);
			int ret = pstmt.executeUpdate();
			//6. ǥ��
			System.out.println("���� ����: "+ret+" / �Խñ� ���� �Ϸ�");
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//7. �ݱ�
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}//update()�� ��
	
	public int getTotal() {
        int cnt = 0;
       
        String sql = "Select count(*) cnt from board";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
       
        try {
            Class.forName(driver);
            con =  DriverManager.getConnection(url,uid,upw);
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                cnt = rs.getInt("cnt");
                System.out.println("�ѰԽù��� : "+cnt);
            }//if          
        } catch (Exception e) {
            e.printStackTrace();
        }finally {         
        	try {
				//7. �ݱ�
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
        }      
        return cnt; 
    }
	
}