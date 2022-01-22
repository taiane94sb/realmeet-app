package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {
    private final RoomRepository roomRepository;

    public RoomValidator(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void validate(CreateRoomDTO createRoomDTO) {
        var validatorErrors = new ValidatorErrors();

        if (
            validateName(createRoomDTO.getName(), validatorErrors) &&
            validateSeats(createRoomDTO.getSeats(), validatorErrors)
        ) {
            validateNameDuplicate(createRoomDTO.getName(), validatorErrors);
        }
        throwOnError(validatorErrors);
    }

    private boolean validateName(String name, ValidatorErrors validatorErrors) {
        return (
            validateRequired(name, ROOM_NAME, validatorErrors) &&
            validateMaxLength(name, ROOM_NAME, ROOM_NAME_MAX_LENGTH, validatorErrors)
        );
    }

    private boolean validateSeats(Integer seats, ValidatorErrors validatorErrors) {
        return (
            validateRequired(seats, ROOM_SEATS, validatorErrors) &&
            validateMaxValue(seats, ROOM_SEATS, ROOM_SEATS_MAX_VALUE, validatorErrors) &&
            validateMinValue(seats, ROOM_SEATS, ROOM_SEATS_MIN_VALUE, validatorErrors)
        );
    }

    private void validateNameDuplicate(String name, ValidatorErrors validatorErrors) {
        roomRepository
            .findByNameAndActive(name, true)
            .ifPresent(__ -> validatorErrors.add(ROOM_NAME, ROOM_NAME + DUPLICATE));
    }
}
