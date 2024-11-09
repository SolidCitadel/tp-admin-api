package solidcitadel.transitplannermanager.domain.transport.direction;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import solidcitadel.transitplannermanager.domain.transport.Time;
import solidcitadel.transitplannermanager.domain.transport.stop.Stop;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcDirectionRepository {
    private final DataSource dataSource;

    public JdbcDirectionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Direction> findAll() {
        String sql = """
                select direction.id, t.name, s1.name, s2.name, requiredTime, fare, s1.ID, s2.ID
                from direction
                    inner join testdb.transporttype t on direction.transportTypeID = t.ID
                    inner join testdb.stop s1 on direction.departureStopID = s1.ID
                    inner join testdb.stop s2 on direction.destinationStopID = s2.ID;""";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            List<Direction> directions = new ArrayList<>();

            while(rs.next()) {
                directions.add(getDirection(rs));
            }

            return directions;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public Direction findById(Long directionId){
        String sql = """
                select direction.id, t.name, s1.name, s2.name, requiredTime, fare, s1.ID, s2.ID
                from direction
                    inner join testdb.transporttype t on direction.transportTypeID = t.ID
                    inner join testdb.stop s1 on direction.departureStopID = s1.ID
                    inner join testdb.stop s2 on direction.destinationStopID = s2.ID
                where direction.ID = ?;""";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, directionId.toString());

            rs = pstmt.executeQuery();

            return getDirection(rs);

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Direction getDirection(ResultSet rs) throws SQLException {
        Direction direction = new Direction();
        direction.setId(rs.getLong("direction.id"));
        direction.setType(rs.getString("t.name"));
        direction.setFare(rs.getInt("fare"));

        Stop departure = new Stop();
        departure.setId(rs.getLong("s1.ID"));
        departure.setName(rs.getString("s1.name"));
        direction.setDeparture(departure);

        Stop destination = new Stop();
        destination.setId(rs.getLong("s2.ID"));
        destination.setName(rs.getString("s2.name"));
        direction.setDestination(destination);

        LocalTime localTime = rs.getTime("requiredTime").toLocalTime();
        Time time = new Time(localTime.getHour(), localTime.getMinute());
        direction.setRequiredTime(time);

        return direction;
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
