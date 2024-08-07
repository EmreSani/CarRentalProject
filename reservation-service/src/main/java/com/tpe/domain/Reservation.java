package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="t_reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startReservationDateTime;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endReservationDateTime;

    @Column(nullable = false, unique = true)
    private Boolean reservationStatus;

    @Column(nullable = false)
    private Double pricePerHour;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    @ManyToOne
    private User user;

    @Column(nullable = false)
    @OneToOne
    private Car car;

}
