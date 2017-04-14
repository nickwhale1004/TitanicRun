package com.titanicrun.game.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.SystemObjects.Button;
import com.titanicrun.game.TitanicClass;


/**
 * Created by Никита on 07.06.2016.
 */
public abstract class MessageScreen extends Screen {
    protected Texture backTexture;
    protected Button okButton, cansleButton;
    private Screen baseScreen;
    private byte process; //0 - to screen, 1 - nothing, 2 - out screen
    private MoveObject backMove, yesMove, noMove;
    private Animation buttonAnim;
    public MessageScreen(GameScreenManager gameScreenManager, Texture backTexture, Screen screen) {
        super(gameScreenManager);
        this.baseScreen = screen;
        this.backTexture = backTexture;
        this.buttonAnim = anim("buttons/ok.png");
        this.okButton = new Button(buttonAnim, anim("buttons/okTuched.png"),
                new Vector2(TitanicClass.ScreenWidth + backTexture.getWidth() / 2
                        - buttonAnim.getTexture().getWidth() - 5,
                        TitanicClass.ScreenHeight / 2 - buttonAnim.getTexture().getHeight() - 5));

        this.cansleButton = new Button(anim("buttons/cansle.png"), anim("buttons/cansleTuched.png"),
                new Vector2(TitanicClass.ScreenWidth +
                        backTexture.getWidth()/2 + 5,
                        TitanicClass.ScreenHeight / 2 - buttonAnim.getTexture().getHeight() - 5));

        this.backMove = new MoveObject(anim(backTexture), new Vector2(TitanicClass.ScreenWidth,
                TitanicClass.ScreenHeight / 2 - backTexture.getHeight() / 2),
                new Vector2(TitanicClass.ScreenWidth / 2 - backTexture.getWidth() / 2,
                        TitanicClass.ScreenHeight / 2 - backTexture.getHeight() / 2), 10);

        this.yesMove = new MoveObject(okButton,
                new Vector2(TitanicClass.ScreenWidth / 2 - buttonAnim.getTexture().getWidth() - 5,
                        TitanicClass.ScreenHeight / 2 - buttonAnim.getTexture().getHeight() - 5), 10);
        this.noMove =new MoveObject(cansleButton,
                new Vector2(TitanicClass.ScreenWidth / 2 + 5,
                         TitanicClass.ScreenHeight / 2 - buttonAnim.getTexture().getHeight() - 5),10);
        this.process = 0;
    }

    @Override
    public void update() {
        backMove.update();
        yesMove.update();
        noMove.update();
        if(process == 0) {
            if(noMove.end) {
                process = 1;
            }
        }
        else if(process == 1) {
            if(okButton.isPressed()) {
                okButtonUpdate();
            }
            else if (cansleButton.isPressed()) {
                cansleButtonUpdate();
            }
            if(okButton.isPressed() || cansleButton.isPressed()) {
                process = 2;
                backMove.change(new Vector2(-backTexture.getWidth(),
                        TitanicClass.ScreenHeight / 2 - backTexture.getHeight() / 2));
                yesMove.change(new Vector2(-backTexture.getWidth()/2 - buttonAnim.getTexture().getWidth() - 5,
                        TitanicClass.ScreenHeight / 2 - buttonAnim.getTexture().getHeight() - 5));
                noMove.change(new Vector2(-backTexture.getWidth()/2 + 5,
                        TitanicClass.ScreenHeight / 2 - buttonAnim.getTexture().getHeight() - 5));
            }
        }
        else if(process == 2) {
            baseScreen.update();
            if(noMove.end) {
                gameScreenManager.setScreen(baseScreen);
            }
        }
    }
    abstract public  void okButtonUpdate();
    abstract public void cansleButtonUpdate();

    @Override
    public void render(SpriteBatch spriteBatch) {
        baseScreen.render(spriteBatch);
        backMove.render(spriteBatch);
        okButton.render(spriteBatch);
        cansleButton.render(spriteBatch);
    }

}
