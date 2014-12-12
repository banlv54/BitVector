import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class BitVector {
	
	public int max_index;
	
	public static void main(String[] args) {
		NumberEncode arrayNumber[] = new NumberEncode[3];
		NumberEncode arrayNumber1[] = new NumberEncode[2];
		BitVector bitVector = new BitVector();
		PrintWriter fO;
		int size = 9;
		try {
			fO = new PrintWriter(new BufferedWriter(new FileWriter("output.txt")));
//			NumberEncod - index, size,
//			NumberEncod - index, size, value
			
//			vd: 2a+b=5
//			a+b=4
			
//			arrayNumber = [a,a,b]
			arrayNumber[0] = new NumberEncode(0, size);
			arrayNumber[1] = new NumberEncode(1, size);
			arrayNumber[2] = new NumberEncode(1, size);
//			arrayNumber1 = [a,b]			
			arrayNumber1[0] = new NumberEncode(0, size);
			arrayNumber1[1] = new NumberEncode(1, size);

//			sum1=5
			NumberEncode sum1 = new NumberEncode(5);
			sum1.setSize(size);
//			sum2=4
			NumberEncode sum2 = new NumberEncode(4);
			sum2.setSize(size);
//			sum1 = 2a+b
			sum1.add(arrayNumber, fO);
//			sum2=a+b
			sum2.add(arrayNumber1, fO);

//			Encode cac bien da biet gia tri trong mang
			bitVector.encodeDecimal(arrayNumber, fO);
			bitVector.encodeDecimal(arrayNumber1, fO);
			
//			Encode 1 bien cu the
			sum1.encodeSelf(fO);
			sum2.encodeSelf(fO);

			fO.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
	private void encodeDecimal(NumberEncode []arr, PrintWriter fO){
		for (int i=0; i< arr.length; i++){
			if (arr[i].valueB != ""){
				arr[i].encodeSelf(fO);
			}
		}
	}
	
	
	
	
	
	
	private void add_number(int[][] a, int n, PrintWriter fO) {
		// TODO Auto-generated method stub
//		fO.println("add num");
		for(int i=0; i < a.length; i++){
			for (int j=1; j<a[i].length; j++){
				if (a[i][j] == 1){
					fO.println(a[i][0]*n + j + " 0");
				} else {
					fO.println(-a[i][0]*n - j + " 0");
				}
			}
		}
	}
	public int add_vector(int[] arr, int eq, int n, PrintWriter fO){
//		fO.println("add vector");
		int max = arr.length + 1;
		if (max == 3){
			add_two(arr[0], arr[1], eq, max, n, fO);
		}else{
			for (int index=0; index < arr.length; index++){
				if (index == 0){
					add_two(arr[0], arr[1], max+1, max, n, fO);
					max = max+1;
				} else if(index == arr.length - 1) {
					add_two(max, arr[index], eq, max+1, n, fO);
					max = max+1;
				} else {
					add_two(max, arr[index], max+2, max+1, n, fO);
					max = max+2;
				}
			}
		}
		return max;
	}
	private void add_two(int x, int y, int z, int c, int n, PrintWriter fO) {
		// TODO Auto-generated method stub
//		fO.println("add two");
		xor(x,y,z,c,n,fO);
		at_least_two(x,y,c,n,fO);
		other(c,n,fO);
	}
	private void other(int c, int n, PrintWriter fO) {
		// TODO Auto-generated method stub
//		C0 = 0
//		fO.println("add other");
		fO.println((-n*c-1) + " 0");
//		Cn+1 = Zn+1
//		fO.println((-n*c-n-1) + " " + (n*z+n+1) + " 0");
//		fO.println((n*c+n+1) + " " + (-n*z-n-1) + " 0");
	}
	private void at_least_two(int x, int y, int c, int n, PrintWriter fO) {
		// TODO Auto-generated method stub
//		fO.println("add least two");
		String s = "";
		for(int i=1; i<n; i++){
			s = (n*x+i) + " " + (n*y+i) + " " + (-n*c-i-1) + " 0";
			fO.println(s);
			s = (-n*x-i) + " " + (-n*y-i) + " " + (n*c+i+1) + " 0";
			fO.println(s);
			s = (n*x+i) + " " + (n*c+i) + " " + (-n*c-i-1) + " 0";
			fO.println(s);
			s = (-n*x-i) + " " + (-n*c-i) + " " + (n*c+i+1) + " 0";
			fO.println(s);
			s = (n*y+i) + " " + (n*c+i) + " " + (-n*c-i-1) + " 0";
			fO.println(s);
			s = (-n*y-i) + " " + (-n*c-i) + " " + (n*c+i+1) + " 0";
			fO.println(s);
		}
		fO.println((-n*x-n) + " 0");
		fO.println((-n*y-n) + " 0");
	}
	private void xor(int x, int y, int z, int c, int n, PrintWriter fO) {
		// TODO Auto-generated method stub
//		fO.println("add xor");
		String s = "";
		for(int i=1; i<=n; i++){
			s = (n*x+i) + " " + (-n*y-i) + " " + (-n*z-i) + " " + (-n*c-i) + " 0";
			fO.println(s);
			s = (-n*x-i) + " " + (n*y+i) + " " + (n*z+i) + " " + (n*c+i) + " 0";
			fO.println(s);
			s = (-n*x-i) + " " + (n*y+i) + " " + (-n*z-i) + " " + (-n*c-i) + " 0";
			fO.println(s);
			s = (n*x+i) + " " + (-n*y-i) + " " + (n*z+i) + " " + (n*c+i) + " 0";
			fO.println(s);
			s = (-n*x-i) + " " + (-n*y-i) + " " + (-n*z-i) + " " + (n*c+i) + " 0";
			fO.println(s);
			s = (n*x+i) + " " + (n*y+i) + " " + (n*z+i) + " " + (-n*c-i) + " 0";
			fO.println(s);
			s = (n*x+i) + " " + (n*y+i) + " " + (-n*z-i) + " " + (n*c+i) + " 0";
			fO.println(s);
			s = (-n*x-i) + " " + (-n*y-i) + " " + (n*z+i) + " " + (-n*c-i) + " 0";
			fO.println(s);
		}
	}
}
