package view.gui;

import java.awt.Graphics;

public class Panel extends GUIComponent {

    private static final int DEFAULT_POSITION_X = 0;
    
    private static final int DEFAULT_POSITION_Y = 0;

    private static final int DEFAULT_WIDTH = 80;
    
    private static final int DEFAULT_HEIGHT = 80;

    private static final boolean DEFAULT_FILL = true;

    protected boolean fill;

    public Panel(int positionx, int positiony, int width, int height){
        setPosition(positionx, positiony);
        setSize(width, height);
        this.fill = DEFAULT_FILL;
    }

    public Panel(int width, int height){
        this(DEFAULT_POSITION_X, DEFAULT_POSITION_Y, width, height);
    }

    public Panel(){
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public void setFill(boolean fill) {
        this.fill = fill;
    }

    @Override
    protected void paint(Graphics graphics, GUIContainer container) {
        organiseComponents();
        graphics.setColor(color);
        
        if(fill){
            graphics.fillRect(positionx, positiony, width, height);
        }else{
            graphics.drawRect(positionx, positiony, width - 1, height - 1);
        }
    }

    private void organiseComponents(){
        int currentHeight = 0;
        for(int i = 0; i < components.size(); i++){
            GUIComponent component = components.get(i);
            component.setPositiony(currentHeight);
            currentHeight += component.getHeight();
        }
    }

}
