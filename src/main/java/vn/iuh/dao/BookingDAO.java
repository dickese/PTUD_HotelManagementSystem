package vn.iuh.dao;

import vn.iuh.dto.repository.BookingInfo;
import vn.iuh.dto.repository.RoomInfo;
import vn.iuh.exception.TableEntityMismatch;
import vn.iuh.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private final Connection connection;

    public BookingDAO() {
        this.connection = DatabaseUtil.getConnect();
    }

    public List<RoomInfo> findAllRoomInfo() {
        String query = "SELECT r.id, r.room_name, r.room_status, rc.room_type, rc.number_customer" +
                       ", rlp.updated_daily_price, rlp.updated_hourly_price" +
                       " FROM Room r" +
                       " JOIN RoomCategory rc ON rc.id = r.room_category_id" +
                       " JOIN RoomListPrice rlp ON rlp.room_category_id = rc.id" +
                       " ORDER BY rlp.create_at DESC, r.id ASC";
        List<RoomInfo> rooms = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            var rs = ps.executeQuery();
            while (rs.next())
                rooms.add(mapResultSetToRoomInfo(rs));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (TableEntityMismatch mismatchException) {
            System.out.println(mismatchException.getMessage());
        }

        return rooms;
    }

    public List<BookingInfo> findAllBookingInfo(List<String> nonAvailableRoomIds) {
        if (nonAvailableRoomIds.isEmpty())
            return new ArrayList<>();

        StringBuilder query = new StringBuilder(
                "SELECT r.id, c.customer_name, rrd.time_in, rrd.time_out" +
                " FROM Room r" +
                " JOIN RoomReservationDetail rrd ON r.id = rrd.room_id" +
                " JOIN ReservationForm rf ON rf.id = rrd.reservation_form_id" +
                " JOIN Customer c ON c.id = rf.customer_id" +
                " WHERE r.id IN (");

        for (int i = 0; i < nonAvailableRoomIds.size(); i++) {
            query.append("?");
            if (i < nonAvailableRoomIds.size() - 1) {
                query.append(", ");
            }
        }
        query.append(")");

        List<BookingInfo> bookings = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(query.toString());

            for (int i = 0; i < nonAvailableRoomIds.size(); i++) {
                ps.setString(i + 1, nonAvailableRoomIds.get(i));
            }

            var rs = ps.executeQuery();

            while (rs.next())
                bookings.add(mapResultSetToBookingInfo(rs));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (TableEntityMismatch mismatchException) {
            System.out.println(mismatchException.getMessage());
        }

        return bookings;
    }

    public boolean updateRoomStatus(String roomId, String newStatus) {
        String query = "UPDATE Room SET room_status = ? WHERE id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, newStatus);
            ps.setString(2, roomId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private RoomInfo mapResultSetToRoomInfo(ResultSet rs) {
        try {
            return new RoomInfo(
                    rs.getString("id"),
                    rs.getString("room_name"),
                    rs.getString("room_status"),
                    rs.getString("room_type"),
                    rs.getString("number_customer"),
                    rs.getDouble("updated_daily_price"),
                    rs.getDouble("updated_hourly_price")
            );
        } catch (SQLException e) {
            throw new TableEntityMismatch("Can`t map ResultSet to RoomInfo" + e.getMessage());
        }
    }

    private BookingInfo mapResultSetToBookingInfo(ResultSet rs) throws SQLException {
        return new BookingInfo(
                rs.getString("id"),
                rs.getString("customer_name"),
                rs.getDate("time_in"),
                rs.getDate("time_out")
        );
    }
}
