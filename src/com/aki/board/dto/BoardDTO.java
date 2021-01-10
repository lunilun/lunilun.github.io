package com.aki.board.dto;

public class BoardDTO {
	
	private long no;
	private String title, content, writer, use, writeDate;
	private int hit;
	
	/*
	 * BoardDTO(){};
	 * 
	 * BoardDTO(long no,String title, String content, String writer, String
	 * writeDate, int hit){ this.content=content; this.title = title; this.writeDate
	 * = writeDate; this.writer = writer; this.no=no; this.hit = hit; }
	 */

	public long getNo() {
		return no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	
	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	//데이터 확인용
	@Override
	public String toString() {
		return "BoardDTO [no=" + no + ", title=" + title + ", content=" + content + ", writer=" + writer + ", use="
				+ use + ", writeDate=" + writeDate + ", hit=" + hit + "]";
	}

}
