package com.guimizi.challenge.paymentservice.controller;

import com.guimizi.challenge.paymentservice.model.api.ListResponse;
import com.guimizi.challenge.paymentservice.model.api.user.UserStatusResponse;
import com.guimizi.challenge.paymentservice.service.user.UserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserStatusController {

    private UserStatusService userStatusService;

    @Autowired
    public UserStatusController(UserStatusService userStatusService) {
        this.userStatusService = userStatusService;
    }

    @GetMapping("/user")
    public UserStatusResponse getUserStatus(@RequestParam Integer userId) {
        return this.userStatusService.retrieveUserStatus(userId);
    }

    @GetMapping("/users/topdebt")
    public ListResponse<UserStatusResponse> getTopDebtUsers(@RequestParam Integer pageNumber,
                                                            @RequestParam(required = false, defaultValue = "50") Integer pageSize) {
        return this.userStatusService.retrieveTopDebtUsers(pageNumber, pageSize);
    }


}
