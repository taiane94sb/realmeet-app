package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.utils.TestDataCreator.newCreateRoomDTO;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.sw2you.realmeet.exception.InvalidRequestException;
import br.com.sw2you.realmeet.validator.RoomValidator;
import br.com.sw2you.realmeet.validator.ValidatorError;
import org.apache.commons.lang3.StringUtils;
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

    @Test
    void testValidateWhenRoomNameExceedsLength() {
        var exception = Assertions.assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateRoomDTO().name(StringUtils.rightPad("X", ROOM_NAME_MAX_LENGTH + 1, "X")))
        );

        assertEquals(1, exception.getValidatorErrors().getNumberOfErrors());
        assertEquals(
            new ValidatorError(ROOM_NAME, ROOM_NAME + EXCEEDS_MAX_LENGTH),
            exception.getValidatorErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenSeatsAreMissing() {
        var exception = Assertions.assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateRoomDTO().seats(null))
        );

        assertEquals(1, exception.getValidatorErrors().getNumberOfErrors());
        assertEquals(new ValidatorError(ROOM_SEATS, ROOM_SEATS + MISSING), exception.getValidatorErrors().getError(0));
    }

    @Test
    void testValidateWhenSeatsAreLeesThenMinValue() {
        var exception = Assertions.assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateRoomDTO().seats(ROOM_SEATS_MIN_VALUE - 1))
        );

        assertEquals(1, exception.getValidatorErrors().getNumberOfErrors());
        assertEquals(
            new ValidatorError(ROOM_SEATS, ROOM_SEATS + BELOW_MIN_VALUE),
            exception.getValidatorErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenSeatsAreGreaterThenMaxValue() {
        var exception = Assertions.assertThrows(
            InvalidRequestException.class,
            () -> victim.validate(newCreateRoomDTO().seats(ROOM_SEATS_MAX_VALUE + 1))
        );

        assertEquals(1, exception.getValidatorErrors().getNumberOfErrors());
        assertEquals(
            new ValidatorError(ROOM_SEATS, ROOM_SEATS + EXCEEDS_MAX_VALUE),
            exception.getValidatorErrors().getError(0)
        );
    }
}
