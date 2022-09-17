import api.DWGA;
import api.EdgeData;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyPanel1 extends JPanel {
    List<EdgeData> edges = new ArrayList<>();
    List<NodeData> nodes = new ArrayList<>();
    DWGA ourGraph;//Can we do it more clever without saving this variable??
    private double xMinNew;
    private double yMinNew;
    private double xMaxNew;
    private double yMaxNew;


    public MyPanel1(DWGA gr) {
        this.ourGraph = gr;
        scalingsize();
        this.setPreferredSize(new Dimension(900, 600));
        repaint();
    }

    private void scalingsize(){
        xMinNew = Integer.MAX_VALUE;
        yMinNew = Integer.MAX_VALUE;
        xMaxNew = Integer.MIN_VALUE;
        yMaxNew = Integer.MIN_VALUE;
        Iterator<NodeData> iterator = ourGraph.getGraph().nodeIter();
        while (iterator.hasNext()){
            NodeData node = iterator.next();
            xMinNew = Math.min(node.getLocation().x(),xMinNew);
            yMinNew = Math.min(node.getLocation().y(),yMinNew);
            xMaxNew = Math.max(node.getLocation().x(),xMaxNew);
            yMaxNew = Math.max(node.getLocation().y(),yMaxNew);
        }
    }


    private int getXScale(NodeData node) {
        return 50+ (int) (((node.getLocation().x() - xMinNew)*800/(xMaxNew-xMinNew)));
    }

    private int getYScale(NodeData node) {
        return 50+ (int) (((node.getLocation().y() - yMinNew)*500/(yMaxNew-yMinNew)));
    }

    @Override
    public void paint(Graphics g) {
        scalingsize();
        super.paint(g);
        Graphics2D g1 = (Graphics2D) g;
        Iterator<NodeData> currIter = ourGraph.getGraph().nodeIter();
        //Drawing nodes on canvas
        while (currIter.hasNext()) {
            NodeData currNode = currIter.next();
            g.setColor(Color.BLACK);
            if (nodes.contains(currNode)) {
                g.setColor(Color.BLUE);
            }
            g.fillOval(getXScale(currNode), getYScale(currNode), 15, 15);//We need to do the scaling right!
            g.drawString(currNode.getKey() + "", getXScale(currNode), getYScale(currNode));
        }

        Iterator<EdgeData> Edges = this.ourGraph.getGraph().edgeIter();
        while (Edges.hasNext()) {
            EdgeData currEdge = Edges.next();
            if (!edges.contains(currEdge)) {
                NodeData src = this.ourGraph.getGraph().getNode(currEdge.getSrc());
                NodeData dest = this.ourGraph.getGraph().getNode(currEdge.getDest());
                g.setColor(Color.RED);
                g.drawLine(getXScale(src) + 8, getYScale(src) + 8, getXScale(dest) + 8, getYScale(dest) + 8);
                drawArrow(g1, getXScale(src) + 8, getYScale(src) + 8, getXScale(dest) + 8, getYScale(dest) + 8);
            }
        }
        for (EdgeData currEdge : edges) {
            NodeData src = this.ourGraph.getGraph().getNode(currEdge.getSrc());
            NodeData dest = this.ourGraph.getGraph().getNode(currEdge.getDest());
            g.setColor(Color.GREEN);
            g.drawLine(getXScale(src) + 8, getYScale(src) + 8, getXScale(dest) + 8, getYScale(dest) + 8);
            drawArrow(g1, getXScale(src) + 8, getYScale(src) + 8, getXScale(dest) + 8, getYScale(dest) + 8);
        }
    }


    public void setNodes(List<NodeData> nodes) {
        this.nodes = nodes;
    }

    public void setEdges(List<EdgeData> edges) {
        this.edges = edges;
    }

    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
        g.fillPolygon(new int[]{len, len - 5, len - 5, len},
                new int[]{0, -5, 5, 0}, 4);
    }
}