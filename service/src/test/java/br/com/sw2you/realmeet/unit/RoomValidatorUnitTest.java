package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.utils.TestConstants.DEFAULT_ROOM_NAME;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newCreateRoomDTO;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newRoomBuilder;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.core.BaseUnitTest;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.InvalidRequestException;
import br.com.sw2you.realmeet.validator.RoomValidator;
import br.com.sw2you.realmeet.validator.ValidatorError;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class RoomValidatorUnitTest extends BaseUnitTest {
    private RoomValidator victim;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void setupEach() {
        victim = new RoomValidator(roomRepository);
    }

    @Test
    void testValidateWhenRoomIsValid() {
        victim.validate(newCreateRoomDTO());
    }

    @Test
    void testValidateWhenRoomIsMissing() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate((CreateRoomDTO) newCreateRoomDTO().name(null))
        );
        assertEquals(1, exception.getValidatorErrors().getNumberOfErrors());
        assertEquals(new ValidatorError(ROOM_NAME, ROOM_NAME + MISSING), exception.getValidatorErrors().getError(0));
    }

    @Test
    void testValidateWhenRoomNameExceedsLength() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () ->
                victim.validate(
                    (CreateRoomDTO) newCreateRoomDTO().name(StringUtils.rightPad("X", ROOM_NAME_MAX_LENGTH + 1, "X"))
                )
        );

        assertEquals(1, exception.getValidatorErrors().getNumberOfErrors());
        assertEquals(
            new ValidatorError(ROOM_NAME, ROOM_NAME + EXCEEDS_MAX_LENGTH),
            exception.getValidatorErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenRoomSeatsAreMissing() {
        var exception = Assertions.assertThrows(
            InvalidRequestException.class,
            () -> victim.validate((CreateRoomDTO) newCreateRoomDTO().seats(null))
        );

        Assertions.assertEquals(1, exception.getValidatorErrors().getNumberOfErrors());
        Assertions.assertEquals(
            new ValidatorError(ROOM_SEATS, ROOM_SEATS + MISSING),
            exception.getValidatorErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenSeatsAreLessThenMinValue() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate((CreateRoomDTO) newCreateRoomDTO().seats(ROOM_SEATS_MIN_VALUE - 1))
        );

        assertEquals(1, exception.getValidatorErrors().getNumberOfErrors());
        assertEquals(
            new ValidatorError(ROOM_SEATS, ROOM_SEATS + BELOW_MIN_VALUE),
            exception.getValidatorErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenSeatsAreGreaterThenMaxValue() {
        var exception = assertThrows(
            InvalidRequestException.class,
            () -> victim.validate((CreateRoomDTO) newCreateRoomDTO().seats(ROOM_SEATS_MAX_VALUE + 1))
        );

        assertEquals(1, exception.getValidatorErrors().getNumberOfErrors());
        assertEquals(
            new ValidatorError(ROOM_SEATS, ROOM_SEATS + EXCEEDS_MAX_VALUE),
            exception.getValidatorErrors().getError(0)
        );
    }

    @Test
    void testValidateWhenRoomNameIsDuplicate() {
        given(roomRepository.findByNameAndActive(DEFAULT_ROOM_NAME, true))
            .willReturn(Optional.of(newRoomBuilder().build()));

        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO()));
        assertEquals(1, exception.getValidatorErrors().getNumberOfErrors());
        assertEquals(new ValidatorError(ROOM_NAME, ROOM_NAME + DUPLICATE), exception.getValidatorErrors().getError(0));
    }
}
