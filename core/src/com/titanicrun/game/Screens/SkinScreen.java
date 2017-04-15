package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.SystemObjects.Balance;
import com.titanicrun.game.Objects.SystemObjects.Button;
import com.titanicrun.game.Objects.PlayObjects.Mark;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.SystemObjects.PlayerAnimation;
import com.titanicrun.game.Objects.SystemObjects.Putter;
import  com.badlogic.gdx.graphics.Texture;
import com.titanicrun.game.Objects.SystemObjects.Scroller;
import com.titanicrun.game.Screens.Messages.BuyMessage;
import com.titanicrun.game.Screens.Messages.CantBuyMessage;
import com.titanicrun.game.TitanicClass;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Никита on 08.04.2016.
 */
public class SkinScreen extends Screen {

    private BitmapFont font, font2;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter, parameter2;
    private Putter tableSkin;
    private Scroller scroll;
    private Texture scrollBack, skinBack, skinUpBack;
    private Preferences animSittings, lockSittings;
    private Button select, menu, buy;
    private ArrayList<PlayerAnimation> playerAnimations;
    private ArrayList<Integer> prices;
    private Map<Integer, Integer> lockedIDs;
    private ArrayList<Mark> lockedMarks;
    private Animation front, back, sliderAnim;
    private Mark selected, preview;
    private int countOfPerson;
    private MoveObject slider;
    private Balance playBalance;
    public byte process; //0 -nothing, 1 - slider to center, 2 - slider out screen
    public boolean messResult;
    private Screen screen;

    public SkinScreen(GameScreenManager gameScreenManager, Balance balance) {
        super(gameScreenManager);
        //----------------------------------------------------//
        this.process = 0;
        this.playBalance = balance;
        this.screen = new MenuScreen(gameScreenManager, playBalance);
        //----------------------------------------------------//
        //Н А С Т Р О Й К И
        this.animSittings = Gdx.app.getPreferences("Animation");
        this.lockSittings = Gdx.app.getPreferences("Locked");
        this.font = new BitmapFont();
        this.font2 = new BitmapFont();
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("peepo.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.size = 30;
        this.parameter.color = Color.WHITE;
        this.font = generator.generateFont(parameter);

        //font.setColor(0.95f, 0.92f, 0.03f, 1);
        this.parameter2.size = 30;
        this.parameter2.color = new Color(0.95f, 0.92f, 0.03f, 1);
        this.font2 = generator.generateFont(parameter2);
        //Т Е К С Т У Р Ы
        this.scrollBack = new Texture("sllBack.png");
        this.skinBack = new Texture("backs/skin.png");
        this.skinUpBack = new Texture("backs/skinUp.png");
        //К Н О П К И
        this.select = new Button(anim("buttons/select.png"), anim("buttons/selectTuched.png"),
                new Vector2(TitanicClass.ScreenWidth/2 + 20, TitanicClass.ScreenHeight/2 + 45));
        this.menu = new Button(anim("buttons/menuSmall.png"), anim("buttons/menuSmallTuched.png"),
                new Vector2(TitanicClass.ScreenWidth/2 - anim("buttons/menu.png").getTexture().getWidth() - 23,
                        TitanicClass.ScreenHeight/2 + 45));
        this.buy = new Button(anim("buttons/buy.png"), anim("buttons/buyTuched.png"),
                new Vector2(TitanicClass.ScreenWidth/2 + 20, TitanicClass.ScreenHeight/2 + 45));
        //С П И С О К  К Н О П О К
        this.countOfPerson = 4;
        ArrayList<Button> buttons = new ArrayList<Button>();
        for(int i = 1; i <= countOfPerson; i++)
            buttons.add(new Button(anim("players/"+i+"playerSkin.png"), anim("players/"+i+"playerSkin.png"), new Vector2(0, 0)));
        Rectangle rectangle = new Rectangle(0,TitanicClass.ScreenHeight/2, TitanicClass.ScreenWidth-40, TitanicClass.ScreenHeight/2);
        //Т А Б Л И Ц А  ( P U T T E R )
        this.tableSkin = new Putter(rectangle,buttons);
        //S C R O L L E R
        this.scroll = new Scroller(tableSkin, anim("sll.png"), 30);
        //А Н И М А Ц И И  П Е Р С О Н А Ж Е Й
        this.playerAnimations = new ArrayList<PlayerAnimation>();
        for(int i = 1; i <= countOfPerson; i++)
            playerAnimations.add(new PlayerAnimation(new Animation(new Texture[]{new Texture("players/"+i+"player.png"),
                new Texture("players/"+i+"player2.png"),
                new Texture("players/"+i+"player3.png"), new Texture("players/"+i+"player2.png")},5),
                anim("players/"+i+"playerFront.png"), anim("players/"+i+"playerPreview.png")));
        //И Н И Ц И А Л И З А Ц И Я  З А Б Л О К И Р О В А Н Н Ы Х  П Е Р С О Н А Ж Е Й  И  ИХ  М Е Т О К
        lockedIDs = new HashMap<Integer, Integer>();
        lockedMarks = new ArrayList<Mark>();
        prices = new ArrayList<Integer>();
        for (int i = 0; i < countOfPerson; i++) {
            prices.add(i * 250);
        }
        lockedMarks.add(new Mark(anim("players/unknow.png"), tableSkin, -1, new Vector2(0, 0)));
        lockedIDs.put(0,0);
        for(int i = 1; i < countOfPerson; i++) {
            int value = lockSittings.getInteger(i+"");
            lockedIDs.put(i,value);
            if(value == 0) {
                lockedMarks.add(new Mark(anim("players/unknow.png"), tableSkin, i, new Vector2(0, 0)));
            }
            else {
                lockedMarks.add(new Mark(anim("players/unknow.png"), tableSkin, -1, new Vector2(0, 0)));
            }
        }
        //П Р Е В Ь Ю  А Н И М А Ц И И
        int animation = animSittings.getInteger("Animation");
        this.front = playerAnimations.get(animation).preview;
        this.back = playerAnimations.get(animation).run;
        //М Е Т К И  В Ы Б О Р А  И  П Р Е В Ь Ю
        this.selected = new Mark(anim("selected.png"),tableSkin,animSittings.getInteger("Animation"),
                new Vector2(5, tableSkin.skins.get(0).animation.getTexture().getHeight() - anim("selected.png").getTexture().getHeight()-5));
        this.preview = new Mark(anim("preview.png"), tableSkin, animSittings.getInteger("Animation"), new Vector2(0,0));
        //С Л А Й Д Е Р
        this.sliderAnim = anim("backs/runner.png");
        this.slider = new MoveObject(sliderAnim, new Vector2(2*sliderAnim.getTexture().getWidth(),0), new Vector2(0,0),20);

    }
    @Override
    public void update() {
        if(process == 0) {       //nothing
            selected.update();
            scroll.update();
            if(lockedIDs.get(scroll.items.getAnimation()) != 0 || scroll.items.getAnimation() == 0) {
                select.update();
                if (select.isPressed()) {
                    if(lockedIDs.get(scroll.items.getAnimation()) != 0 || scroll.items.getAnimation() == 0) {
                        animSittings.putInteger("Animation", scroll.items.getAnimation());
                        animSittings.flush();
                        selected.setTo(scroll.items.getAnimation());
                    }
                }
            }
            else {
                buy.update();
                if (buy.isPressed()) {
                    if (playBalance.getBalance() - prices.get(scroll.items.getAnimation()) >= 0) {
                        gameScreenManager.setScreen(new BuyMessage(gameScreenManager, this));
                        lockSittings.putInteger(scroll.items.getAnimation()+"",1);
                        lockSittings.flush();
                    }
                    else {
                        gameScreenManager.setScreen(new CantBuyMessage(gameScreenManager, this));
                    }
                }
            }
            if (menu.isPressed()) {
                Gdx.app.getPreferences("Balance").putInteger("Balance", playBalance.getBalance());
                Gdx.app.getPreferences("Balance").flush();
                process = 1;
            }
            if(messResult) {
                playBalance.Buy(prices.get(scroll.items.getAnimation()));
                lockedIDs.put(scroll.items.getAnimation(), 1);
                lockedMarks.set(scroll.items.getAnimation(),
                        new Mark(anim("players/unknow.png"), tableSkin, -1, new Vector2(0, 0)));
                messResult = false;
            }

            menu.update();
            back.update();
            front.update();
            preview.update();


            for(Mark x : lockedMarks) {
                x.update();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.R)) {
                for(int i = 1; i < countOfPerson; i++) {
                    lockSittings.putInteger(i + "", 0);
                    lockSittings.flush();
                }
            }
            if(Gdx.input.isKeyPressed(Input.Keys.O)) {
                playBalance.Buy(playBalance.getBalance());
            }
            if(Gdx.input.isKeyPressed(Input.Keys.I)) {
                playBalance.addPoint(500);
            }
                front = playerAnimations.get(scroll.items.getAnimation()).preview;
                back = playerAnimations.get(scroll.items.getAnimation()).run;
                preview.setTo(scroll.items.getAnimation());
        }
        else if(process == 1) {     //slider to center
            slider.update();
            if(slider.end) {
                process = 2;
                slider.change(new Vector2(-sliderAnim.getTexture().getWidth(),0));
            }
        }
        else if(process == 2) {     //slider out screen
            slider.update();
            screen.update();
            if(slider.end) {
                gameScreenManager.setScreen(screen);
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (process != 2) {
            spriteBatch.draw(skinBack, 0, 0);
            scroll.items.render(spriteBatch);
            for(Mark x : lockedMarks) {
                x.render(spriteBatch);
            }
            preview.render(spriteBatch);
            selected.render(spriteBatch);
            spriteBatch.draw(skinUpBack, 0, 0);
            menu.render(spriteBatch);
            spriteBatch.draw(scrollBack, scroll.position.x, 30);
            scroll.render(spriteBatch);
            if(lockedIDs.get(scroll.items.getAnimation()) != 0 || scroll.items.getAnimation() == 0) {
                select.render(spriteBatch);
                spriteBatch.draw(front.getTexture(), 10 + menu.position.x + menu.animation.getTexture().getWidth() / 2 -
                                front.getTexture().getWidth() / 2,
                        menu.position.y + menu.animation.getTexture().getHeight() + 85);
                spriteBatch.draw(back.getTexture(), -10 + select.position.x + select.animation.getTexture().getWidth() / 2 -
                                back.getTexture().getWidth() / 2,
                        select.position.y + select.animation.getTexture().getHeight() + 85);
            }
            else {
                buy.render(spriteBatch);
                font.draw(spriteBatch, "PRICE: " + Integer.toString(prices.get(scroll.items.getAnimation())), TitanicClass.ScreenWidth - 150, TitanicClass.ScreenHeight - 4);
            }
            /*
            C Т А Р А Я  О Т Р И С О В К А  Б А Л А Н С А
            playBalance.render(spriteBatch);
            */
            //Н О В А Я  О Т Р И С О В К А  Б А Л А Н С А
            //F O N T   Д Л Я   Т Е К С Т А
            font.draw(spriteBatch, "MONEY: ", 5, TitanicClass.ScreenHeight - 4);
            font2.draw(spriteBatch, Integer.toString(playBalance.getBalance()), 100, TitanicClass.ScreenHeight - 4);
        }
        else {
            screen.render(spriteBatch);
        }
        spriteBatch.draw(slider.getTexture(), slider.position.x, slider.position.y);
    }

}
