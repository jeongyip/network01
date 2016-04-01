package com.estsoft.network.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {
	private final static int PORT = 5050;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 2. binding
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhostAddress = inetAddress.getHostAddress();
			serverSocket.bind( new InetSocketAddress( localhostAddress, PORT ) );
			System.out.println( "[server] binding " + localhostAddress + ":" + PORT );
			
			//3. accpet 연결 요청 기다림
			Socket socket = serverSocket.accept(); //blocking
			
			//4. 연결 성공
			InetSocketAddress remoteAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			//String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
			String remoteHostAddress = remoteAddress.getHostName();
			int remoteHostPort = remoteAddress.getPort();
			System.out.println( "[server] 연결됨  from " + remoteHostAddress + ":" + remoteHostPort );
			
			//5. IOStream 받아 오기
			try {
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				while( true ) {
					//6. 데이터 읽기
					byte[] buffer = new byte[256];
					int readByteCount = inputStream.read( buffer ); //blocking
					if( readByteCount <= -1 ) {
						//정상종료
						System.out.println( "[server] closed by client");
						break;
					}
					String data = new String( buffer, 0, readByteCount, "utf-8" );
					System.out.println( "[server] received : " + data );
					
					//7. 쓰기
					outputStream.write( data.getBytes( "UTF-8" ) );
				}
			} catch( SocketException ex ) {
				System.out.println( "[server] 비정상적으로 클라이언트가 종료 되었습니다." );
			} catch( IOException ex ) {
				ex.printStackTrace();
			} finally {
				//8. 자원정리
				if( socket != null && socket.isClosed() == false ) {
					socket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 자원정리
			try {
				if( serverSocket != null && serverSocket.isClosed() == false ) {
					serverSocket.close();
				}
			} catch( IOException ex ) {
				ex.printStackTrace();
			}
		}
	}
}