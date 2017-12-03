package datas;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataConverter {
    private static List<RewardPoint> pairList;

    public static List<RewardPoint> loadData() {
        if (pairList == null) {
            List<Point> allPoints = getAllPoints();
            List<Integer> allRewards = getAllRewards();
            pairList = IntStream.range(0, Math.min(allPoints.size(), allRewards.size()))
                    .mapToObj(i -> new RewardPoint(allPoints.get(i), allRewards.get(i)))
                    .collect(Collectors.toList());
            return pairList;
        } else {
            return pairList;
        }
    }

    private static List<Point> getAllPoints() {
        Path pointPath = Paths.get(Data.POINTS_SOURCE);
        final int[] pointCount = {1};
        List<Point> pointList = new ArrayList<>();
        try {
            Stream<String> allPoints = Files.lines(pointPath, StandardCharsets.UTF_8);
            allPoints.forEach(pointXY -> addPointToList(pointXY, pointCount, pointList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pointList;
    }

    private static void addPointToList(String pointXY, int[] pointCount, List<Point> pointList) {
        String[] currentPoint = pointXY.split(" ");
        String pointX = currentPoint[0];
        String pointY = currentPoint[1];
        Point point = new Point(pointCount[0], Integer.parseInt(pointX), Integer.parseInt(pointY));
        pointList.add(point);
        pointCount[0]++;
    }


    private static List<Integer> getAllRewards() {
        Path rewardPath = Paths.get(Data.REWARD_SOURCE);
        List<Integer> rewardList = new ArrayList<>();
        try {
            Stream<String> allRewards = Files.lines(rewardPath, StandardCharsets.UTF_8);
            allRewards.forEach(reward -> rewardList.add(Integer.parseInt(reward)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rewardList;
    }
}
