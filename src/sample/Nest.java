package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static java.lang.Math.random;

/**
 * Created by vertigo on 2/22/16.
 */
public class Nest extends Circle {

    private static final int ANT_SIZE = 5;
    public static double DEFAULT_RATE = 0.5;
    public static final int ANT_SPEED = 10;

    private NestSize size;
    private int population;
    private Text populationText;
    private double rate;
    private Group group;

    public NestSize getSize() {
        return size;
    }

    public int getPopulation() {
        return population;
    }

    public double getRate() {
        return rate;
    }

    public Group getGroup() {
        return group;
    }

    public Player getHolder() {
        return holder;
    }

    public void defend(Player player, int units) {
        if (this.holder == player) {
            this.population += units;
        } else {
            this.population -= units;
            if (this.population < 0) {
                this.population = - this.population;
                this.holder = player;
                setFill(player.color);
            }
        }
        this.populationText.setText(String.valueOf(this.population));
    }

    public int attack() {
        int n = (int) (this.population * DEFAULT_RATE);
        this.population -= n;
        this.populationText.setText(String.valueOf(this.population));
        return n;
    }


    private Player holder;

    public Nest(Group group, NestSize size, double x, double y, int pop, Player pl) {
        super(x, y, size.getRadius(), pl.color);
        this.size = size;
        this.population = pop;
        this.holder = pl;
        setId(toString());
        this.group = group;
        this.group.getChildren().add(this);
        this.populationText = new Text(x, y, String.valueOf(this.population));
        this.populationText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.group.getChildren().add(populationText);

        setOnDragDetected(new OnDragDetectedEvent());
        setOnDragOver(new OnDragOverEvent());
        setOnDragDropped(new OnDragDroppedEvent());
    }

    private class OnDragDetectedEvent implements EventHandler<MouseEvent> {
        public void handle(MouseEvent event) {
            Dragboard db = startDragAndDrop(TransferMode.LINK);
            ClipboardContent content = new ClipboardContent();
            // Store node ID in order to know what is dragged.
            content.putString(getId());
            db.setContent(content);
            event.consume();
        }
    }

    private class OnDragOverEvent implements EventHandler<DragEvent> {
        @Override
        public void handle(DragEvent event) {
            if (event.getGestureSource() != this
                    && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.LINK);
            }
            event.consume();
        }
    }

    private class OnDragDroppedEvent implements EventHandler<DragEvent> {

        @Override
        public void handle(DragEvent dragEvent) {
            final Nest sourceNest = (Nest) dragEvent.getGestureSource();
            final Nest targetNest = (Nest) dragEvent.getTarget();

            double distance = Math.sqrt(
                    (sourceNest.getCenterX() - targetNest.getCenterX()) * (sourceNest.getCenterX() - targetNest.getCenterX())
                    +
                    (sourceNest.getCenterY() - targetNest.getCenterY()) * (sourceNest.getCenterY() - targetNest.getCenterY())
            );
            final int n = sourceNest.attack();
            final Group ants = new Group();
            for (int i = 0; i < n; i++) {
                Circle circle = new Circle(ANT_SIZE, sourceNest.getHolder().color);
                ants.getChildren().add(circle);
            }

            group.getChildren().add(ants);


            Timeline timeline = new Timeline();
            for (Node ant : ants.getChildren()) {
                timeline.getKeyFrames().addAll(
                        new KeyFrame(Duration.ZERO, // set start position at 0
                                new KeyValue(ant.translateXProperty(), sourceNest.getCenterX() - sourceNest.getRadius() / 2 + sourceNest.getRadius() * random()),
                                new KeyValue(ant.translateYProperty(), sourceNest.getCenterY() - sourceNest.getRadius() / 2 + sourceNest.getRadius() * random())
                        ),
                        new KeyFrame(new Duration(distance * ANT_SPEED),
                                new KeyValue(ant.translateXProperty(), targetNest.getCenterX() - targetNest.getRadius() / 2 + targetNest.getRadius() * random()),
                                new KeyValue(ant.translateYProperty(), targetNest.getCenterY() - targetNest.getRadius() / 2 + targetNest.getRadius() * random())
                        )
                );
            }
            timeline.play();
            timeline.setOnFinished(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent actionEvent) {
                    ants.getChildren().clear();
                    targetNest.defend(sourceNest.getHolder(), n);
                }
            });

            dragEvent.setDropCompleted(true);
            dragEvent.consume();
        }
    }
}

