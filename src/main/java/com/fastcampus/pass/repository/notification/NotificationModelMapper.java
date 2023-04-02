package com.fastcampus.pass.repository.notification;

import com.fastcampus.pass.repository.booking.BookingEntity;
import com.fastcampus.pass.util.LocalDatetimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

// ReportPolicy.IGNORE : 일치하지 않는 필드는 무시한다.
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationModelMapper {
    NotificationModelMapper INSTANCE = Mappers.getMapper(NotificationModelMapper.class);

    // 필드명이 같지 않거나 custom하게 매핑해 주기 위해서는 @Mapping을 추가해 주면 된다.
    @Mapping(target = "uuid", source = "bookingEntity.userEntity.uuid")
    @Mapping(target = "text", source = "bookingEntity.startedAt", qualifiedByName = "text")
    NotificationEntity toNotificationEntity(BookingEntity bookingEntity, NotificationEvent event);

    // 알람 보낼 메시지 생상
    @Named("text")
    default String text(LocalDateTime startedAt) {
        return String.format("안녕하세요. %s 수업 시작합니다. 수업 전 출석 체크 부탁드립니다. \uD380\uDE0A"
                , LocalDatetimeUtils.format(startedAt));
    }
}
