package impersonal.cookie.gamemaker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.swing.*;
import java.io.File;

public class Graph extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	static Texture btn_empty_on;
	static Texture btn_empty_off;
	static Texture btn_empty_hover;

	static Texture btn_build_on;
	static Texture btn_build_off;
	static Texture btn_build_hover;

	static Texture btn_walls_on;
	static Texture btn_walls_off;
	static Texture btn_walls_hover;

	static Texture btn_objects_on;
	static Texture btn_objects_off;
	static Texture btn_objects_hover;

	static Texture btn_events_on;
	static Texture btn_events_off;
	static Texture btn_events_hover;

	static Texture btn_left_on;
	static Texture btn_left_off;
	static Texture btn_left_hover;

	static Texture btn_right_on;
	static Texture btn_right_off;
	static Texture btn_right_hover;

	static Texture btn_up_on;
	static Texture btn_up_off;
	static Texture btn_up_hover;

	static Texture btn_down_on;
	static Texture btn_down_off;
	static Texture btn_down_hover;

	static Texture btn_play_on;
	static Texture btn_play_off;
	static Texture btn_play_hover;

	static Texture btn_stop_on;
	static Texture btn_stop_off;
	static Texture btn_stop_hover;

	static Texture btn_deny_on;
	static Texture btn_deny_off;
	static Texture btn_deny_hover;

	static Texture btn_accept_on;
	static Texture btn_accept_off;
	static Texture btn_accept_hover;

	static Texture btn_plus_on;
	static Texture btn_plus_off;
	static Texture btn_plus_hover;

	static Button[] btns;


	static Texture[] tileset;
	static File folder;
	static boolean load = false;
	static int page = 0;

	static Map map = new Map();

	static int selectedTile = 0;

	static int eyeX = 0,eyeY = 0;
	static int feyeX = 0,feyeY = 0;

	static float zoom = 1;


	public static int mode = 0;

	final static int BUILDING = 0;
	final static int WALLS = 1;
	final static int OBJECTS = 2;
	final static int EVENTS = 3;

	public void setMode(int mode){
		Graph.mode = mode;

		switch (mode){
			case BUILDING:
				btns[0].on();
				btns[1].off();
				btns[2].off();
				btns[3].off();

				btns[4].show();
				btns[5].show();
				btns[6].hide();
				btns[7].hide();
				btns[8].show();
				break;
			case WALLS:
				btns[1].on();
				btns[0].off();
				btns[2].off();
				btns[3].off();

				btns[4].hide();
				btns[5].hide();
				btns[6].hide();
				btns[7].hide();
				btns[8].hide();
				break;

			case OBJECTS:
				btns[2].on();
				btns[0].off();
				btns[1].off();
				btns[3].off();

				btns[4].show();
				btns[5].show();
				btns[6].hide();
				btns[7].hide();
				btns[8].show();
				break;

			case EVENTS:
				btns[3].on();
				btns[0].off();
				btns[2].off();
				btns[1].off();

				btns[4].hide();
				btns[5].hide();
				btns[6].show();
				btns[7].show();
				btns[8].hide();
				break;
		}
	}

	@Override
	public void create () {

		Gdx.input.setInputProcessor(new Mistener());

		batch = new SpriteBatch();

		img = new Texture("badlogic.jpg");

		btn_empty_on =    new Texture("buttons/btn_empty_on.png");
		btn_empty_off =   new Texture("buttons/btn_empty_off.png");
		btn_empty_hover = new Texture("buttons/btn_empty_hov.png");

		btn_build_on =    new Texture("buttons/btn_build_on.png");
		btn_build_off =   new Texture("buttons/btn_build_off.png");
		btn_build_hover = new Texture("buttons/btn_build_hov.png");

		btn_walls_on =    new Texture("buttons/btn_wall_on.png");
		btn_walls_off =   new Texture("buttons/btn_wall_off.png");
		btn_walls_hover = new Texture("buttons/btn_wall_hov.png");

		btn_objects_on =    new Texture("buttons/btn_obj_on.png");
		btn_objects_off =   new Texture("buttons/btn_obj_off.png");
		btn_objects_hover = new Texture("buttons/btn_obj_hov.png");

		btn_events_on =    new Texture("buttons/btn_events_on.png");
		btn_events_off =   new Texture("buttons/btn_events_off.png");
		btn_events_hover = new Texture("buttons/btn_events_hov.png");

		btn_left_on =    new Texture("buttons/btn_left_on.png");
		btn_left_off =   new Texture("buttons/btn_left_off.png");
		btn_left_hover = new Texture("buttons/btn_left_hov.png");

		btn_right_on =    new Texture("buttons/btn_right_on.png");
		btn_right_off =   new Texture("buttons/btn_right_off.png");
		btn_right_hover = new Texture("buttons/btn_right_hov.png");

		btn_up_on =    new Texture("buttons/btn_up_on.png");
		btn_up_off =   new Texture("buttons/btn_up_off.png");
		btn_up_hover = new Texture("buttons/btn_up_hov.png");

		btn_down_on =    new Texture("buttons/btn_down_on.png");
		btn_down_off =   new Texture("buttons/btn_down_off.png");
		btn_down_hover = new Texture("buttons/btn_down_hov.png");

		btn_accept_on =    new Texture("buttons/btn_accept_on.png");
		btn_accept_off =   new Texture("buttons/btn_accept_off.png");
		btn_accept_hover = new Texture("buttons/btn_accept_hov.png");

		btn_deny_on =    new Texture("buttons/btn_deny_on.png");
		btn_deny_off =   new Texture("buttons/btn_deny_off.png");
		btn_deny_hover = new Texture("buttons/btn_deny_hov.png");

		btn_stop_on =    new Texture("buttons/btn_stop_on.png");
		btn_stop_off =   new Texture("buttons/btn_stop_off.png");
		btn_stop_hover = new Texture("buttons/btn_stop_hov.png");

		btn_play_on =    new Texture("buttons/btn_play_on.png");
		btn_play_off =   new Texture("buttons/btn_play_off.png");
		btn_play_hover = new Texture("buttons/btn_play_hov.png");

		btn_plus_on =    new Texture("buttons/btn_plus_on.png");
		btn_plus_off =   new Texture("buttons/btn_plus_off.png");
		btn_plus_hover = new Texture("buttons/btn_plus_hov.png");

		btns = new Button[]{
				new Button(btn_build_on,btn_build_hover,btn_build_off,0,0,80,80) {
					@Override
					public void onClick() {
						setMode(BUILDING);
					}
				},
				new Button(btn_walls_on,btn_walls_hover,btn_walls_off,80,0,80,80) {
					@Override
					public void onClick() {
						setMode(WALLS);
					}
				},
				new Button(btn_objects_on,btn_objects_hover,btn_objects_off,80*2,0,80,80) {
					@Override
					public void onClick() {
						setMode(OBJECTS);
					}
				},
				new Button(btn_events_on,btn_events_hover,btn_events_off,80*3,0,80,80) {
					@Override
					public void onClick() {
						setMode(EVENTS);
					}
				},

				new Button(btn_left_on,btn_left_hover,btn_left_off,80*4+15,15,50,50) {
					@Override
					public void onClick() {
						page--;
						if(page<0)page = tileset.length/5;
					}
				},
				new Button(btn_right_on,btn_right_hover,btn_right_off,80*10+15,15,50,50) {
					@Override
					public void onClick() {
						page++;
						if(page>tileset.length/5)page=0;
					}
				},

				new Button(btn_play_on,btn_play_hover,btn_play_off,80*6+40,0,80,80,true,true) {
					@Override
					public void onClick() {
						flip();
					}
				},
				new Button(btn_stop_on,btn_stop_hover,btn_stop_off,80*7+40,0,80,80,true,true) {
					@Override
					public void onClick() {
						flip();
					}
				},

				new Button(btn_empty_on,btn_empty_hover,btn_empty_off,80*7+20,80+10,40,40) {
					@Override
					public void onClick() {
						new Thread(new Runnable() {
							@Override
							public void run() {
								JFileChooser chooser = new JFileChooser();
								JFrame f = new JFrame();
								f.setVisible(true);
								f.toFront();
								f.setVisible(false);
								chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
								int res = chooser.showSaveDialog(f);

								f.dispose();
								if (res == JFileChooser.APPROVE_OPTION) {
									folder = chooser.getSelectedFile();

									load = true;



								}
							}
						}).start();
					}
				},

		};

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		loadTiles();

		drawMap();
		if(mode==BUILDING)
			drawMapBuildElements();
		drawInterface();




		batch.end();
	}

	public static int selectX, selectY;

	private void drawMapBuildElements() {
		batch.setColor(1,1,1,0.5f);
		if(tileset!=null && !Mistener.isDown) {
			if(selectedTile>=tileset.length)selectedTile=tileset.length-1;
			draw(tileset[selectedTile], Mistener.selectedTileX * (int)(80*zoom)+feyeX, 800-(Mistener.selectedTileY+1) * (int)(80*zoom)+feyeY, (int)(80*zoom),(int)(80*zoom));
		}
		batch.setColor(1,1,1,1f);
	}

	private void loadTiles() {
		if(load) {
			load = false;
			File[] listOfFiles = folder.listFiles();
			if (listOfFiles != null) {
				tileset = new Texture[listOfFiles.length];

				for (int i = 0; i < listOfFiles.length; i++) {
					System.out.println(listOfFiles[i].getPath());
					tileset[i] = new Texture(listOfFiles[i].getPath());
				}

			}
		}
	}

	private void drawMap() {

		feyeX = eyeX;
		feyeY = eyeY;

		if(Mistener.button == 2){
			feyeX+=Mistener.dx;
			feyeY+=Mistener.dy;
		}

		if(tileset!=null)
		map.drawTiles(batch,tileset, feyeX,feyeY, zoom);

		batch.end();
		map.drawWalls(feyeX,feyeY, zoom);
		batch.begin();
	}

	private void drawInterface() {
		for(Button b: btns){
			b.draw(this.batch);
		}

		if(mode==BUILDING&&tileset!=null){
			for(int i = page*5; i< tileset.length && (i-page*5<5);i++){
				if(tileset[i]!=null) {
					if(selectedTile==i) batch.setColor(1,1,1,1);else batch.setColor(1,1,1,0.2f);
					draw(tileset[i], 80 * 5 + 80 * i - (80*5*page), 0, 80, 80);
				}
			}

			batch.setColor(1,1,1,1);
		}

	}

	private void draw(Texture texture, int x, int y, int w, int h) {
		batch.draw(texture,x,800-y-h,w,h);
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
