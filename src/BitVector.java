import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class BitVector {
	
	public int max_index;
	
	public BitVector(){
		max_index = 0;
	}
	public static void main(String[] args) {
		BitVector b = new BitVector();
		NumberEncode arrayNumber[] = new NumberEncode[3];
		
		
		int []arr1 = {0,1};
		int [][]arr2 ={{1,1,1,0,0,0,0,0,0,0},
				       {2,1,0,1,0,0,0,0,0,0}};
		PrintWriter fO;
		try {
			fO = new PrintWriter(new BufferedWriter(new FileWriter("output.txt")));
			int n = 4;
			int max = b.add_vector(arr1, 2, 2*n+1, fO);
			b.add_number(arr2, 2*n+1, fO);
			System.out.println(max);
			fO.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
