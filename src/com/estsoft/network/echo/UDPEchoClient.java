package com.estsoft.network.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

// 03.16(1) - UDP
// 메세지가 잘 도착했는지 안 했는지 알려주지 X -> 무책임

public class UDPEchoClient {
   private static final String SERVER_IP = "192.168.1.27";
   private static final int SERVER_PORT = 5000;
   private static final int BUFFER_SIZE =    1024;
   
   public static void main(String[] args) {
      DatagramSocket socket = null;
      
      try {
         // -------- 전송
         socket = new DatagramSocket();       // 우체통 만드는 과정
//         String message = "hello world";                // 편지 쓰는 과정
         String message = "";
         byte[] sendData = message.getBytes("UTF-8");

         DatagramPacket sendPacket = 
               new DatagramPacket(sendData, sendData.length,new InetSocketAddress(SERVER_IP, SERVER_PORT)); // 편지봉투에 넣는 과정
                              // 보낼 데이터, 데이터의 길이, 목적지 주소
         socket.send(sendPacket);
         
         // -------- 수신
         DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
         socket.receive(receivePacket);
         message = new String(receivePacket.getData(),0,receivePacket.getLength(), "UTF-8");
         System.out.println(message);
         
      } catch (IOException e) {
         e.printStackTrace();
      } finally{
         if(socket!= null && socket.isClosed()==false){
            socket.close();
         }
      }

   }
}

