/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leveleditor.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import leveleditor.ThumbnailView;

/**
 *
 * @author Francis
 */
public class Sprite {
    private ThumbnailView tn;
    private String name;
    private SpriteProperties properties = new SpriteProperties();
    
    private BufferedImage src;
    private BufferedImage[] image;
    
    public Sprite(File img) throws IOException {
        src = ImageIO.read(img);
        image = new BufferedImage[1];
        image[0] = src;
        tn = new ThumbnailView(src);
        name = img.getName();
    }

    public ThumbnailView getThumbnail() {
        return tn;
    }

    public String getName() {
        return name;
    }
    
    public SpriteProperties getProperties() {
        return properties;
    }

    public BufferedImage getImage(int num) {
        return image[num];
    }
    
    public static BufferedImage getTiled(BufferedImage source, int cols, int rows, int num) {
        int swidth = source.getWidth(), sheight = source.getHeight();
        
        int deswidth = swidth / cols, desheight = sheight / rows;
        
        int xi = num % cols, yi = num / cols;
        
        return source.getSubimage(xi * deswidth, yi * desheight, deswidth, desheight);
    }
    
    public static BufferedImage[] getTiledSet(BufferedImage source, int cols, int rows) {
        BufferedImage[] image = new BufferedImage[cols * rows];
        for (int i = 0; i < image.length; i ++) {
            image[i] = getTiled(source, cols, rows, i);
        }
        return image;
    }

    void updateImage() {
        if (properties.tiled) {
            this.image = getTiledSet(src, properties.tilecols, properties.tilerows);
        } else {
            image = new BufferedImage[1];
            image[0] = src;
        }
    }
}
