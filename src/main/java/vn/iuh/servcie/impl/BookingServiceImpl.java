package vn.iuh.servcie.impl;

import vn.iuh.constraint.RoomStatus;
import vn.iuh.dao.BookingDAO;
import vn.iuh.dao.RoomDAO;
import vn.iuh.dto.repository.BookingInfo;
import vn.iuh.dto.repository.RoomInfo;
import vn.iuh.dto.response.BookingResponse;
import vn.iuh.entity.Room;
import vn.iuh.servcie.BookingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookingServiceImpl implements BookingService {
    private final BookingDAO bookingDAO;

    public BookingServiceImpl() {
        this.bookingDAO = new BookingDAO();
    }

    @Override
    public List<BookingResponse> getAllBookingInfo() {
        // Get All Room Info
        List<RoomInfo> roomInfos = bookingDAO.findAllRoomInfo();

        // Get All non-available Room Ids
        List<String> nonAvailableRoomIds = new ArrayList<>();

        // Create BookingResponse each Room info
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (RoomInfo roomInfo : roomInfos) {
            if (Objects.equals(roomInfo.getRoomStatus(), RoomStatus.ROOM_BOOKED_STATUS.getStatus()) ||
                Objects.equals(roomInfo.getRoomStatus(), RoomStatus.ROOM_USING_STATUS.getStatus())) {

                nonAvailableRoomIds.add(roomInfo.getId());
            }

            bookingResponses.add(createBookingResponse(roomInfo));
        }

        // Find all Booking Info for non-available rooms
        List<BookingInfo> bookingInfos = bookingDAO.findAllBookingInfo(nonAvailableRoomIds);

        // Update BookingResponse with Booking Info
        for (BookingInfo bookingInfo : bookingInfos) {
            BookingResponse bookingResponse = bookingResponses.get(bookingResponses.indexOf(bookingInfo.getRoomId()));
            if (bookingResponse != null) {
                bookingResponse.updateBookingInfo(
                        bookingInfo.getCustomerName(),
                        bookingInfo.getTimeIn(),
                        bookingInfo.getTimeOut()
                );
            }
        }

        return bookingResponses;
    }

    private BookingResponse createBookingResponse(RoomInfo roomInfo) {
        return new BookingResponse(
                roomInfo.getId(),
                roomInfo.getRoomName(),
                roomInfo.getRoomStatus(),
                roomInfo.getRoomType(),
                roomInfo.getNumberOfCustomers(),
                roomInfo.getDailyPrice(),
                roomInfo.getHourlyPrice()
        );
    }

}
