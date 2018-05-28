package com.kh.websocket.unicast;

import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;


@ServerEndpoint("/unicast")
public class Unicast {
	// webSocket 전용 Session
	// 연결한 사용자 리스트 단, 한 사용자가 2번 연결하면 안되기
	// 때문에 Set을 통해 연결 정보에 대한 중복을 배제한다.
	private static Set<Session> clients
	= Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void onOpen(Session session){
		// OnOpen(어노테이션) : 
		// 톰캣에서 구동되었을때 동적 바인딩을 시켜준다.
		
		System.out.println(session);
		
		clients.add(session);
	}
	
	@OnMessage
	public void onMessage(String msg, Session session) throws IOException{
		System.out.println(msg);
		
		// 하나의 일 처리를 수행할 동안
		// 사용자 변경이 일어나면 안된다.
		// 즉, Null 값을 방지하기 위해서 
		// 동기화 처리를 수행 작성.
		synchronized(clients){
			
			for ( Session se : clients ){
				// 현재 연결된 사용자 모두에게 
				// 데이터를 전달한다.
				// 사용자와 일치하는 세션값만 if문으로 
				// 메세지를 지운다
				if ( !se.equals(session) )
				{
					// 실제 데이터를 전달하는 부분
					se.getBasicRemote().sendText(msg);
				}
				
			}
		}
	}
	
	@OnError
	public void onError(Throwable e){
		// 데이터 전달 과정에서 에러가 발생할 경우
		// 수행하는 메소드
		e.printStackTrace();
	}
	
	@OnClose
	public void onClose(Session session){
		clients.remove(session);
	}
	
}

