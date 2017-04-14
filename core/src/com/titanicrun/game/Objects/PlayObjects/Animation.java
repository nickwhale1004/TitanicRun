package com.titanicrun.game.Objects.PlayObjects;

import com.badlogic.gdx.graphics.Texture;
/**
 * Created by Никита on 28.01.2016.
 */
public class Animation {
    private Texture[] textures;
    private int textureID;
    private int speed;
    private int currentFrame;

    public Animation(Texture[] textures, int speed) {
        this.textures = textures;
        this.textureID = 0;
        this.speed = speed;
        this.currentFrame = 0;
    }


    public void update() {
        if (currentFrame >= speed) {
            textureID++;
            currentFrame = 0;
        }
        else {
            currentFrame++;
        }
        if(textureID == textures.length)
            textureID = 0;
    }
    public Texture getTexture() {
        return textures[textureID];
    }

    public void setTexture(String s) {
        textures[textureID] = new Texture(s);
    }
}
