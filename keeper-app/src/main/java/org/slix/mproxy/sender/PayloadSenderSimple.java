package org.slix.mproxy.sender;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("payloadSenderSimple")
@Setter
public class PayloadSenderSimple implements PayloadSender {

    private static final int FAILS_COUNT = 3;
    private static int failsEmulator = 0;

    @Override
    public void sendingForward(Object payload) {
        //just log it
        if (failsEmulator++ < FAILS_COUNT) {
            throw new RuntimeException("Error during sending");
        }
        failsEmulator = 0;
        log.info("Emulate FinalBackend sending: " + payload);
    }
}
