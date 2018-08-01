import java.sql.*;
import java.util.ArrayList;

public class SQLiteJDBC{
    Connection conn;
    Statement stat;

    public SQLiteJDBC() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:test.db");

        stat =conn.createStatement();
    }

    public void createTable() throws SQLException {
        String sql ="CREATE TABLE IF NOT EXISTS HightScore" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAMEPlay VARCHAR(50) NOT NULL, " +
                " SCORE INT NOT NULL," +
                "APPLESCORE INT NOT NULL)" ;
        stat.executeUpdate(sql);
    }

    public void insertScore(String name, int score, int appScore) throws SQLException {
        String sql = "INSERT INTO HightScore(NAMEPlay, SCORE, APPLESCORE) VALUES (\'" + name + "\',"+ score +", "+ appScore +");";
        stat.executeUpdate(sql);

    }

    public void updateScore(int id, String name, int score, int appScore) throws SQLException {
        String sql = "UPDATE HightScore SET NAMEPlay = \'" + name + "\', SCORE = " + score + ", APPLESCORE = " + appScore +" WHERE ID = " + id +";" ;
          stat.executeUpdate(sql);
    }

    public int queryMinScore() throws SQLException {
        int idMin = 0;
        String sql = "SELECT * FROM HightScore ORDER BY SCORE ASC LIMIT 1;";
        ResultSet rs = stat.executeQuery(sql);
        while (rs.next()){
            idMin = rs.getInt(1);
        }

        return idMin;
    }

    public int queryMaxScore(){
        int score = 0;
        String sql = "SELECT * FROM HightScore ORDER BY DESC LIMIT 1;";
        ResultSet rs = null;
        try {
            rs = stat.executeQuery(sql);
            if (rs == null) return -1;
            while (rs.next()){
                score = rs.getInt(3);
            }

        } catch (SQLException e) {

        }

        return score;
    }

    public ArrayList<HightScore> queryTop5() throws SQLException {
        ArrayList top5 = new ArrayList();


        String sql = "SELECT * FROM HightScore ORDER BY SCORE DESC LIMIT 5;";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()){
            String name = rs.getString(2);
            int score = rs.getInt(3);
            int appScore = rs.getInt(4);
            HightScore hightScore = new HightScore(name, score, appScore);
            top5.add(hightScore);

        }

        return top5;
    }



    public void query(String sql) throws SQLException {
        stat.executeQuery(sql);

    }

    public void close(){

        try {
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
