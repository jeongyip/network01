package com.estsoft.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {
	
	private static final String SERVER_ADDRESS = "192.168.1.18"; //채팅방 연사람의 IP
	private static final int SERVER_PORT = 9090;  //PORT : 모뎀에 연결하는 통로 
	
	public static void main(String[] args) {
		Scanner scanner = null;
		Socket socket = null; //클라이언트의 소켓
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;
		
		try {
			
			// 1. 키보드 연결
			scanner = new Scanner( System.in );

			// 2. socket 생성 (클라이언트의 소켓)
			socket = new Socket();

			// 3. 연결 ( 서버에 연결하겠다 !!!!!!!!!!!!!!!)
			socket.connect( new InetSocketAddress( SERVER_ADDRESS, SERVER_PORT ) ); 

			// 4. reader/ writer 생성
			//InputStreamReader : 바이트 입력 스트림에 연결되어 문자 입력 스트림인 Reader로 변화시키는 보조스트림.
			//StandardCharsets : UTF-8로 인코딩 
			bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream(), StandardCharsets.UTF_8 ) );
			printWriter = new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), StandardCharsets.UTF_8 ), true );

			// 5. join 프로토콜
			System.out.print("닉네임>>");
			String nickname = scanner.nextLine();
			printWriter.println("join:" + nickname);
			printWriter.flush();
			bufferedReader.readLine();
			//System.out.println( data );
			
			// 6. ChatClientRecevieThread 시작
			// 대화의 시작
			Thread thread = new ChatClientReceiveThread(bufferedReader);
			thread.start(); //run 시작!

			// 7. 키보드 입력 처리
			while (true) {
				//System.out.print(">>");
				String input = scanner.nextLine();

				if ("quit".equals(input) == true) {
					// 8. quit 프로토콜 처리
					printWriter.println("quit");
					printWriter.flush();
					break;
				} else {
					// 9. 메시지 처리
					printWriter.println( "message:" + input );
					// socket.getOutputStream().write( ("message:" + input + "\r\n").getBytes( "UTF-8" )  );
					// socket.getOutputStream().flush();
					printWriter.flush();
				}
			}
		} catch (Exception ex) {
			log("error:" + ex);
		} finally {
			// 10. 자원정리
			try {
				if( scanner != null ) {
					scanner.close();
				}
				if( bufferedReader != null ) {
					bufferedReader.close();
				}
				if( printWriter != null ) {
					printWriter.close();
				}
				if( socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch( IOException ex ) {
				log( "error:" + ex );
			}
		}
	}

	public static void log(String log) {
		System.out.println("[chat-client] " + log);
	}
}

