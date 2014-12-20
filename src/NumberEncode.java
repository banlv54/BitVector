import java.io.PrintWriter;

public class NumberEncode {
	public int index;
	public int size;
	public int valueD=0;//Decimal
	public String valueB="";//Binary
	static int MAX_INDEX = 0;
	public NumberEncode(){
		index = MAX_INDEX;
		size = 0;
		MAX_INDEX += 1;
	}
	public NumberEncode(int valueD){
		this.valueD = valueD;
		this.valueB = Integer.toBinaryString(valueD);
		this.index = MAX_INDEX;
		this.size = valueB.length();
		MAX_INDEX += 1;
	}
	public NumberEncode(String valueB){
		this.valueB = valueB;
		this.valueD = Integer.parseInt(valueB, 2);
		this.index = MAX_INDEX;
		this.size = valueB.length();
		MAX_INDEX += 1;
	}
	public NumberEncode(int index, int size, int valueD){
		this.index = index;
		this.size = size;
		this.valueD = valueD;
		this.valueB = Integer.toBinaryString(valueD);
		if (index == MAX_INDEX){
			MAX_INDEX += 1;
		}
	}
	public NumberEncode(int index, int size){
		this.index = index;
		this.size = size;
		if (index == MAX_INDEX){
			MAX_INDEX += 1;
		}
	}
	public NumberEncode(int index, int size, String valueB){
		this.index = index;
		this.size = size;
		this.valueB = valueB;
		if (index == MAX_INDEX){
			MAX_INDEX += 1;
		}
	}
//	set function
	public void setSize(int size){
		this.size = size;
	}
	public void setValueB(String valueB){
		this.valueB = valueB;
		this.valueD = Integer.parseInt(valueB, 2);
	}
	public void setValuD(int valueD){
		this.valueD = valueD;
		this.valueB = Integer.toBinaryString(valueD);
	}
//	get function
	public String toBinary(){
		return valueB;
	}
	public int[] toArrayBinary(){
		String []arr = valueB.split("");
		int res[] = new int[size];
		for (int i=1; i<=arr.length; i++){
			res[i-1] = Integer.parseInt(arr[arr.length-i]);
		}
		for (int i=arr.length; i<size; i++){
			res[i] = 0;
		}
		return res;
	}
	
	public int valueD(){
		return valueD;
	}
	public int bit(int n){
		return size*index+n;
	}
	public void setMaxIndex(int max){
		MAX_INDEX = max;
	}
	public int getMaxIndex(){
		return MAX_INDEX;
	}
	public int getIndex(){
		return index;
	}

//	generate
	public void encodeSelf(PrintWriter fO){
		int []arr = toArrayBinary();
		for (int i=0; i< size; i++){
			if (arr[i] == 0){
				fO.println(-this.bit(1+i) + " 0");
			}else {
				fO.println(this.bit(1+i) + " 0");
			}
		}
	}

//	this = number1 + number2 with carry
	public void add(NumberEncode number1, NumberEncode number2, PrintWriter fO){
		NumberEncode carry = new NumberEncode(MAX_INDEX, size); 
		this.xor(number1, number2, carry, fO);
		this.at_least_two(number1, number2, carry, fO);
		fO.println((-carry.bit(1)) + " 0");
	}

//	sum = number1 + number2 with carry
//	return new number - sum
	public NumberEncode sum(NumberEncode number1, NumberEncode number2, PrintWriter fO){
		NumberEncode sum = new NumberEncode(MAX_INDEX, size); 
		sum.add(number1, number2, fO);
		return sum;
	}
	
//	sum = number1 + number2 with carry
	public void add(NumberEncode []arr, PrintWriter fO){
		NumberEncode tmp;// = new NumberEncode(MAX_INDEX, size);
		switch (arr.length) {
		case 0:
			break;
		case 1:
			this.equal(arr[0], fO);
			break;
		case 2:
			this.add(arr[0], arr[1], fO);
			break;
		default:
			tmp = this.sum(arr[0], arr[1], fO);
			for (int i=2; i<arr.length-1; i++){
				tmp = this.sum(tmp, arr[i], fO);
			}
			this.add(tmp, arr[arr.length-1], fO);
			break;
		}
		return;
	}
	
//	this = number
	public void equal(NumberEncode number, PrintWriter fO){
		for (int i=1; i<=size; i++){
			fO.println(this.bit(i) + " " + (-number.bit(i)) + " 0");
			fO.println(-this.bit(i) + " " + number.bit(i) + " 0");
		}
	}
//	this = number1 + number2 with carry
	private void at_least_two(NumberEncode number1, NumberEncode number2, NumberEncode carry, PrintWriter fO) {
		// TODO Auto-generated method stub
		String s = "";
	    for(int i=1; i<size; i++){
	        s = number1.bit(i) + " " + number2.bit(i) + " " + (-carry.bit(i+1)) + " 0";
	        fO.println(s);
	        s = (-number1.bit(i)) + " " + (-number2.bit(i)) + " " + (carry.bit(i+1)) + " 0";
	        fO.println(s);
	        s = number1.bit(i) + " " + carry.bit(i) + " " + (-carry.bit(i+1)) + " 0";
	        fO.println(s);
	        s = (-number1.bit(i)) + " " + (-carry.bit(i)) + " " + (carry.bit(i+1)) + " 0";
	        fO.println(s);
	        s = number2.bit(i) + " " + carry.bit(i) + " " + (-carry.bit(i+1)) + " 0";
	        fO.println(s);
	        s = (-number2.bit(i)) + " " + (-carry.bit(i)) + " " + (carry.bit(i+1)) + " 0";
	        fO.println(s);
	    }
	    fO.println((-number1.bit(size)) + " 0");
	    fO.println((-number2.bit(size)) + " 0");
	}
	
//	this = number1 XOR number2 XOR carry
	private void xor(NumberEncode number1, NumberEncode number2, NumberEncode carry, PrintWriter fO) {
		// TODO Auto-generated method stub
		String s = "";
		for(int i=1; i<=size; i++){
			s = this.bit(i) + " " + (-number1.bit(i)) + " " + (-number2.bit(i)) + " " + (-carry.bit(i)) + " 0";
			fO.println(s);
			s = (-this.bit(i)) + " " + number1.bit(i) + " " + number2.bit(i) + " " + carry.bit(i) + " 0";
			fO.println(s);
			s = (-this.bit(i)) + " " + number1.bit(i) + " " + (-number2.bit(i)) + " " + (-carry.bit(i)) + " 0";
			fO.println(s);
			s = this.bit(i) + " " + (-number1.bit(i)) + " " + number2.bit(i) + " " + carry.bit(i) + " 0";
			fO.println(s);
			s = (-this.bit(i)) + " " + (-number1.bit(i)) + " " + (-number2.bit(i)) + " " + carry.bit(i) + " 0";
			fO.println(s);
			s = this.bit(i) + " " + number1.bit(i) + " " + number2.bit(i) + " " + (-carry.bit(i)) + " 0";
			fO.println(s);
			s = this.bit(i) + " " + number1.bit(i) + " " + (-number2.bit(i)) + " " + carry.bit(i) + " 0";
			fO.println(s);
			s = (-this.bit(i)) + " " + (-number1.bit(i)) + " " + number2.bit(i) + " " + (-carry.bit(i)) + " 0";
			fO.println(s);
		}
	}
	
//	set value
}
