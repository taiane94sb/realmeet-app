package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.InvalidRequestException;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {
    private final RoomRepository roomRepository;

    public RoomValidator(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void validate(CreateRoomDTO createRoomDTO) {
        var validatorErrors = new ValidatorErrors();

        // Room Name
        validateRequired(createRoomDTO.getName(), ROOM_NAME, validatorErrors);
        validateMaxLength(createRoomDTO.getName(), ROOM_NAME, ROOM_NAME_MAX_LENGTH, validatorErrors);

        // Room Seats
        validateRequired(createRoomDTO.getSeats(), ROOM_SEATS, validatorErrors);
        validateMaxValue(createRoomDTO.getSeats(), ROOM_SEATS, ROOM_SEATS_MAX_VALUE, validatorErrors);
        validateMinValue(createRoomDTO.getSeats(), ROOM_SEATS, ROOM_SEATS_MIN_VALUE, validatorErrors);

        throwOnError(validatorErrors);

        validateNameDuplicate(createRoomDTO.getName());
    }

    private void validateNameDuplicate(String name) {
        roomRepository
            .findByNameAndActive(name, true)
            .ifPresent(
                __ -> {
                    throw new InvalidRequestException(new ValidatorError(ROOM_NAME, ROOM_NAME + DUPLICATE));
                }
            );
    }
}
