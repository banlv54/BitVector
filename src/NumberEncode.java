import java.io.PrintWriter;


public class NumberEncode {
	public int index;
	public int size;
	public int valueD;//Decimal
	public String valueB;//Binary
	static int MAX_INDEX = 0;
	public NumberEncode(){
		index = 0;
		size = 0;
	}
	public NumberEncode(int valueD){
		this.valueD = valueD;
		this.valueB = Integer.toBinaryString(valueD);
		this.index = 0;
		this.size = valueB.length();
	}
	public NumberEncode(String valueB){
		this.valueB = valueB;
		this.valueD = Integer.parseInt(valueB, 2);
		this.index = 0;
		this.size = valueB.length();
	}
	public NumberEncode(int _index, int _size, int _valueD){
		index = _index;
		size = _size;
		valueD = _valueD;
		valueB = Integer.toBinaryString(_valueD);
	}
	
	public NumberEncode(int _index, int _size){
		index = _index;
		size = _size;
	}
	public NumberEncode(int _index, int _size, String _valueB){
		index = _index;
		size = _size;
		valueB = _valueB;
	}
	
	public void setSize(int _size){
		size = _size;
	}
	public String toBinary(){
		return valueB;
	}
	public int[] toArrayBinary(){
		String arr[] = valueB.split(" ");
		int res[] = new int[arr.length];
		for (int i=0; i< arr.length; i++){
			res[i] = Integer.parseInt(arr[i]);
		}
		return res;
	}
	public int toDecimal(){
		return valueD;
	}
	public int bit(int n){
		return size*index+n;
	}
//	this + number2 = number3 with carry
	public void add(NumberEncode number2, NumberEncode number3, PrintWriter fO){
		NumberEncode carry = new NumberEncode(MAX_INDEX, size); 
		this.xor(number2, number3, carry, fO);
		this.at_least_two(number2, carry, fO);
		fO.println((-carry.bit(1)) + " 0");
	}
//	this + number2 = number3 with carry
	private void at_least_two(NumberEncode number2, NumberEncode carry, PrintWriter fO) {
		// TODO Auto-generated method stub
		String s = "";
		for(int i=1; i<size; i++){
			s = this.bit(i) + " " + number2.bit(i) + " " + (-carry.bit(i+1)) + " 0";
			fO.println(s);
			s = (-this.bit(i)) + " " + (-number2.bit(i)) + " " + (carry.bit(i+1)) + " 0";
			fO.println(s);
			s = this.bit(i) + " " + carry.bit(i) + " " + (-carry.bit(i+1)) + " 0";
			fO.println(s);
			s = (-this.bit(i)) + " " + (-carry.bit(i)) + " " + (carry.bit(i+1)) + " 0";
			fO.println(s);
			s = number2.bit(i) + " " + carry.bit(i) + " " + (-carry.bit(i+1)) + " 0";
			fO.println(s);
			s = (-number2.bit(i)) + " " + (-carry.bit(i)) + " " + (carry.bit(i+1)) + " 0";
			fO.println(s);
		}
		fO.println((-this.bit(size)) + " 0");
		fO.println((-number2.bit(size)) + " 0");
	}
	
//	this + number2 = number3
	private void xor(NumberEncode number2, NumberEncode number3, NumberEncode carry, PrintWriter fO) {
		// TODO Auto-generated method stub
		String s = "";
		for(int i=1; i<=size; i++){
			s = this.bit(i) + " " + (-number2.bit(i)) + " " + (-number3.bit(i)) + " " + (-carry.bit(i)) + " 0";
			fO.println(s);
			s = (-this.bit(i)) + " " + number2.bit(i) + " " + number3.bit(i) + " " + carry.bit(i) + " 0";
			fO.println(s);
			s = (-this.bit(i)) + " " + number2.bit(i) + " " + (-number3.bit(i)) + " " + (-carry.bit(i)) + " 0";
			fO.println(s);
			s = this.bit(i) + " " + (-number2.bit(i)) + " " + number3.bit(i) + " " + carry.bit(i) + " 0";
			fO.println(s);
			s = (-this.bit(i)) + " " + (-number2.bit(i)) + " " + (-number3.bit(i)) + " " + carry.bit(i) + " 0";
			fO.println(s);
			s = this.bit(i) + " " + number2.bit(i) + " " + number3.bit(i) + " " + (-carry.bit(i)) + " 0";
			fO.println(s);
			s = this.bit(i) + " " + number2.bit(i) + " " + (-number3.bit(i)) + " " + carry.bit(i) + " 0";
			fO.println(s);
			s = (-this.bit(i)) + " " + (-number2.bit(i)) + " " + number3.bit(i) + " " + (-carry.bit(i)) + " 0";
			fO.println(s);
		}
	}
}
