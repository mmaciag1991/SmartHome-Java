package com.sm.smarthome.Utils;

import com.sm.smarthome.Core.Utils.Helpers;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.util.ArrayList;
import java.util.List;

import static com.sm.smarthome.Core.Utils.Helpers.AppHeight;
import static com.sm.smarthome.Core.Utils.Helpers.AppWidth;

public class DisintegrationLogo {

    private double time = 0;

    private GraphicsContext g;

    private List<Particle> particles = new ArrayList<>();
    private int fullSize;

    Thread thread;

    private Parent createContent() {
        Pane root = new Pane();

        Canvas canvas = new Canvas(1280, 720);
        root.getChildren().add(canvas);

        g = canvas.getGraphicsContext2D();

        Image image = new Image(getClass().getResource("/com/sm/smarthome/Media/Images/smarthomeLogo.png").toExternalForm());
        g.drawImage(image, 280, 50);

        disintegrate(image);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += 0.017;

                if (time > 2) {
                    update();
                }
                if (time > 30) {
                    stop();
                }
            }
        };
        timer.start();

        return root;
    }

    private void disintegrate(Image image) {
        PixelReader pixelReader = image.getPixelReader();

        int index = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = pixelReader.getColor(x, y);

                if (!color.equals(Color.TRANSPARENT)) {
                    Particle p = new Particle(x + 280, y + 50, color);
                    index++;
                    p.Index = index;
                    particles.add(p);
                }
            }
        }

        fullSize = particles.size();

    }

    private void update() {
        g.clearRect(0, 0, 1280, 720);

        particles.removeIf(Particle::isDead);

        particles.parallelStream()
                .filter(p -> !p.isActive())
                .sorted((p1, p2) -> (int)(p2.getX() - p1.getX()))
                .limit(fullSize / 30 / 2)
                .forEach(p -> {
                    p.activate(new Point2D(Math.random() - 0.5, Math.random() - 0.5).multiply(2));
                });


        particles.forEach(p -> {
            p.update();
            p.draw(g);
        });

        thread = new Thread(this::waitForFinish);
        thread.setDaemon(true);
        thread.start();

    }

    public Stage primaryStage = new Stage();
    public void start() {
        var content = createContent();
        content.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        Scene scene = new Scene(content, AppWidth, AppHeight);
        JMetro JMetroThemeManager = new JMetro(Style.DARK);
        JMetroThemeManager.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    boolean running = true;
    private void waitForFinish() {

        while (running) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException ignored) {}

            try {
                if (particles.parallelStream().noneMatch(Particle::isActive) && running) {

                    Helpers.CloseStageWithFadeOut(primaryStage);

                    running = false;

                }
            } catch (Exception ignored) {}

        }

        thread.interrupt();
    }
}
