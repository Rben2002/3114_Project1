import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.Test;


public class RectangleTest {

	public void testEquals() {
		Rectangle rec = new Rectangle(1, 1, 2, 2);
		Rectangle recTwo = new Rectangle(1, 1, 2, 2);
		Rectangle recThree = new Rectangle(2, 1, 6, 2);
		Rectangle rec4 = new Rectangle(-2, 1, 6, 2);
		
		assertTrue(rec.equals(recTwo));
		assertFalse(!recThree.equals(rec));
		assertTrue(rec4.isInvalid());
		
		
	}
	
	
}
