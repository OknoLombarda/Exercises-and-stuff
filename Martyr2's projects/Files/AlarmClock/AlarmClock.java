import java.awt.EventQueue;

public class AlarmClock {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			AlarmClockFrame frame = new AlarmClockFrame();
			frame.setVisible(true);
		});
	}
}
