package com.ex.ticket.group.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ex.ticket.common.util.UserUtils;

@RestController
@RequiredArgsConstructor
public class GroupViewController {

    private final UserUtils userUtils;

    @GetMapping("/api/group/{groupId}/home")
    public ModelAndView groupHomeByGroupId(
            @PathVariable(name = "groupId") String groupId
    ){
        ModelAndView modelAndView = new ModelAndView("group/home");

        modelAndView.addObject("role", userUtils.getCurrentUser().getAccountRole().getValue());
        modelAndView.addObject("groupId", groupId);
        return modelAndView;
    }

    @GetMapping("/api/group/{groupId}/manager")
    public ModelAndView managerGroupUser(
        @PathVariable(name = "groupId") String groupId
    ){
        ModelAndView modelAndView = new ModelAndView("group/member");

        modelAndView.addObject("role", userUtils.getCurrentUser().getAccountRole().getValue());
        modelAndView.addObject("groupId", groupId);
        return modelAndView;
    }

    @GetMapping("/api/group/{groupId}/event")
    public ModelAndView manageEvent(
            @PathVariable(name = "groupId") String groupId
    ){
        ModelAndView modelAndView = new ModelAndView("group/event/list");
        modelAndView.addObject("role", userUtils.getCurrentUser().getAccountRole().getValue());
        modelAndView.addObject("groupId", groupId);
        return modelAndView;
    }

    @GetMapping("/api/group/{groupId}/event/{eventId}/detail")
    public ModelAndView manageEventDetail(
            @PathVariable(name = "groupId") String groupId,
            @PathVariable(name = "eventId") String eventId
    ){
        ModelAndView modelAndView = new ModelAndView("group/event/detail");

        modelAndView.addObject("role", userUtils.getCurrentUser().getAccountRole().getValue());
        modelAndView.addObject("groupId", groupId);
        modelAndView.addObject("eventId", eventId);
        return modelAndView;
    }

}
