package com.estsoft.network.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPEchoServer {

	private static final int PORT = 5050;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(PORT);
			
			DatagramPacket receivePacket = 
					new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
			socket.receive(receivePacket);
			
			String message = 
					new String(receivePacket.getData(),0,receivePacket.getLength(),"UTF-8");
			System.out.println(message);
			
			byte[] sendData = message.getBytes("UTF-8");
			DatagramPacket sendPacket = 
					new DatagramPacket(sendData,sendData.length,receivePacket.getSocketAddress());
			socket.send(sendPacket);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(socket != null && socket.isClosed() == false){
				socket.close();
			}
		}
		

	}

}
