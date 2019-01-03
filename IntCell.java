package ss.week7.ProducerConsumer;

/**
 * Interface used in Producer/Consumer program.
 */
public interface IntCell 
{
	public void setValue(int val) throws InterruptedException;
	public int getValue() throws InterruptedException;
}
