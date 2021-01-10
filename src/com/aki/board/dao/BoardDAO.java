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
	
	//오라클 서버에 접속할 정보 정의(공통 정보라 상속시키면 편함) -->전역변수로 사용
	String driver = "oracle.jdbc.driver.OracleDriver"; //ojdbc안에 존재함
	String url="jdbc:oracle:thin:@localhost:1521:orcl";
	String uid = "mytest";
	String upw = "mytest";
	
	//게시판 리스트
	public List<BoardDTO> list(int begin, int end, String search) {
		
		//리턴 객체
		List<BoardDTO> list = null; //DB에서 가져온 데이터를 넣는 객체
		
		//DB연동에서 사용할 객체 선언
		Connection con = null;//연결 객체
		PreparedStatement pstmt = null;//실행문 객체
		ResultSet rs = null; //DB에서 데이터를 가져오면 저장하는 객체(테이블 형식)
		try {
			//1. 드라이버 확인
			Class.forName(driver); //static 형식으로 되어있기 때문에(다양한 static 중, 필요한 static만 올려서 사용하도록)
			//2. 연결
			con =  DriverManager.getConnection(url,uid,upw);
			//3. 처리할 오라클 문장 생성
			if(search==null || search=="") {
				String sql = 
					"select *  from "
					+ "(select rownum rn,no ,title, writer, use, to_char(writedate,'yyyy-mm-dd') writedate, hit  from"
					+ "(select * from board "+" order by no asc)) where rn between ? and ?";
					//4. 실행 객체
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, begin);
				pstmt.setInt(2, end);
			}else {
				String sql2 = "select * from board where title LIKE '%"+search.trim()+"%'";
				pstmt = con.prepareStatement(sql2);
			}
			//5. 실행 -> rs, select문이라서 executeQuery()를 사용 --> 결과가 resultSet
			// insert, update = executeUpdate()를 사용 --> 결과가 int(0/1)
			rs = pstmt.executeQuery();
			//6. 표시
			if(rs !=null) {
				//list가 null이면(미생성) 생성하기 위한 if문(맨  처음만 생성)
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
				//7. 닫기
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("BoardDAO.list:"+list);
		return list;
	}//list()의 끝
	
	//=======================게시판 글보기==============================
	public BoardDTO view(int no){
		
		//리턴 객체
		BoardDTO dto = null; //DB에서 가져온 데이터를 넣는 객체
		
		//DB연동에서 사용할 객체 선언
		Connection con = null;//연결 객체
		PreparedStatement pstmt = null;//실행문 객체
		ResultSet rs = null; //DB에서 데이터를 가져오면 저장하는 객체(테이블 형식)
		
		try {
			//1. 드라이버 확인
			Class.forName(driver); //static 형식으로 되어있기 때문에(다양한 static 중, 필요한 static만 올려서 사용하도록)
			//2. 연결
			con =  DriverManager.getConnection(url,uid,upw);
			//3. 처리할 오라클 문장 생성
			String sql = "select no ,title,content,writer, use, "
					+"to_char(writedate,'yyyy-mm-dd') writedate, hit"
					+ " from board where no=?";
			//4. 실행 객체 & 데이터 셋팅
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			//5. 실행 -> rs, select문이라서 executeQuery()를 사용 --> 결과가 resultSet
			// insert, update = executeUpdate()를 사용 --> 결과가 int(0/1)
			rs = pstmt.executeQuery();
			//6. 표시
			if(rs !=null && rs.next()) {
				//list가 null이면(미생성) 생성하기 위한 if문(맨  처음만 생성)
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
				//7. 닫기
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("BoardDAO.dto:"+dto);
		return dto;
	}//view()의 끝
	
	//==============게시판 글보기 시 조회수 1증가=====================
	public void increase(int no) {
		
		//오라클 서버 접속 정보는 전역변수로 정의
		
		//DB연동에서 사용할 객체 선언
		Connection con = null;//연결 객체
		PreparedStatement pstmt = null;//실행문 객체
		
		try {
			//1. 드라이버 확인
			Class.forName(driver); //static 형식으로 되어있기 때문에(다양한 static 중, 필요한 static만 올려서 사용하도록)
			//2. 연결
			con =  DriverManager.getConnection(url,uid,upw);
			//3. 처리할 오라클 문장 생성
			String sql = "update board set hit = hit+1 where no=?";
			//4. 실행 객체 & 데이터 셋팅
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			//5. 실행 -> rs, select문이라서 executeQuery()를 사용 --> 결과가 resultSet
			// insert, update = executeUpdate()를 사용 --> 결과가 int(0/1)
			pstmt.executeUpdate();
			//6. 표시
			System.out.println("게시판 글 조회수 1증가");
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//7. 닫기
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}//increase()의 끝
	
	//==============게시판 글쓰기=====================
	public void write(BoardDTO dto) {
		
		//오라클 서버 접속 정보는 전역변수로 정의
		
		//DB연동에서 사용할 객체 선언
		Connection con = null;//연결 객체
		PreparedStatement pstmt = null;//실행문 객체
		
		try {
			//1. 드라이버 확인
			Class.forName(driver); //static 형식으로 되어있기 때문에(다양한 static 중, 필요한 static만 올려서 사용하도록)
			//2. 연결
			con =  DriverManager.getConnection(url,uid,upw);
			//3. 처리할 오라클 문장 생성
			String sql = "insert into board(no,title,content,writer,use) "
					+ " values(board_seq.nextval, ?, ?, ?, ?)";
			//4. 실행 객체 & 데이터 셋팅
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getWriter());
			pstmt.setNString(4, dto.getUse());
			//5. 실행 -> rs, select문이라서 executeQuery()를 사용 --> 결과가 resultSet
			// insert, update = executeUpdate()를 사용 --> 결과가 int(0/1)
			pstmt.executeUpdate();
			//6. 표시
			System.out.println("게시판 등록 완료");
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//7. 닫기
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}//write()의 끝
	
	public void delete(int no) {
		
		//오라클 서버 접속 정보는 전역변수로 정의
		
		//DB연동에서 사용할 객체 선언
		Connection con = null;//연결 객체
		PreparedStatement pstmt = null;//실행문 객체
		
		try {
			//1. 드라이버 확인
			Class.forName(driver); //static 형식으로 되어있기 때문에(다양한 static 중, 필요한 static만 올려서 사용하도록)
			//2. 연결
			con =  DriverManager.getConnection(url,uid,upw);
			//3. 처리할 오라클 문장 생성
			String sql = "Delete From board where no=?";
			//4. 실행 객체 & 데이터 셋팅
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			//5. 실행 -> rs, select문이라서 executeQuery()를 사용 --> 결과가 resultSet
			// insert, update = executeUpdate()를 사용 --> 결과가 int(0/1)
			pstmt.executeUpdate();
			//6. 표시
			System.out.println("게시글 삭제 완료");
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//7. 닫기
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}//delete()의 끝
	
	public void update(BoardDTO dto, int no) {
		
		//DB연동에서 사용할 객체 선언
		Connection con = null;//연결 객체
		PreparedStatement pstmt = null;//실행문 객체
		
		try {
			//1. 드라이버 확인
			Class.forName(driver); //static 형식으로 되어있기 때문에(다양한 static 중, 필요한 static만 올려서 사용하도록)
			//2. 연결
			con =  DriverManager.getConnection(url,uid,upw);
			//3. 처리할 오라클 문장 생성		
			//String sql = "update board set title=+"+dto.getTitle().trim()+", content="+dto.getContent().trim()+ " where no=?";
			String sql = "update board set title = ?, use = ?, content = ? where no= ?";
			//4. 실행 객체 & 데이터 셋팅
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getUse());
			pstmt.setString(3, dto.getContent());
			pstmt.setLong(4, no);
			int ret = pstmt.executeUpdate();
			//6. 표시
			System.out.println("변경 사항: "+ret+" / 게시글 수정 완료");
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//7. 닫기
				if(con != null) con.close();
				if(pstmt != null) pstmt.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}//update()의 끝
	
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
                System.out.println("총게시물수 : "+cnt);
            }//if          
        } catch (Exception e) {
            e.printStackTrace();
        }finally {         
        	try {
				//7. 닫기
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