import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class BitVector {
	public static void main(String[] args) {
		BitVector b = new BitVector();
		int []arr1 = {0,0,0,0,1};
		int [][]arr2 ={{1,0,1,0,0,0},
				       {2,0,1,0,0,0}};
		PrintWriter fO;
		try {
			fO = new PrintWriter(new BufferedWriter(new FileWriter("output.txt")));
			int n = 2;
//			int max = b.add_vector(arr1, 2, 2*n+1, fO);
			b.add_number(arr2, 2*n+1, fO);
//			b.shift_x(0, 3, 2, fO);
			b.multiplication(0,1,2,2,n,fO);
//			System.out.println(max);
			fO.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
	private void multiplication(int a, int b, int c, int max, int n, PrintWriter fO) {
//		a*b=c
//		SHIFT_A ^ b0
		shift_x(a, n, max+1, fO);
		int []arr = new int[n];
		for(int i=1; i<n;i++){
			arr[i]=max+i;
		}
		arr[0]=a;
		int N=2*n+1;
		add_vector_mul(arr,b, c, N, fO);
		and_bit(a, b, n, max, fO);
	}
//	a ^ bit0 = b
//	-b v a | -b v bit0 | b -a -bit0 
	private void and_bit(int a, int bit, int n, int b, PrintWriter fO) {
		for (int i=1; i<=n;i++){
			fO.println(-b*n-i + " " + (a*n+i) + " 0");
			fO.println(-b*n-i + " " + bit + " 0");
			fO.println(b*n+i + " " + (a*n+i) + " " + bit + " 0");
		}
	}
	private void shift_x(int x, int n, int max, PrintWriter fO) {
		// TODO Auto-generated method stub
		int N = 2*n +1;
//		gan gia tri 0 cho bit cua x tu n+1 toi 2n+1
		for(int i=x*N+n+1; i<=(x+1)*N; i++){
			fO.println(-i + " 0");
		}
		
		for (int i=max; i<max+n; i++){
//			gan gia tri 0 cho cac bien shift i x
//			0000xxxx0000
			for (int j=N; j>n+i; j--){
				fO.println(-(x+i)*N-j + " 0");
			}
			for (int j=0; j<i;j++){
				fO.println(-(x+i)*N-j-1 + " 0");
			}
//			gan bien shift_x bang x voi cac bit
			for (int j=i+1; j<=n+i; j++){
//				Xi =SHIFT_Xi+n
				fO.println(-N*x-j+i + " " + ((x+i)*N+j) + " 0");
				fO.println(N*x+j-i + " " + (-(x+i)*N-j) + " 0");
			}
		}
	}
	private void add_number(int[][] a, int n, PrintWriter fO) {
		// TODO Auto-generated method stub
//		fO.println("add num");
		for(int i=0; i < a.length; i++){
			String s = "";
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
			for (int index=1; index < arr.length; index++){
				if (index == 1){
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
	
	public int add_vector_mul(int[] arr, int b, int eq, int n, PrintWriter fO){
//		fO.println("add vector");
		int max = arr.length + 1;
		if (max == 3){
			add_two_mul(arr[0], arr[1], eq, max, n, fO,b+2);
		}else{
			for (int index=1; index < arr.length; index++){
				if (index == 1){
					add_two_mul(arr[0], arr[1], max+1, max, n, fO, b+2);
					max = max+1;
				} else if(index == arr.length - 1) {
					add_two_mul(max, arr[index], eq, max+1, n, fO,b+index+1);
					max = max+1;
				} else {
					add_two_mul(max, arr[index], max+2, max+1, n, fO,b+index+1);
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
		other(c,z,n,fO);
	}
	
	private void add_two_mul(int x, int y, int z, int c, int n, PrintWriter fO, int bit) {
		// TODO Auto-generated method stub
//		fO.println("add two");
		xor_mul(x,y,z,c,n,fO, bit);
		at_least_two_mul(x,y,c,n,fO,bit);
		other(c,z,n,fO);
	}
	
	private void other(int c, int z, int n, PrintWriter fO) {
		// TODO Auto-generated method stub
//		C0 = 0
		fO.println(String.valueOf(-n*c-1) + " 0");
	}
	private void at_least_two(int x, int y, int c, int n, PrintWriter fO) {
		// TODO Auto-generated method stub
//		fO.println("add least two");
		String s = "";
		for(int i=1; i<n; i++){
			s = String.valueOf(n*x+i) + " " + String.valueOf(n*y+i) + " " + String.valueOf(-n*c-i-1) + " 0";
			fO.println(s);
			s = String.valueOf(-n*x-i) + " " + String.valueOf(-n*y-i) + " " + String.valueOf(n*c+i+1) + " 0";
			fO.println(s);
			s = String.valueOf(n*x+i) + " " + String.valueOf(n*c+i) + " " + String.valueOf(-n*c-i-1) + " 0";
			fO.println(s);
			s = String.valueOf(-n*x-i) + " " + String.valueOf(-n*c-i) + " " + String.valueOf(n*c+i+1) + " 0";
			fO.println(s);
			s = String.valueOf(n*y+i) + " " + String.valueOf(n*c+i) + " " + String.valueOf(-n*c-i-1) + " 0";
			fO.println(s);
			s = String.valueOf(-n*y-i) + " " + String.valueOf(-n*c-i) + " " + String.valueOf(n*c+i+1) + " 0";
			fO.println(s);
		}
		fO.println(String.valueOf(-n*x-n) + " 0");
		fO.println(String.valueOf(-n*y-n) + " 0");
	}
	private void xor(int x, int y, int z, int c, int n, PrintWriter fO) {
		// TODO Auto-generated method stub
//		fO.println("add xor");
		String s = "";
		for(int i=1; i<=n; i++){
			s = String.valueOf(n*x+i) + " " + String.valueOf(-n*y-i) + " " + String.valueOf(-n*z-i) + " " + String.valueOf(-n*c-i) + " 0";
			fO.println(s);
			s = String.valueOf(-n*x-i) + " " + String.valueOf(n*y+i) + " " + String.valueOf(n*z+i) + " " + String.valueOf(n*c+i) + " 0";
			fO.println(s);
			s = String.valueOf(-n*x-i) + " " + String.valueOf(n*y+i) + " " + String.valueOf(-n*z-i) + " " + String.valueOf(-n*c-i) + " 0";
			fO.println(s);
			s = String.valueOf(n*x+i) + " " + String.valueOf(-n*y-i) + " " + String.valueOf(n*z+i) + " " + String.valueOf(n*c+i) + " 0";
			fO.println(s);
			s = String.valueOf(-n*x-i) + " " + String.valueOf(-n*y-i) + " " + String.valueOf(-n*z-i) + " " + String.valueOf(n*c+i) + " 0";
			fO.println(s);
			s = String.valueOf(n*x+i) + " " + String.valueOf(n*y+i) + " " + String.valueOf(n*z+i) + " " + String.valueOf(-n*c-i) + " 0";
			fO.println(s);
			s = String.valueOf(n*x+i) + " " + String.valueOf(n*y+i) + " " + String.valueOf(-n*z-i) + " " + String.valueOf(n*c+i) + " 0";
			fO.println(s);
			s = String.valueOf(-n*x-i) + " " + String.valueOf(-n*y-i) + " " + String.valueOf(n*z+i) + " " + String.valueOf(-n*c-i) + " 0";
			fO.println(s);
		}
	}
	
	/// multiple

	private void at_least_two_mul(int x, int y, int c, int n, PrintWriter fO, int bit) {
		// TODO Auto-generated method stub
//		fO.println("add least two");
		String s = "";
		for(int i=1; i<n; i++){
			s = String.valueOf(n*x+i) + " " + String.valueOf(n*y+i) + " " + String.valueOf(-n*c-i-1) + " 0";
			fO.println(s);
			s = String.valueOf(-n*x-i) + " " + String.valueOf(-bit) + " " + String.valueOf(-n*y-i) + " " + String.valueOf(n*c+i+1) + " 0";
			fO.println(s);
			s = String.valueOf(n*x+i) + " " + String.valueOf(n*c+i) + " " + String.valueOf(-n*c-i-1) + " 0";
			fO.println(s);
			s = String.valueOf(-n*x-i) + " " + String.valueOf(-n*c-i) + " " + String.valueOf(n*c+i+1) + " 0";
			fO.println(s);
			s = String.valueOf(n*y+i) + " " + String.valueOf(n*c+i) + " " + String.valueOf(-n*c-i-1) + " 0";
			fO.println(s);
			s = String.valueOf(-bit) + " " + String.valueOf(-n*y-i) + " " + String.valueOf(-n*c-i) + " " + String.valueOf(n*c+i+1) + " 0";
			fO.println(s);
			
			s = String.valueOf(n*x+i) + " " + String.valueOf(bit) + " " + String.valueOf(-n*c-i-1) + " 0";
			fO.println(s);
			s = String.valueOf(bit) + " " + String.valueOf(n*c+i) + " " + String.valueOf(-n*c-i-1) + " 0";
			fO.println(s);
		}
		fO.println(String.valueOf(-n*x-n) + " 0");
		fO.println(String.valueOf(-n*y-n) + " 0");
	}
	private void xor_mul(int x, int y, int z, int c, int n, PrintWriter fO, int bit) {
		// TODO Auto-generated method stub
//		fO.println("add xor");
		String s = "";
		for(int i=1; i<=n; i++){
			s = String.valueOf(n*x+i) + " " + String.valueOf(-bit) + " " + String.valueOf(-n*y-i) + " " + String.valueOf(-n*z-i) + " " + String.valueOf(-n*c-i) + " 0";
			fO.println(s);
			s = String.valueOf(-n*x-i) + " " + String.valueOf(n*y+i) + " " + String.valueOf(n*z+i) + " " + String.valueOf(n*c+i) + " 0";
			fO.println(s);
			s = String.valueOf(-n*x-i) + " " + String.valueOf(bit) + " " + String.valueOf(n*z+i) + " " + String.valueOf(n*c+i) + " 0";
			fO.println(s);
			s = String.valueOf(-n*x-i) + " " + String.valueOf(n*y+i) + " " + String.valueOf(-n*z-i) + " " + String.valueOf(-n*c-i) + " 0";
			fO.println(s);
			s = String.valueOf(-n*x-i) + " " + String.valueOf(bit) + " " + String.valueOf(-n*z-i) + " " + String.valueOf(-n*c-i) + " 0";
			fO.println(s);
			s = String.valueOf(n*x+i) + " " + String.valueOf(-bit) + " " + String.valueOf(-n*y-i) + " " + String.valueOf(n*z+i) + " " + String.valueOf(n*c+i) + " 0";
			fO.println(s);
			s = String.valueOf(-n*x-i) + " " + String.valueOf(-bit) + " " +  String.valueOf(-n*y-i) + " " + String.valueOf(-n*z-i) + " " + String.valueOf(n*c+i) + " 0";
			fO.println(s);
			s = String.valueOf(n*x+i) + " " + String.valueOf(n*y+i) + " " + String.valueOf(n*z+i) + " " + String.valueOf(-n*c-i) + " 0";
			fO.println(s);
			s = String.valueOf(n*x+i) + " " + String.valueOf(bit) + " " + String.valueOf(n*z+i) + " " + String.valueOf(-n*c-i) + " 0";
			fO.println(s);
			s = String.valueOf(n*x+i) + " " + String.valueOf(n*y+i) + " " + String.valueOf(-n*z-i) + " " + String.valueOf(n*c+i) + " 0";
			fO.println(s);
			s = String.valueOf(n*x+i) + " " + String.valueOf(bit) + " " + String.valueOf(-n*z-i) + " " + String.valueOf(n*c+i) + " 0";
			fO.println(s);
			s = String.valueOf(-n*x-i) + " " + String.valueOf(-bit) + " " + String.valueOf(-n*y-i) + " " + String.valueOf(n*z+i) + " " + String.valueOf(-n*c-i) + " 0";
			fO.println(s);
		}
	}
}
