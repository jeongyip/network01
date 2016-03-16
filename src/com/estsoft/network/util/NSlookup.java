package com.estsoft.network.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSlookup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String domain;
		
		while(true){			
			try {
				System.out.print("> ");
				domain = sc.nextLine();
				
				if(domain.equals("quit")) break;
				InetAddress[] inetAddresses = InetAddress.getAllByName(domain);
				
				for(int i = 0; i<inetAddresses.length; i++){
					System.out.println(domain + " : "+inetAddresses[i].getHostAddress());
				}
								
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				System.out.println("알수없는 도메인 입니다.");
			}
		}
		



	}

}
