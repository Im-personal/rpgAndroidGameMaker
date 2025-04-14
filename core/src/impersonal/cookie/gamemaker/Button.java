package impersonal.cookie.gamemaker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class Button {

    private final Texture on;
    private final Texture hover;
    private Texture off;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    private final int ON = 0;
    private final int OFF = 1;

    private int type = OFF;

    private boolean isHover = false;

    public boolean inactive = false;
    private boolean hidden = false;


    public Button(Texture on, Texture hover, Texture off, int x, int y, int width, int height){
        this.on = on;
        this.hover = hover;
        this.off = off;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Button(Texture on, Texture hover, Texture off, int x, int y, int width, int height,boolean inactive,boolean hide){
        this.on = on;
        this.hover = hover;
        this.off = off;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.inactive = inactive;
        this.hidden = hide;
    }

    public void draw(Batch b){
        if(hidden)return;
        if(isHover) {
            b.draw(hover, x, 800-y-height, width, height);
            return;
        }
        switch(type) {
            case ON:
                b.draw(on, x, 800-y-height, width, height);
                break;
            case OFF:
                b.draw(off, x, 800-y-height, width, height);
                break;
        }
    }

    public void on(){
        type = ON;
    }

    public void off(){
        type = OFF;
    }

    public void flip(){
        type = type==ON?OFF:ON;
    }

    public abstract void onClick();

    public void hide(){
        hidden =true;
        inactive = true;
    }

    public void show(){
        hidden =false;
        inactive = false;
    }

    public boolean click(){
        if(!inactive&&isHover) {
            onClick();
            return true;
        }
        return false;
    }

    public void checkHover(int mx, int my){
        if(!inactive)
            isHover=mx>x && mx<(x+width) && my>y && my<(y+width);
    }


}
