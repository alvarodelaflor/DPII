
package utilities;

public class CSVGen {

	private static void gen(final int reps) {

		for (int i = 0; i <= reps; i++)
			System.out.println("csvusername" + i + "," + "csvmailnum" + i + "@mail.com");
	}

	public static void main(final String[] args) {

		CSVGen.gen(50);
	}
}
