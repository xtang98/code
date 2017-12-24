package algorithms.inheritance;
//java no virtual key word, every method is virtual, @Override is optional for derived class
public class MethodOverride {
	public static void main(String[] args) {
		Bike b1 = new Bike(10);
		Bike b2 = new MountainBike(12, 5);
		b1.print();
		b2.print();
		
		
		Snow var1 = new Sleet();
		
		Rain var2 = new Rain();
		Snow var3 = new Fog();
		Object var4 = new Snow();
		Sleet var5 = new Fog();
		Snow var6 = new Rain();
		
		
		
		//***var1.method1();			

		var2.method1();			
		System.out.println("****************");

		var1.method2();
		System.out.println("****************");

		var2.method2();			
		System.out.println("****************");


		var3.method2();			
		System.out.println("****************");

		//var4.method2();			

		var5.method2();			
		System.out.println("****************");

		var1.method3();		
		System.out.println("****************");


		var2.method3();			
		System.out.println("****************");

		var3.method3();			
		System.out.println("****************");

		//var4.method3();			

		var5.method3();			
		System.out.println("****************333");

		//xxxxxxxxxxxxruntimeerror!!! ((Rain)var4).method1();		

		((Fog)var5).method1();		

		//((Sleet)var3).method1();	

		((Sleet)var3).method3();	

	        //xxxxxxxxxxruntime error((Fog)var6).method3();		

		((Snow)var4).method2();		

		//*********************((Sleet)var4).method3();	

	        //((Rain)var6.method3();		

	}
}

class Bike {
	int speed;
	public Bike(int speed) {
		this.speed = speed;
	}
	public void print() {
		System.out.println("this is a "+this.getClass().getSimpleName());
	}
	

}

class MountainBike extends Bike{
	int seatHeight;
	public MountainBike(int speed, int seatHeight) {
		super(speed);
		this.seatHeight = seatHeight;
	}
	@Override
	public void print() {
		System.out.println("this is a "+this.getClass().getSimpleName());
	}
}

class Fog extends Sleet {
    public void method1() {
	System.out.println("Fog 1");
    }

    public void method3() {
	System.out.println("Fog 3");
    }
}

class Rain extends Snow {
    public void method1() {
	System.out.println("Rain 1");
    }

    public void method2() {
	System.out.println("Rain 2");
    }
}

class Sleet extends Snow {
    public void method2() {
	System.out.println("Sleet 2");
super.method2();
method3();
    }

    public void method3() {
	System.out.println("Sleet 3");
    }
}

class Snow {
    public void method2() {
	System.out.println("Snow 2");
    }

    public void method3() {
	System.out.println("Snow 3");
    }
}

//And assuming the following variables have been defined:

