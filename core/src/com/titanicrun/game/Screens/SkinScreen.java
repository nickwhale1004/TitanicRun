package com.titanicrun.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.titanicrun.game.Objects.PlayObjects.Animation;
import com.titanicrun.game.Objects.SystemObjects.Balance;
import com.titanicrun.game.Objects.SystemObjects.Button;
import com.titanicrun.game.Objects.PlayObjects.Mark;
import com.titanicrun.game.Objects.PlayObjects.MoveObject;
import com.titanicrun.game.Objects.SystemObjects.GameTexturesLoader;
import com.titanicrun.game.Objects.SystemObjects.PlayerAnimation;
import com.titanicrun.game.Objects.SystemObjects.PlayersNames;
import com.titanicrun.game.Objects.SystemObjects.Putter;
import  com.badlogic.gdx.graphics.Texture;
import com.titanicrun.game.Objects.SystemObjects.Scroller;
import com.titanicrun.game.Objects.SystemObjects.Text;
import com.titanicrun.game.Objects.SystemObjects.TouchPanel;
import com.titanicrun.game.Screens.Messages.BuyMessage;
import com.titanicrun.game.Screens.Messages.CantBuyMessage;
import com.titanicrun.game.TitanicClass;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Никита on 08.04.2016.
 */
public class SkinScreen extends Screen {

    private Text money, moneyVal, price, playerName;
    private Putter tableSkin;
    private TouchPanel scroll;
    private Texture scrollBack, skinBack, skinUpBack;
    private Preferences animSittings, lockSittings, prices, balanceSitting;
    private Button select, menu, buy;
    private ArrayList<PlayerAnimation> playerAnimations;
    //private ArrayList<Integer> prices;
    private Map<Integer, Integer> lockedIDs;
    private Array<Mark> lockedMarks;
    private Animation front, back, sliderAnim;
    private Mark selected, preview;
    private int countOfPerson;
    private MoveObject slider;
    private Balance playBalance;
    public byte process; //0 -nothing, 1 - slider to center, 2 - slider out screen
    public boolean messResult;
    private String screen;

    public SkinScreen(GameScreenManager gameScreenManager, String name) {
        super(gameScreenManager, name);
        //----------------------------------------------------//
        this.process = 0;
        //----------------------------------------------------//
        //Н А С Т Р О Й К И
        this.balanceSitting = Gdx.app.getPreferences("Balance");
        this.playBalance = new Balance(balanceSitting.getInteger("Balance"));
        this.animSittings = Gdx.app.getPreferences("Animation");
        this.lockSittings = Gdx.app.getPreferences("Locked");
        this.money = new Text(new Vector2(5, TitanicClass.ScreenHeight - 4), "MONEY: ", Color.WHITE, 25, false);
        this.moneyVal = new Text(new Vector2(100, TitanicClass.ScreenHeight - 4), "null", Color.YELLOW, 25, false);
        this.price = new Text(new Vector2(TitanicClass.ScreenWidth - 150, TitanicClass.ScreenHeight - 4),
                "null", Color.WHITE, 25, false);
        this.playerName = new Text(new Vector2(TitanicClass.ScreenWidth/2, TitanicClass.ScreenHeight-20),
                "null", Color.WHITE, 40, true);
        //Т Е К С Т У Р Ы
        this.scrollBack = GameTexturesLoader.get("sllBack.png").getTexture();
        this.skinBack = GameTexturesLoader.get("backs/skin.png").getTexture();
        this.skinUpBack = GameTexturesLoader.get("backs/skinUp.png").getTexture();
        //К Н О П К И
        this.select = new Button(GameTexturesLoader.get("buttons/select.png"), GameTexturesLoader.get("buttons/selectTuched.png"),
                new Vector2(TitanicClass.ScreenWidth/2 + 6, TitanicClass.ScreenHeight/2 + 50), 0);
        this.menu = new Button(GameTexturesLoader.get("buttons/menuSmall.png"), GameTexturesLoader.get("buttons/menuSmallTuched.png"),
                new Vector2(TitanicClass.ScreenWidth/2 - GameTexturesLoader.get("buttons/menu.png").getTexture().getWidth() - 13,
                        TitanicClass.ScreenHeight/2 + 50),0);
        this.buy = new Button(GameTexturesLoader.get("buttons/buy.png"), GameTexturesLoader.get("buttons/buyTuched.png"),
                new Vector2(TitanicClass.ScreenWidth/2 + 6, TitanicClass.ScreenHeight/2 + 50),0);
        //С П И С О К  К Н О П О К
        this.countOfPerson = 9;
        ArrayList<Button> buttons = new ArrayList<Button>();
        for(int i = 1; i <= countOfPerson; i++)
            buttons.add(new Button(GameTexturesLoader.get("players/"+i+"playerSkin.png"), GameTexturesLoader.get("players/"+i+"playerSkin.png"), new Vector2(0, 0),1));
        Rectangle rectangle = new Rectangle(63,TitanicClass.ScreenHeight/2, TitanicClass.ScreenWidth+63, TitanicClass.ScreenHeight/2);
        //Т А Б Л И Ц А  ( P U T T E R )
        this.tableSkin = new Putter(rectangle,buttons);
        //S C R O L L E R
        this.scroll = new TouchPanel(tableSkin/*, GameTexturesLoader.get("sll.png"), 30*/);
        //А Н И М А Ц И И  П Е Р С О Н А Ж Е Й
        this.playerAnimations = new ArrayList<PlayerAnimation>();
        for(int i = 1; i <= countOfPerson; i++)
            playerAnimations.add(new PlayerAnimation(new Animation(new Texture[]{
                    GameTexturesLoader.get("players/"+i+"player.png").getTexture(),
                    GameTexturesLoader.get("players/"+i+"player2.png").getTexture(),
                    GameTexturesLoader.get("players/"+i+"player3.png").getTexture(),
                    GameTexturesLoader.get("players/"+i+"player2.png").getTexture()},5),
                    GameTexturesLoader.get("players/"+i+"playerFront.png"),
                    GameTexturesLoader.get("players/"+i+"playerPreview.png")));
        //И Н И Ц И А Л И З А Ц И Я  З А Б Л О К И Р О В А Н Н Ы Х  П Е Р С О Н А Ж Е Й  И  ИХ  М Е Т О К
        lockedIDs = new HashMap<Integer, Integer>();
        lockedMarks = new Array<Mark>();
        prices = Gdx.app.getPreferences("Prices");
            for (int i = 0; i < countOfPerson; i++) {
                prices.putInteger(Integer.toString(i), i * 500);
            }
            prices.flush();
        lockedMarks.add(new Mark(GameTexturesLoader.get("players/unknow.png"), tableSkin, -1, new Vector2(0, 0)));
        lockedIDs.put(0,0);
        for(int i = 1; i < countOfPerson; i++) {
            int value = lockSittings.getInteger(i+"");
            lockedIDs.put(i,value);
            if(value == 0) {
                lockedMarks.add(new Mark(GameTexturesLoader.get("players/unknow.png"), tableSkin, i, new Vector2(0, 0)));
            }
            else {
                lockedMarks.add(new Mark(GameTexturesLoader.get("players/unknow.png"), tableSkin, -1, new Vector2(0, 0)));
            }
        }
        //П Р Е В Ь Ю  А Н И М А Ц И И
        int animation = animSittings.getInteger("Animation");
        this.front = playerAnimations.get(animation).preview;
        this.back = playerAnimations.get(animation).run;
        //М Е Т К И  В Ы Б О Р А  И  П Р Е В Ь Ю
        this.selected = new Mark(GameTexturesLoader.get("selected.png"),tableSkin,animSittings.getInteger("Animation"),
                new Vector2(5, tableSkin.skins.get(0).animation.getTexture().getHeight() - GameTexturesLoader.get("selected.png").getTexture().getHeight()-5));
        this.preview = new Mark(GameTexturesLoader.get("preview.png"), tableSkin, animSittings.getInteger("Animation"), new Vector2(0,0));
        //С Л А Й Д Е Р
        this.sliderAnim = GameTexturesLoader.get("backs/runner.png");
        this.slider = new MoveObject(sliderAnim, new Vector2(2*sliderAnim.getTexture().getWidth(),0), new Vector2(0,0),20);
    }
    @Override
    public void update() {
        gameScreenManager.removeScreen("Buy");
        gameScreenManager.removeScreen("Cant Buy");
        if(process == 0) {       //nothing
            if(screen == null) {
                screen = "MenuScreen";
            }
            selected.update();
            scroll.update();
            if(lockedIDs.get(scroll.items.getAnimation()) != 0 || scroll.items.getAnimation() == 0) {
                if(scroll.isPressed == 0)
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
                if(scroll.isPressed == 0)
                    buy.update();
                if (buy.isPressed()) {
                    if (playBalance.getBalance() - prices.getInteger(scroll.items.getAnimation()+"") >= 0) {
                        gameScreenManager.addScreen(new BuyMessage(gameScreenManager, this, "Buy"));
                        lockSittings.putInteger(scroll.items.getAnimation()+"",1);
                        lockSittings.flush();
                    }
                    else {
                        gameScreenManager.addScreen(new CantBuyMessage(gameScreenManager, this, "Cant Buy"));
                    }
                }
            }
            if (menu.isPressed() || (Gdx.input.isKeyPressed(Input.Keys.BACK) && !Gdx.input.isCatchBackKey())) {
                Gdx.input.setCatchBackKey(true);
                balanceSitting.putInteger("Balance", playBalance.getBalance());
                balanceSitting.flush();
                process = 1;
            }
            if(messResult) {
                playBalance.Buy(prices.getInteger(scroll.items.getAnimation()+""));
                lockedIDs.put(scroll.items.getAnimation(), 1);
                lockedMarks.set(scroll.items.getAnimation(),
                        new Mark(GameTexturesLoader.get("players/unknow.png"), tableSkin, -1, new Vector2(0, 0)));
                messResult = false;
            }
            if(scroll.isPressed == 0)
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
            gameScreenManager.getScreen(screen).update();
            if(slider.end) {
                gameScreenManager.setScreen(screen);
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (process != 2) {
            spriteBatch.draw(skinBack, 0, 0);
            scroll.render(spriteBatch);


            for(Mark x : lockedMarks) {
                x.render(spriteBatch);
            }

            preview.render(spriteBatch);
            selected.render(spriteBatch);
            spriteBatch.draw(skinUpBack, 0, 0);
            menu.render(spriteBatch);
           // spriteBatch.draw(scrollBack, scroll.position.x, 30);
           // scroll.render(spriteBatch);
            if(lockedIDs.get(scroll.items.getAnimation()) != 0 || scroll.items.getAnimation() == 0) {
                select.render(spriteBatch);
                spriteBatch.draw(front.getTexture(), menu.position.x + menu.animation.getTexture().getWidth() / 2 -
                                front.getTexture().getWidth() / 2,
                        menu.position.y + menu.animation.getTexture().getHeight() + 50);
                spriteBatch.draw(back.getTexture(), select.position.x + select.animation.getTexture().getWidth() / 2 -
                                back.getTexture().getWidth() / 2,
                        select.position.y + select.animation.getTexture().getHeight() + 50);
            }
            else {
                buy.render(spriteBatch);
                price.textValue = "PRICE: " + prices.getInteger(scroll.items.getAnimation()+"");
                price.render(spriteBatch);
            }
            money.render(spriteBatch);
            moneyVal.textValue = playBalance.getBalance()+"";
            moneyVal.render(spriteBatch);
            playerName.textValue = PlayersNames.getPlayerName(scroll.items.getAnimation())+"";
            playerName.render(spriteBatch);
        }
        else {
            gameScreenManager.getScreen(screen).render(spriteBatch);
        }
        spriteBatch.draw(slider.getTexture(), slider.position.x, slider.position.y);

    }

    @Override
    public void reset() {
        process = 0;
        scroll.reset();
        slider.reset();
        menu.reset();
        buy.reset();
        select.reset();
        slider.change(new Vector2(0,0));
        gameScreenManager.getScreen("GameScreen").reset();
    }

}
