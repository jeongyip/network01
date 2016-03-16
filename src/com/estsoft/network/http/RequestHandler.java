package com.estsoft.network.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;

public class RequestHandler extends Thread {
	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader br = null;
		OutputStream outputStream = null;

		try {
			// get IOStream
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = socket.getOutputStream();

			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			consolLog("connected from " + inetSocketAddress.getHostName() + ":" + inetSocketAddress.getPort());

			String line = null;
			String request = null;
			while (true) {

				line = br.readLine();

				if (line == null || "".equals(line)) {
					break;
				}
				if (request == null) {
					request = line;

				}

			}

			consolLog("request:" + request);

			String[] tokens = request.split(" ");
			if ("GET".equals(tokens[0])) {
				responseStaticResource(outputStream, tokens[1], tokens[2]);
				// resource:web에서는 정보들을 resource라한다.
				// static: 번하지않는, program이 아닌, server로 올려버리면 고정이되는!!
			} else {

			}

			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
			// 이 write가 browser가 받게될 정보 (HTTP PROTOCOL에 따라서 만듬)
			// outputStream.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));
			// outputStream.write("Content-Type:text/html;
			// charset=utf-8\r\n".getBytes("UTF-8"));
			// outputStream.write("\r\n".getBytes());
			// outputStream.write("<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할
			// 준비가 된 것입니다.</h1>".getBytes("UTF-8"));

		} catch (Exception ex) {
			SimpleHttpServer.consolLog("error:" + ex);
		} finally {
			// clean-up
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}

			} catch (IOException ex) {
				consolLog("error:" + ex);
			}
		}
	}

	private void responseStaticResource(OutputStream outputStream, String url, String protocol) throws IOException {
		// checked exception -> body를 try-catch하기 싫으니깐 throws를 통해서 method를 사용할 때
		// exception 쓰도록 함
		if ("/".equals(url)) {
			// welcome file
			url = "/index.html";
		}

		// webapp이 src폴더와 같이 있으면 돌아간다
		File file = new File("./webapp" + url);

		// data가 없으니깐 서버가 data없이 그냥 끊어버리는 경우다
		if (file.exists() == false) {
			response404Error(outputStream, protocol);
		}
		
		if (file.exists() == false) {
			return;
		}
		byte[] body = Files.readAllBytes(file.toPath());

		// 뭐지 이뻐짐
		String mimeType = Files.probeContentType(file.toPath());
		// 예제 응답입니다.
		// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
		// 이 write가 browser가 받게될 정보 (HTTP PROTOCOL에 따라서 만듬)
		// protocol: protocol에 맞도록+앞에 protocol 쓴다
		outputStream.write((protocol + "HTTP/1.1 200 OK\r\n").getBytes("UTF-8"));

		outputStream.write(("Content-Type:" + mimeType + "; charset=utf-8\r\n").getBytes("UTF-8"));
		// body가 시작됨을 알려줌 -> 한줄 빈 개행
		outputStream.write("\r\n".getBytes());

		// body 시작
		outputStream.write(body);
	}

	// msg만 들어오면 자동으로 찍히게 하려고 static으로 만들었음 -> 귀찮으니까
	public void consolLog(String message) {
		System.out.println("[HttpServer] " + message);
	}

	private void response404Error(OutputStream outputStream, String protocol) throws IOException {
		File file = new File("./webapp/error/404.html");
		byte[] body = Files.readAllBytes(file.toPath());

		//404 출력멘트
		outputStream.write((protocol + "404 File Not Found\r\n").getBytes("UTF-8"));

		//뭔가 아무것도 없으면 없어보이니깐 씀
		outputStream.write("Content-Type:text/html; charset=utf-8\r\n".getBytes("UTF-8"));
		// body가 시작됨을 알려줌 -> 한줄 빈 개행
		outputStream.write("\r\n".getBytes());

		// body 시작
		outputStream.write(body);
	}
}