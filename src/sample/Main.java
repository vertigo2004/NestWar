package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import static java.lang.Math.random;

public class Main extends Application {
    private final static int NEST_SIZE = 50;
    private final static int ANT_SIZE = 5;

    @Override
    public void start(Stage primaryStage) throws Exception{
        final Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        primaryStage.setTitle("Nest War");
        primaryStage.setScene(scene);
        primaryStage.show();
/*
        final Circle start = new Circle(NEST_SIZE, Color.web("white", .15));
        start.setCenterX(200);
        start.setCenterY(200);
        start.setId("source");
        final Circle end = new Circle(NEST_SIZE, Color.web("white", .15));
        end.setCenterX(600);
        end.setCenterY(400);
        end.setId("target");

        Group ants = new Group();
        for (int i=0; i<5; i++) {
            Circle circle = new Circle(ANT_SIZE, Color.web("white", 0.6));
            ants.getChildren().add(circle);
        }

        start.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("DragDetected");
                Dragboard db = start.startDragAndDrop(TransferMode.LINK);
                ClipboardContent content = new ClipboardContent();
                // Store node ID in order to know what is dragged.
                content.putString(start.getId());
                db.setContent(content);
                event.consume();
            }
        });

        end.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != end &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.LINK);
                }
                event.consume();
            }
        });

        end.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent mouseDragEvent) {
                System.out.println("DragDropped");
//                Circle sourceNest = (Circle) mouseDragEvent.getSource();
//                Circle targetNest = (Circle) mouseDragEvent.getTarget();
                Circle sourceNest = start;
                Circle targetNest = end;

//                if (sourceNest != targetNest) {
                final Group ants = new Group();
                for (int i = 0; i < 5; i++) {
                    Circle circle = new Circle(ANT_SIZE, Color.web("white", 0.6));
                    ants.getChildren().add(circle);
                }

                root.getChildren().add(ants);


                Timeline timeline = new Timeline();
                for (Node ant : ants.getChildren()) {
                    timeline.getKeyFrames().addAll(
                            new KeyFrame(Duration.ZERO, // set start position at 0
                                    new KeyValue(ant.translateXProperty(), sourceNest.getCenterX() - NEST_SIZE / 2 + NEST_SIZE * random()),
                                    new KeyValue(ant.translateYProperty(), sourceNest.getCenterY() - NEST_SIZE / 2 + NEST_SIZE * random())
                            ),
                            new KeyFrame(new Duration(5000), // set end position at 40s
                                    new KeyValue(ant.translateXProperty(), targetNest.getCenterX() - NEST_SIZE / 2 + NEST_SIZE * random()),
                                    new KeyValue(ant.translateYProperty(), targetNest.getCenterY() - NEST_SIZE / 2 + NEST_SIZE * random())
                            )
                    );
                }
                timeline.play();
                timeline.setOnFinished(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent actionEvent) {
                        ants.getChildren().clear();
                    }
                });

                mouseDragEvent.setDropCompleted(true);
                mouseDragEvent.consume();
            }
//            }

        });

        Group circles = new Group();
        circles.getChildren().add(start);
        circles.getChildren().add(end);

        root.getChildren().add(circles);
*/
        Group map = new Group();
        Nest nest1 = new Nest(map, NestSize.MEDIUM, 100, 100, 10, Player.RED);
        Nest nest2 = new Nest(map, NestSize.MEDIUM, 700, 500, 10, Player.BLUE);
        Nest nest3 = new Nest(map, NestSize.MEDIUM, 300, 300, 10, Player.GREY);

        root.getChildren().add(map);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
