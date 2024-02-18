import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class SkipListTest extends student.TestCase {
	private SkipList<Integer, String> skipList;

	public void setUp() {
		skipList = new SkipList<> ();
	}

	public void testInsert() {
		skipList.insert(new KVPair<>(1, "One"));
		skipList.insert(new KVPair<>(2, "Two"));
		assertEquals(2, skipList.size());

	}

	public void testSearch() {
		skipList.insert(new KVPair<>(1, "One"));
		skipList.insert(new KVPair<>(2, "Two"));
		ArrayList<KVPair<Integer, String>> result = skipList.search(1);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("One", result.get(0).getValue());

	}

	public void testKeyRemove() {
		skipList.insert(new KVPair<>(1, "One"));
		skipList.insert(new KVPair<>(2, "Two"));
		KVPair<Integer, String> removed = skipList.remove(1);

		assertNotNull(removed);
		assertEquals("One", removed.getValue());
		assertEquals(1, skipList.size());
	}

	public void testValueRemove() {
		skipList.insert(new KVPair<>(1, "One"));
		skipList.insert(new KVPair<>(3, "Three"));
		KVPair<Integer, String> removed = skipList.removeByValue("Three");

		assertNotNull(removed);
		assertEquals(3, (int)removed.getKey());
		assertEquals(1, skipList.size());;

	}

	public void testDump() {
		// Set up a new ByteArrayOutputStream to capture output
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		PrintStream originalOut = System.out; // Hold the original System.out
		System.setOut(new PrintStream(outContent)); // Set System.out to our stream

		// Insert some elements into the skip list
		skipList.insert(new KVPair<>(1, "One"));
		skipList.insert(new KVPair<>(2, "Two"));
		skipList.insert(new KVPair<>(3, "Three"));

		// Call the dump function, which prints to System.out
		skipList.dump();

		// Construct the expected output string
		String expectedOutput = "SkipList dump:\n" +
				"node 0: (1, One) Level: [some level]\n" + // Note: Levels are random
				"node 1: (2, Two) Level: [some level]\n" +
				"node 2: (3, Three) Level: [some level]\n" +
				"SkipList size is: 3\n";

		// Use contains or pattern matching to validate the output since levels are random and unpredictable
		assertTrue(outContent.toString().contains("SkipList dump:"));
		assertTrue(outContent.toString().contains("SkipList size is: 3"));
		assertTrue(outContent.toString().contains("(1, One)"));
		assertTrue(outContent.toString().contains("(2, Two)"));
		assertTrue(outContent.toString().contains("(3, Three)"));

		// Optionally, check for more specific patterns if necessary

		// Reset System.out to its original stream
		System.setOut(originalOut);
	}

	public void testIterator() {
		skipList.insert(new KVPair<>(1, "One"));
		Iterator<KVPair<Integer, String>> iterator = skipList.iterator();

		assertTrue(iterator.hasNext());
		assertEquals("One", iterator.next().getValue());
		assertFalse(iterator.hasNext());

	}



}