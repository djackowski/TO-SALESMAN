import algorithms.NearestNeighbours;
import datas.DataConverter;
import datas.RewardPoint;
import swing.PointDrawer;
import swing.PointFrame;

import java.util.List;
import java.util.function.Consumer;

public class Engine {
    public void start() {
        NearestNeighbours nearestNeighbours = new NearestNeighbours();
        PointDrawer pointDrawer = new PointDrawer(nearestNeighbours);
        new PointFrame(pointDrawer);
    }

    private void printAllPointsWithRewards() {
        List<RewardPoint> pairs = DataConverter.loadData();
        pairs.forEach(new Consumer<RewardPoint>() {
            @Override
            public void accept(RewardPoint rewardPoint) {
                System.out.println("datas.Point: " + rewardPoint.getPoint().getId() + " " + rewardPoint.getPoint().getX()
                + " " + rewardPoint.getPoint().getY() + " Reward: " + rewardPoint.getReward());
            }
        });
    }
}
