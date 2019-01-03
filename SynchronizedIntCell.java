package ss.week7.ProducerConsumer;

/**
 * Incorrect communication between IntProducer en IntConsumer.
 */
/**
And a hint as to what you might want to wait for:
As said before, each value must be consumed only once by one consumer.
A producer has to write a specific value. This must then be read by one consumer.
it is unacceptable to have the producer overwrite the value if it has not been read.
*/
 
public class SynchronizedIntCell implements IntCell {
	private int value = 0; 
	private int step=0;
	
	//7.17 boolean to remember set value or get value
	public boolean valueHasBeenRead() //to know the consumer has read a value or not? 
	{
			return (step%2==0);
	}
	/**while wait should be in both threads (setValue and getValue)*/
	public synchronized void setValue(int valueArg) //for producer to set value. so while wait should be here
	{
		try{
			while ((!valueHasBeenRead()))//it is unacceptable to have the producer overwrite the value if it has not been read.
				wait();
		}catch(InterruptedException e){
			System.out.println("there is an error");
		}
		this.value = valueArg;
		step=step+1;
		notifyAll(); // notify all threads waiting on the condition
		
	}
 
	public synchronized int getValue() 
	{ ///for consumer to read.so while wait should not be here
		try{
			while ((valueHasBeenRead()))
				wait();
		}catch(InterruptedException e){
			System.out.println("there is an error");
		}
		step=step+1;
		notifyAll();
		return value;
		
	}
	
}

/**************************************************************************
 * (C) Copyright 1999 by Deitel & Associates, Inc. and Prentice Hall.     *
 * All Rights Reserved.                                                   *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
