package impersonal.cookie.gamemaker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class Map {

    private ArrayList<ArrayList<int[]>> map;

    private boolean first = true;
    private int width = 0, height = 0;
    public Map(){
        map = new ArrayList<>();
        map.add(new ArrayList<int[]>());

        walls = new ArrayList<>();
    }

    private final int[] nothing = new int[]{-1};

    private ArrayList<int[]> walls;

    public void removeTile(int x, int y){
        if(x>=0 && y>=0 && x<map.size() && y<map.get(x).size()){
            map.get(x).set(y,nothing);
        }
    }

    public void setTile(int tile, int x, int y){
        if(first){
            first = false;
            map.get(0).add(new int[]{tile});
        }else{
            if(x<0) {
                shift(-x, 0);
                x=0;
            }
            if(y<0) {
                shift(0, -y);
                y=0;
            }


            while(x>=map.size()){
                map.add(new ArrayList<int[]>());
            }

            for(int i = 0; i<map.size();i++)
                while(y>=map.get(i).size()){
                    map.get(i).add(nothing);
                }

            map.get(x).set(y,new int[]{tile});
        }
    }

    public void drawTiles(Batch b, Texture[] tiles, int eyeX, int eyeY, float zoom){
        if(!first)
        for(int i = 0; i<map.size();i++){
            for(int j = 0; j<map.get(i).size();j++){
                if(map.get(i).get(j)[0]>=0)
                b.draw(tiles[map.get(i).get(j)[0]],i*(80*zoom)+eyeX, j*(80*zoom)-eyeY,80*zoom,80*zoom);
            }
        }
    }

    public void drawWalls(int eyeX, int eyeY, float zoom){

        ShapeRenderer sr = new ShapeRenderer();
        sr.setColor(Color.WHITE);

        sr.begin(ShapeRenderer.ShapeType.Line);

        for(int[] wall: walls){
            sr.line(eyeX+wall[0]*zoom,wall[1]*zoom-eyeY,wall[2]*zoom+eyeX,wall[3]*zoom-eyeY);
        }

        if(Mistener.isDown && Graph.mode==Graph.WALLS && Mistener.button==0){
            sr.line(Mistener.tx,800-Mistener.ty,Mistener.mx,800-Mistener.my);
        }

        if(Mistener.isDown && Graph.mode==Graph.WALLS && Mistener.button==1){
            sr.setColor(Color.RED);
            sr.line(Mistener.tx,800-Mistener.ty,Mistener.mx,800-Mistener.my);
        }

        sr.end();


    }


    private void addX(int n){
        while(n>=map.size()){
            map.add(0,new ArrayList<int[]>());
        }
    }

    private void addY(int n){
        for(int i = 0; i<map.size();i++)
            while(n>=map.get(i).size()){
                map.get(i).add(0,nothing);
            }
    }

    private void moveWall(int[] wall, int x, int y){
        wall[0]+=x;wall[2]+=x;
        wall[1]+=y;wall[3]+=y;
    }

    public void shift(int x,int y){
        width = map.size();
        height = map.get(0).size();
        addX(width+x-1);
        addY(height+y-1);

        for(int[] wall: walls){
            moveWall(wall,x*80,y*80);
        }

    }

    public boolean isFirst(){return first;}

    public void addWall(int x1,int y1, int x2, int y2){
        walls.add(new int[]{x1,y1,x2,y2});
    }

    public void removeWalls(int x1,int y1, int x2, int y2){
        ArrayList<int[]> remove = new ArrayList<>();
        for(int[] wall: walls){
            if(intersect(wall[0],wall[1],wall[2],wall[3],x1,y1,x2,y2))
                remove.add(wall);
        }

        walls.removeAll(remove);

    }

    private boolean intersect(float x11,float y11,float x21,float y21,float x12, float y12, float x22,float y22) {
        float det, gamma, lambda;
        det = (x21 - x11) * (y22 - y12) - (x22 - x12) * (y21 - y11);
        if (det == 0) {
            return false;
        } else {
            lambda = ((y22 - y12) * (x22 - x11) + (x12 - x22) * (y22 - y11)) / det;
            gamma = ((y11 - y21) * (x22 - x11) + (x21 - x11) * (y22 - y11)) / det;
            return (0 < lambda && lambda < 1) && (0 < gamma && gamma < 1);
        }
    }

    double dist(double x,double y,double x1, double y1)
    {
        return Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
    }

    public int[] getClosestWall(int x, int y) {

        int resx=0,resy=0;
        int mindist = 2147000000;

        for(int[] wall:walls){
            int dist = (int)dist(wall[0],wall[1],x,y);
            if(dist<mindist){
                mindist=dist;
                resx = wall[0];
                resy = wall[1];

            }

            dist = (int)dist(wall[2],wall[3],x,y);
            if(dist<mindist){
                mindist=dist;
                resx = wall[2];
                resy = wall[3];

            }
        }

        return new int[]{resx,resy};
    }
}
