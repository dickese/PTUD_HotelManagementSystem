package vn.iuh.servcie.impl;

import vn.iuh.constraint.EntityIDSymbol;
import vn.iuh.constraint.RoomStatus;
import vn.iuh.dao.BookingDAO;
import vn.iuh.dto.event.create.BookingCreationEvent;
import vn.iuh.dto.event.create.RoomFilter;
import vn.iuh.dto.repository.BookingInfo;
import vn.iuh.dto.repository.RoomInfo;
import vn.iuh.dto.response.BookingResponse;
import vn.iuh.entity.*;
import vn.iuh.servcie.BookingService;
import vn.iuh.util.EntityUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookingServiceImpl implements BookingService {
    private final BookingDAO bookingDAO;

    public BookingServiceImpl() {
        this.bookingDAO = new BookingDAO();
    }

    @Override
    public boolean createBooking(BookingCreationEvent bookingCreationEvent) {
        // TODO find customer by CCCD or Phone number
        Customer customer = null;

        bookingDAO.enableTransaction();
        try {
            // 1. Create ReservationFormEntity & insert to DB
            ReservationForm reservationForm = createReservationFormEntity(bookingCreationEvent, null);
            bookingDAO.insertReservationForm(reservationForm);

            // 2. Create RoomReservationDetail Entity & insert to DB
            List<RoomReservationDetail> roomReservationDetails = new ArrayList<>();
            for (String roomId : bookingCreationEvent.getRoomIds())
                roomReservationDetails.add(
                        createRoomReservationDetailEntity(bookingCreationEvent, roomId, reservationForm.getId()));

            bookingDAO.insertRoomReservationDetail(reservationForm, roomReservationDetails);

            // 3. Create HistoryCheckInEntity & insert to DB
            List<HistoryCheckIn> historyCheckIns = new ArrayList<>();
            for (RoomReservationDetail roomReservationDetail : roomReservationDetails) {
                historyCheckIns.add(createHistoryCheckInEntity(roomReservationDetail));
            }

            bookingDAO.insertHistoryCheckIn(reservationForm, historyCheckIns);

            // 4. Create RoomUsageServiceEntity & insert to DB
            List<RoomUsageService> roomUsageServices = new ArrayList<>();
            for (String serviceId : bookingCreationEvent.getServiceIds())
                roomUsageServices.add(
                        createRoomUsageServiceEntity(bookingCreationEvent, serviceId, reservationForm.getId()));

            bookingDAO.insertRoomUsageService(reservationForm, roomUsageServices);

            // 5. Update Room Status
            for (String roomId : bookingCreationEvent.getRoomIds())
                bookingDAO.updateRoomStatus(roomId, RoomStatus.ROOM_CHECKING_STATUS.getStatus());

        } catch (Exception e) {
            System.out.println("Lỗi khi đặt phòng: " + e.getMessage());
            bookingDAO.rollbackTransaction();
            bookingDAO.disableTransaction();
            return false;
        }

        bookingDAO.commitTransaction();
        bookingDAO.disableTransaction();
        return true;
    }

    @Override
    public List<BookingResponse> getAllEmptyRooms() {
        List<RoomInfo> RoomInfos = bookingDAO.findAllEmptyRooms();

        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (RoomInfo roomInfo : RoomInfos) {
            bookingResponses.add(createBookingResponse(roomInfo));
        }

        return bookingResponses;
    }

    @Override
    public List<BookingResponse> getRoomsByFilter(RoomFilter roomFilter) {
        return bookingDAO.findRoomsByFilter(roomFilter);
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
                Objects.equals(roomInfo.getRoomStatus(), RoomStatus.ROOM_CHECKING_STATUS.getStatus()) ||
                Objects.equals(roomInfo.getRoomStatus(), RoomStatus.ROOM_USING_STATUS.getStatus()) ||
                Objects.equals(roomInfo.getRoomStatus(), RoomStatus.ROOM_CHECKOUT_LATE_STATUS.getStatus())
            ) {

                nonAvailableRoomIds.add(roomInfo.getId());
            }

            bookingResponses.add(createBookingResponse(roomInfo));
        }

        // Find all Booking Info for non-available rooms
        List<BookingInfo> bookingInfos = bookingDAO.findAllBookingInfo(nonAvailableRoomIds);

        // Update BookingResponse with Booking Info
        for (BookingInfo bookingInfo : bookingInfos) {
            for (BookingResponse bookingResponse : bookingResponses) {
                if (Objects.equals(bookingResponse.getRoomId(), bookingInfo.getRoomId())) {
                    bookingResponse.updateBookingInfo(
                            bookingInfo.getCustomerName(),
                            bookingInfo.getTimeIn(),
                            bookingInfo.getTimeOut()
                    );
                }
            }
        }

        return bookingResponses;
    }

    private ReservationForm createReservationFormEntity(BookingCreationEvent bookingCreationEvent, String customerId) {
        String id;
        String prefix = EntityIDSymbol.RESERVATION_FORM_PREFIX.getPrefix();
        int numberLength = EntityIDSymbol.RESERVATION_FORM_PREFIX.getLength();

        ReservationForm lastedReservationForm = bookingDAO.findLastReservationForm();
        if (lastedReservationForm == null) {
            id = EntityUtil.increaseEntityID(null, prefix, numberLength);
        } else {
            id = EntityUtil.increaseEntityID(lastedReservationForm.getId(), prefix, numberLength);
        }

        return new ReservationForm(
                id,
                bookingCreationEvent.getReserveDate(),
                bookingCreationEvent.getNote(),
                bookingCreationEvent.getCheckInDate(),
                bookingCreationEvent.getCheckOutDate(),
                bookingCreationEvent.getInitialPrice(),
                bookingCreationEvent.getDepositPrice(),
                bookingCreationEvent.isAdvanced(),
                customerId,
                bookingCreationEvent.getShiftAssignmentId(),
                bookingCreationEvent.getCreateAt(),
                bookingCreationEvent.getIsDeleTed()
        );
    }

    private RoomReservationDetail createRoomReservationDetailEntity(BookingCreationEvent bookingCreationEvent,
                                                                    String roomId, String reservationFormId) {
        String id;
        String prefix = EntityIDSymbol.ROOM_RESERVATION_DETAIL_PREFIX.getPrefix();
        int numberLength = EntityIDSymbol.ROOM_RESERVATION_DETAIL_PREFIX.getLength();

        RoomReservationDetail lastedReservationDetail = bookingDAO.findLastRoomReservationDetail();
        if (lastedReservationDetail == null) {
            id = EntityUtil.increaseEntityID(null, prefix, numberLength);
        } else {
            id = EntityUtil.increaseEntityID(lastedReservationDetail.getId(), prefix, numberLength);
        }


        return new RoomReservationDetail(
                id,
                bookingCreationEvent.getCheckInDate(),
                bookingCreationEvent.getCheckOutDate(),
                null,
                roomId,
                reservationFormId,
                bookingCreationEvent.getShiftAssignmentId()
        );
    }

    private RoomUsageService createRoomUsageServiceEntity(BookingCreationEvent bookingCreationEvent, String serviceId,
                                                          String reservationFormId) {
        String id;
        String prefix = EntityIDSymbol.ROOM_USAGE_SERVICE_PREFIX.getPrefix();
        int numberLength = EntityIDSymbol.ROOM_USAGE_SERVICE_PREFIX.getLength();

        RoomUsageService lastedRoomUsageService = bookingDAO.findLastRoomUsageService();
        if (lastedRoomUsageService == null) {
            id = EntityUtil.increaseEntityID(null, prefix, numberLength);
        } else {
            id = EntityUtil.increaseEntityID(lastedRoomUsageService.getId(), prefix, numberLength);
        }

        return new RoomUsageService(
                id,
                10,
                1,
                Date.valueOf(java.time.LocalDate.now()),
                serviceId,
                reservationFormId,
                bookingCreationEvent.getShiftAssignmentId()
        );
    }

    private HistoryCheckIn createHistoryCheckInEntity(RoomReservationDetail roomReservationDetail) {
        String id;
        String prefix = EntityIDSymbol.HISTORY_CHECKIN_PREFIX.getPrefix();
        int numberLength = EntityIDSymbol.HISTORY_CHECKIN_PREFIX.getLength();

        HistoryCheckIn lastedHistoryCheckIn = bookingDAO.findLastHistoryCheckIn();
        if (lastedHistoryCheckIn == null) {
            id = EntityUtil.increaseEntityID(null, prefix, numberLength);
        } else {
            id = EntityUtil.increaseEntityID(lastedHistoryCheckIn.getId(), prefix, numberLength);
        }

        return new HistoryCheckIn(
                id,
                roomReservationDetail.getTimeIn(),
                true,
                roomReservationDetail.getId()
        );
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
