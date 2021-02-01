import java.util.LinkedList;
import java.util.Random;
// Jacob Vesche
// Producer consumer threadding. 
// programming problem 1
public class ProgrammingProblem1{
	
	public static void main (String[] args) throws InterruptedException{
		
		final ProdCons prodcons = new ProdCons();
		
		Thread thread1 = new Thread(new Runnable() {
			
			@Override
			public void run(){
				try{
					prodcons.produce();
					
				}
				catch(InterruptedException e){
				
					e.printStackTrace();
				}
			}
		});
		
		Thread thread2 = new Thread(new Runnable(){
			@Override
				public void run(){
					try{
						prodcons.consume();
					}
					catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			
		});
		
		thread1.start();
		thread2.start();
		
		thread1.join();
		thread2.join();
		
	}
	
	public static class ProdCons{

		LinkedList<Integer> list = new LinkedList<>(); 
		int sizeBuffer = 1;
		
		public void produce() throws InterruptedException{
			Random rand = new Random();
			int val1 = 0;
			while(true){
				synchronized (this){
					
					while (list.size() == sizeBuffer)
						wait();
				
					System.out.println("Producer produced: " + val1);
				
					list.add(val1++);
				
					int randNum = rand.nextInt(3000);
					randNum += 3000;
				
					notify();

					Thread.sleep(randNum);
				}
			}
		}
		
		public void consume() throws InterruptedException{
			Random rand1 = new Random();
			while(true){
				synchronized (this){
					while(list.size() == 0)
						wait();
					
					int val2 = list.removeFirst();
					
					System.out.println("Consumer consumed: " + val2);
					
					int randNum = rand1.nextInt(3000);
					randNum += 3000;
					
					notify();
					
					Thread.sleep(randNum);
					
					
				}
			}
		}
	}
}