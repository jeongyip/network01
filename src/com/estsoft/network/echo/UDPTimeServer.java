package com.estsoft.network.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
	private static final int PORT = 6060;
	private static final int BUFFER_SIZE = 1024;
	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket( PORT );
			
			while( true ) {
				DatagramPacket receivePacket = new DatagramPacket( new byte[ BUFFER_SIZE ], BUFFER_SIZE );
				socket.receive( receivePacket );

				// 송신
				String date = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" ).format( new Date() );
				byte[] sendData = date.getBytes( "UTF-8" );
				DatagramPacket sendPacket = new DatagramPacket(
						sendData, 
						sendData.length,
						receivePacket.getSocketAddress()
						);
				socket.send( sendPacket );
			}
		} catch( IOException ex ) {
			ex.printStackTrace();
		} finally {
			if( socket != null && socket.isClosed() == false ) {
				socket.close();
			}
		}
	}
}