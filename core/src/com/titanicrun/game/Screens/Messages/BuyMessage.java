package com.titanicrun.game.Screens.Messages;

import com.badlogic.gdx.graphics.Texture;
import com.titanicrun.game.Screens.GameScreenManager;
import com.titanicrun.game.Screens.MessageScreen;
import com.titanicrun.game.Screens.SkinScreen;

/**
 * Created by Никита on 07.06.2016.
 */
public class BuyMessage extends MessageScreen {
    private SkinScreen screen;
    public BuyMessage(GameScreenManager gameScreenManager,  SkinScreen screen) {
        super(gameScreenManager, new Texture("messages/buyMessage.png"), screen);
        this.screen = screen;
    }
    @Override
    public void okButtonUpdate() {
        screen.messResult = true;
    }
    @Override
    public void cansleButtonUpdate() {
        screen.messResult = false;
    }
}
