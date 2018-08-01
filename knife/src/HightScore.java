public class HightScore {
    private String name;
    private int score;
    private int appleScore;

    public HightScore(String name, int score, int appleScore) {
        this.name = name;
        this.score = score;
        this.appleScore = appleScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAppleScore() {
        return appleScore;
    }

    public void setAppleScore(int appleScore) {
        this.appleScore = appleScore;
    }
}
