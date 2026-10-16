package rhythmgame.notes;
import rhythmgame.core.Judgement;

public class TapNote extends Note {

	public TapNote(double time, char key) {
        super(time, key, 100);
    }
	
	   @Override
	    public int score(Judgement judgement) {
	        return (int) (getBasePoints() * judgement.getMultiplier());
	    }//score() gives perfect:100*0.1=100 orGOOD:100*0.7=70 OR MISS:0 
}
