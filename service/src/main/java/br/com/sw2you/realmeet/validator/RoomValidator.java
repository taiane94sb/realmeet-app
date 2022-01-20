package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.ROOM_NAME;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.ROOM_NAME_MAX_LENGTH;
import static br.com.sw2you.realmeet.validator.ValidatorUtils.*;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {

    public void validate(CreateRoomDTO createRoomDTO) {
        var validatorErrors = new ValidatorErrors();

        // Room Name
        validateRequired(createRoomDTO.getName(), ROOM_NAME, validatorErrors);
        validateMaxLength(createRoomDTO.getName(), ROOM_NAME, ROOM_NAME_MAX_LENGTH, validatorErrors);

        throwOnError(validatorErrors);
    }
}
