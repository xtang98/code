package oodesign;

import java.lang.reflect.Field;
import java.util.Arrays;

public class GenericCopy {

    /**
     * Deep-copies the values from one object to the other
     *
     */
    public static void main(String[] args) {
    	A a1 = new A(1, 2, new B("string 1", 10));
    	A a2 = new A(3, 4, new B("string 2", 20));
    	System.out.println("a1 is :" + a1);
    	System.out.println("a2 is :" + a2);
    	copyFields(a1, a2);
    	System.out.println("After copying...");
    	System.out.println("a1 is :" + a1);
    	System.out.println("a2 is :" + a2);
    }

    public static <T> void copyFields(T from, T to) {
    	for (Field f : from.getClass().getFields()) {
    		try {
    			if (isPrimitivish(f.getType())) {
    				f.set(to, f.get(from));
    			} else {
    				copyFields(f.get(from), f.get(to));
    			}
    		} catch (IllegalArgumentException | IllegalAccessException e) {
    			e.printStackTrace();
    		}
    	}
    }
    

    
    public boolean doesObjectContainField(Object object, String fieldName) {
        return Arrays.stream(object.getClass().getFields())
                .anyMatch(f -> f.getName().equals(fieldName));
    }

    private static boolean isPrimitivish(Class c) {

	  return c.isPrimitive() || c == String.class || c == Boolean.class
	    || c == Byte.class || c == Short.class || c == Character.class
	    || c == Integer.class || c == Float.class || c == Double.class
	    || c == Long.class;
    }
}

class A {

   public int x;
   public int y;
   public B bObj;

    public A(int x, int y, B b) {


  this.x = x;

  this.y = y;

  this.bObj = b;

    }

    @Override
    public String toString() {

  return "[" + this.x + "," + this.y + "," + this.bObj.toString() + "]";
    }
}

class B {

    public String str;
    public int z;

    public B(String str, int z) {


  this.str = str;

  this.z = z;

    }

    @Override
    public String toString() {

  return "[" + this.str + "," + this.z + "]";
    }
}