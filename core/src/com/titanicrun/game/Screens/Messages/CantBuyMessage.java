package com.titanicrun.game.Screens.Messages;

import com.badlogic.gdx.graphics.Texture;
import com.titanicrun.game.Screens.GameScreenManager;
import com.titanicrun.game.Screens.MessageScreen;
import com.titanicrun.game.Screens.Screen;
import com.titanicrun.game.Screens.SkinScreen;


/**
 * Created by Никита on 12.06.2016.
 */
public class CantBuyMessage extends MessageScreen {
    public CantBuyMessage(GameScreenManager gameScreenManager, SkinScreen screen) {
        super(gameScreenManager, new Texture("messages/cantBuyMessage.png"), screen);
    }

    @Override
    public void okButtonUpdate() {

    }

    @Override
    public void cansleButtonUpdate() {

    }
}
