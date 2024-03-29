package view.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class GUIComponent extends GUIContainer {

    private static final Color DEFAULT_COLOR = Color.LIGHT_GRAY;
    
    private static final AxisAlignment DEFAULT_AXIS_ALIGNMENT = AxisAlignment.Y_AXIS;
    
    private static final HorizontalAlignment DEFAULT_HORIZONTAL_ALIGNMENT = HorizontalAlignment.CENTER;

    private static final VerticalAlignment DEFAULT_VERTICAL_ALIGNMENT = VerticalAlignment.MIDDLE;

    protected Color color;
    
    protected boolean hover;

    protected AxisAlignment axisAlignment;

    protected HorizontalAlignment horizontalAlignment;

    protected VerticalAlignment verticalAlignment;

    protected List<GUIComponent> components;

    protected GUIComponent(){
        this.color = DEFAULT_COLOR;
        this.axisAlignment = DEFAULT_AXIS_ALIGNMENT;
        this.horizontalAlignment = DEFAULT_HORIZONTAL_ALIGNMENT;
        this.verticalAlignment = DEFAULT_VERTICAL_ALIGNMENT;
        this.components = new ArrayList<>();
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setAxisAlignment(AxisAlignment axisAlignment) {
        this.axisAlignment = axisAlignment;
    }
    
    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public boolean add(GUIComponent component){
        return components.add(component);
    }

    public boolean remove(GUIComponent component){
        return components.remove(component);
    }

    public void removeAll(){
        components.clear();
    }

    protected void paint(Graphics graphics, GUIContainer container){
        organise();
        for (GUIComponent component : components) {
            component.paint(graphics, this);
        }
    }

    private void organise(){
        switch (verticalAlignment) {
            case LOWER: verticalOrganiseLower(); break;
            case MIDDLE: verticalOrganiseMiddle(); break;
            case UPPER: verticalOrganiseUpper(); break;
            default: break;
        }
        switch (horizontalAlignment) {
            case RIGHT: horizontalOrganiseRight(); break;
            case CENTER: horizontalOrganiseCenter(); break;
            case LEFT: horizontalOrganiseLeft(); break;
            default: break;
        }
    }
    
    private void horizontalOrganiseCenter(){
        int maxSize = 0;
        
        for(GUIComponent component : components){
            maxSize += component.width;
        }

        int posx = positionx + (width - maxSize) / 2;

        if(axisAlignment == AxisAlignment.Y_AXIS){
            posx = positionx + width / 2;
        }

        for(GUIComponent component : components){
            if(axisAlignment == AxisAlignment.Y_AXIS){
                component.positionx = posx - component.width / 2;
            }else{
                component.positionx = posx;
                posx += component.width;
            }
        }
    }
    
    private void verticalOrganiseMiddle(){
        int maxSize = 0;
        
        for(GUIComponent component : components){
            maxSize += component.height;
        }

        int posy = positiony + (height - maxSize) / 2;

        if(axisAlignment == AxisAlignment.X_AXIS){
            posy = positiony + height / 2;
        }

        for(GUIComponent component : components){
            if(axisAlignment == AxisAlignment.X_AXIS){
                component.positiony = posy - component.height / 2;
            }else{
                component.positiony = posy;
                posy += component.height;
            }
        }
    }
    
    private void verticalOrganiseLower(){
        int posy = positiony + height;

        ListIterator<GUIComponent> iterator = components.listIterator(components.size());
        while(iterator.hasPrevious()){
            GUIComponent component = iterator.previous();
            if(axisAlignment == AxisAlignment.Y_AXIS){
                posy -= component.height;
            }else{
                posy = positiony + height - component.height;
            }
            component.positiony = posy;
        }
    }
    
    private void verticalOrganiseUpper(){
        int posy = positiony;

        for(GUIComponent component : components){
            component.positiony = posy;
            if(axisAlignment == AxisAlignment.Y_AXIS){
                posy += component.height;
            }
        }
    }
    
    private void horizontalOrganiseRight(){
        int posx = positionx + width;

        ListIterator<GUIComponent> iterator = components.listIterator(components.size());
        while(iterator.hasPrevious()){
            GUIComponent component = iterator.previous();
            if(axisAlignment == AxisAlignment.X_AXIS){
                posx -= component.width;
            }else{
                posx = positionx + width - component.width;
            }
            component.positionx = posx;
        }
    }
    
    private void horizontalOrganiseLeft(){
        int posx = positionx;

        for(GUIComponent component : components){
            component.positionx = posx;
            if(axisAlignment == AxisAlignment.X_AXIS){
                posx += component.width;
            }
        }
    }
    
    protected boolean isHover(int mx, int my){
        return mx >= positionx && mx <= positionx + width && my >= positiony && my <= positiony + height;
    }

    protected abstract void action();

    protected boolean clickComponents(int mx, int my){
        boolean one = false;

        if(hover){
            action();
            one = true;
        }
        
        for(GUIComponent component : components){
            if(component.clickComponents(mx, my)){
                one = true;
            }
        }
        
        return one;
    }
    
    protected GUIComponent hoverComponents(int mx, int my){
        GUIComponent last = null;
        hover = false;

        if(isHover(mx, my)){
            last = this;
        }

        for(GUIComponent component : components){
            GUIComponent crnt = component.hoverComponents(mx, my);
            if(crnt != null){
                last = crnt;
            }
        }

        return last;
    }

    @Override
    public String toString() {
        return "GUIComponent [color=" + color + ", verticalAlignment=" + verticalAlignment + ", horizontalAlignment=" + horizontalAlignment + ", components=" + components +
            ", super=" + super.toString() + "]";
    }

}
