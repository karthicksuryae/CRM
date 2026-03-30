package GenericUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class JavaUtility {

	// Generate random number

	public int togetRandomNumber() {
		Random ran = new Random();
		int randomcount = ran.nextInt();
		return randomcount;
	}

	// Get current date

	public String toExpDate(int days) {
		Date date = new Date();
		SimpleDateFormat sim = new SimpleDateFormat("dd-MM-yyyy");
		sim.format(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 20);
		return sim.format(cal.getTime());

	}

}
