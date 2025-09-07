package vn.iuh.servcie;

import vn.iuh.dto.repository.BookingInfo;
import vn.iuh.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {
    List<BookingResponse> getAllBookingInfo();
}
