// Code by - SATYARTH
public class modifiedDES {

	public static String dtb(int x) {
		return Integer.toBinaryString(x);
	}
	
	public static int btd(String x) {
		return Integer.parseInt(x,2);
	}
	
	public static String pBox(String x) {
		String temp="";
		for(int i = 0;i<8;i++) {
			temp += x.charAt(i+1);
			temp += x.charAt(i);
			i++;
		}
		return temp;
	}
	
	public static String rev(String x) {
		String temp="";
		for(int i=x.length()-1;i>=0;i--) temp+=x.charAt(i);
		return temp;
	}
	
	public static String xorOpr(String x,String y) {
		String temp="";
		for(int i=0;i<x.length();i++) {
			int k1=Integer.parseInt(x.charAt(i)+"");
			int k2=Integer.parseInt(y.charAt(i)+"");
			int k = k1 ^ k2;
			temp +=k;
		}
		return temp;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String pt="HI";
		String key = "101011010110";
		
		int pt1=pt.charAt(0);
		int pt2=pt.charAt(1);
		
		String bpt="0"+dtb(pt1)+"0"+dtb(pt2);
		System.out.println(bpt);
		
		// initial permutation
		String ip = rev(bpt);
		System.out.println(ip);
		
// S box  =================================================================================		
		String[][] sBox=new String[4][16];
		
		// first row of s-box
		sBox[0][0]="1111";  sBox[0][1]="1110";  sBox[0][2]="1101";  sBox[0][3]="1100";
		sBox[0][4]="1011";  sBox[0][5]="1010";  sBox[0][6]="1001";  sBox[0][7]="1000";
		sBox[0][8]="0111";  sBox[0][9]="0110";  sBox[0][10]="0101"; sBox[0][11]="0100";
		sBox[0][12]="0011"; sBox[0][13]="0010"; sBox[0][14]="0001"; sBox[0][15]="0000";
		
		// second row of s-box
		
		sBox[1][0]="1010";  sBox[1][1]="1011";  sBox[1][2]="1000";  sBox[1][3]="1001";
		sBox[1][4]="1110";  sBox[1][5]="1111";  sBox[1][6]="1100";  sBox[1][7]="1101";
		sBox[1][8]="0110";  sBox[1][9]="0011";  sBox[1][10]="0000"; sBox[1][11]="0001";
		sBox[1][12]="0110"; sBox[1][13]="0111"; sBox[1][14]="0100"; sBox[1][15]="0101";
		
		// Third row of s-box
		
		sBox[2][0]="0101";  sBox[2][1]="0100";  sBox[2][2]="0111";  sBox[2][3]="0110";
		sBox[2][4]="0001";  sBox[2][5]="0000";  sBox[2][6]="0011";  sBox[2][7]="0010";
		sBox[2][8]="1101";  sBox[2][9]="1100";  sBox[2][10]="1111"; sBox[2][11]="1110";
		sBox[2][12]="1001"; sBox[2][13]="1000"; sBox[2][14]="1011"; sBox[2][15]="1010";
		
		// fourth row of s-box
		
		sBox[3][0]="0000";  sBox[3][1]="0001";  sBox[3][2]="0010";  sBox[3][3]="0011";
		sBox[3][4]="0100";  sBox[3][5]="0101";  sBox[3][6]="0110";  sBox[3][7]="0111";
		sBox[3][8]="1000";  sBox[3][9]="1001";  sBox[3][10]="1010"; sBox[3][11]="1011";
		sBox[3][12]="1100"; sBox[3][13]="1101"; sBox[3][14]="1110"; sBox[3][15]="1111";
// ========================================================================================
		
		
		
		String LPT = ip.substring(0,8);  // LPT
		String RPT = ip.substring(8,16); // RPT
		System.out.println("LPT : "+LPT+" RPT : "+RPT);
		
		
		int round = 4;
		
		while(round!=0) {
			String lpt = LPT;
			String rpt = RPT;
			
			// expanded RPT
			String erpt = rpt.charAt(7)+rpt.substring(0,4)+
					rpt.charAt(4)+rpt.charAt(3)+rpt.substring(4,8)+rpt.charAt(0);
			
			String xorOpr= xorOpr(erpt,key);
		//	System.out.println("xor1 : "+xorOpr);
			
			// now we need to convert the 12 - bits into 8-bits using S-box
			String ls=xorOpr.substring(0,6);
			int lsr = btd(ls.charAt(0)+""+ls.charAt(5));
			int lsc = btd(ls.substring(1,5));
			String lpart=sBox[lsr][lsc];
			
			String rs=xorOpr.substring(6,12);
			int rsr = btd(rs.charAt(0)+""+rs.charAt(5));
			int rsc = btd(rs.substring(1,5));
			String rpart=sBox[rsr][rsc];
			
			String afterSbox = lpart+rpart;
			System.out.println("After s-box: "+afterSbox);
			
			// now we need to flip the bits using p-Box
			
			String afterPbox = pBox(afterSbox);
			System.out.println("After P-box : "+afterPbox);
			
	//  apply xor operation b/w afterPbox and lpt to get the rpt for next round
			
			RPT = xorOpr(afterPbox,lpt);  // RPT for next round
			LPT = rpt; // LPT for next round
			
			round--;
		
		}
		
		String tempAns= LPT+RPT;
		
		String finalAns= rev(tempAns);  // final answer after 4 rounds of DES
		
		System.out.println("Final Cipher Text : "+ finalAns);
		
		
		
		// now finding the digital signature:- 
		String digest = "00"+finalAns.substring(0,15); // right shift by 2
		String pKey = "000"+finalAns.substring(0,14); // right shift by 3
		
		String digitalSignature = xorOpr(digest,pKey);
		
		System.out.println("Digital Signature : "+digitalSignature);
		
		}

}
