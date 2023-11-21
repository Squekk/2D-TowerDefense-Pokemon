package src.util;

import src.entity.Piafabec;
import src.entity.Machoc;
import src.Main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class WaveLogic {
    private Main main;
	private int chrono;
	private int x = 0;
	private int y = 192;
	private List<Timer> waveTimers = new ArrayList<>();

	public void setChrono(int chrono) {
		this.chrono = chrono;
	}

    public WaveLogic(Main main) {
        this.main = main;
    }

    public void wave1() {
		System.out.println("WAVE : 1");
        spawnPiafabecWithDelay(5);
		spawnMachocWithDelay(1);
    }
	public void wave2() {
		System.out.println("WAVE : 2");
		spawnMachocWithDelay(2);
		spawnPiafabecWithDelay(9);
	}
	public void wave3() {
		System.out.println("WAVE : 3");
		spawnMachocWithDelay(6);
		spawnPiafabecWithDelay(12);
	}
	public void wave4() {
		System.out.println("WAVE : 4");
		spawnMachocWithDelay(9);
		spawnPiafabecWithDelay(18);
	}
	public void wave5() {
		System.out.println("FINAL WAVE");
		spawnMachocWithDelay(14);
		spawnPiafabecWithDelay(23);
	}
    private void spawnPiafabecWithDelay(int numberOfEnemies) {
		for (int i = 0; i < numberOfEnemies; i++) {
			javax.swing.Timer timer = new javax.swing.Timer(1000 * (i + 1), e -> {
				main.addEnemy(new Piafabec(x, y));
			});
			timer.setRepeats(false);
			timer.start();
		}
	}
	private void spawnMachocWithDelay(int numberOfEnemies) {
		for (int i = 0; i < numberOfEnemies; i++) {
			javax.swing.Timer timer = new javax.swing.Timer(1000 * (i + 1), e -> {
				main.addEnemy(new Machoc(x, y));
			});
			timer.setRepeats(false);
			timer.start();
		}
	}
}

