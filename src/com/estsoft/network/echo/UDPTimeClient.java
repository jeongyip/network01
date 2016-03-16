package com.estsoft.network.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPTimeClient {
	private static final String SERVER_IP = "192.168.1.15";
	private static final int SERVER_PORT = 6060;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
			
			// 전송
			String message = "";
			byte[] sendData = message.getBytes( "UTF-8" );
			DatagramPacket sendPacket = 
					new DatagramPacket(  
							sendData, 
							sendData.length, 
							new InetSocketAddress( SERVER_IP, SERVER_PORT ) );
			socket.send(  sendPacket );
			
			// 수신
			DatagramPacket receivePacket = new DatagramPacket( new byte[ BUFFER_SIZE ], BUFFER_SIZE );
			socket.receive( receivePacket );
			message = new String( receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8" );
			System.out.println( message );
			
		} catch( IOException ex ) {
			ex.printStackTrace();
		} finally {
			if( socket != null && socket.isClosed() == false ) {
				socket.close();
			}
		}
	}

}

