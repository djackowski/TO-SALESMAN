package datas;

public class RewardPoint {
    private Point point;
    private Integer reward;

    public RewardPoint() {

    }

    public RewardPoint(Point point, Integer reward) {
        this.point = point;
        this.reward = reward;
    }

    public Point getPoint() {
        return point;
    }

    public Integer getReward() {
        return reward;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RewardPoint that = (RewardPoint) o;

        if (point != null ? !point.equals(that.point) : that.point != null) return false;
        return reward != null ? reward.equals(that.reward) : that.reward == null;
    }

    @Override
    public int hashCode() {
        int result = point != null ? point.hashCode() : 0;
        result = 31 * result + (reward != null ? reward.hashCode() : 0);
        return result;
    }
}
