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


        setUp(10, "grass", false);
        setUp(11, "grass", false);
        setUp(12, "/water/water_0", true);
        setUp(13, "/water/water_1", true);
        setUp(14, "grass", false);
        setUp(15, "grass", false);
        setUp(16, "grass", false);
        setUp(17, "grass", false);
        setUp(18, "grass", false);
        setUp(19, "grass", false);
        setUp(20, "grass", false);
        setUp(21, "grass", false);
        setUp(22, "grass", false);
        setUp(23, "grass", false);
        setUp(24, "grass", false);
        setUp(25, "grass", false);
        setUp(26, "grass", false);
        setUp(27, "grass", false);
        setUp(28, "grass", false);
        setUp(29, "grass", false);
        setUp(30, "grass", false);
        setUp(31, "grass", false);
        setUp(32, "grass", false);
        setUp(33, "grass", false);
        setUp(34, "grass", false);
        setUp(35, "grass", false);
        setUp(36, "grass", false);
        setUp(37, "grass", false);
        setUp(38, "grass", false);
        setUp(39, "grass", false);
        setUp(40, "grass", false);
        setUp(41, "grass", false);






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
