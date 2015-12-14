package core.data.model;

import controller.Main;
import core.data.structure.Box;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import net.client.ClientCom;

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

    double size = 50;
    double diam = 10;
    GraphicsContext context = this.getGraphicsContext2D();
    int rowIndex=0;
    int colIndex=0;

    public void setSize(double size) {
        this.size = size;
    }

    public void setDiam(double diam) {
        this.diam = diam;
    }

    public GraphicsContext getContext() {
        return context;
    }

    public void setContext(GraphicsContext context) {
        this.context = context;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public double getSize() {
        return size;
    }

    public double getDiam() {
        return diam;
    }



    public GraphicalSquare(int rowIndex, int colIndex){
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        context.setFill(Paint.valueOf("RED"));
        context.fillOval(0,0,diam, diam);
        context.fillOval(0,size,diam, diam);
        context.fillOval(size,0,diam, diam);
        context.fillOval(size,size,diam, diam);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();

                double threshX = Math.abs(x-size);
                double threshY =  Math.abs(y-size);
                double min = x + y + threshX + y;
                int l=0;
                if(x<=size && y<=size && x>=0 && y>=0){
                    if(min< threshX + y + threshX + threshY) {
                        min = threshX + y + threshX + threshY;
                        l=1;
                    }
                    if(min< threshX + threshY + x+ threshY) {
                        min = threshX + threshY + x + threshY;
                        l=2;
                    }
                    if(min< x+ threshY + x + y) {
                        min = x + threshY + x + y;
                        l=3;
                    }
                }

                if (l==0){
                    Main.getCom().putLine(true, rowIndex, colIndex);
                }
                if (l==1){
                    Main.getCom().putLine(false, rowIndex, colIndex+1);
                }
                if (l==2){
                    Main.getCom().putLine(true, rowIndex+1, colIndex);
                }
                if (l==3){
                    Main.getCom().putLine(false, rowIndex, colIndex);
                }
            }
        }); //TODO: set onMouseClicked event

    }




    public boolean setBottom(Player bottom) {
        context.setFill(((GraphicalPlayer)bottom).getColor());
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
