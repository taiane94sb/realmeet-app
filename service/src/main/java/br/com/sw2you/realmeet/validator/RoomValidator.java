package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import java.util.Objects;
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
            validateNameDuplicate(null, createRoomDTO.getName(), validatorErrors);
        }
        throwOnError(validatorErrors);
    }

    public void validate(Long roomId, UpdateRoomDTO updateRoomDTO) {
        var validatorErrors = new ValidatorErrors();

        if (
            validateRequired(roomId, ROOM_ID, validatorErrors) &&
            validateName(updateRoomDTO.getName(), validatorErrors) &&
            validateSeats(updateRoomDTO.getSeats(), validatorErrors)
        ) {
            validateNameDuplicate(roomId, updateRoomDTO.getName(), validatorErrors);
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

    private void validateNameDuplicate(Long roomIdToExclude, String name, ValidatorErrors validatorErrors) {
        roomRepository
            .findByNameAndActive(name, true)
            .ifPresent(
                room -> {
                    if (!Objects.isNull(roomIdToExclude) && !Objects.equals(room.getId(), roomIdToExclude)) {
                        validatorErrors.add(ROOM_NAME, ROOM_NAME + DUPLICATE);
                    }
                }
            );
    }
}
