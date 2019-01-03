package ss.week7.ProducerConsumer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.*;

public class FinegrainedIntCell implements IntCell { 
	private int value = 0; 
	private Lock lock = new ReentrantLock();
	private Condition cond=lock.newCondition();
	private int step=0; 
	
	public boolean valueHasBeenRead()//to know the consumer has read a value or not? 
	{
		return (step%2==0);
	}
	
	@Override
	public void setValue(int val) throws InterruptedException
	{
		lock.lock();
		try
		{
			while (!(valueHasBeenRead()))/*if each value that is put in the buffer (by the producer) is read and printed only once by one consumer, then ok to proceed*/
				cond.await();
			this.value = val;
			step=step+1; // how to combine condition with the lock? 
			cond.signal ();//this should be after updating the step. 
			/**signal() should be in both thread 1 and thread 2.
			 * because signal() in thread 1 notifies thread 2
			 * signal() in thread 2 notifies thread 1*/
			/**wrong way:
			 * cond.signal();
			 * step=step+1;
			 * because the step is updated but the other threads do not know that the step is updated*/
		} catch (InterruptedException e){
			System.out.println("error");
		}
		finally { 
			lock.unlock(); 
		}
	}

	@Override
	public int getValue() throws InterruptedException {
		Thread.sleep(1000); 
		lock.lock();
		try {
			/**the code "while await" should be in both thread 1 and thread 2
			 * if only thread 1 has while await,
			 * then thread 2 might be executed twice*/
			while ((valueHasBeenRead()))/*if each value that is put in the buffer (by the producer) is read and printed only once by one consumer, then ok to proceed*/
				cond.await();
			step=step+1;
			cond.signal ();
			// return should not in try
		}catch (InterruptedException e){
			System.out.println("error");
		}finally{
			lock.unlock();
			return value;//return should not be in try. 
		}
		
	}

}
