//package ovoce.thedrake.ui;
//
//import javafx.scene.control.Label;
//import javafx.scene.layout.Pane;
//import javafx.util.Builder;
//import ovoce.thedrake.game.PlayingSide;
//
//public class CapturedView extends Pane implements Builder {
//
//    private PlayingSide side;
//    private Label label;
//
//    public CapturedView(PlayingSide side) {
//        this.side = side;
//        this.label = new Label("0");
//        this.setPrefSize(512, 100);
//    }
//
//    public void addCapturedTroop() {
//        this.label = new Label("1");
//    }
//
//    @Override
//    public Object build() {
////        GameState state = createTestGame();
////        return new BoardView(state);
////        return new CapturedView();
//    }
//
//}
