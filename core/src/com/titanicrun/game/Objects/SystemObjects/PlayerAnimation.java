package com.titanicrun.game.Objects.SystemObjects;

import com.titanicrun.game.Objects.PlayObjects.Animation;

/**
 * Created by Никита on 10.04.2016.
 */
public class PlayerAnimation {
    public Animation run, fly, preview;
    public PlayerAnimation(Animation run, Animation fly, Animation preview)
    {
        this.run = run;
        this.fly = fly;
        this.preview = preview;
    }
}
