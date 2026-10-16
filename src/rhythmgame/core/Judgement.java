package rhythmgame.core;

	public enum Judgement {  //enum is a type that can only be a fixed list of values if in main I were to write :Jodgement result=Judgement.AWESOME; then compile error
	    PERFECT(1.0),   
	    GOOD(0.7),      
	    MISS(0.0);      

	    private final double multiplier;

	    Judgement(double multiplier) {
	        this.multiplier = multiplier;
	    }

	    public double getMultiplier() {
	        return multiplier;
	    }
	}

