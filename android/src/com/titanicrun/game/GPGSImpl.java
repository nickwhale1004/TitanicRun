package com.titanicrun.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.Games;


/**
 * Created by Andrey (cb) Mikheev
 * GPGS
 * 26.09.2016
 */
public class GPGSImpl implements GPGS, GoogleApiClient.ConnectionCallbacks,
                                 GoogleApiClient.OnConnectionFailedListener {

    final static String className = GPGSImpl.class.getSimpleName();

    private static final int RC_SIGN_IN           = 9001;
    private static final int REQUEST_ACHIEVEMENTS = 9002;
    private static final int REQUEST_LEADERBOARD  = 9003; // increment next constants
    private static final int RC_SAVED_GAMES = 9009;

    private final String[] ACHEIVEMENT = {
            "CgkI-5zYhOUfEAIQAQ",
            "CgkI-5zYhOUfEAIQAg"
    };
    private final String   LEADERBOARD = "CgkI-5zYhOUfEAIQAw";
    public    GoogleApiClient client;
    protected  AndroidLauncher context;

    public void init( AndroidLauncher context ) {
        this.context = context;

        Games.GamesOptions gamesOptions;
        gamesOptions = Games.GamesOptions.builder().setRequireGooglePlus(true).build();

        // http://android-developers.blogspot.ru/2016/01/play-games-permissions-are-changing-in.html
        // В обновлениях написано, что нужно использовать ТОЛЬКО те АПИ, которые действительно
        // нужны.
        client = new GoogleApiClient.Builder( context )
               // .addApi(gamesOptions)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API, gamesOptions).addScope(Games.SCOPE_GAMES)
                .addApi(Drive.API).addScope(Drive.SCOPE_APPFOLDER)
                .build();
        // .addApi( Plus.API ).addScope( Plus.SCOPE_PLUS_LOGIN ) - it is not necessary
        // .addScope( Games.SCOPE_GAMES ) - it is not necessary too
    }

    @Override
    public void connect() {
        if ( isConnected() ) { return; }

        client.registerConnectionCallbacks( this );
        client.registerConnectionFailedListener( this );
        client.connect();

        Log.d( className, "Client: log in" );
    }

    @Override
    public void disconnect() {
        if ( !isConnected() ) { return; }

        client.unregisterConnectionCallbacks( this );
        client.unregisterConnectionFailedListener( this );
        client.disconnect();

        Log.d( className, "Client: log out" );
    }

    @Override
    public void signOut() {
        if ( !isConnected() ) { return; }

        Games.signOut( client );

        Log.d( className, "Client: sign out" );
    }

    @Override
    public boolean isConnected() {
        return client != null && client.isConnected();
    }

    public void onActivityResult( int request, int response, Intent intent ) {
        Log.d( className, "Activity requestCode: " + request );

        if ( request == RC_SIGN_IN ) {
            Log.d( className, "RC_SIGN_IN, responseCode=" + response + ", intent=" + intent );
            if ( response == context.RESULT_OK ) {
                client.connect();
            }
            else {
                int error = R.string.connect_error;
                BaseGameUtils.showActivityResultError( context, request, response, error );
            }
        }
    }

    @Override
    public void unlockAchievement( int n ) {
        if ( !isConnected() ) { return; }

        if ( n > ACHEIVEMENT.length ) { return; }
        else { Games.Achievements.unlock( client, ACHEIVEMENT[ n ] ); }
    }

    @Override
    public void unlockIncrementAchievement( int n, int count ) {
        if ( !isConnected() ) { return; }

        if ( n > ACHEIVEMENT.length ) { return; }
        else { Games.Achievements.increment( client, ACHEIVEMENT[ n ], count ); }
    }

    @Override
    public void showAchievements() {
        if ( !isConnected() ) { return; }

        Intent intent = Games.Achievements.getAchievementsIntent( client );
        context.startActivityForResult( intent, REQUEST_ACHIEVEMENTS );
    }

    @Override
    public void submitScore( long score ) {
        if ( !isConnected() ) { return; }

        Games.Leaderboards.submitScore( client, LEADERBOARD, score );
    }

    @Override
    public void showLeaderboard() {
        if ( !isConnected() ) { return; }

        Intent intent = Games.Leaderboards.getLeaderboardIntent( client, LEADERBOARD );
        context.startActivityForResult( intent, REQUEST_LEADERBOARD );
    }

    @Override
    public void onConnected( Bundle connectionHint ) {
        Log.d( className, "Client: Success connected xD" );
    }

    @Override
    public void onConnectionSuspended( int i ) {
        client.connect();
    }

    @Override
    public void onConnectionFailed( ConnectionResult connectionResult ) {
        Log.d( className, "Connection Failed, result: " + connectionResult );

        String error = connectionResult.getErrorMessage();
        BaseGameUtils.resolveConnectionFailure( context, client, connectionResult,
                                                RC_SIGN_IN, error );
    }
    public void showSavedGamesUI() {
        if (!isConnected()) return;
        int maxNumberOfSavedGamesToShow = 5;
        Intent savedGamesIntent = Games.Snapshots.getSelectSnapshotIntent(client,
                "See My Saves", true, true, maxNumberOfSavedGamesToShow);
        context.startActivityForResult(savedGamesIntent, RC_SAVED_GAMES);
    }
}
