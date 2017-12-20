package ovoce.thedrake.ui;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Builder;
import ovoce.thedrake.game.*;

public class TroopStackView extends HBox implements StackContext, Builder {
    public boolean empty;

    private TroopStacks troops;
    private Troop troopOnTop;
    private final Border defaultBorder = new Border(
            new BorderStroke(
                    Color.BLACK,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    new BorderWidths(1)));

    public TroopStackView() {
        super();
        this.setBorder(defaultBorder);
        this.empty = false;
    }

    @Override
    public Object build() {
        return new TroopStackView();
    }

    @Override
    public void changeTroop(Troop troopOnTop) {
        this.troopOnTop = troopOnTop;

        this.setPrefSize(100, 100);
        this.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        TroopImageSet images = new TroopImageSet(troopOnTop.info().name());
        BackgroundImage bgImage = new BackgroundImage(
                images.get(troopOnTop.side(), troopOnTop.face()), null, null, null, null);
        this.setBackground(new Background(bgImage));
    }

    public void addBorder() {
        this.setBorder(new Border(
                new BorderStroke(
                        Color.RED,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(3))));
    }

    @Override
    public void removeBorder() {
        if (!empty) this.setBorder(defaultBorder);
    }

    @Override
    public void lastTroop() {
        this.empty = true;
        this.setBackground(null);
        this.setBorder(null);
    }
}
