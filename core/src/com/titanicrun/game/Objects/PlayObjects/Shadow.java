package com.titanicrun.game.Objects.PlayObjects;

    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.badlogic.gdx.math.Vector2;
    import com.titanicrun.game.Objects.BaseObject;
    import com.titanicrun.game.Screens.GameScreen;

    import java.util.Random;

    /**
     * Created by Никита on 06.04.2016.
     */
    public class Shadow extends BaseObject {
        private int time, time2;
        private int interval, interval2;
        private boolean draw;
        private byte process; //0 - wait, 1 - fr light, 2 - sc light

    public Shadow(GameScreen gameScreen, Animation animation) {
        super(gameScreen, animation, new Vector2(0,0));
        this.time = 0;
        this.time2 = 0;
        this.interval = new Random().nextInt(1000);
        this.interval2 = 0;
        this.draw = false;
    }
    public void tick(){
        time = 0;
        interval2 = 10+new Random().nextInt(50);
        process = 1;
        draw = true;
    }
    @Override
    public void update() {
        if(process == 0) {
            time++;
            if (time >= interval)
                tick();
        }
        else if (process == 1) {
            time2++;
            if(time2 >= interval2) {
                process = 0;
                draw = false;
                time2 = 0;
                interval = new Random().nextInt(1000);
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(draw)
            spriteBatch.draw(animation.getTexture(), position.x, position.y);
    }
}
