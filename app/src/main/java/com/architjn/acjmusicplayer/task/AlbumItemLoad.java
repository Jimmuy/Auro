package com.architjn.acjmusicplayer.task;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.TextView;

import com.architjn.acjmusicplayer.R;
import com.architjn.acjmusicplayer.utils.adapters.AlbumListAdapter;

/**
 * Created by architjn on 29/11/15.
 */
public class AlbumItemLoad extends AsyncTask<Void, Void, Void> {

    private Context context;
    private String artPath;
    private TextView name, artist;
    private View bgView;
    private ValueAnimator colorAnimation;
    private Bitmap bmp;
    private long duration = 800;

    public AlbumItemLoad(Context context, String artPath, View header) {
        this.context = context;
        this.artPath = artPath;
        name = (TextView) header.findViewById(R.id.album_list_long_name);
        artist = (TextView) header.findViewById(R.id.album_list_long_artist);
        bgView = header.findViewById(R.id.album_grid_header_bg);
    }

    public AlbumItemLoad(Context context, String artPath, AlbumListAdapter.SimpleItemViewHolder holder) {
        this.context = context;
        this.artPath = artPath;
        this.name = holder.name;
        this.artist = holder.artist;
        this.bgView = holder.bottomBg;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        bmp = BitmapFactory.decodeFile(artPath);
        Palette.generateAsync(bmp,
                new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(final Palette palette) {
                        try {
                            Integer colorFrom = context.getResources().getColor(android.R.color.white);
                            Integer colorTo = palette.getVibrantColor(palette.getDarkVibrantColor(
                                    palette.getDarkMutedColor(palette.getMutedColor(
                                            context.getResources().getColor(R.color.colorPrimary)))));
                            colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                            colorAnimation.setDuration(duration);
                            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                                @Override
                                public void onAnimationUpdate(ValueAnimator animator) {
                                    bgView.setBackgroundColor((Integer) animator.getAnimatedValue());
                                }

                            });
                            colorAnimation.start();
                            Integer colorFrom1 = context.getResources().getColor(R.color.album_grid_name_default);
                            Integer colorTo1 = palette.getVibrantSwatch().getBodyTextColor();
                            colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom1, colorTo1);
                            colorAnimation.setDuration(duration);
                            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animator) {
                                    name.setTextColor((Integer) animator.getAnimatedValue());
                                }
                            });
                            colorAnimation.start();
                            Integer colorFrom2 = context.getResources().getColor(R.color.album_grid_artist_default);
                            Integer colorTo2 = palette.getVibrantSwatch().getTitleTextColor();
                            colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom2, colorTo2);
                            colorAnimation.setDuration(duration);
                            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animator) {
                                    artist.setTextColor((Integer) animator.getAnimatedValue());
                                }
                            });
                            colorAnimation.start();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            bgView.setBackgroundColor(context.getResources()
                                    .getColor(R.color.colorPrimary));
                        }
                    }
                });
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
