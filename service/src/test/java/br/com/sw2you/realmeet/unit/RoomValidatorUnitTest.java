package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.utils.TestDataCreator.newCreateRoomDTO;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.MISSING;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.ROOM_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.sw2you.realmeet.exception.InvalidRequestException;
import br.com.sw2you.realmeet.validator.RoomValidator;
import br.com.sw2you.realmeet.validator.ValidatorError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoomValidatorUnitTest {
    private RoomValidator victim;

    @BeforeEach
    void setupEach() {
        victim = new RoomValidator();
    }

    @Test
    void testValidateWhenRoomIsValid() {
        victim.validate(newCreateRoomDTO());
    }

    @Test
    void testValidateWhenRoomIsMissing() {
        var exception = Assertions.assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateRoomDTO().name(null))
        );
        assertEquals(1, exception.getValidatorErrors().getNumberOfErrors());
        assertEquals(new ValidatorError(ROOM_NAME, ROOM_NAME + MISSING), exception.getValidatorErrors().getError(0));
    }
}
