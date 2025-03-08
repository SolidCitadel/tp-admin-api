package solidcitadel.transitplannermanager.stop;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcStopRepository {
    private final DataSource dataSource;

    public JdbcStopRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Stop> findAll() {
        String sql = "select * from stop";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            List<Stop> stops = new ArrayList<>();

            while(rs.next()) {
                Stop stop = new Stop("");
                stop.setId(rs.getLong("id"));
                stop.setName(rs.getString("name"));
                stops.add(stop);
            }

            return stops;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public Stop save(Stop stop) {
        String sql = "insert into stop(name)\n" +
                "values(?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stop.getName());

            pstmt.executeUpdate();
            System.out.println("Stop add: " + stop);

            return stop;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, null);
        }
    }

    public boolean delete(Long stopId) {
        String sql = "delete from stop where ID = ?;";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, stopId.intValue());

            pstmt.executeUpdate();
            System.out.println("Stop delete: " + stopId);
            return true;
        }
            catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Stop delete fail: " + stopId);
            return false;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, null);
        }
    }

    // DataSource 객체로부터 Connection 객체 획득
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    // 할당한 리소스 해제
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Connection 객체 리소스 해제
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
