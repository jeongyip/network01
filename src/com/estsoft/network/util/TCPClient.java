package com.estsoft.network.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class TCPClient {

	private static final String SERVER_IP = "192.168.1.15";
	private static final int SERVER_PORT = 5050;
	
	public static void main(String[] args) {
		Socket socket = null;
		
		try{
			//1. 소켓 생성
			socket = new Socket();
			
			//1-1 소켓 버퍼 사이즈 확인
			int rcvBufferSize = socket.getReceiveBufferSize();
			int sndBufferSize = socket.getSendBufferSize();
			System.out.println(rcvBufferSize+"."+sndBufferSize);
			
			socket.setReceiveBufferSize(1024*1024*10);
			socket.setSendBufferSize(1024*1024*10);
			
			
			//2. 서버 연결
			socket.connect( new InetSocketAddress(SERVER_IP,SERVER_PORT) );
			
			//3.IOStream 받아오기
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			//4.읽기/쓰기
			String data = "Hello World";
			outputStream.write(data.getBytes("UTF-8")); //-> 스트링을 바이트로 바꿔주기
			
			byte[] buffer = new byte[256];
			int readByteCount = inputStream.read(buffer);
			if(readByteCount<-1){
				//socket 연결이 끊어짐
				System.out.println("[client] closed by server");
				return ;
			}
			
			data = new String(buffer,0,readByteCount,"UTF-8");
			System.out.println("[client] received :"+data);
			
//			socket.close();
		}catch(SocketException e){
			System.out.println("[client] 비정상적으로 클라이언트가 종료 되었습니다.");
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(socket != null && socket.isClosed() == false){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
