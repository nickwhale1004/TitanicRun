package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.titanicrun.game.Objects.BaseObject;
import com.titanicrun.game.Screens.GameScreen;
import com.titanicrun.game.TitanicClass;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Никита on 29.01.2016.
 */
public class EnemiesCreator extends Creator {
    private Array<Enemy> readyList, workList;
    private GameScreen gameScreen;

    public EnemiesCreator(GameScreen gameScreen, Animation animation, float speed) {
        super(animation, speed);
        this.gameScreen = gameScreen;
        readyList = new Array<Enemy>(20);
        workList = new Array<Enemy>(20);
        for (int i = 0; i < 20; i++) {
            readyList.add(new Enemy(gameScreen, animation, new Texture("ball.png"), 0, -1));
            //создаём предметы в базовом размещении
        }
    }

    @Override
    public void update() {
        time++;
        if (time >= interval) {
            time = 0;
            if(interval >= 40)
                interval-=0.5;
            tick();
        }
        for (int i = 0; i < workList.size; i++) {
        //обновляю только рабочие предметы и удаляю те, что в процессе 5 (закончили работу)
            Enemy x = workList.get(i);
            x.update();
            if (x.process == 5) {
                x.process = 0;
                x.change(-1, 0, objAnimation);
                readyList.add(x);
                workList.removeValue(x,true);
            }
        }
    }

    private void tick() {
        //меняю расположение предмета и делаю его рабочим
        Enemy temp = readyList.get(0);
        readyList.removeIndex(0);

        Random random = new Random();
        int type = random.nextInt(2);   //0 - слева, 1 - справа
        int position = 350 + random.nextInt(TitanicClass.ScreenHeight);

        temp.change(type, position, objAnimation);
        temp.process = 1;

        workList.add(temp);   //добавляю в список рабочих
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        for (BaseObject x : workList) {
            x.render(spriteBatch);
        }
    }
    public void reset() {
        time = 0;
        for (int i = 0; i < workList.size; i++) {
            Enemy x = workList.get(i);
                x.process = 0;
                x.time = 0;
                x.change(-1, 0, objAnimation);
                readyList.add(x);
        }
        workList = new Array<Enemy>(20);
    }

    public static class Enemy extends BaseObject {
        int time = 0;
        private Texture waring;
        private int type; //0 - left, 1 - right
        protected byte process; //0 - birth (wait creator), 1 - move to screen, 2 - move in screen (wait timer), 3 - move out screen,
        // 4 - move to next side, 5 - stop (wait creator)

        public Enemy(GameScreen gameScreen, Animation animation, Texture waring, int position, int type) {
            super(gameScreen, animation, new Vector2(0, position));
            this.waring = waring;
            this.type = type;
            this.process = 0;
        }
        //что бы не создавать каждый раз новый объект - увеличить производитльность, просто меняю его расположение из creator'а
        //суть та же, что и в конструторе
        public void change(int type, int position, Animation animation) {
            this.type = type;
            this.animation = animation;
            if (type == 0) {
                this.position = new Vector2(-waring.getWidth(), position);
            } else if (type == 1) {
                this.position = new Vector2(TitanicClass.ScreenWidth + waring.getWidth(), position);
            } else {
                this.position = new Vector2(0, 0);
            }
            this.process = 0;
        }

        @Override
        public void update() {
            animation.update();
            if(gameScreen.player.getBound().overlaps(getBound())) {
                gameScreen.player.dieFromObj = true;
                gameScreen.player.Die();
                gameScreen.Die();
            }
            if (process == 1) {      //move to screen
                if (type == 0) {
                    position.x+=3;

                    if (position.x >= 0) {
                        position.x = 0;
                        process = 2;
                    }
                } else {
                    position.x-=3;

                    if (position.x + waring.getWidth() <= TitanicClass.ScreenWidth) {
                        position.x = TitanicClass.ScreenWidth - waring.getWidth();
                        process = 2;
                    }
                }
            } else if (process == 2) {  //just move in screen
                time++;
                if (time >= 100) {
                    process = 3;
                    time = 0;
                }
            } else if (process == 3) {  //move out screen
                if (type == 0) {
                    position.x-=3;
                    if (position.x <= -animation.getTexture().getWidth()) {
                        position.x = -animation.getTexture().getWidth();
                        process = 4;
                    }
                } else {
                    position.x+=3;
                    if (position.x >= TitanicClass.ScreenWidth + animation.getTexture().getWidth()) {
                        position.x = TitanicClass.ScreenWidth + animation.getTexture().getWidth();
                        process = 4;
                    }
                }
            } else if (process == 4) {      //move to next side of screen
                if (type == 0) {
                    position.x += 15;
                    if (position.x >= TitanicClass.ScreenWidth) {
                        process = 5;
                        gameScreen.score++;
                    }
                } else {
                    position.x -= 15;
                    if (position.x + animation.getTexture().getWidth() <= 0) {
                        process = 5;        //end
                        gameScreen.score++;
                    }
                }
            }
            if (process != 0)       //if not wait creator to make it move
                position.y--;
        }

        @Override
        public void render(SpriteBatch spriteBatch) {
            //рисую шарик, когда нужно, иначе розовый квадратик (который летает)
            if (process == 4)
                spriteBatch.draw(animation.getTexture(), position.x, position.y);
            else
                spriteBatch.draw(waring, position.x, position.y);
        }

        @Override
        public void reset() {

        }
    }
}
