package core.data.model;

import core.data.structure.Box;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/**
 * <p> Graphical Square is a Graphical implementation of the Box interface. </p> <p> Created by Majid Vaghari on
 * 11/17/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.2.0 Updated super class
 * @see Box
 * @since version 1.0.0
 */
public class GraphicalSquare extends AbstractSquare {

    GraphicsContext context = this.getGraphicsContext2D();
    double size = 50;
    double diam = 10;

    public GraphicalSquare(){
        context.setFill(Paint.valueOf("RED"));
        context.fillOval(0,0,diam, diam);
        context.fillOval(0,size,diam, diam);
        context.fillOval(size,0,diam, diam);
        context.fillOval(size,size,diam, diam);
        this.setOnMouseClicked(null); //TODO: set onMouseClicked event
    }

    public boolean setBottom(GraphicalPlayer bottom) {
        context.setFill(bottom.getColor());
        context.strokeLine(0, size, size, size);
        return super.setBottom(bottom);
    }
    public boolean setRight(GraphicalPlayer right) {
        context.setFill(right.getColor());
        context.strokeLine(0, size, size, size);
        return super.setBottom(right);
    }

     public boolean setTop(GraphicalPlayer top) {
        context.setFill(top.getColor());
        context.strokeLine(0, size, size, size);
        return super.setBottom(top);
    }

     public boolean setLeft(GraphicalPlayer left) {
        context.setFill(left.getColor());
        context.strokeLine(0, size, size, size);
        return super.setBottom(left);
    }








}
