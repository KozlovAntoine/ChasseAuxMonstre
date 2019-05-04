package graphics;

import java.util.Timer;
import java.util.TimerTask;

import game.Chasseur;
import game.Monstre;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class DeuxJoueurs extends Game {
	
	private Timer t;
	
	public DeuxJoueurs(Joueur j1, Joueur j2) {
		super("Chasse aux monstres : 2 joueurs");
		if(j1.estMonstre()) {
			m = (Monstre) j1;
			c = (Chasseur) j2;
		} else {
			c = (Chasseur) j1;
			m = (Monstre) j2;
		}
		middle.setOnMouseClicked(e -> {
			int x = (int) (e.getX() / taille_case);
			int y = (int) (e.getY() / taille_case);
			if(c.peutJouer()) {
				if(reveal(x,y)) {
					System.out.println("LE CHASSEUR A GAGNE WOLA");
				}
			}
		});
		scene.setOnKeyPressed(e -> {
			if(m.peutJouer()) {
				KeyCode k = e.getCode();
				switch(k) {
					case UP: moveMonstre(0,-1); break;
					case DOWN: moveMonstre(0,1); break;
					case LEFT: moveMonstre(-1,0); break;
					case RIGHT: moveMonstre(1,0); break;
				}
			}
		});
		stage.setOnCloseRequest(e -> {t.cancel();});
		loop();
		
		t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						draw();
					}
				});
			}
		}, 0, 100);
	}

	@Override
	public void loop() {
		m.setup(size);
		translate = new TranslateTransition();
		translate.setDuration(Duration.millis(1000));
		translate.setToX(offsetX + (m.getX() * taille_case));
		translate.setToY(offsetY + (m.getY() * taille_case));
		translate.setNode(monstre);
		translate.play();
		System.out.println("X:" + m.getX() + "Y:" + m.getY());
		m.setJouer(true);
		c.setJouer(false);
	}

}
