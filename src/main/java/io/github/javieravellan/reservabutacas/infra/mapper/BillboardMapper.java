package io.github.javieravellan.reservabutacas.infra.mapper;

import io.github.javieravellan.reservabutacas.domain.BillboardMovieRecord;
import io.github.javieravellan.reservabutacas.domain.BillboardRecord;
import io.github.javieravellan.reservabutacas.infra.entity.Billboard;
import io.github.javieravellan.reservabutacas.infra.entity.BillboardMovie;
import io.github.javieravellan.reservabutacas.infra.entity.Movie;
import io.github.javieravellan.reservabutacas.infra.entity.Room;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BillboardMapper {
    public static BillboardRecord toDto(Billboard billboard) {
        return new BillboardRecord(
                billboard.getId(),
                billboard.getDate(),
                billboard.getStartTime(),
                billboard.getEndTime(),
                billboard.getBillboardMovies().stream()
                        .map(billboardMovie -> new BillboardMovieRecord(
                                billboardMovie.getId(),
                                billboardMovie.getMovie().getId(),
                                billboardMovie.getRoomId(),
                                billboard.getId(),
                                billboardMovie.getShowTime()
                        )).toList()
        );
    }

    public static Billboard toEntity(BillboardRecord billboardRecord) {
        Billboard billboard = new Billboard();
        billboard.setId(billboardRecord.id());
        billboard.setDate(billboardRecord.date());
        billboard.setStartTime(billboardRecord.startTime());
        billboard.setEndTime(billboardRecord.endTime());
        billboard.setBillboardMovies(billboardRecord.billboardMovies().stream()
                .map(bmr -> {
                    var bm = new BillboardMovie();
                    // set movie
                    var movie = new Movie();
                    movie.setId(bmr.movieId());
                    bm.setMovie(movie);
                    // set room
                    var room = new Room();
                    room.setId(bmr.roomId());
                    bm.setRoom(room);
                    bm.setShowTime(bmr.showTime());
                    return bm;
                })
                .toList());

        return billboard;
    }
}
