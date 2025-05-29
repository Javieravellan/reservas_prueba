package io.github.javieravellan.reservabutacas.infra.mapper;

import io.github.javieravellan.reservabutacas.domain.*;
import io.github.javieravellan.reservabutacas.infra.entity.Billboard;
import io.github.javieravellan.reservabutacas.infra.entity.BillboardMovie;
import io.github.javieravellan.reservabutacas.infra.entity.Movie;
import io.github.javieravellan.reservabutacas.infra.entity.Room;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BillboardMapper {
    public static BillboardRecord toDto(Billboard billboard) {
        return new BillboardRecord(
                billboard.getId(),
                billboard.getDate(),
                billboard.isStatus(),
                billboard.getStartTime(),
                billboard.getEndTime(),
                billboard.getBillboardMovies().stream()
                        .map(billboardMovie -> new BillboardMovieRecord(
                                billboardMovie.getId(),
                                new MovieShort(billboardMovie.getMovie().getId(), billboardMovie.getMovie().getName(), billboardMovie.getMovie().getGenre()),
                                new RoomRecord(billboardMovie.getRoomId(), billboardMovie.getRoomName(), billboardMovie.getRoom().getNumber(),
                                        billboardMovie.getRoom().getSeats().stream()
                                                .map(seat -> new SeatRecord(
                                                        seat.getId(),
                                                        seat.getNumber(),
                                                        seat.getRowNumber(),
                                                        seat.isStatus(),
                                                        billboardMovie.getRoomId(),
                                                        billboardMovie.getRoomName()
                                                )).toList()
                                ),
                                billboard.getId(),
                                billboardMovie.getShowTime()
                        )).toList()
        );
    }

    public static Billboard toEntity(BillboardRecord billboardRecord) {
        Billboard billboard = new Billboard();
        billboard.setId(billboardRecord.id());
        billboard.setDate(billboardRecord.date());
        billboard.setStatus(billboardRecord.status());
        billboard.setStartTime(billboardRecord.startTime());
        billboard.setEndTime(billboardRecord.endTime());
        if (billboardRecord.billboardMovies() != null) {
            billboard.setBillboardMovies(billboardRecord.billboardMovies().stream()
                    .map(bmr -> {
                        var bm = new BillboardMovie();
                        // set movie
                        var movie = new Movie();
                        movie.setId(bmr.movie().id());
                        bm.setMovie(movie);
                        // set room
                        var room = new Room();
                        room.setId(bmr.room().id());
                        bm.setRoom(room);
                        bm.setShowTime(bmr.showTime());
                        bm.setStatus(true);
                        bm.setBillboard(billboard);
                        return bm;
                    })
                    .collect(Collectors.toList()));
        }
        return billboard;
    }

    public static void partialUpdate(Billboard billboard, BillboardRecord billboardRecord) {
        if (billboardRecord.id() != null) {
            billboard.setId(billboardRecord.id());
        }
        if (billboardRecord.date() != null) {
            billboard.setDate(billboardRecord.date());
        }
        if (billboardRecord.status() != billboard.isStatus()) {
            billboard.setStatus(billboardRecord.status());
        }
        if (billboardRecord.startTime() != billboard.getStartTime()) {
            billboard.setStartTime(billboardRecord.startTime());
        }
        if (billboardRecord.endTime() != billboard.getEndTime()) {
            billboard.setEndTime(billboardRecord.endTime());
        }
        if (billboardRecord.billboardMovies() != null) {
            billboard.setBillboardMovies(billboardRecord.billboardMovies().stream()
                    .map(bmr -> {
                        var bm = new BillboardMovie();
                        // set movie
                        var movie = new Movie();
                        movie.setId(bmr.movie().id());
                        bm.setMovie(movie);
                        // set room
                        var room = new Room();
                        room.setId(bmr.room().id());
                        bm.setRoom(room);
                        bm.setShowTime(bmr.showTime());
                        bm.setBillboard(billboard);
                        bm.setStatus(true);
                        return bm;
                    })
                    .collect(Collectors.toList()));
        }
    }
}
