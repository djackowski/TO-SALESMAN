package swing;

import algorithms.NearestNeighbours;
import datas.DataConverter;
import datas.RewardPoint;
import mvp.PointView;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.function.Consumer;

public class PointDrawer extends JPanel implements PointView {
    private static final int POINT_DIAMETER = 10;
    private static final int SCALE_DIVIDE = 3;
    private NearestNeighbours nearestNeighbours;

    public PointDrawer(NearestNeighbours nearestNeighbours) {
        this.nearestNeighbours = nearestNeighbours;
        try {
            nearestNeighbours.start();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;

        g2d.setColor(Color.red);

        drawPoints(g2d);

        drawLines(g2d);
    }

    private void drawLines(Graphics2D g2d) {
        List<RewardPoint> visitedPointsList = nearestNeighbours.getBestProfitPointsList();

        for(int i = 0; i < visitedPointsList.size() - 1; i++) {
            g2d.drawLine(visitedPointsList.get(i).getPoint().getX() / SCALE_DIVIDE, visitedPointsList.get(i).getPoint().getY() / SCALE_DIVIDE,
                    visitedPointsList.get(i + 1).getPoint().getX() / SCALE_DIVIDE, visitedPointsList.get(i + 1).getPoint().getY() / SCALE_DIVIDE);
        }
    }

    private void drawPoints(Graphics2D graphics) {
        List<RewardPoint> points = DataConverter.loadData();
        points.forEach(new Consumer<RewardPoint>() {
            @Override
            public void accept(RewardPoint point) {
                graphics.drawString(String.valueOf(point.getPoint().getId()), point.getPoint().getX() / SCALE_DIVIDE, point.getPoint().getY() / SCALE_DIVIDE);
                Ellipse2D.Double circle = new Ellipse2D.Double(point.getPoint().getX() / SCALE_DIVIDE,
                        point.getPoint().getY() / SCALE_DIVIDE, POINT_DIAMETER, POINT_DIAMETER);
                graphics.fill(circle);
            }
        });
    }

    @Override
    public void drawLine(RewardPoint previousPoint, RewardPoint currentPoint) {

    }
}
