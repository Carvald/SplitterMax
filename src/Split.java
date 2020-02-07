import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class Split {

	/**
	 * @param args
	 * @throws IOException
	 */

	private static final String ORIGINAL = "¡·…ÈÕÌ”Û⁄˙—Ò‹¸";
	private static final String REPLACEMENT = "AaEeIiOoUuNnUu";

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		String line = "";

		try {

			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream("C:/countries/NAMESFILE"), "UTF-8"));
			line = br.readLine();
			FileOutputStream fos = new FileOutputStream("C:/countries/" + nameIso(line));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

			while ((line = br.readLine()) != null) {
				String mine = line.trim();
				if (mine.contains("NAMES") || mine.contains("LAST")) {
					bw.close();
					fos = new FileOutputStream("C:/countries/" + nameIso(mine));
					bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
				} else {
					bw.write(line);
					bw.newLine();
				}
			}
			bw.close();

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}

	}

	public static String nameIso(String name) {
		String[] arr;
		boolean x = false;
		if (!name.contains("LAST")) {
			arr = name.split("NAMES");
			x = true;
		} else {
			arr = name.split("LAST");
		}
		String[] countryCodes = Locale.getISOCountries();
		for (String countryCode : countryCodes) {
			String upperClearName = stripAccents(arr[0].trim()).toUpperCase();
			Locale outLocale = Locale.forLanguageTag("en_GB");
			Locale locale = new Locale("", countryCode);
			if (locale.getDisplayCountry(outLocale).toUpperCase().equals(upperClearName))
				name = countryCode+ (x ? "" : "-LN");
		}
		return name;
	}

	public static String stripAccents(String str) {
		if (str == null) {
			return null;
		}
		char[] array = str.toCharArray();
		for (int index = 0; index < array.length; index++) {
			int pos = ORIGINAL.indexOf(array[index]);
			if (pos > -1) {
				array[index] = REPLACEMENT.charAt(pos);
			}
		}
		return new String(array);
	}

}