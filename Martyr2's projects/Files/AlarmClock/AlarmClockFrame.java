import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AlarmClockFrame extends JFrame {
	private static final long serialVersionUID = 7154722218971259233L;
	private static final int WIDTH = 300;
	private static final int HEIGHT = 123;
	
	private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();;
	
	private JButton alarmClock;
	private JButton timer;
	private JButton chooseSound;
	private JButton stop;
	private JFileChooser chooser;
	private File sound;
	private Clip cl;

	public AlarmClockFrame() {
		setTitle("Alarm Clock");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 2));
		
		alarmClock = new JButton("Alarm Clock");
		timer = new JButton("Timer");
		chooseSound = new JButton("Choose sound");
		stop = new JButton("Stop");
		
		alarmClock.setEnabled(false);
		timer.setEnabled(false);
		stop.setEnabled(false);
		
		chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Audio files", "wav"));
		
		chooseSound.addActionListener(event -> {
			chooser.setCurrentDirectory(new File("."));
			
			int result = chooser.showOpenDialog(AlarmClockFrame.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				sound = chooser.getSelectedFile();
				try {
					prepareSound();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					e.printStackTrace();
				}
				alarmClock.setEnabled(true);
				timer.setEnabled(true);
			}
		});
		
		stop.addActionListener(event -> {
			stopSound();
			executorService.shutdown();
			
			System.exit(0);
		});
		
		alarmClock.addActionListener(event -> {
			String input = JOptionPane.showInputDialog(this, "Ring at (h:m)");
			
			if (!input.isBlank()) {
				alarmClock.setEnabled(false);
				timer.setEnabled(false);
				chooseSound.setEnabled(false);
				stop.setEnabled(true);
				setState(Frame.ICONIFIED);
				String[] temp = input.split(":");
				LocalTime time = LocalTime.of(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
				LocalTime now = LocalTime.now();
				time = time.minusHours(now.getHour());
				time = time.minusMinutes(now.getMinute());
				
				timer(time.getHour() * 3600 + time.getMinute() * 60);
			}
		});
		
		timer.addActionListener(event -> {
			String input = JOptionPane.showInputDialog(this, "Ring in (sec)");
			
			if (!input.isBlank()) {
				alarmClock.setEnabled(false);
				timer.setEnabled(false);
				chooseSound.setEnabled(false);
				stop.setEnabled(true);
				setState(Frame.ICONIFIED);
				
				timer(Integer.parseInt(input));
			}
		});
		
		Dimension buttonSize = chooseSound.getMinimumSize();
		alarmClock.setPreferredSize(buttonSize);
		timer.setPreferredSize(buttonSize);
		
		JPanel left = new JPanel();
		left.add(alarmClock);
		left.add(timer);
		left.add(chooseSound);
		
		add(left);
		add(stop);
	}
	
	public void timer(int time) {
	    executorService.scheduleAtFixedRate(() -> playSound(), time, 1, TimeUnit.SECONDS);
	}
	
	public void prepareSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		AudioInputStream au = AudioSystem.getAudioInputStream(sound.getAbsoluteFile());
		cl = AudioSystem.getClip();
		cl.open(au);
	}
	
	public void playSound() {
		cl.loop(Clip.LOOP_CONTINUOUSLY);
		cl.start();
		setState(Frame.NORMAL);
	}
	
	public void stopSound() {
		cl.stop();
	}
}
