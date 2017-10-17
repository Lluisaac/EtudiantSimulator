
package mainPackage.graphicsEngine.design;

import javax.swing.UIManager;

public class DesignTask {

	/**
	 * Setting the system Look and Feel or the Nimbus Look and Feel if the
	 * system's is not avaible
	 **/
	public static void setBestLookAndFeelAvailable() {

		String system_lf = UIManager.getSystemLookAndFeelClassName().toLowerCase();
		if (system_lf.contains("metal")) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			}
			catch (Exception e) {
			}
		}
		else {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			catch (Exception e) {
			}
		}
	}
}
