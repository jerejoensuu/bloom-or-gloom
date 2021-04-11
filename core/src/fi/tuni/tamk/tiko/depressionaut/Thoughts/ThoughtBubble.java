package fi.tuni.tamk.tiko.depressionaut;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ThoughtBubble extends Actor {
    private final Texture posBubble = new Texture("thoughts/positiveBubble.png");
    private final Texture posCircle = new Texture("thoughts/positiveCircle.png");
    private final Texture negBubble = new Texture("thoughts/negativeBubble.png");
    private final Texture negCircle = new Texture("thoughts/negativeCircle.png");

    private ThoughtBubble[] thoughts = new ThoughtBubble[3];

    private Vector2 position;
    private Emotion emotion;
    private Rectangle hitbox;
    private float scale;

    private enum Emotion{
        POSITIVE,
        NEGATIVE;
    }

    public void createThought(float offset) {
        int randInt = 0;
        while(!isListFull()) {
            randInt = (int)(Math.random()*3);
            if (thoughts[randInt] == null) {
                thoughts[randInt] = new ThoughtBubble();
                break;
            }
        }
        ThoughtBubble temp = thoughts[randInt];
        temp.position = getPosition(randInt, offset);
        temp.scale = 0.01f;
        switch ((int)(Math.random()*2)) {
            case 1: temp.emotion = Emotion.POSITIVE;
                break;
            case 2: temp.emotion = Emotion.NEGATIVE;
                break;
        }
        temp.hitbox = new Rectangle(temp.position.x,
                temp.position.y,
                temp.getBubbleTexture().getWidth(),
                temp.getBubbleTexture().getHeight());

        thoughts[randInt] = temp;
    }

    public void render(SpriteBatch batch) {
        for (ThoughtBubble bubble : thoughts) {
            if (bubble != null) {
                // draw bubble
                batch.draw(bubble.getBubbleTexture(),
                        bubble.position.x,
                        bubble.position.y,
                        bubble.getBubbleTexture().getWidth() / 2f,
                        bubble.getBubbleTexture().getHeight() / 2f,
                        bubble.getBubbleTexture().getWidth(),
                        bubble.getBubbleTexture().getHeight(),
                        bubble.scale,
                        bubble.scale,
                        0,
                        0,
                        0,
                        bubble.getBubbleTexture().getWidth(),
                        bubble.getBubbleTexture().getHeight(),
                        false,
                        false);
                bubble.scale();
            }
        }
    }

    public void scale() {
        if (this.scale <= 1) {
            this.scale *= 1.1;
        }
    }

    public void checkForClear(float x, float y) {
        for (int i = 0; i < thoughts.length; i++) {
            if (thoughts[i] != null) {
                if (thoughts[i].hitbox.contains(x, y)) {
                    thoughts[i] = null;
                }
            }
        }
    }

    public Vector2 getPosition(int index, float offset) {
        switch (index) {
            case 0: return new Vector2(180, 1920 - 1380 + offset);
            case 1: return new Vector2(217, 1920 - 965 + offset);
            case 2: return new Vector2(668, 1920 - 773 + offset);
        }
        return new Vector2();
    }

    public boolean isListFull() {
        for (ThoughtBubble thought : thoughts) {
            if (thought == null) {
                return false;
            }
        }
        return true;
    }

    public Texture getBubbleTexture() {
        if (this.emotion == Emotion.POSITIVE) {
            return posBubble;
        } else {
            return negBubble;
        }
    }

    public Texture getCircleTexture() {
        if (this.emotion == Emotion.POSITIVE) {
            return posCircle;
        } else {
            return negCircle;
        }
    }
}
