package test;

import java.util.ArrayList;

public class SwtichTest {

	public static void main(String[] args) {
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(111);
		al.add(222);
		al.add(333);
		al.add(444);
		al.add(555);
		for(Integer priceKey:al){
    		switch(priceKey)
    		{
    			case 333:System.out.println(priceKey);break;
        		case 444:System.out.println(priceKey);break;
        		case 555:System.out.println(priceKey);break;
        		case 111:System.out.println(priceKey);break;
        		case 222:System.out.println(priceKey);break;
        		default:break;
    		}
    	}

	}

}
