package vn.iuh.dao;

import vn.iuh.entity.WorkingHistory;
import vn.iuh.exception.TableEntityMismatch;
import vn.iuh.util.DatabaseUtil;

import java.sql.*;

public class WorkingHistoryDAO {
    private final Connection connection;

    public WorkingHistoryDAO() {
        this.connection = DatabaseUtil.getConnect();
    }

    public WorkingHistoryDAO(Connection connection) {
        this.connection = connection;
    }

    public WorkingHistory getWorkingHistoryByID(String id) {
        String query = "SELECT * FROM WorkingHistory WHERE id = ? AND is_deleted = 0";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToWorkingHistory(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (TableEntityMismatch te) {
            System.out.println(te.getMessage());
        }

        return null;
    }

    public WorkingHistory createWorkingHistory(WorkingHistory wh) {
        String query = "INSERT INTO WorkingHistory (id, task_name, create_time, action_description, shift_assignment_id, account_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, wh.getId());
            ps.setString(2, wh.getTaskName());
            ps.setTimestamp(3, wh.getCreateTime() != null ? new Timestamp(wh.getCreateTime().getTime()) : null);
            ps.setString(4, wh.getActionDescription());
            ps.setString(5, wh.getShiftAssignmentId());
            ps.setString(6, wh.getAccountId());

            ps.executeUpdate();
            return wh;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public WorkingHistory updateWorkingHistory(WorkingHistory wh) {
        String query = "UPDATE WorkingHistory SET task_name = ?, create_time = ?, action_description = ?, " +
                "shift_assignment_id = ?, account_id = ?, create_at = ? " +
                "WHERE id = ? AND is_deleted = 0";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, wh.getTaskName());
            ps.setTimestamp(2, wh.getCreateTime() != null ? new Timestamp(wh.getCreateTime().getTime()) : null);
            ps.setString(3, wh.getActionDescription());
            ps.setString(4, wh.getShiftAssignmentId());
            ps.setString(5, wh.getAccountId());
            ps.setTimestamp(6, wh.getCreateAt() != null ? new Timestamp(wh.getCreateAt().getTime()) : null);
            ps.setString(7, wh.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return getWorkingHistoryByID(wh.getId());
            } else {
                System.out.println("No working history found with ID: " + wh.getId());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public boolean deleteWorkingHistoryByID(String id) {
        if (getWorkingHistoryByID(id) == null) {
            System.out.println("No working history found with ID: " + id);
            return false;
        }

        String query = "UPDATE WorkingHistory SET is_deleted = 1 WHERE id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Working history has been deleted successfully");
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private WorkingHistory mapResultSetToWorkingHistory(ResultSet rs) throws SQLException {
        WorkingHistory wh = new WorkingHistory();
        try {
            wh.setId(rs.getString("id"));
            wh.setTaskName(rs.getString("task_name"));
            wh.setCreateTime(rs.getTimestamp("create_time"));
            wh.setActionDescription(rs.getString("action_description"));
            wh.setShiftAssignmentId(rs.getString("shift_assignment_id"));
            wh.setAccountId(rs.getString("account_id"));
            wh.setCreateAt(rs.getTimestamp("create_at"));
            wh.setIsDeleted(rs.getInt("is_deleted"));
            return wh;
        } catch (SQLException e) {
            throw new TableEntityMismatch("Can't map ResultSet to WorkingHistory: " + e);
        }
    }
}

