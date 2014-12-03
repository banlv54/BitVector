import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


public class BitVector {
	public static void main(String[] args) {
		BitVector b = new BitVector();
		int []a = {1,2,3,4};
		b.add_vector(a, 4, 4);
    }
	public void add_vector(int[] arr, int n, int max_var){
		for (int index=0; index < arr.length; index++){
			if (index == 0){
				add_two(arr[0], arr[1], max_var+1, n);
			}else{
				add_two(max_var, arr[index], max_var+1, n);
			}
		}
	}
	private void add_two(int x, int y, int z, int n) {
		// TODO Auto-generated method stub
		try {
			PrintWriter fO= new PrintWriter(new BufferedWriter(new FileWriter("output.txt")));
			String s1="",s2="",s3="",s4 = "";
			for (int i=0; i<2*n; i++){
				s1 += String.valueOf(-i) + " ";
				s2 += String.valueOf(i) + " ";
				s3 += String.valueOf(-i) + " ";
				s4 += String.valueOf(i) + " ";
			}
			s1 += "0"; s2 += "0"; s3 += "0"; s4 += "0";
			System.out.println(s1);
			fO.println(s1);fO.println(s2);fO.println(s3);fO.println(s4);
			fO.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
