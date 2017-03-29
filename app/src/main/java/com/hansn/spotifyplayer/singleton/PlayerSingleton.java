package com.hansn.spotifyplayer.singleton;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Metadata;
import com.spotify.sdk.android.player.PlaybackState;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

public class PlayerSingleton implements Player.NotificationCallback, ConnectionStateCallback {

    private static PlayerSingleton instance;
    private Player mPlayer;

    public static PlayerSingleton getInstance() {
        if (instance == null) {
            instance = new PlayerSingleton();
        }
        return instance;
    }

    public void init(int resultCode, String clientId, Intent intent, Context context) {
        AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
        if (response.getType() == AuthenticationResponse.Type.TOKEN) {
            Config playerConfig = new Config(context, response.getAccessToken(), clientId);
            Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                @Override
                public void onInitialized(SpotifyPlayer spotifyPlayer) {
                    mPlayer = spotifyPlayer;

                    mPlayer.addConnectionStateCallback(PlayerSingleton.this);
                    mPlayer.addNotificationCallback(PlayerSingleton.this);
                }

                @Override
                public void onError(Throwable throwable) {
                    Log.e("PlayerSingleton", "Could not initialize player: " + throwable.getMessage());
                }
            });
        }
    }

    public void playSong(String url) {
        if (mPlayer == null) {
            Log.w("PlayerService", "Attempting to play song before player is initialized");
            return;
        }

        Metadata metaData = mPlayer.getMetadata();
        if (metaData.currentTrack != null) {
            mPlayer.resume(new Player.OperationCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Error error) {

                }
            });
        } else {
            mPlayer.playUri(null, url, 0, 0);
        }
    }

    public void pauseSong() {
        mPlayer.pause(new Player.OperationCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Error error) {

            }
        });
    }

    public void setSongPos(final double pos) {
        mPlayer.seekToPosition(new Player.OperationCallback() {
            @Override
            public void onSuccess() {
                Log.d("PlayerService", "Seeked to position: " + pos + " ms");
            }

            @Override
            public void onError(Error error) {
                Log.d("PlayerService", "Seek failed (pos: " + pos + " ms");
            }
        }, (int)pos);
    }

    public double getCurrentPosAsPercentage() {
        PlaybackState state = mPlayer.getPlaybackState();
        long currentPosMs = state.positionMs;
        long songDurationMs = 0;

        Metadata.Track currentTrack = mPlayer.getMetadata().currentTrack;

        if (currentTrack != null) {
            songDurationMs = currentTrack.durationMs;
        }

        if (songDurationMs < 1 || currentPosMs < 1) {
            return 0;
        }
        return ((double)currentPosMs / songDurationMs) * 100;
    }

    public long getSongDuration() {
        Metadata.Track currentTrack = mPlayer.getMetadata().currentTrack;
        if (currentTrack != null) {
            return currentTrack.durationMs;
        }
        return 0;
    }

    @Override
    public void onLoggedIn() {
        Log.d("PlayerService", "Logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("PlayerService", "Logged out");
    }

    @Override
    public void onLoginFailed(Error error) {
        Log.d("PlayerService", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("PlayerService", "Temporary error");
    }

    @Override
    public void onConnectionMessage(String s) {
        Log.d("PlayerService", s);
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("PlayerService", "Playback event");
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("PlayerService", "Playback error: " + error.name());
    }
}
