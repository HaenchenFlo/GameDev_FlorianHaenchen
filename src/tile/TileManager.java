package tile;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();

        loadMap("/maps/worldV2.txt");

    }

    public void getTileImage() {

        //Placeholder
        setUp(0, "grass", false);
        setUp(1, "grass", false);
        setUp(2, "grass", false);
        setUp(3, "grass", false);
        setUp(4, "grass", false);
        setUp(5, "grass", false);
        setUp(6, "grass", false);
        setUp(7, "grass", false);
        setUp(8, "/water/water_0", false);
        setUp(9, "/water/water_0", false);


        setUp(10, "/old/grass00", false);
        setUp(11, "/old/grass01", false);
        setUp(12, "/old/water00", true);
        setUp(13, "/old/water01", true);
        setUp(14, "/old/water02", true);
        setUp(15, "/old/water03", true);
        setUp(16, "/old/water04", true);
        setUp(17, "/old/water05", true);
        setUp(18, "/old/water06", true);
        setUp(19, "/old/water07", true);
        setUp(20, "/old/water08", true);
        setUp(21, "/old/water09", true);
        setUp(22, "/old/water10", true);
        setUp(23, "/old/water11", true);
        setUp(24, "/old/water12", true);
        setUp(25, "/old/water13", true);
        setUp(26, "/old/road00", false);
        setUp(27, "/old/road01", false);
        setUp(28, "/old/road02", false);
        setUp(29, "/old/road03", false);
        setUp(30, "/old/road04", false);
        setUp(31, "/old/road05", false);
        setUp(32, "/old/road06", false);
        setUp(33, "/old/road07", false);
        setUp(34, "/old/road08", false);
        setUp(35, "/old/road09", false);
        setUp(36, "/old/road10", false);
        setUp(37, "/old/road11", false);
        setUp(38, "/old/road12", false);
        setUp(39, "/old/earth", false);
        setUp(40, "/old/wall", true);
        setUp(41, "/old/tree", true);






    }

    public void setUp(int index, String imageName, boolean collision) {
        Utility uTool = new Utility();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].image =  uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (Exception _) {

        }
    }

    public void loadMap(String filePath) {

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while(col < gp.maxWorldCol) {


                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception _) {

        }
    }


    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
