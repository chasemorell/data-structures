package prog03;


public class LinearFib implements Fib{

	@Override
	public double fib(int n) {
		// TODO Auto-generated method stub
		int a = 0;
		int b = 1;
		int result = 0;
		
		for(int i = 0;i <= n-2; i ++){
			result = a + b;
			a = b;
			b = result;
			
		}
		return result;
	}

	@Override
	public double o(int n) {
		// TODO Auto-generated method stub
		return n;
	}
	
}