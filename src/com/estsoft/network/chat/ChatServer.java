package com.estsoft.network.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

	private static final int PORT = 9090;
	
	public static void main(String[] args) {
		//TCP로 데이터를 전송하기 위한 기본 소켓
		//서버에만 있는것, 하나 뿐이다!
		ServerSocket serverSocket = null; 
		//PrintWriter : writer 
		List<PrintWriter> listPrintWriters = new ArrayList<PrintWriter>();
		
		try {
			
			//1. 서버소켓 생성
			serverSocket = new ServerSocket();
			
			//2. binding
			InetAddress inetAddress = InetAddress.getLocalHost(); //현재 사용중인 지역 호스트에 대한 InetAddress 객체를 얻음
			String hostAddress = inetAddress.getHostAddress(); // IP주소의 점 문자열 형식을 가져옴
			
			serverSocket.bind( new InetSocketAddress( hostAddress, PORT ) );
			//[chat-server] bind 192.168.1.18:9090
			log( "bind " + hostAddress + ":" + PORT );
			
			//3. 연결 요청 기다림
			while( true ) {
				//서버는 반복해서 사용자를 받는다.
				//받은 객체들을 소켓으로 생성~
				Socket socket = serverSocket.accept();
				
				Thread thread = new ChatServerProcessThread( socket, listPrintWriters );
				thread.start();
			}
		} catch( IOException ex ) {
			log( "error:" + ex );
		} finally {
			if( serverSocket != null && serverSocket.isClosed() == false ) {
				try {
					serverSocket.close();
				} catch( IOException ex ) {
					log( "error:" + ex );
				}
			}
		}
	}
	
	public static void log( String log ) {
		System.out.println( "[chat-server] " + log );
	}
}

