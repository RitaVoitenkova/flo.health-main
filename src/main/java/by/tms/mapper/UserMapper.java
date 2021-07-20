package by.tms.mapper;

import by.tms.dto.UserRegistrationDto;
import by.tms.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserRegistrationDto dto);
}
