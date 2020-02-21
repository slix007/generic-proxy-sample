package org.slix.mproxy.receiver;

import lombok.RequiredArgsConstructor;
import org.slix.mproxy.service.KeeperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keeper")
@RequiredArgsConstructor
public class KeeperController {

    private final KeeperService keeperService;

    @PostMapping(value = "/item", consumes = {
            MediaType.ALL_VALUE,
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    private ResponseEntity<String> save(@RequestBody String theItem, ServerHttpRequest request) {

        //noinspection ConstantConditions
        final String ip = request.getRemoteAddress().getAddress().getHostAddress();

        final String idString = keeperService.keepRequestAndForward(theItem, ip);

        return new ResponseEntity<>(idString, HttpStatus.CREATED);
    }

    @GetMapping(value = "/item", consumes = {
            MediaType.ALL_VALUE,
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    private ResponseEntity<String> save() {
        return new ResponseEntity<>("use POST", HttpStatus.OK);
    }

}
