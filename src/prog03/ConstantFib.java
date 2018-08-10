package prog03;

public class ConstantFib extends PowerFib{
	public double o (int n) {
        return 1;                                                                                                                                                                                                                     
    }

    /** Raise x to the n'th power
	@param x x
	@param n n
	@return x to the n'th power
    */
    protected double pow (double x, int n) {
    	return(Math.pow(x, n));
    	
    }
}
