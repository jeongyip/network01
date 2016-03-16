package com.estsoft.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

//나의 아이피 출력하기 
public class LocalHost {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			//IP주소를 표현하는 클래스
			String hostAddress = inetAddress.getHostAddress();
			String hostname = inetAddress.getHostName();
			
			System.out.println(hostAddress); // 
			System.out.println(hostname);
			
			//ip주소 바이트 배열
			byte[] addresses = inetAddress.getAddress();//ip가 쪼개서 들어옴(바이트 배열로)
			for(int i =0; i< addresses.length; i++){
				int address = addresses[i]& 0x000000ff; //음수 -> 양수
				System.out.print(address);
				if(i<addresses.length-1){
					System.out.print(".");
				}
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
