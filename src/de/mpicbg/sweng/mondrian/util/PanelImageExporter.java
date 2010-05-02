package de.mpicbg.sweng.mondrian.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * A small util class which is able to export the content of an JPanel into an PNG-file or to the sysyem clipboard
 * (using CTRL-c)
 */
public class PanelImageExporter {

    JPanel myPanel;

    private static File lastPath = null;


    public PanelImageExporter(JPanel panel, boolean attachListners) {
        myPanel = panel;
        myPanel.setFocusable(true);

        if (attachListners) {
            myPanel.addKeyListener(new KeyAdapter() {

                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_C && e.isMetaDown())
                        new ImageClipper().copyToClipboard(getPanelImage());

                    if (e.getKeyCode() == KeyEvent.VK_S && e.isAltDown() && e.isMetaDown())
                        showSaveImageDialog();
                }
            });
        }
    }


    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void showSaveImageDialog() {
        File initPath = lastPath == null ? new File(".").getAbsoluteFile() : lastPath;

        JFileChooser fileChooser = new JFileChooser(initPath);
        int status = fileChooser.showSaveDialog(myPanel);
        if (status != JFileChooser.APPROVE_OPTION)
            return;

        lastPath = fileChooser.getSelectedFile().getParentFile();

        File imageFile = fileChooser.getSelectedFile();
        imageFile = imageFile.getName().endsWith(".png") ? imageFile : new File(imageFile.getAbsolutePath() + ".png");

        saveImage2File(imageFile);
    }


    public void saveImage2File(File imageFile) {
        BufferedImage image = getPanelImage();

        try {
            ImageIO.write(image, "png", imageFile);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }


    private BufferedImage getPanelImage() {
        BufferedImage image = new BufferedImage(myPanel.getWidth(), myPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        myPanel.paint(g2);
        g2.dispose();
        return image;
    }
}
