package vn.iuh.dao;

import vn.iuh.entity.Account;
import vn.iuh.entity.Job;
import vn.iuh.exception.TableEntityMismatch;
import vn.iuh.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobDAO {
    private final Connection connection;

    public JobDAO() {
        this.connection = DatabaseUtil.getConnect();
    }

    public JobDAO(Connection connection) {
        this.connection = connection;
    }

    public Job createJob(Job job) {
        String query  = "insert into Job(id,status_name, start_time, end_time, room_id) values" +
                "(?, ? , ? , ? , ? )";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, job.getId());
            ps.setString(2, job.getStatusName());
            ps.setTimestamp(3, job.getStartTime());
            ps.setTimestamp(4, job.getEndTime());
            ps.setString(5, job.getRoomdId());

            ps.executeUpdate();
            return job;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
