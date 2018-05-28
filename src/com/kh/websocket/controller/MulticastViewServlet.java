package com.kh.websocket.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/multiView.do")
public class MulticastViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// 현재 열려 있는 채팅 방의 명칭
	public static ArrayList<String> roomList;
	
    public MulticastViewServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// 방의 list를 서버 내의 모두가 공유하기 위해 어플리케이션으로 지정
		ServletContext application = request.getServletContext();
		
		// 기존에 있던 방의 리스트를 받아온다.
		roomList = (ArrayList<String>)application.getAttribute("roomList");
		
		System.out.println(roomList+"여기널임?");
		
		if ( roomList == null || roomList.isEmpty() )
		{
			roomList = new ArrayList<String>();
			
			roomList.add("채팅방1");
			roomList.add("채팅방2");
			roomList.add("채팅방3");
			
			application.setAttribute("roomList", roomList);
			System.out.println(roomList);
		}
		
		if ( !roomList.isEmpty() )
		{
			response.sendRedirect("views/multicast.jsp");
		}
//		else
//		{
//			// 에러페이지
//			System.out.println("에러임다.");
//		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
