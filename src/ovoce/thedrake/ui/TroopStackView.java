package ovoce.thedrake.ui;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Builder;
import ovoce.thedrake.game.*;

import java.util.ArrayList;
import java.util.List;

public class TroopStackView extends HBox implements StackContext, Builder {
    private TroopStacks troops;
    private Troop troopOnTop;

    public TroopStackView() {
        super();
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
                        Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(3))));
    }

    @Override
    public void removeBorder() {
        this.setBorder(null);
    }

    public void setTroops(TroopStacks troops) {
        this.troops = troops;
    }
}
