/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.regex.Pattern;
import leveleditor.data.Pair;
import leveleditor.data.Sprite;

/**
 *
 * @author franz
 */
public class Entity {
    private int x;
    private int y;
    private Sprite sprite;
    private boolean selected;
    private int activetile;
    private String name, type = "entity";
    private float sprScale = 1;
    
    private static int count = 0;
    
    private ArrayList<Pair<String, String>> customProperties = new ArrayList<>();
    private boolean moving;
    private int movingToX, movingToY;
    
    public Entity(Sprite spr, int x, int y) {
        this.x = x;
        this.y = y;
        sprite = spr;
        type = sprite.getName().substring(0, sprite.getName().length() - 4);
        name = type + "-" + count ++ + "-" + sprite.getName();
    }
    
    public void paint(Graphics2D g2d, Point center, float scale) {
        g2d.setColor(Color.red);
        g2d.setStroke(new BasicStroke(4));
        BufferedImage img = sprite.getImage(activetile);
        if (selected) {
            int xx = (int) ((x + center.x) * scale) - 5;
            int yy = (int) ((y + center.y) * scale) - 5;
            int xx2 = (int) ((x + center.x + img.getWidth() * sprScale) * scale) + 5;
            int yy2 = (int) ((y + center.y + img.getHeight() * sprScale) * scale) + 5;

            g2d.drawLine(xx, yy, xx2, yy);
            g2d.drawLine(xx, yy2, xx2, yy2);
            g2d.drawLine(xx, yy, xx, yy2);
            g2d.drawLine(xx2, yy, xx2, yy2);
        }
        g2d.drawImage(img, (int) ((x + center.x) * scale),
                (int) ((y + center.y) * scale), (int) (img.getWidth() * sprScale * scale), 
                (int) (img.getHeight() * sprScale * scale), null);
        if (moving) {
            Composite composite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2d.setColor(Color.BLUE);
            g2d.drawLine((int) ((x + img.getWidth() * sprScale / 2 + center.x) * scale), (int) ((y + img.getHeight() * sprScale / 2 + center.y) * scale),
                    (int) ((movingToX + img.getWidth() * sprScale / 2 + center.x) * scale), (int) ((movingToY + img.getHeight() * sprScale / 2 + center.y) * scale));
            g2d.drawImage(img, (int) ((movingToX + center.x) * scale),
                (int) ((movingToY + center.y) * scale), (int) (img.getWidth() * sprScale * scale), 
                (int) (img.getHeight() * sprScale * scale), null);
            g2d.setComposite(composite);
        }
    }

    public boolean isMouseOverMain(Point clicked, Point center, float scale) {
        Rectangle rect = new Rectangle((int) ((x + center.x) * scale), (int) ((y + center.y) * scale),
                (int) (sprite.getImage(activetile).getWidth() * sprScale * scale), (int) (sprite.getImage(activetile).getHeight() * sprScale * scale));
        return rect.contains(clicked);
    }
    
    public boolean isMouseOver(Point clicked, Point center, float scale) {
        Rectangle rect = new Rectangle((int) ((x + center.x) * scale), (int) ((y + center.y) * scale),
                (int) (sprite.getImage(activetile).getWidth() * sprScale * scale), (int) (sprite.getImage(activetile).getHeight() * sprScale * scale));
        return rect.contains(clicked) || isMovingOver(clicked, center, scale);
    }
    
    public void setPosition(Point pos) {
        x = pos.x;
        y = pos.y;
    }

    @Deprecated
    public void incrementPos(int x, int y) {
        this.x += x;
        this.y += y;
    }
    
    public void setSelected(boolean state) {
        selected = state;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() 
    {
        BufferedImage img = sprite.getImage(activetile);
        return (480 - y - img.getHeight());
    }

    public void setName(String alue) {
        name = alue;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Pair<String, String>> getCustomProperties() {
        return customProperties;
    }

    public int getActiveTile() {
        return activetile;
    }

    public void setActiveTile(int activetile) {
        this.activetile = activetile;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getMovingToX() {
        return movingToX;
    }

    public void setMovingToX(int movingToX) {
        this.movingToX = movingToX;
    }

    public int getMovingToY() {
        return movingToY;
    }

    public void setMovingToY(int movingToY) {
        this.movingToY = movingToY;
    }

    void incrementPos(Point point, Point center, float scale, int x, int y) {
        if (isMovingOver(point, center, scale) && moving) {
            movingToX += x;
            movingToY += y;
        } else {
            incrementPos(x, y);
        }
    }

    private boolean isMovingOver(Point point, Point center, float scale) {
        Rectangle rect = new Rectangle((int) ((movingToX + center.x) * scale), (int) ((movingToY + center.y) * scale),
                (int) (sprite.getImage(activetile).getWidth() * sprScale * scale), (int) (sprite.getImage(activetile).getHeight() * sprScale * scale));
        return rect.contains(point);
    }

    public float getSprScale() {
        return sprScale;
    }

    public void setSprScale(float sprScale) {
        this.sprScale = sprScale;
    }
}
