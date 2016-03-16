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
		Socket socket = null;
		ServerSocket serverSocket = null;
		// 서버 소켓 생성
		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();
			// 2.binding
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhostAddress = inetAddress.getHostAddress();
			serverSocket.bind(new InetSocketAddress(localhostAddress, PORT));
			System.out.println("[server] binding. " + localhostAddress + ":"
					+ PORT);

			// 3.accept 연결 요청 기다림
			socket = serverSocket.accept();// blocking

			// 4.연결 성공
			InetSocketAddress remoteAddress = (InetSocketAddress) socket
					.getRemoteSocketAddress();
//			String remoteHostAddress = remoteAddress.getAddress()
//					.getHostAddress();
			String remoteHostAddress = remoteAddress.getHostName();
			int remoteHostPort = remoteAddress.getPort();

			System.out.println("[server] 연결됨 from" + remoteAddress + ":"
					+ remoteHostPort);

			// 5.IOStream 받아오기
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			// 6.데이터 읽기
			while (true) {
				byte[] buffer = new byte[256];// blocking
				int readByteCount = inputStream.read(buffer);
				if (readByteCount <= -1) {
					System.out.println("[server] closed by client");
					break;
				}

				String data = new String(buffer, 0, readByteCount, "utf-8");
				System.out.println("[server] received : " + data);

				// 7.쓰기
				outputStream.write(data.getBytes("UTF-8"));
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (SocketException e) {
			System.out.println("[server] 비정상적으로 클라이언트가 종료되었습니다.");
			System.out.println("");
		} finally {
			// 8.자원정리
			try {
				socket.close();
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (serverSocket != null
							&& serverSocket.isClosed() == false) {
						serverSocket.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}
}
