package com.demo.cars.mapper;

import com.demo.cars.dto.PaymentDto;
import com.demo.cars.entity.Payment;
import com.demo.cars.model.payment.PaymentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", imports = {java.sql.Timestamp.class, java.time.Instant.class})
public interface PaymentMapper {
    PaymentRequest dtoToRequest(PaymentDto dto);

    @Mapping(target = "userId", source = "user.id")
    PaymentDto entityToDto(Payment entity);

    @Mapping(target = "userId", source = "user.id")
    List<PaymentDto> entityToDto(List<Payment> payments);

    @Mapping(target = "paymentDate", expression = "java(Timestamp.from(Instant.now()))")
    @Mapping(target = "user.id", source = "userId")
    Payment dtoToEntity(PaymentDto dto);

    @Mapping(target = "paymentDate", expression = "java(Timestamp.from(Instant.now()))")
    @Mapping(target = "user.id", source = "userId")
    Payment requestToEntity(PaymentRequest request);

}
