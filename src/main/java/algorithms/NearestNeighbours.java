package algorithms;

import datas.DataConverter;
import datas.RewardPoint;

import java.util.*;
import java.util.function.Supplier;

public class NearestNeighbours {
    private static int COST = 5;
    private RewardPoint currentPoint = new RewardPoint();
    private RewardPoint firstPoint = new RewardPoint();
    private RewardPoint newPoint = new RewardPoint();
    private List<RewardPoint> visitedPointsList = new ArrayList<>();
    private List<RewardPoint> points = DataConverter.loadData();
    private Map<RewardPoint, Double> profitsMap = new HashMap<>();
    private Map<RewardPoint, List<RewardPoint>> pointVisitedPointsList = new HashMap<>();
    private int rewards;
    private double costs;
    private double resultProfit;
    private List<RewardPoint> bestProfitPointsList = new ArrayList<>();


    public void start() throws Throwable {
        for (RewardPoint rewardPoint : points) {
            double profit = calculate(rewardPoint.getPoint().getId());
            profitsMap.put(rewardPoint, profit);
            pointVisitedPointsList.put(rewardPoint, visitedPointsList);
        }

        Map.Entry<RewardPoint, Double> rewardPointDoubleEntry = profitsMap.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get();
        bestProfitPointsList = new ArrayList<>(pointVisitedPointsList.get(rewardPointDoubleEntry.getKey()));

        System.out.println(rewardPointDoubleEntry.getKey().getPoint().getId() + " " + rewardPointDoubleEntry.getValue());
        System.out.println();
    }

    private double calculate(int initPointID) throws Throwable {
        currentPoint = new RewardPoint();
        firstPoint = new RewardPoint();
        newPoint = new RewardPoint();
        visitedPointsList = new ArrayList<>();
        rewards = 0;
        costs = 0;
        resultProfit = 0;



        firstPoint = findPointWithId(initPointID);
        currentPoint = firstPoint;
        visitedPointsList.add(firstPoint);
        resultProfit = firstPoint.getReward();
        rewards += firstPoint.getReward();

        do {
            findNextBestNeighbour();

        } while (!currentPoint.equals(firstPoint));

        visitedPointsList.add(currentPoint);
        System.out.println("Profit = " + resultProfit);
        System.out.println("Rewards = " + rewards);
        System.out.println("Costs = " + costs);

        return resultProfit;

    }

    private void findNextBestNeighbour() throws Throwable {
        final double[] maxProfit = {Double.MIN_VALUE};

        DataConverter.loadData().forEach(testingPoint -> NearestNeighbours.this.checkTestingPoint(testingPoint, maxProfit));


        if (maxProfit[0] == Double.MIN_VALUE) {
            costs += distanceBetween(currentPoint, firstPoint) * COST;
            currentPoint = firstPoint;
            resultProfit = rewards - costs;
            return;
        }
        costs += distanceBetween(currentPoint, newPoint) * COST;
        rewards += newPoint.getReward();

        visitedPointsList.add(newPoint);
        currentPoint = newPoint;
    }

    private void checkTestingPoint(RewardPoint testingPoint, double[] maxProfit) {
        double profit = testingPoint.getReward() - distanceBetween(currentPoint, testingPoint) * COST;
        if (!visitedPointsList.contains(testingPoint) && profit > maxProfit[0]) {
            maxProfit[0] = profit;
            newPoint = testingPoint;
        }
    }

    private double distanceBetween(RewardPoint first, RewardPoint second) {
        double xPow = Math.pow(second.getPoint().getX() - first.getPoint().getX(), 2);
        double yPow = Math.pow(second.getPoint().getY() - first.getPoint().getY(), 2);
        return Math.sqrt(xPow + yPow);
    }


    private RewardPoint randPoint() throws Throwable {
        int idPoint = new Random().nextInt(100) + 1;
        return findPointWithId(idPoint);
    }

    private RewardPoint findPointWithId(Integer id) throws Throwable {
        return DataConverter.loadData().stream()
                .filter(rewardPoint -> rewardPoint.getPoint().getId() == id)
                .findFirst()
                .orElseThrow((Supplier<Throwable>) () -> new Exception("There is no point with specified id"));
    }


    public List<RewardPoint> getBestProfitPointsList() {
        return bestProfitPointsList;
    }
}
