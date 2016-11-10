class buffer{
	int data;
	boolean empty;
	public buffer(){
		empty = true;
		data = -99;
	}
	public synchronized int get(){
		while(empty){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		empty = true;
		System.out.println("소비자"+data+"번째 피자 소비");
		notifyAll();
		return data;
	}
	public synchronized void put(int input){
		while(!empty){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		empty = false;
		data = input;
		System.out.println("생산자"+input+"번째 피자 생산");
		notifyAll();
		return;
	}
}
class producer extends Thread{
	buffer mybuffer;
	public producer(buffer input){
		mybuffer = input;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 0;i<100;i++){
			mybuffer.put(i);
		}
	}
	
}
class consumer implements Runnable{
	buffer mybuffer;
	public consumer(buffer input){
		mybuffer = input;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 0;i<100;i++){
			int  v = mybuffer.get();
		}
	}
	
}
public class WDB{
	public static void main(String args[]){
		buffer b = new buffer();
		producer p1 = new producer(b);
		consumer c1 = new consumer(b);
		p1.start();
		Thread tc1 = new Thread(c1);
		tc1.start();
	}
}