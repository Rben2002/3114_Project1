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
		Rectangle rec5 = new Rectangle(2, -4, 6, 2);
		Rectangle Invalid = new Rectangle(10, 10, 2000, 9);
		
		String recOut = "X: 1, Y: 1, Width: 2, Height: 2";
		
		// equals() tests
		assertTrue(rec.equals(recTwo));
		assertFalse(!recThree.equals(rec));
		// isInvalidTests()
		assertTrue(rec4.isInvalid());
		assertTrue(Invalid.isInvalid());
		assertFalse(rec.isInvalid());
		assertTrue(rec5.isInvalid());
		// getXCoordinate tests
		assertFalse(rec4.getxCoordinate() == 3);
		assertTrue(rec.getxCoordinate() ==1);
		// getyCoordinate tests
		assertTrue(rec.getyCoordinate() == 1);
		assertFalse(rec.getyCoordinate() == 50);
		
		// ToString() Tests
		assertTrue(rec.toString() == recOut);
		
		
	}
	
	
}
