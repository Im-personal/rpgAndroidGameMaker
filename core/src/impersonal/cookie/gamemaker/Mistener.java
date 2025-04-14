package impersonal.cookie.gamemaker;

import com.badlogic.gdx.InputProcessor;

public class Mistener implements InputProcessor {

    public static int tx,ty,mx,my,ux,uy;

    public static boolean isShift = false;
    public static boolean isCtrl = false;
    public static boolean isAlt = false;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case 59:
                isShift=true;
                break;
            case 129:
                isCtrl=true;
                break;
            case 57:
                isAlt=true;
                break;
            case 62://SPACE
                Graph.eyeY=0;
                Graph.eyeX=0;
                Graph.zoom=1;
                break;
            default:
                System.out.println(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case 59:
                isShift=false;
                break;
            case 129:
                isCtrl=false;
                break;
            case 57:
                isAlt=false;
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    public static int selectedTileX = 0;
    public static int selectedTileY = 0;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        tx=screenX;ty=screenY;
        Mistener.button = button;
        if(button==0) {
            boolean isButtonPressed = false;
            for (Button b : Graph.btns) {
                if (b.click()) isButtonPressed = true;
            }

            if (screenY < 80 && screenX > 80 * 5 && screenX < 80 * 10) {
                Graph.selectedTile = ((screenX - 80 * 5) / 80) + Graph.page * 5;
                isButtonPressed = true;
            }

            if (screenY > 80 && !isButtonPressed) {
                if(Graph.mode==Graph.BUILDING) {
                    if (Graph.map.isFirst()) {
                        Graph.eyeX -= -selectedTileX * 80;
                        Graph.eyeY += -selectedTileY * 80;
                    }
                    Graph.map.setTile(Graph.selectedTile, selectedTileX, selectedTileY);

                    if (selectedTileX < 0) {
                        Graph.eyeX -= -selectedTileX * (80 * Graph.zoom);
                        selectedTileX = 0;
                    }

                    if (selectedTileY < 0) {
                        Graph.eyeY += -selectedTileY * (80 * Graph.zoom);
                        selectedTileY = 0;
                    }
                }
            }


            if(Graph.mode==Graph.WALLS){

                if(isAlt){
//                    tx=(Math.round(tx/80f))*80+(Graph.eyeX%80);
//                    ty=(Math.round(ty/80f))*80-(Graph.eyeY%80);
                }

                if(isCtrl){
                    int[] close = Graph.map.getClosestWall((int)((tx-Graph.eyeX)/Graph.zoom),(int)((800-ty+Graph.eyeY)/Graph.zoom));
                    tx = (int)(close[0]*Graph.zoom+Graph.eyeX);
                    ty = (int)(800-close[1]*Graph.zoom-Graph.eyeY);
                }
            }

        }

        if(button==1 && Graph.mode==Graph.BUILDING){
            Graph.map.removeTile(selectedTileX,selectedTileY);
        }
        isDown=true;
        return false;
    }
    static int button;
    static boolean isDown=false;
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDown=false;

        ux = screenX;
        uy = screenY;

        if(Graph.mode==Graph.WALLS){
            if(isShift){
                if(Math.abs(dx)<Math.abs(dy))ux=tx;else uy=ty;
            }
        }

        if(button==2) {
            Graph.eyeX += dx;
            Graph.eyeY += dy;
        }

        if(button==0){
            if(Graph.mode==Graph.WALLS){
                Graph.map.addWall((int) ((tx-Graph.eyeX)/Graph.zoom),(int)((800-ty+Graph.eyeY)/Graph.zoom),(int)((ux-Graph.eyeX)/Graph.zoom),(int)((800-uy+Graph.eyeY)/Graph.zoom));
            }
        }

        if(button==1){
            if(Graph.mode==Graph.WALLS){
                Graph.map.removeWalls((int) ((tx-Graph.eyeX)/Graph.zoom),(int)((800-ty+Graph.eyeY)/Graph.zoom),(int)((screenX-Graph.eyeX)/Graph.zoom),(int)((800-screenY+Graph.eyeY)/Graph.zoom));
            }
        }


        dx=0;
        dy=0;

        return false;
    }
    public static int dx,dy;
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        dx=screenX-tx;
        dy=screenY-ty;

        mx = screenX;
        my = screenY;

        if(Graph.mode==Graph.WALLS){
            if(isShift){
                if(Math.abs(dx)<Math.abs(dy))mx=tx;else my=ty;
            }
        }

        if(button==0){
            countTileNumber(screenX,screenY);
            if(Graph.mode == Graph.BUILDING) {
                Graph.map.setTile(Graph.selectedTile, selectedTileX, selectedTileY);

                if(selectedTileX<0){
                    Graph.eyeX+=selectedTileX*(80*Graph.zoom);
                    selectedTileX=0;
                }

                if(selectedTileY<0){
                    Graph.eyeY-=selectedTileY*(80*Graph.zoom);
                    selectedTileY=0;
                }
            }
        }

        if(button==1 && Graph.mode==Graph.BUILDING){
            countTileNumber(screenX,screenY);
            Graph.map.removeTile(selectedTileX, selectedTileY);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mx = screenX;
        my = screenY;
        for(Button b: Graph.btns){
            b.checkHover(screenX,screenY);
        }
        countTileNumber(screenX,screenY);
        return false;
    }

    private void countTileNumber(float screenX, float screenY){
        selectedTileX = (int)Math.floor((screenX-Graph.eyeX)/(80f*Graph.zoom));
        selectedTileY = (int)Math.floor((800-screenY+Graph.eyeY)/(80f*Graph.zoom));
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {


        if(amountY<0) {
            Graph.zoom *= 1.1;
            Graph.eyeX-=(int)((mx-Graph.eyeX)*0.1);
            Graph.eyeY-=(int)(((my)-(800+Graph.eyeY))*0.1);

        }
        else {
            Graph.zoom /= 1.1;
            Graph.eyeX+=(int)((mx-Graph.eyeX)*0.1);
        }



        return false;
    }
}
